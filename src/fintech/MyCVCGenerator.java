package fintech;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MyCVCGenerator implements CVCGenerator {

	@Override
	public byte[] getCVCValue(byte[] data, byte[] key1, byte[] key2, int digits) {
		byte[] blockA = new byte[8];
		byte[] blockB = new byte[8];
		byte[] cvv = new byte[3];
		int index = 0;
		int indexCvv = 0;
		//2 blocks
		blockA = Arrays.copyOfRange(data, 0, 8);
		blockB = Arrays.copyOfRange(data, 8, 16);
		System.out.println("Block A: "+Arrays.toString(blockA));
		System.out.println("Block B: "+Arrays.toString(blockB));
		byte[] phase4Result = null;
		try {
			phase4Result = Util.encryptDES(key1, blockA, true);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}
		//phase 5:XOR phase 4 result with blockB
		byte[] xorResult = Util.xorBytes(phase4Result, blockB);
		//phase 5: encrpyt xor result with keyA
		byte[] phase5Result = null;
		try {
			phase5Result = Util.encrypt3DES(key1, key2, xorResult, true);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		//extract the the three left most digit between 0-9
		for (int i = 0; i < phase5Result.length; i++) {
			if (phase5Result[i] <= 0x09 && phase5Result[i] >= 0x00) {
				cvv[indexCvv] = phase5Result[i];
				indexCvv++;
			}
		}
		return cvv;
	}

	/**
	 * @param pan - 13-19 length
	 * @param expiry - 4 length (04/21)
	 * @param serviceCode - 3 length 
	 */
	@Override
	public byte[] getCVCValue(byte[] pan, byte[] expiry, byte[] serviceCode, byte[] key1, byte[] key2, int digits) {
		byte[] tempBlock = new byte[16];
		byte[] blockA = new byte[8];
		byte[] blockB = new byte[8];
		byte[] cvv = new byte[3];
		int index = 0;
		int indexCvv = 0;
		//first insert the pan to blockA
		for (int i = 0; i < pan.length; i += 2, index++) {
			tempBlock[index] = Util.convertTwoBytesToOne(pan, i);
		}
		//insert expiry and sc
		for (int i = 0; i < expiry.length; i++, index++) {
			tempBlock[index] = expiry[i];
		}
		for (int i = 0; i < serviceCode.length; i++, index++) {
			tempBlock[index] = serviceCode[i];
		}
		//2 blocks
		blockA = Arrays.copyOfRange(tempBlock, 0, 8);
		blockB = Arrays.copyOfRange(tempBlock, 8, 16);
		System.out.println("Block A: "+Arrays.toString(blockA));
		System.out.println("Block B: "+Arrays.toString(blockB));
		//phase 4:encrypt blockA with key1
		byte[] phase4Result = null;
		try {
			phase4Result = Util.encryptDES(key1, blockA, true);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}
		//phase 5:XOR phase 4 result with blockB
		byte[] xorResult = Util.xorBytes(phase4Result, blockB);
		//phase 5: encrpyt xor result with keyA
		byte[] phase5Result = null;
		try {
			phase5Result = Util.encrypt3DES(key1, key2, xorResult, true);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		//extract the the three left most digit between 0-9
		for (int i = 0; i < phase5Result.length; i++) {
			if (phase5Result[i] <= 0x09 && phase5Result[i] >= 0x00) {
				cvv[indexCvv] = phase5Result[i];
				indexCvv++;
			}
		}
		return cvv;
	}


	@Override
	public boolean checkCVCValue(byte[] data, byte[] key1, byte[] key2, byte[] cvcValue) {
		// TODO Auto-generated method stub
		return false;
	}

}
