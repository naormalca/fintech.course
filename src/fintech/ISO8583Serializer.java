package fintech;

import java.util.Map;

public interface ISO8583Serializer {
	
	public byte[] serializeISO8583(int version, Map<Integer, byte[]> data);
}
