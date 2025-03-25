package xyz.lp.mq.broker.utils;

public class ByteConvertUtil {

    public static byte[] intToBytes(int value) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (byte) (value >>> (i * 8));
        }
        return result;
    }

    public static byte[] intToBytes(int... values) {
        byte[] result = new byte[values.length * 4];
        int index = 0;
        for (int value : values) {
            for (int i = 0; i < 4; i++) {
                result[index++] = (byte) (value >>> (i * 8));
            }
        }
        return result;
    }

    public static int bytesToInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result |= (bytes[i] & 0xFF) << (i * 8);
        }
        return result;
    }

}
