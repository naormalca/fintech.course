package org.hit.fintech2018.malca.Assignment3;

import java.util.Map;

public interface ISO8583Serializer
{
    byte[] serializeISO8583(int version, Map<Integer, byte[]> data);
}
