package fintech;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
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
		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		//ensure key is 8 length
		key = Arrays.copyOfRange(key, 0, 8);
		//create key
		SecretKey sKey = (SecretKey) new SecretKeySpec(key, "DES");
		if (toEncrypt)
			cipher.init(Cipher.ENCRYPT_MODE, sKey, new IvParameterSpec(new byte[8]));
		else
			cipher.init(Cipher.DECRYPT_MODE, sKey, new IvParameterSpec(new byte[8]));
		return cipher.doFinal(data);
	}
	public static byte[] mergeKeys(byte[] key1, byte[] key2, int newKeyLength) {
		String funcName = "mergeKeys";
		byte[] newKey = new byte[newKeyLength];
		print_log(funcName, "Key1: "+byteArrayToHex(key1));
		print_log(funcName, "Key2: "+byteArrayToHex(key2));
		for (int i = 0; i < newKeyLength; i++) {
			//fill one key of 24 with 2 keys of 8
			if (i < key1.length)
				newKey[i] = key1[i];
			else if (i >= key1.length && i < key1.length + key2.length) {
				newKey[i] = key2[i - key2.length];
			}
			else
				newKey[i] = key1[i - key2.length - key1.length];
		}
		print_log(funcName,"Merged Key:"+ byteArrayToHex((newKey)));
		return newKey;
	}
	public static byte[] mergeKeys_x(byte[] key1, byte[] key2, int newKeyLength) {
		String funcName = "mergeKeys_x";
		byte[] newKey = new byte[newKeyLength];
		key1 = convertOneByteToTwoArray(key1);
		key2 = convertOneByteToTwoArray(key2);
		print_log(funcName, "Key1: "+byteArrayToHex(key1));
		print_log(funcName, "Key2: "+byteArrayToHex(key2));
		for (int i = 0; i < newKeyLength; i++) {
			//fill one key of 24 with 2 keys of 8
			if (i < key1.length)
				newKey[i] = key1[i];
			else if (i >= key1.length && i < key1.length + key2.length) {
				newKey[i] = key2[i - key2.length];
			}
			else
				newKey[i] = key1[i - key2.length - key1.length];
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
		int b1 = Byte.toUnsignedInt(pan[index]);
		int b2 = Byte.toUnsignedInt(pan[index+1]);
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
		print_log(funcName, byteArrayToHex(data));
		byte[] ret = new byte[data.length * 2];
		for (int i = 0, index = 0; i < data.length; i++, index += 2) {
			byte[] temp = convertOneByteToTwo(data[i]);
			ret[index] = temp[0];
			ret[index+1] = temp[1];
		}
		print_log(funcName, byteArrayToHex(ret));
		return ret;
	}
	public static void print_log(String unit, String msg) {
		System.out.println("["+unit+"] "+msg);
	}
	public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b));
		   return sb.toString();
		}
	public static byte[] extractCvv(byte[] data, int digits) {
		byte[] cvv = new byte[digits];
		int indexCvv = 0;
		//extract the the three left most digit between 0-9
		for (int i = 0; i < data.length && indexCvv < digits; i++) {
			if (data[i] <= 9 && data[i] >= 0) {
				cvv[indexCvv] = data[i];
				indexCvv++;
			}
		}
		if (indexCvv != 3) {
			//extract the cvv from bytes that higher than 0x09
				for (int i = 0; i < data.length && indexCvv < digits; i++) {
					if (data[i] > 0x09) {
						cvv[indexCvv] = (byte) (data[i] - 10);
						indexCvv++;
					}
				}
			}
		return cvv;
	}
}
