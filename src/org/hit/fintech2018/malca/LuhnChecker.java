package org.hit.fintech2018.malca;

public interface LuhnChecker {
	public byte getLuhnDigit(byte[] data) throws Exception;
	public boolean isLuhnValid(byte[] data) throws Exception;
}
