package org.hit.fintech2018.malca;

import java.io.IOException;
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
		// split to two blocks
		blockA = Arrays.copyOfRange(data, 0, 8);
		blockB = Arrays.copyOfRange(data, 8, 16);

		Util.print_log(funcName, Util.byteArrayToHex(blockA));
		Util.print_log(funcName, Util.byteArrayToHex(blockB));

		// phase 4:encrypt blockA with key1
		byte[] phase4Result = null;
		byte[] phase5Result = null;
		try {
			phase4Result = Util.encryptDES(key1, blockA, true);
			Util.print_log(funcName, "Phase 4: " + Arrays.toString(blockB));
			// phase 5:XOR phase 4 result with blockB
			byte[] xorResult = Util.xorBytes(phase4Result, blockB);
			Util.print_log(funcName, "xorResult: " + Util.byteArrayToHex(xorResult));
			// phase 5: encrpyt xor result with keyA
			phase5Result = Util.encrypt3DES(key1, key2, xorResult, true);
			Util.print_log(funcName, "Phase5: " + Util.byteArrayToHex(phase5Result));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			System.out.println("Terminate the program.");
			System.exit(0);
		}
		return Util.extractCvv(phase5Result, digits);
		// TODO:return with padding unpack
	}

	/**
	 * Assumptions: 1)The pan, expiry and serviceCode is unpacked
	 * 				2)the data, key1 and key2 is packed
	 * @param pan         - 13-19 length
	 * @param expiry      - 4 length (04/21)
	 * @param serviceCode - 3 length
	 */
	@Override
	public byte[] getCVCValue(byte[] pan, byte[] expiry, byte[] serviceCode, byte[] key1, byte[] key2, int digits) {
		unpackedDataValidation(pan, expiry, serviceCode);
		byte[] tempBlock = new byte[16];
		try {
			// concat
			byte[] concated = Util.byteArraysConcat(pan, expiry, serviceCode);
			tempBlock = Util.packData(concated);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Terminate the program.");
			System.exit(0);
		}
		return getCVCValue(tempBlock, key1, key2, digits);
	}

	/*
	 * The function check if the input cvc is correct.
	 * Assumptions: 1)The cvcValue defines the length of the output cvc.
	 */
	@Override
	public boolean checkCVCValue(byte[] data, byte[] key1, byte[] key2, byte[] cvcValue) {
		byte[] compre = getCVCValue(data, key1, key2, cvcValue == null ? 0 : cvcValue.length);
		return Arrays.equals(compre, cvcValue);
	}

	public void packedDataValidation(byte[] data, byte[] key1, byte[] key2, int digits) {
		try {
			if (data == null || key1 == null || key2 == null)
				throw new NullPointerException("data or keys are null");
			if (digits != 3 && digits != 4)
				throw new Exception("CvcValue has invalid, the length must be 3 or 4.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Terminate the program.");
			System.exit(0);
		}
	}
	public void unpackedDataValidation(byte[] pan, byte[] expiry, byte[] serviceCode) {
		try {
			// handle null and wrong sizes
			if (pan == null || expiry == null || serviceCode == null)
				throw new NullPointerException("data or keys are null");
			if (pan.length >= 19 && pan.length <= 13)
				throw new Exception("Pan number invalid, the length of pan must be between 13 to 19");
			if (expiry.length != 4)
				throw new Exception("Expiry number invalid, the length of must be 4.");
			if (serviceCode.length != 3)
				throw new Exception("serviceCode number invalid, the length of must be 3.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Terminate the program.");
			System.exit(0);
		}
	}
}
