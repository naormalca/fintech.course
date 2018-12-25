package org.hit.fintech2018.malca;

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
		String funcName = "getCVCValue";
		byte[] blockA = new byte[8];
		byte[] blockB = new byte[8];
		//split to two blocks
		blockA = Arrays.copyOfRange(data, 0, 8);
		blockB = Arrays.copyOfRange(data, 8, 16);
		
		Util.print_log(funcName, Util.byteArrayToHex(blockA));
		Util.print_log(funcName, Util.byteArrayToHex(blockB));

		//phase 4:encrypt blockA with key1
		byte[] phase4Result = null;
		byte[] phase5Result = null;
		try {
			phase4Result = Util.encryptDES(key1, blockA, true);
			Util.print_log(funcName, "Phase 4: "+Arrays.toString(blockB));
			//phase 5:XOR phase 4 result with blockB
			byte[] xorResult = Util.xorBytes(phase4Result, blockB);
			Util.print_log(funcName, "xorResult: "+Util.byteArrayToHex(xorResult));
			//phase 5: encrpyt xor result with keyA
			phase5Result = Util.encrypt3DES(key1, key2, xorResult, true);
			Util.print_log(funcName, "Phase5: "+Util.byteArrayToHex(phase5Result));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}
		return Util.extractCvv(phase5Result, digits);
	}

	/**
	 * @param pan - 13-19 length
	 * @param expiry - 4 length (04/21)
	 * @param serviceCode - 3 length 
	 */
	@Override
	public byte[] getCVCValue(byte[] pan, byte[] expiry, byte[] serviceCode, byte[] key1, byte[] key2, int digits) {
		byte[] tempBlock = new byte[16];
		int index = 0;
	
		//first insert the pan to blockA
		for (int i = 0; i < pan.length; i += 2, index++)
			tempBlock[index] = Util.convertTwoBytesToOne(pan, i);
		//insert expiry and sc to blockB
		for (int i = 0; i < expiry.length; i++, index++)
			tempBlock[index] = expiry[i];
		for (int i = 0; i < serviceCode.length; i++, index++)
			tempBlock[index] = serviceCode[i];
		//split to two blocks
		return getCVCValue(tempBlock, key1, key2, digits);
	}

	@Override
	public boolean checkCVCValue(byte[] data, byte[] key1, byte[] key2, byte[] cvcValue) {
		byte[] compre = getCVCValue(data, key1, key2, cvcValue.length);
		return Arrays.equals(compre, cvcValue);
	}

}
