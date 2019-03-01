package org.hit.fintech2018.malca.Assignment3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class Helpers {
    /**
     * this function convert two bytes to one byte when the values of the two bytes
     * is between 0x00 to 0x0F
     *
     * @param pan
     * @param index current index of @param pan
     * @return product
     */
    public static byte convertTwoBytesToOne(byte[] pan, int index) {
        byte product = (byte) ((pan[index] << 4) | (pan[index + 1] & 0x0F));
        return product;
    }

    public static byte[] convertTwoBytesToOneArray(byte[] data) {
        //TODO:padding left
        byte[] ret = new byte[data.length / 2];
        for (int i = 0, index = 0; i < ret.length; i++, index += 2)
            ret[i] = convertTwoBytesToOne(data, index);
        return ret;
    }

    public static byte[] intToByteArray(int value) {
        return BigInteger.valueOf(value).toByteArray();
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x ", b));
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static byte[] leftPadding(byte[] data, int newLength, char paddingCharacter) {
        if (newLength == data.length)
            return data;
        byte[] ret = new byte[newLength];

        for (int i = 0; i < newLength - data.length; i++)
            ret[i] = (byte) (paddingCharacter - '0');
        System.arraycopy(data, 0, ret, newLength - data.length, data.length);
        return ret;
    }

    public static byte[] rightPadding(byte[] data, int newLength, char paddingCharacter) {
        if (newLength == data.length)
            return data;

        byte[] ret = new byte[newLength];
        System.arraycopy(data, 0, ret, 0, data.length);
        for (int i = data.length; i < newLength; i++) {
            ret[i] = (byte) paddingCharacter;
        }

        return ret;
    }

    public static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    public static byte[] binaryToHex(byte[] data) throws Exception {

        if (data.length % 4 != 0)
            throw new Exception("Invalid length");
        byte[] arrayToReturn = new byte[data.length / 4];

        StringBuilder sb = new StringBuilder();
        for (byte datum : data)
            sb.append(datum);
        String binaryStr = sb.toString();

        for (int i = 0; i < data.length / 4; i++) {
            String current4bit = binaryStr.substring(i * 4, i * 4 + 4);
            int decimal = Integer.parseInt(current4bit, 2);
            String hexStr = Integer.toString(decimal, 16).toUpperCase();
            arrayToReturn[i] = (byte) hexStr.charAt(0);
        }

        return arrayToReturn;

    }

    public static byte[] byteArraysConcat(byte[]... arrays) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] arr : arrays)
            outputStream.write(arr);
        return outputStream.toByteArray();

    }
}

