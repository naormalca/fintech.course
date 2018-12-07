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
		Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
		//create key
		byte[] newKey = mergeKeys(key1, key2, 24);		
		SecretKey sKey = (SecretKey) new SecretKeySpec(newKey,"DESede");
		if (toEncrypt)
			cipher.init(Cipher.ENCRYPT_MODE, sKey, new IvParameterSpec(new byte[8]));
		else
			cipher.init(Cipher.DECRYPT_MODE, sKey, new IvParameterSpec(new byte[8]));
		return cipher.doFinal(data);
	}
	public static byte[] encryptDES(byte[] key, byte[] data, boolean toEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		//ensure key is 8 length
		key = Arrays.copyOfRange(key, 0, 8);
		//create key
		SecretKey sKey = (SecretKey) new SecretKeySpec(key, "DES");
		if (toEncrypt)
			cipher.init(Cipher.ENCRYPT_MODE, sKey);
		else
			cipher.init(Cipher.DECRYPT_MODE, sKey);
		return cipher.doFinal(data);
	}
	public static byte[] mergeKeys(byte[] key1, byte[] key2, int newKeyLength) {
		byte[] newKey = new byte[newKeyLength];
		System.out.println("key1:" + Arrays.toString(key1));
		System.out.println("key2:" + Arrays.toString(key2));
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
		System.out.println("merged keys:" + Arrays.toString(newKey));
		return newKey;
	}
	
	public static byte[] xorBytes(byte[] first, byte[] second) {
		byte[] xorResult = new byte[first.length];
		for (int i = 0; i < first.length; i++) {
			xorResult[i] = (byte) (first[i] ^ second[i]);
		}
		return xorResult;
	}
	public static byte convertTwoBytesToOne(byte[] pan, int index) {
		byte product = (byte) ((pan[index] << 4) | (pan[index+1] & 0x0F));
		return product;
	}
	/**
	 * the function for testing the "convertTwoBytesToOne" function
	 * @param data a byte to convert
	 * @return original bytes
	 */
	public static byte[] convertOneByteToTwo(byte data) {
		byte[] ret = new byte[2];
		ret[0] = (byte) ((data & 0xF0) >> 4);
		ret[1] = (byte) (data & 0x0F);
		System.out.println(Arrays.toString(ret));
		return ret;
	}
}
