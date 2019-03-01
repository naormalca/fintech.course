package org.hit.fintech2018.malca.Assignment3;

import org.hit.fintech2018.malca.Assignment3.formats.Iso8583FiledParser;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyISO8583Serializer implements ISO8583Serializer {
    @Override
    public byte[] serializeISO8583(int version, Map<Integer, byte[]> data) {
        //1.retrieve the the data elements filed from bitmap
        List<Integer> dataElements = getDataElementsFromBitmap(data.get(1));
        //2.get all data elements and serialized them onto bytes stream
        ByteArrayOutputStream serializedStream = new ByteArrayOutputStream();
        if (version == 1) {
            try{
                //Insert MTID (In my assumption, we support only iso8583-1993 and the others files is constant)
                //so it will be always 01 00 - 1983 Authorization message, request from acquirer.
                serializedStream.write(new byte[]{0x01, 0x00});
                //data elements
                for (int pos : dataElements) {
                    byte[] serializedFiled = Iso8583FiledParser.parseISO8583Filed(data.get(pos), pos);
                    serializedStream.write(serializedFiled);
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("RETURN NULL");
                return null;
            }
        } else{
            System.out.println("Only ISO8583 version 1 (=1993) supported");
            return null;
        }
        return serializedStream.toByteArray();
    }

    /**
     * this function gets all data elements from the bitmap
     * @param bitmap
     * @return list of data elements
     */
    private List<Integer> getDataElementsFromBitmap(byte[] bitmap) {
        List<Integer> elements = new ArrayList<>();
        for (int i = 0; i < bitmap.length; i++) {
            if (bitmap[i] == 0x01) elements.add(i + 1);
        }
        return elements;
    }
}
