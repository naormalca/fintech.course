package org.hit.fintech2018.malca;

import java.util.Arrays;

public class MyLuhnChecker implements LuhnChecker {
	// Default constructor
	MyLuhnChecker() {

	}

	/**
	 * in my assumption the input exclude the check digit.
	 * @param data credit card
	 * @return the check digit of the credit card by Luhn algorithm
	 * @throws Exception
	 */
	@Override
	public byte getLuhnDigit(byte[] data) throws Exception {
		dataValidation(data);
		byte sum = 0;
		for (int i = 0; i < data.length; i++) {
			if (i % 2 ==0) {
				sum += (data[data.length - 1 - i] << 1) % 9;
			} else {
				sum += data[data.length -1 - i];
			}
		}
		return (byte) ((10 - (sum % 10)) % 10);
	}

	/**
	 * in my assumption the input include the check digit.
	 * @param data credit card
	 * @return if the check digit of data is correct
	 * @throws Exception
	 */
	@Override
	public boolean isLuhnValid(byte[] data) throws Exception {
		byte[] dataNonLuhnDigit = Arrays.copyOfRange(data, 0, data.length - 1);
		return getLuhnDigit(dataNonLuhnDigit) == data[data.length - 1];
	}

	/**
	 * function to check edge cases of inputs
	 * @param data credit card
	 * @throws Exception
	 */
	private void dataValidation(byte[] data) throws Exception {
		String msg = "Invalid data, ";
		if (data == null)
			throw new NullPointerException(msg + "The data is null");
		if (data.length < 1)
			throw new Exception(msg + "The data is too short");
		for (int i = 0; i < data.length; i++) {
			if (data[i] > 9 || data[i] < 0)
				throw new Exception(msg + "Only digit accepted(" + data[i] + ")");
		}

	}
}
