import java.math.BigInteger;

public class BigIntCalculation {
    private static BigInteger num1 = new BigInteger("0");
    private static BigInteger num2 = new BigInteger("0");

    public static String bcmul(String numStr1, String numStr2) {
        try {
            num1 = new BigInteger(numStr1);
        } catch(Exception e1) {
        }
        try {
            num2 = new BigInteger(numStr2);
        } catch(Exception e2) {
        }
        try {
            return num1.multiply(num2) + "";
        } catch(Exception e) {
            return "0";
        }
    }

    public static String bcadd(String numStr1, String numStr2) {
        try {
            num1 = new BigInteger(numStr1);
        } catch(Exception e1) {
        }
        try {
            num2 = new BigInteger(numStr2);
        } catch(Exception e2) {
        }
        return num1.add(num2) + "";
    }

    public static int bccomp(String numStr1, String numStr2) {
        try {
            num1 = new BigInteger(numStr1);
        } catch(Exception e1) {
        }
        try {
            num2 = new BigInteger(numStr2);
        } catch(Exception e2) {
        }
        return num1.compareTo(num2);
    }

    public static String bcmod(String numStr1, String numStr2) {
        try {
            num1 = new BigInteger(numStr1);
        } catch(Exception e1) {
        }
        try {
            num2 = new BigInteger(numStr2);
        } catch(Exception e2) {
        }
        return num1.mod(num2) + "";
    }

    public static String bcdiv(String numStr1, String numStr2) {
        try {
            num1 = new BigInteger(numStr1);
        } catch(Exception e1) {
        }
        try {
            num2 = new BigInteger(numStr2);
        } catch(Exception e2) {
        }
        if (num2.equals("0")) {
            return "";
        } else {
            return num1.divide(num2) + "";
        }
    }
}
