package org.hit.fintech2018.malca.Assignment3;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ISO8583Serializer mRun;
        mRun = new MyISO8583Serializer();
        Map<Integer, byte[]> data = new LinkedHashMap<>();
        data.put(2, new byte[]{
                0x04, 0x05, 0x08, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x01});
        data.put(8, new byte[]{0x01, 0x02, 0x03});
        data.put(9, new byte[]{0x04, 0x05, 0x06, 0x07});
        data.put(18, new byte[]{0x08, 0x09, 0x00, 0x01});
        data.put(49, new byte[]{0x02, 0x03, 0x04,});
        data.put(1, new byte[]{
                1, 1, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0});
        byte[] s = mRun.serializeISO8583(1, data);
        Helpers.byteArrayToHex(s);
    }
}
