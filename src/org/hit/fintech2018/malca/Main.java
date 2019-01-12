package org.hit.fintech2018.malca;

public class Main {
	public static void main(String[] args) throws Exception {
		//testCVCGenerator();
		testLuhn();
	}

	public static void testLuhn() {
		MyLuhnChecker t = new MyLuhnChecker();
		String[] cardsNumbers = { "45800000000000007", "5458659295978282","312345671234567123456712345621",
				"44802912703390768", "47564325589428465"};
		for (String str : cardsNumbers) {
			try {
				System.out.println(str + ": " + t.isLuhnValid(stringToByteArray(str)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	

	public static byte[] stringToByteArray(String byteArrayAsString) {
		byte[] arrayToReturn = new byte[byteArrayAsString.length()];
		for (int i = 0; i < byteArrayAsString.length(); i++) {
			arrayToReturn[i] = (byte) ((byteArrayAsString.charAt(i)) - '0');
		}
		return arrayToReturn;

	}

	public static void testCVCGenerator() {
		byte[] pan = {4,1,2,3,4,5,0,9,9,0,1,2,2,1,2,9};
		byte[] expiry = {0, 7, 2, 1};
		byte[] sc = {2, 0, 2};
		//41 23 45 09 90 12 21 29 07 21 20 20 00 00 00 00 
		byte[] data = {0x41,0x23,0x45,0x09,(byte)0x90,0x12,0x21,0x29,0x07,0x21,0x20,0x20,0x00,0x00,0x00,0x00};
		//D6 76 DC F3 AF B7 35 05
		byte[] key1 = {(byte)0xD6,(byte)0x76,(byte)0xDC,(byte)0xF3,(byte)0xAF,(byte)0xB7,(byte)0x35,(byte)0x05};
		//32 EA F0 0E A2 53 D8 75
		byte[] key2 = {(byte)0x32,(byte)0xEA,(byte)0xF0,(byte) 0x0E,(byte)0xA2 ,(byte)0x53,(byte)0xD8,(byte)0x75};

		MyCVCGenerator s = new MyCVCGenerator();
		Util.print_log("MAIN", Util.byteArrayToHex(s.getCVCValue(data, key1, key2, 4)));
		Util.print_log("MAIN", Util.byteArrayToHex((s.getCVCValue(pan, expiry, sc, key1, key2, 4))));
		boolean CHECK = s.checkCVCValue(data, key1, key2, new byte[] {2, 4, 1});
		System.out.println(CHECK);
	}
}
