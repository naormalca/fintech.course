package org.hit.fintech2018.malca;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Util {

	public static byte[] encrypt3DES(byte[] key1, byte[] key2, byte[] data, boolean toEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
		//create key
		byte[] newKey = mergeKeys(key1, key2, 24);		
		SecretKey sKey = (SecretKey) new SecretKeySpec(newKey,"DESede");
		if (toEncrypt)
			cipher.init(Cipher.ENCRYPT_MODE, sKey);
		else
			cipher.init(Cipher.DECRYPT_MODE, sKey);
		return cipher.doFinal(data);
	}
	
	public static byte[] encryptDES(byte[] key, byte[] data, boolean toEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		//create key
		SecretKey sKey = (SecretKey) new SecretKeySpec(key, "DES");
		if (toEncrypt)
			cipher.init(Cipher.ENCRYPT_MODE, sKey);
		else
			cipher.init(Cipher.DECRYPT_MODE, sKey);
		return cipher.doFinal(data);
	}
	
	public static byte[] mergeKeys(byte[] key1, byte[] key2, int newKeyLength) {
		String funcName = "mergeKeys";
		byte[] newKey = new byte[newKeyLength];
		try {
			newKey = byteArraysConcat(key1, key2, key1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		print_log(funcName,"Merged Key:"+ byteArrayToHex((newKey)));
		return newKey;
	}
		
	public static byte[] xorBytes(byte[] first, byte[] second) {
		//warp the data from 8 to 16 bytes
		byte[] warpFirst = convertOneByteToTwoArray(first);
		byte[] warpSecond = convertOneByteToTwoArray(second);
		byte[] warpXorResult = new byte[warpFirst.length];
		for (int i = 0; i < warpFirst.length; i++) {
			warpXorResult[i] = (byte) (warpFirst[i] ^ warpSecond[i]);
		}
		//pack the data from 16 to 8 bytes
		byte[] packXorResult = convertTwoBytesToOneArray(warpXorResult);
		return packXorResult;
	}
	
	/**
	 * this function convert two bytes to one byte when the values of the two bytes
	 * is between 0x00 to 0x0F
	 * @param pan 
	 * @param index current index of @param pan
	 * @return product
	 */
	public static byte convertTwoBytesToOne(byte[] pan, int index) {
		byte product = (byte) ((pan[index] << 4) | (pan[index+1] & 0x0F));
		return product;
	}
	
	public static byte[] convertTwoBytesToOneArray(byte[] data) {
		byte[] ret = new byte[data.length / 2];
		for (int i = 0, index = 0; i < ret.length; i++, index +=2)
			ret[i] = convertTwoBytesToOne(data, index);
		return ret;
	}
	
	/**
	 * this function is for testing the "convertTwoBytesToOne" function
	 * @param data a byte to convert
	 * @return original bytes
	 */
	public static byte[] convertOneByteToTwo(byte data) {
		byte[] ret = new byte[2];
		ret[0] = (byte) ((data & 0xF0) >> 4);
		ret[1] = (byte) (data & 0x0F);
		return ret;
	}

	/**
	 * Receive array of bytes and 
	 * @param data
	 * @return
	 */
	public static byte[] convertOneByteToTwoArray(byte[] data) {
		String funcName = "convertOneByteToTwoArray";
		print_log(funcName, "Input: "+byteArrayToHex(data));
		byte[] ret = new byte[data.length * 2];
		for (int i = 0, index = 0; i < data.length; i++, index += 2) {
			byte[] temp = convertOneByteToTwo(data[i]);
			ret[index] = temp[0];
			ret[index+1] = temp[1];
		}
		print_log(funcName, "Output: "+byteArrayToHex(ret));
		return ret;
	}
	
	public static void print_log(String unit, String msg) {
		System.out.println("["+unit+"] "+msg);
	}
	
	public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x ", b));
		   return sb.toString();
	}

	public static byte[] extractCvv(byte[] data, int digits) {
		int indexCvv = 0;
		byte[] cvv = new byte[digits];
		data = Util.convertOneByteToTwoArray(data);
		//extract the the three left most digit between 0-9
		for (int i = 0; i < data.length && indexCvv < digits; i++) {
			if (data[i] <= 9 && data[i] >= 0) {
				cvv[indexCvv] = data[i];
				indexCvv++;
			}
		}
		if (indexCvv != digits) {
			//extract the cvv from bytes that higher than 0x09
				for (int i = 0; i < data.length && indexCvv < digits; i++) {
					if (data[i] > 9) {
						cvv[indexCvv] = (byte) (data[i] - 10);
						indexCvv++;
					}
				}
			}
		System.out.println(byteArrayToHex(cvv));
		return cvv;
	}
	
	public static byte[] rightPadding(byte[] source, int newLength) {
        return Arrays.copyOf(source, newLength);
    }
	
	public static byte[] byteArraysConcat(byte[]...arrays)   throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] arr : arrays)
            outputStream.write(arr);
        return outputStream.toByteArray();

    }
	/*
	 * Also padding to left
	 */
	public static byte[] packData(byte[] data) {
		//extend and padding
		data = rightPadding(data, 32);
		//pack
		data = convertTwoBytesToOneArray(data);
		return data;
	}

	
}
