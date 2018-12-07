package fintech;

public interface LuhnChecker {
	public byte getLuhnDigit(byte[] data) throws Exception;
	public boolean isLuhnValid(byte[] data) throws Exception;
}
