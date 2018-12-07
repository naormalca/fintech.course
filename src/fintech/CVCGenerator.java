package fintech;

public interface CVCGenerator {
	
	public byte[] getCVCValue(byte[] data, byte[] key1, byte[] key2, int digits);
	
	public byte[] getCVCValue(byte[] pan, byte [] expiry, byte[] serviceCode, byte[] key1, byte[] key2, int digits);
	
	public boolean checkCVCValue(byte[] data, byte[] key1, byte[] key2, byte [] cvcValue);

}
