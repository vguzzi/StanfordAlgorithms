import java.math.BigInteger;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;

public class Karatsuba {

    public static void main(String[] args) {
        final BigInteger a = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592"); // "3141592653589793238462643383279502884197169399375105820974944592"; // "5678"
        final BigInteger b = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627"); // "2718281828459045235360287471352662497757247093699959574966967627"; // "1291"
        // result = 8,539,734,222,673,567,065,463,550,869,546,574,495,034,888,535,765,114,961,879,601,127,067,743,044,893,204,848,617,875,072,216,249,073,013,374,895,871,952,806,582,723,184

        Karatsuba app = new Karatsuba();

        String result = String.valueOf(app.karatsuba(a, b));
        println(result);
    }

    private BigInteger karatsuba(BigInteger x, BigInteger y) {
        // cutoff to brute force
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 2000) return x.multiply(y);                // optimize this parameter

        // number of bits divided by 2, rounded up
        N = (N / 2) + (N % 2);

        // x = a + 2^N b,   y = c + 2^N d
        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        // compute sub-expressions
        BigInteger ac    = karatsuba(a, c);
        BigInteger bd    = karatsuba(b, d);
        BigInteger abcd  = karatsuba(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
    }

    private BigInteger split(BigInteger input, int start, int end) {
        int n = input.toString().length();
        if (n == 1) return input;
        if (end > n) end = n;
        return new BigInteger(String.valueOf(input).substring(start, end));
    }
}



