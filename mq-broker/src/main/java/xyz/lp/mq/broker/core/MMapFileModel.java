package xyz.lp.mq.broker.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class MMapFileModel {

    private File file;
    private FileChannel fileChannel;
    // 直接内存，jvm gc 管不到
    private MappedByteBuffer mappedByteBuffer;

    public void loadFileInMMap(String filePath, int pos, int size) throws IOException {
        this.file = new File(filePath);
        if (!this.file.exists()) {
            throw new FileNotFoundException("file not found: " + filePath);
        }
        this.fileChannel = new RandomAccessFile(file, "rw").getChannel();
        this.mappedByteBuffer = this.fileChannel.map(FileChannel.MapMode.READ_WRITE, pos, size);
    }

    public byte[] readContent(int pos, int size) {
        this.mappedByteBuffer.position(pos);
        byte[] bytes = new byte[size];
        this.mappedByteBuffer.get(bytes);
        return bytes;
    }

    public void writeContent(byte[] bytes) {
        writeContent(bytes, false);
    }

    public void writeContent(byte[] bytes, boolean force) {
        this.mappedByteBuffer.put(bytes);
        if (force) {
            this.mappedByteBuffer.force();
        }
    }

    public void clean() {
        if (mappedByteBuffer == null || !mappedByteBuffer.isDirect() || mappedByteBuffer.capacity() == 0)
            return;
        invoke(invoke(viewed(mappedByteBuffer), "cleaner"), "clean");
    }

    private Object invoke(final Object target, final String methodName, final Class<?>... args) {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = method(target, methodName, args);
                    method.setAccessible(true);
                    return method.invoke(target);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    private Method method(Object target, String methodName, Class<?>[] args)
            throws NoSuchMethodException {
        try {
            return target.getClass().getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return target.getClass().getDeclaredMethod(methodName, args);
        }
    }

    private ByteBuffer viewed(ByteBuffer buffer) {
        String methodName = "viewedBuffer";
        Method[] methods = buffer.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("attachment")) {
                methodName = "attachment";
                break;
            }
        }

        ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
        if (viewedBuffer == null)
            return buffer;
        else
            return viewed(viewedBuffer);
    }

}
