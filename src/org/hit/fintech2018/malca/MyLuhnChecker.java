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
            System.out.println("data: "+data[len - i]+" i: "+i+" pos: "+ (len-i));

		}
		return (byte) (sum % 10);
	}

	public boolean isLuhnValid(byte[] data) throws Exception {
		byte[] noCheckDigit = Arrays.copyOfRange(data, 0, data.length-1);
		byte checkDigit = getLuhnDigit(noCheckDigit);
		if (checkDigit == 0)
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
			if (data[i] > 9 || data[i] < 0)
				throw new Exception(msg+"Only digit accepted("+data[i]+")");
		}
		
	}
  //byte[] digits2 = {4,5,8,0,1,4,0,0,0,1,2,1,3,2,1,8};
    public byte getLuhnDigit1(byte[] data) throws Exception
    {
        byte sum=0;
        for (int i = 0 ; i < data.length  ; i++)
        {
            int positionByLuhnIndexing = data.length-i;
            System.out.println("data: "+data[i]+" i: "+i+" pos: "+ positionByLuhnIndexing);
            if (positionByLuhnIndexing%2!=0)
            	sum += data[i];
            else
            	sum += (byte) ((data[i] * 2)%9);
            System.out.println(sum);
        }
        return (byte) (sum%10);
    }

    public boolean isLuhnValid1(byte[] data) throws Exception
    {
        return (getLuhnDigit1(data)==0);
    }



}
