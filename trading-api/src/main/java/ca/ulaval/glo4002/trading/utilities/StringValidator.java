package ca.ulaval.glo4002.trading.utilities;

public class StringValidator {
    public static boolean isNullOrEmpty(String _value) {
        return _value == null || _value.trim().isEmpty();
    }
}
