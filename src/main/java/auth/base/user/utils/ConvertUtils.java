package auth.base.user.utils;

public final class ConvertUtils {
    public static Long convertStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static int convertToCent(double amount) {
        return (int) Math.round(amount);
    }
}
