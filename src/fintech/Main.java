package fintech;



public class Main {
	public static void main(String[] args) throws Exception  {
		/*	//058721952
		byte[] digits = {0,5,8,7,2,1,9,5,2};
		byte[] digits2 = {4,5,8,0,1,4,0,0,0,1,2,1,3,2,1,8};
		//201568326
		fintech.MyLuhnChecker t = new fintech.MyLuhnChecker();
		System.out.println(t.getLuhnDigit(digits2));
		System.out.println(t.isLuhnValid(digits));*/
		//byte[] keyOne = {0x0D,6,7,6,0x0D,0x0C,0x0F,3,0x0A,0x0F,0x0B,0x07,3,5,0,5};
		//byte[] keyTwo = {0x03,0x02,0x0E,0x0A,0x0F,0x00,0x00,0x0E,0x0A,0x02,0x05,0x03,0x0D,0x08,0x07,0x05};
		byte[] pan = {4,1,2,3,4,5,0,9,9,0,1,2,2,1,2,9};
		byte[] expiry = {0x07,0x21};
		byte[] sc = {0x20,0x20};
		//System.out.println("Pan length: "+pan.length);
		//41 23 45 09 90 12 21 29 07 21 20 20 00 00 00 00 
		byte[] data = {0x41,0x23,0x45,0x09,(byte)0x90,0x12,0x21,0x29,0x07,0x21,0x20,0x20,0x00,0x00,0x00,0x00};
		//D6 76 DC F3 AF B7 35 05
		byte[] key1 = {(byte)0xD6,(byte)0x76,(byte)0xDC,(byte)0xF3,(byte)0xAF,(byte)0xB7,(byte)0x35,(byte)0x05};
		//32 EA F0 0E A2 53 D8 75
		byte[] key2 = {(byte)32,(byte)0xEA,(byte)0xF0,(byte) 0xA2,(byte)0x0E ,(byte)0x53,(byte)0xD8,(byte)75};
		//2F FE BE A6 1B 98 C3 89
		System.out.println("Keys length:"+key1.length+" and "+key2.length);
 		
		MyCVCGenerator s = new MyCVCGenerator();
		Util.print_log("MAIN", Util.byteArrayToHex(s.getCVCValue(data, key1, key2, 4)));
		System.out.println("======================================");
		Util.print_log("MAIN", Util.byteArrayToHex(s.getCVCValue(pan, expiry, sc, key1, key2, 4)));
	}

}



















//the last digit need to complance to number that / in 10
