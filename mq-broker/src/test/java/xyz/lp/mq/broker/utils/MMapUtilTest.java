package xyz.lp.mq.broker.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MMapUtilTest {

    private MMapUtil mMapUtil;
    public static final String filePath = "/Users/ruifeng/bmox/mq-java/broker/store/topic/order/000";

    @Before
    public void setup() throws IOException {
        mMapUtil = new MMapUtil();
        mMapUtil.loadFileInMMap(filePath, 0, 1 * 1024 * 1024);
    }

    @Test
    public void testLoadFileInMMap() throws IOException {
        // mMapUtil.loadFileInMMap(filePath, 0, 1 * 1024 * 1024);
    }

    @Test
    public void testWriteContent() {
        String str = "hello world";
        byte[] writeContent = str.getBytes();
        mMapUtil.writeContent(writeContent);
        byte[] readContent = mMapUtil.readContent(0, writeContent.length);
        System.out.println(new String(readContent));
    }

}