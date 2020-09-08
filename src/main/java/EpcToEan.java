public class EpcToEan {
    private static String error = "";

    public static String convertToEan(String epc) {
        String binEpc = strPad(strBaseConvert(epc, 16, 2),96, "0");
        if (binEpc.length() == 96) {
            error = "";
            String company = strBaseConvert(binEpc.substring(14, 34), 2, 10);
            String indicator = strBaseConvert(binEpc.substring(34, 38), 2, 10);
            String itemref = strBaseConvert(binEpc.substring(38, 58), 2, 10);
            String serial = strBaseConvert(binEpc.substring(58, 96), 2, 10);
            String tempean = indicator + company + itemref;
            String checksum = Integer.toString(calculateCheckSum(tempean));
            String ean = company + itemref + checksum;
            return ean;
        } else {
            error = "Invalid EPC Code";
            return null;
        }
    }

    private static String strPad(String ean, int length, String pad) {
        // PHP: str_pad($ean, $length, $pad, STR_PAD_LEFT)
        String text = ean;
        for (int x = 0; x < length - ean.length(); x++) text = pad + text;
        return text;
    }

    private static int cInt(String str, int index) {
        // ean "5678" with index 3, will return int 7.
        String c = str.substring(index, index + 1);
        String regex = "[0-9]+";
        if (c.matches(regex)) {
            return Integer.parseInt(str);
        } else {
            return 0;
        }
    }

    private static int calculateCheckSum(String ean) {
        ean = strPad(ean,13, "0");
        try {
            return (10 - ((3 * (cInt(ean, 0) + cInt(ean,2) + cInt(ean,4) + cInt(ean,6) + cInt(ean,8) + cInt(ean,10) + cInt(ean,12))
                    + (cInt(ean, 1) + cInt(ean,3) + cInt(ean,5) + cInt(ean,7) + cInt(ean,9) + cInt(ean,11))) % 10)) % 10;
        } catch (Exception e) {
            return 0;
        }
    }

    private static String baseConvert(final String inputValue, final int fromBase, final int toBase) {
        if (fromBase < 2 || fromBase > 36 || toBase < 2 || toBase > 36) {
            return "0";
        }
        try {
            return Integer.toString(Integer.parseInt(inputValue, fromBase), toBase);
        } catch (Exception e) {
            return "0";
        }
    }

    private static String strBaseConvert(String str, int frombase, int tobase) {
        str = str.trim();
        int len = str.length();
        String q = "0";
        String s = "";
        String r1;
        String r2;
        if (frombase != 10) {
            for (int i = 0; i < len; i++) {
                r1 = baseConvert(str.substring(i, i+1), frombase, 10);
                q = BigIntCalculation.bcadd(BigIntCalculation.bcmul(q, Integer.toString(frombase)), r1);
            }
        } else {
            q = str;
        }
        if (tobase != 10) {
            while (BigIntCalculation.bccomp(q, "0") > 0) {
                r2 = BigIntCalculation.bcmod(q, Integer.toString(tobase));
                s = baseConvert(r2, 10, tobase) + s;
                q = BigIntCalculation.bcdiv(q, Integer.toString(tobase));
            }
        } else {
            s = q;
        }
        return s;
    }
}
