// package xyz.lp.mq.broker.utils;
//
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.io.RandomAccessFile;
// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;
// import java.nio.ByteBuffer;
// import java.nio.MappedByteBuffer;
// import java.nio.channels.FileChannel;
// import java.security.AccessController;
// import java.security.PrivilegedAction;
// import java.util.Scanner;
// import java.util.concurrent.TimeUnit;
//
// import sun.nio.ch.FileChannelImpl;
//
// /**
//  * @Desciption: mmap 工具类
//  *
//  * 支持映射文件的指定 position
//  * 支持从指定 position 读/写
//  * 释放内存映射
//  */
// public class MMapUtil {
//
//     private File file;
//     private FileChannel fileChannel;
//     // 直接内存，jvm gc 管不到
//     private MappedByteBuffer mappedByteBuffer;
//
//     public void loadFileInMMap(String filePath, int pos, int size) throws IOException {
//         this.file = new File(filePath);
//         if (!this.file.exists()) {
//             throw new FileNotFoundException("file not found: " + filePath);
//         }
//         this.fileChannel = new RandomAccessFile(file, "rw").getChannel();
//         this.mappedByteBuffer = this.fileChannel.map(FileChannel.MapMode.READ_WRITE, pos, size);
//     }
//
//     public byte[] readContent(int pos, int size) {
//         this.mappedByteBuffer.position(pos);
//         byte[] bytes = new byte[size];
//         this.mappedByteBuffer.get(bytes);
//         return bytes;
//     }
//
//     public void writeContent(byte[] bytes) {
//         writeContent(bytes, false);
//     }
//
//     public void writeContent(byte[] bytes, boolean force) {
//         this.mappedByteBuffer.put(bytes);
//         if (force) {
//             this.mappedByteBuffer.force();
//         }
//     }
//
//     // 不推荐使用
//     // 不推荐的原因是因为使用了sun包下不稳定的代码
//     public void clear() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//         // // 在关闭资源时执行以下代码释放内存
//         // Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
//         // m.setAccessible(true);
//         // m.invoke(FileChannelImpl.class, mappedByteBuffer);
//     }
//
//     public void clean() {
//         if (mappedByteBuffer == null || !mappedByteBuffer.isDirect() || mappedByteBuffer.capacity() == 0)
//             return;
//         invoke(invoke(viewed(mappedByteBuffer), "cleaner"), "clean");
//     }
//
//     private Object invoke(final Object target, final String methodName, final Class<?>... args) {
//         return AccessController.doPrivileged(new PrivilegedAction<Object>() {
//             public Object run() {
//                 try {
//                     Method method = method(target, methodName, args);
//                     method.setAccessible(true);
//                     return method.invoke(target);
//                 } catch (Exception e) {
//                     throw new IllegalStateException(e);
//                 }
//             }
//         });
//     }
//
//     private Method method(Object target, String methodName, Class<?>[] args)
//             throws NoSuchMethodException {
//         try {
//             return target.getClass().getMethod(methodName, args);
//         } catch (NoSuchMethodException e) {
//             return target.getClass().getDeclaredMethod(methodName, args);
//         }
//     }
//
//     private ByteBuffer viewed(ByteBuffer buffer) {
//         String methodName = "viewedBuffer";
//         Method[] methods = buffer.getClass().getMethods();
//         for (int i = 0; i < methods.length; i++) {
//             if (methods[i].getName().equals("attachment")) {
//                 methodName = "attachment";
//                 break;
//             }
//         }
//
//         ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
//         if (viewedBuffer == null)
//             return buffer;
//         else
//             return viewed(viewedBuffer);
//     }
//
//     /**
//      * 使用 arthas https://arthas.aliyun.com/ 观察直接内存的释放
//      */
//     public static void main(String[] args) {
//         MMapUtil mmapUtil = new MMapUtil();
//         Scanner scanner = new Scanner(System.in);
//         while (true) {
//             System.out.println("input size (exit to exit):");
//             String input = scanner.nextLine();
//             if ("exit".equals(input)) {
//                 return;
//             }
//             try {
//                 int size = Integer.parseInt(input);
//                 mmapUtil.loadFileInMMap("/Users/ruifeng/bmox/mq-java/broker/store/topic/order/000", 0, size);
//                 // byte[] bytes = mmapUtil.readContent(0, 10);
//                 // mmapUtil.writeContent(bytes);
//                 Thread.sleep(TimeUnit.SECONDS.toMillis(5));
//                 mmapUtil.clean();
//             } catch (IOException | InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
//
// }
