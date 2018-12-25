package org.hit.fintech2018.malca;

import java.util.Arrays;

public class MyLuhnChecker implements LuhnChecker {
	
	public byte getLuhnDigit(byte[] data) throws Exception {
		dataValidation(data);
		int len = data.length - 1;
		byte sum = 0;
		for (int i = 0; i <= len; i++) {
			int temp;
			if (i % 2 != 0) 
				temp = (data[len - i] << 1) % 9;
			else
				temp = (data[len - i]);
			if (temp > 9)
				temp = 10 - temp;
			sum += temp;
		}
		return sum;
	}
	public boolean isLuhnValid(byte[] data) throws Exception {
		dataValidation(data);
		byte[] noCheckDigit = Arrays.copyOfRange(data, 0, data.length-1);
		byte checkDigit = getLuhnDigit(noCheckDigit);
		if (checkDigit % 10 == 0)
			return true;
		return false;
	}

	
	private void dataValidation(byte[] data) throws Exception {
		String msg = "Invalid data, ";
		if (data == null)
			throw new NullPointerException(msg+"The data is null");
		if (data.length < 1)
			throw new Exception(msg+"The data is too short");
		for (int i = 0; i < data.length; i++) {
			if (data[i] > 9)
				throw new Exception(msg+"Only digit accepted("+data[i]+")");
		}
	}



}
