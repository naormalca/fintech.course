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
		String funcName = "getCVCValue";
		byte[] blockA = new byte[8];
		byte[] blockB = new byte[8];
		byte[] cvv = new byte[digits];
		int indexCvv = 0;
		//split to two blocks
		blockA = Arrays.copyOfRange(data, 0, 8);
		blockB = Arrays.copyOfRange(data, 8, 16);
		
		Util.print_log(funcName, Util.byteArrayToHex(blockA));
		Util.print_log(funcName, Util.byteArrayToHex(blockB));

		
		//phase 4:encrypt blockA with key1
		byte[] phase4Result = null;
		try {
			phase4Result = Util.encryptDES(key1, blockA, true);
			Util.print_log(funcName, "Phase 4: "+Arrays.toString(blockB));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}
		//phase 5:XOR phase 4 result with blockB
		byte[] xorResult = Util.xorBytes(phase4Result, blockB);
		Util.print_log(funcName, "xorResult: "+Util.byteArrayToHex(xorResult));
		//phase 5: encrpyt xor result with keyA
		byte[] phase5Result = null;
		try {
			phase5Result = Util.encrypt3DES(key1, key2, xorResult, true);
			Util.print_log(funcName, "Phase5: "+Util.byteArrayToHex(phase5Result));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		cvv = Util.extrcatCvv(phase5Result, digits);
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
		byte[] cvv = new byte[digits];
		int index = 0;
		int indexCvv = 0;
		String funcName = "getCVCValue";
		
		//first insert the pan to blockA
		for (int i = 0; i < pan.length; i += 2, index++)
			tempBlock[index] = Util.convertTwoBytesToOne(pan, i);
		//insert expiry and sc to blockB
		for (int i = 0; i < expiry.length; i++, index++)
			tempBlock[index] = expiry[i];
		for (int i = 0; i < serviceCode.length; i++, index++)
			tempBlock[index] = serviceCode[i];
		//split to two blocks
		blockA = Arrays.copyOfRange(tempBlock, 0, 8);
		blockB = Arrays.copyOfRange(tempBlock, 8, 16);
		
		Util.print_log(funcName, Util.byteArrayToHex(blockA));
		Util.print_log(funcName, Util.byteArrayToHex(blockB));
		
		//phase 4:encrypt blockA with key1
		byte[] phase4Result = null;
		try {
			phase4Result = Util.encryptDES(key1, blockA, true);
			Util.print_log(funcName, Arrays.toString(blockB));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}
		//phase 5:XOR phase 4 result with blockB
		byte[] xorResult = Util.xorBytes(phase4Result, blockB);
		Util.print_log(funcName, Util.byteArrayToHex(xorResult));
		//phase 5: encrpyt xor result with keyA
		byte[] phase5Result = null;
		try {
			phase5Result = Util.encrypt3DES(key1, key2, xorResult, true);
			Util.print_log(funcName, Util.byteArrayToHex(phase5Result));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		cvv = Util.extrcatCvv(phase5Result, digits);
		return cvv;
	}


	@Override
	public boolean checkCVCValue(byte[] data, byte[] key1, byte[] key2, byte[] cvcValue) {
		byte[] compre = getCVCValue(data, key1, key2, cvcValue.length);
		
		if (compre.equals(cvcValue)) {
			return true;
		}
		return false;
	}

}
