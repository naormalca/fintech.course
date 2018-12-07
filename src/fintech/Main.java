package fintech;

import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) throws Exception  {
		/*//4580140001213218
		//058721952
		byte[] digits = {0,5,8,7,2,1,9,5,2};
		byte[] digits2 = {4,5,8,0,1,4,0,0,0,1,2,1,3,2,1,8};
		//201568326
		fintech.MyLuhnChecker t = new fintech.MyLuhnChecker();
		System.out.println(t.getLuhnDigit(digits2));
		System.out.println(t.isLuhnValid(digits));*/
		byte[] pan = {4,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5};
		byte[] expiry = {8,7,0,1};
		byte[] sc = {1,0,1};
		byte[] key1 = {0,1,2,3,4,5,6,7,8,9,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F};
		byte[] key2 = {0x0F,0x0E,0x0D,0x0C,0x0B,0x0A,9,8,7,6,5,4,3,2,1,0};
		System.out.println("Pan length: "+pan.length);
		System.out.println("Keys length:"+key1.length+" and "+key2.length);
 		MyCVCGenerator s = new MyCVCGenerator();
		System.out.println(Arrays.toString(s.getCVCValue(pan, expiry, sc, key1, key2, 3)));
	}
}



















//the last digit need to complance to number that / in 10
