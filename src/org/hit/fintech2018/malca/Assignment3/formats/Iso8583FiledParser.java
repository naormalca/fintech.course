package org.hit.fintech2018.malca.Assignment3.formats;

import java.util.Arrays;

import static org.hit.fintech2018.malca.Assignment3.Helpers.*;

public class Iso8583FiledParser {
    public static byte[] parseISO8583Filed(byte[] data, int pos) throws Exception {
        //classify the filed
        Iso8583Field filed = Iso8583Field.valueOf("F" + pos);
        System.out.println(filed);
        //encoding
        byte[] encodedFiled = encode(filed, data);
        System.out.println("After encoding");
        byteArrayToHex(encodedFiled);
        return encodedFiled;
    }

    private static byte[] encode(Iso8583Field filed, byte[] data) throws Exception {
        System.out.println("Before encoding");
        byteArrayToHex(data);
        switch (filed.getType()) {
            case VAR:
                return LCHAREncoding(filed, data);
            case BITMAP:
                return BitmapEncoding(filed, data);
            case AMOUNT:
                return AmountEncoding(filed, data);
            case NUM:
                return LNUMERICEncoding(filed, data);
            default:
                return null;
        }
    }

    private static byte[] BitmapEncoding(Iso8583Field filed, byte[] data) throws Exception {
        if (data.length != 64)
            throw new NumberFormatException("Bitmap length not valid");
        //convert to hex and pack
        return convertTwoBytesToOneArray(binaryToHex(data));
    }

    /**
     * length = 8
     * input:  {01,02,03}
     * =fixed=
     * packed: {01,23}
     * complete the length with left padding
     * output: {00 00 01 23}
     * <p>
     * =not fixed=
     * packed: {01,23}
     * add the length prefix
     * output: {02 01 23}
     */
    private static byte[] LNUMERICEncoding(Iso8583Field filed, byte[] data) {
        //pack and left padding up to the max length
        byte[] ret;
        if (filed.isFixed()) {
            ret = leftPadding(data, filed.getLength(), '0');
            ret = convertTwoBytesToOneArray(ret);
        } else {
            if((data.length % 2) != 0 )
                data = leftPadding(data, data.length + 1, '0');
            byte[] packed = convertTwoBytesToOneArray(data);
            byte[] lengthPrefix = intToByteArray(data.length);

            ret = new byte[packed.length + lengthPrefix.length];
            System.arraycopy(lengthPrefix, 0, ret, 0, lengthPrefix.length);
            System.arraycopy(packed, 0, ret, lengthPrefix.length, packed.length);
        }
        return ret;

    }
    /**
     * length = 8
     * input:  {01,02,03}
     * =fixed=
     * packed: {01,23}
     * complete the length with right padding
     * output: {01 23 00 00}
     * <p>
     * =not fixed=
     * packed: {01,23}
     * add the length prefix
     * output: {02 01 23}
     */
    private static byte[] LCHAREncoding(Iso8583Field filed, byte[] data) {
        byte[] ret;
        if (filed.isFixed()) {
            ret = rightPadding(data, filed.getLength(), ' ');
            ret = convertTwoBytesToOneArray(ret);
        } else {
            //TODO:TEST IT!!!!!!!!!!!
            if((data.length % 2) != 0 )
                data = rightPadding(data, data.length + 1, ' ');
            byte[] packed = convertTwoBytesToOneArray(data);
            byte[] lengthPrefix = intToByteArray(data.length);

            ret = new byte[packed.length + lengthPrefix.length];
            System.arraycopy(lengthPrefix, 0, ret, 0, lengthPrefix.length);
            System.arraycopy(packed, 0, ret, lengthPrefix.length, packed.length);
        }
        return ret;
    }

    //   ****This function has been written by Yair Uriel****
    private static byte[] AmountEncoding(Iso8583Field filed, byte[] data) throws Exception {
        if (data.length < 3)
            throw new Exception("src array of amount is too short, maybe currency code is missing.");

        // Extract values from input:
        byte[] currencyCode = Arrays.copyOfRange(data, 0, 2);
        byte[] amountOfMoneyRepresentedInBits = Arrays.copyOfRange(data, 3, data.length);

        // Padding the extracted data to fit standard rules:
        currencyCode = leftPadding(currencyCode, 4, '0');
        if (amountOfMoneyRepresentedInBits.length % 2 != 0)
            amountOfMoneyRepresentedInBits = leftPadding(amountOfMoneyRepresentedInBits, amountOfMoneyRepresentedInBits.length + 1, '0');

        // Data concatenation:
        byte[] arrayToReturn = byteArraysConcat(currencyCode, amountOfMoneyRepresentedInBits);

        if (arrayToReturn.length > filed.getLength())
            throw new Exception("Value of amount of money have to many bits.");

        // Bits packing and return.
        return convertTwoBytesToOneArray(arrayToReturn);
    }
}

