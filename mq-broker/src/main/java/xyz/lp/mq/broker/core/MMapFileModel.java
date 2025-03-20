package xyz.lp.mq.broker.core;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.model.CommitLogModel;
import xyz.lp.mq.broker.model.CommitLogMsgModel;
import xyz.lp.mq.broker.model.TopicModel;
import xyz.lp.mq.broker.utils.CommitLogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;

public class MMapFileModel {

    private File file;
    private FileChannel fileChannel;
    // 直接内存，jvm gc 管不到
    private MappedByteBuffer mappedByteBuffer;
    private String topicName;

    public void loadFileInMMap(String topicName, int pos, int size) throws IOException {
        this.topicName = topicName;
        String filePath = getLatestCommitLogFilePath(topicName);
        this.file = new File(filePath);
        if (!this.file.exists()) {
            throw new FileNotFoundException("file not found: " + filePath);
        }
        this.fileChannel = new RandomAccessFile(file, "rw").getChannel();
        this.mappedByteBuffer = this.fileChannel.map(FileChannel.MapMode.READ_WRITE, pos, size);
    }

    private String getLatestCommitLogFilePath(String topicName) {
        TopicModel topicModel = CommonCache.getTopicModelMap().get(topicName);
        if (Objects.isNull(topicModel)) {
            throw new RuntimeException("topic not found: " + topicName);
        }
        String latestCommitLogFilePath;
        CommitLogModel latestCommitLog = topicModel.getLatestCommitLog();
        if (latestCommitLog.isFull()) {
            latestCommitLogFilePath = this.createNewCommitLogFile(topicName, latestCommitLog);
        } else {
            latestCommitLogFilePath = CommitLogUtil.buildCommitLogFilePath(BrokerConstants.MQ_HOME, topicName, latestCommitLog.getFileName());
        }
        return latestCommitLogFilePath;
    }

    private String createNewCommitLogFile(String topicName, CommitLogModel latestCommitLog) {
        String newCommitLogFileName = CommitLogUtil.buildNextCommitLogFileName(latestCommitLog.getFileName());
        String filePath = CommitLogUtil.buildCommitLogFilePath(BrokerConstants.MQ_HOME, topicName, newCommitLogFileName);
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }

    public byte[] readContent(int pos, int size) {
        this.mappedByteBuffer.position(pos);
        byte[] bytes = new byte[size];
        this.mappedByteBuffer.get(bytes);
        return bytes;
    }

    public void writeContent(CommitLogMsgModel commitLogMsg) {
        writeContent(commitLogMsg, false);
    }

    public void writeContent(CommitLogMsgModel commitLogMsg, boolean force) {
        // 写满需要新建文件并 map
        // 封装 raw data
        // offset manager
        // - 线程安全
        //   - AtomicLong，顺序无法保证
        //   - 加锁
        // 定时刷盘
        byte[] bytes = commitLogMsg.toBytes();
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
