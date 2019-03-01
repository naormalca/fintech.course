package org.hit.fintech2018.malca.Assignment3.formats;

/**
 * This enum class represent all ISO8583 fileds that was taken from this source:
 * http://read.pudn.com/downloads206/sourcecode/embed/971390/ISO8583/ISO8583%E6%8A%A5%E6%96%87%E8%A7%84%E8%8C%83/MDSSEC04.PDF
 *
 */
public enum Iso8583Field {

    F0(Iso8583FieldType.NUM, 4, "MESSAGE TYPE INDICATOR", true),
    F1(Iso8583FieldType.BITMAP, 16, "Bitmap", true),
    F2(Iso8583FieldType.NUM, 19, "Primary account number (PAN)", false),
    F3(Iso8583FieldType.NUM, 6, "Processing code", true),
    F4(Iso8583FieldType.NUM, 12, "Amount, transaction", true),
    F5(Iso8583FieldType.NUM, 12, "Amount, settlement", true),
    F6(Iso8583FieldType.NUM, 12, "Amount, cardholder billing", true),
    F7(Iso8583FieldType.NUM, 10, "Transmission date & time", true),
    F8(Iso8583FieldType.NUM, 8, "Amount, cardholder billing fee", true),
    F9(Iso8583FieldType.NUM, 8, "Conversion rate, settlement", true),
    F10(Iso8583FieldType.NUM, 8, "Conversion rate, cardholder billing", true),
    F11(Iso8583FieldType.NUM, 6, "System trace audit number (STAN)", true),
    F12(Iso8583FieldType.NUM, 6, "Time, local transaction (hhmmss)", true),
    F13(Iso8583FieldType.NUM, 4, "Date, local transaction (MMDD)", true),
    F14(Iso8583FieldType.NUM, 4, "Date, expiration", true),
    F15(Iso8583FieldType.NUM, 4, "Date, settlement", true),
    F16(Iso8583FieldType.NUM, 4, "Date, conversion", true),
    F17(Iso8583FieldType.NUM, 4, "Date, capture", true),
    F18(Iso8583FieldType.NUM, 4, "Merchant type/Merchant Category Code", true),
    F19(Iso8583FieldType.NUM, 3, "Acquiring institution country code", true),
    F20(Iso8583FieldType.NUM, 3, "PAN extended, country code", true),
    F21(Iso8583FieldType.NUM, 3, "Forwarding institution. country code", true),
    F22(Iso8583FieldType.NUM, 3, "Point of service entry mode", true),
    F23(Iso8583FieldType.NUM, 3, "Application PAN sequence number", true),
    F24(Iso8583FieldType.NUM, 3, "Function code (ISO 8583:1993)/Network International identifier (NII)", true),
    F25(Iso8583FieldType.NUM, 2, "Point of service condition code", true),
    F26(Iso8583FieldType.NUM, 2, "Point of service capture code", true),
    F27(Iso8583FieldType.NUM, 1, "Authorizing identification response length", true),
    F28(Iso8583FieldType.AMOUNT, 8, "Amount, transaction fee", true),
    F29(Iso8583FieldType.AMOUNT, 8, "Amount, settlement fee", true),
    F30(Iso8583FieldType.AMOUNT, 8, "Amount, transaction processing fee", true),
    F31(Iso8583FieldType.AMOUNT, 8, "Amount, settlement processing fee", true),
    F32(Iso8583FieldType.VAR, 11, "Acquiring institution identification code", false),
    F33(Iso8583FieldType.VAR, 11, "Forwarding institution identification code", false),
    F34(Iso8583FieldType.VAR, 28, "Primary account number, extended", false),
    F35(Iso8583FieldType.VAR, 37, "Track 2 data", false),
    F36(Iso8583FieldType.VAR, 104, "Track 3 data", false),
    F37(Iso8583FieldType.NUM, 12, "Retrieval reference number", true),
    F38(Iso8583FieldType.NUM, 6, "Authorization identification response", true),
    F39(Iso8583FieldType.NUM, 2, "Response code", true),
    F40(Iso8583FieldType.NUM, 3, "Service restriction code", true),
    F41(Iso8583FieldType.NUM, 8, "Card acceptor terminal identification", true),
    F42(Iso8583FieldType.NUM, 15, "Card acceptor identification code", true),
    F43(Iso8583FieldType.NUM, 40, "Card acceptor name/location", true),
    F44(Iso8583FieldType.VAR, 25, "Additional response data", false),
    F45(Iso8583FieldType.VAR, 76, "Track 1 data", false),
    F46(Iso8583FieldType.VAR, 999, "Additional data - ISO", false),
    F47(Iso8583FieldType.VAR, 999, "Additional data - national", false),
    F48(Iso8583FieldType.VAR, 999, "Additional data - private", false),
    F49(Iso8583FieldType.NUM, 3, "Currency code, transaction", true),
    F50(Iso8583FieldType.NUM, 3, "Currency code, settlement", true),
    F51(Iso8583FieldType.NUM, 3, "Currency code, cardholder billing", true),
    F52(Iso8583FieldType.NUM, 64, "Personal identification number data", true),
    F53(Iso8583FieldType.NUM, 16, "Security related control information", true),
    F54(Iso8583FieldType.VAR, 120, "Additional amounts", false),
    F55(Iso8583FieldType.VAR, 255, "ICC Data - EMV having multiple tags", false),
    F56(Iso8583FieldType.VAR, 999, "Reserved ISO", false),
    F57(Iso8583FieldType.VAR, 999, "Reserved national", false),
    F58(Iso8583FieldType.VAR, 999, "Reserved national", false),
    F59(Iso8583FieldType.VAR, 999, "Reserved national", false),
    F60(Iso8583FieldType.VAR, 999, "Reserved national", false),
    F61(Iso8583FieldType.VAR, 999, "Reserved private (Ex=CVV2/Service Code)", false),
    F62(Iso8583FieldType.VAR, 999, "Reserved private (Invoice Number, TPK Key", false),
    F63(Iso8583FieldType.VAR, 999, "Reserved private", false),
    F64(Iso8583FieldType.NUM, 8, "Message authentication code (MAC)", true),
    F65(Iso8583FieldType.NUM, 8, "Bitmap, extended", true),
    F66(Iso8583FieldType.NUM, 1, "Settlement code", true),
    F67(Iso8583FieldType.NUM, 2, "Extended payment code", true),
    F68(Iso8583FieldType.NUM, 3, "Receiving institution country code", true),
    F69(Iso8583FieldType.NUM, 3, "Settlement institution country code", true),
    F70(Iso8583FieldType.NUM, 3, "Network management information code", true),
    F71(Iso8583FieldType.NUM, 4, "Message number", true),
    F72(Iso8583FieldType.NUM, 4, "Message number, last", true),
    F73(Iso8583FieldType.NUM, 6, "Date, action (YYMMDD)", true),
    F74(Iso8583FieldType.NUM, 10, "Credits, number", true),
    F75(Iso8583FieldType.NUM, 10, "Credits, reversal number", true),
    F76(Iso8583FieldType.NUM, 10, "Debits, number", true),
    F77(Iso8583FieldType.NUM, 10, "Debits, reversal number", true),
    F78(Iso8583FieldType.NUM, 10, "Transfer number", true),
    F79(Iso8583FieldType.NUM, 10, "Transfer, reversal number", true),
    F80(Iso8583FieldType.NUM, 10, "Inquiries number", true),
    F81(Iso8583FieldType.NUM, 10, "Authorizations, number", true),
    F82(Iso8583FieldType.NUM, 12, "Credits, processing fee amount", true),
    F83(Iso8583FieldType.NUM, 12, "Credits, transaction fee amount", true),
    F84(Iso8583FieldType.NUM, 12, "Debits, processing fee amount", true),
    F85(Iso8583FieldType.NUM, 12, "Debits, transaction fee amount", true),
    F86(Iso8583FieldType.NUM, 16, "Credits, amount", true),
    F87(Iso8583FieldType.NUM, 16, "Credits, reversal amount", true),
    F88(Iso8583FieldType.NUM, 16, "Debits, amount", true),
    F89(Iso8583FieldType.NUM, 16, "Debits, reversal amount", true),
    F90(Iso8583FieldType.NUM, 42, "Original data elements", true),
    F91(Iso8583FieldType.NUM, 1, "File update code", true),
    F92(Iso8583FieldType.NUM, 2, "File security code", true),
    F93(Iso8583FieldType.NUM, 5, "Response indicator", true),
    F94(Iso8583FieldType.NUM, 7, "Service indicator", true),
    F95(Iso8583FieldType.NUM, 42, "Replacement amounts", true),
    F96(Iso8583FieldType.NUM, 16, "Message security code", true),
    F97(Iso8583FieldType.AMOUNT, 16, "Amount, net settlement", true),
    F98(Iso8583FieldType.NUM, 25, "Payee", true),
    F99(Iso8583FieldType.VAR, 11, "Settlement institution identification code", false),
    F100(Iso8583FieldType.VAR, 11, "Receiving institution identification code", false),
    F101(Iso8583FieldType.VAR, 17, "File name", false),
    F102(Iso8583FieldType.VAR, 28, "Account identification 1", false),
    F103(Iso8583FieldType.VAR, 28, "Account identification 2", false),
    F104(Iso8583FieldType.VAR, 100, "Transaction description", false),
    F105(Iso8583FieldType.VAR, 999, "Reserved for ISO use", false),
    F106(Iso8583FieldType.VAR, 100, "Reserved for ISO use", false),
    F107(Iso8583FieldType.VAR, 999, "Reserved for ISO use", false),
    F108(Iso8583FieldType.VAR, 999, "Reserved for ISO use", false),
    F109(Iso8583FieldType.VAR, 999, "Reserved for ISO use", false),
    F110(Iso8583FieldType.VAR, 999, "Reserved for ISO use", false),
    F111(Iso8583FieldType.VAR, 999, "Reserved for ISO use", false),
    F112(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F113(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F114(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F115(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F116(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F117(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F118(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F119(Iso8583FieldType.VAR, 999, "Reserved for national use", false),
    F120(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F121(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F122(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F123(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F124(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F125(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F126(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F127(Iso8583FieldType.VAR, 999, "Reserved for private use", false),
    F128(Iso8583FieldType.NUM, 8, "Message authentication code", true);

    private final Iso8583FieldType type;
    private final int length;
    private final boolean isFixed;
    private String fieldName;

    Iso8583Field(Iso8583FieldType type, int length, String fieldName, boolean isFixed) {
        this.type = type;
        this.length = length;
        this.fieldName = fieldName;
        this.isFixed = isFixed;
    }

    @Override
    public String toString() {
        return "Filed: " + fieldName + "\n  Length: " + length + "\n  isFixed: "+ isFixed;
    }

    public Iso8583FieldType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
    public boolean isFixed() {
        return isFixed;
    }

    public String getFieldName() {
        return fieldName;
    }
}
