package Lab4;

import java.util.Arrays;

public class Bignum {
    private byte[] number;          // least significand digit first (index 0), most significand last (index length-1)
    private static int mulCounter;  // variable to count the number of multiplications


    public Bignum(int n) {
        number = new byte[n];
    }

    public Bignum(String s) {
        int     n = s.length();
        number = new byte[n];

        for (int i = n-1; i >= 0; i--)
            number[n-i-1] = (byte)Character.getNumericValue(s.charAt(i));
    }

    public Bignum(byte[] number) {
        this.number = number;
    }

    /* print out the number to the string s */
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = number.length-1; i >= 0; i--)
            s.append(number[i]);

        return (s.toString());
    }


    /* print out the given number (for debug only) */
    public void printBigNum(String s) {
        System.out.println(s + ": " + toString());
    }


    /* create a new number whose digits are x[from, to) */
    public Bignum selectBigNum(int from, int to) {
        Bignum r = new Bignum(to-from);

        for (int i = from; i < to; i++)
            r.number[i-from] = number[i];

        return r;
    }


    /* subtract two numbers this - y */
    public Bignum subBigNum(Bignum y) throws Exception {
        Bignum r = new Bignum(number.length);
        int    carry;

        // sub digits, starting from the least significant digit
        carry = 0;
        for (int i = 0; i < number.length; i++) {
            r.number[i] = (byte)(number[i] - (i < y.number.length ? y.number[i] : 0) - carry);
            if (r.number[i] < 0) {
                carry = 1;
                r.number[i] += 10;
            } else
                carry = 0;
        }

        if (carry > 0) {
            throw new Exception("Overflow in subtraction\n");
        }

        return r;
    }


    /* add two numbers together this + y */
    public Bignum addBigNum(Bignum y) {
        Bignum r, a, b;
        int    carry;

        // a is the larger number, b is the smaller
        if (number.length > y.number.length) {
            a = this; b = y;
        } else {
            a = y; b = this;
        }

        r = new Bignum(a.number.length);

        // add digits, starting from the least significant digit
        carry = 0;
        for (int i = 0; i < a.number.length; i++) {
            r.number[i] = (byte)(a.number[i] + (i < b.number.length ? b.number[i] : 0) + carry);
            if (r.number[i] > 9) {
                carry = 1;
                r.number[i] -= 10;
            } else
                carry = 0;
        }

        if (carry > 0) {
            r.number = Arrays.copyOf(r.number, r.number.length+1);
            r.number[r.number.length-1] = 1;
        }

        return r;
    }


    /* multiply two numbers (this * y) together using divide-and-conquer technique */
    public Bignum mulBigNum(Bignum y) throws Exception {
        // you work is to be done here!!!
        Bignum a, b;


        // a is the larger number, b is the smaller
        if (number.length > y.number.length) {
            a = this; b = y;
        } else {
            a = y; b = this;
        }

        if(a.number.length == 0 || b.number.length == 0) {
            return new Bignum("0");
        }

        // Equal lengths
        int n = a.number.length;
        if(b.number.length > n)
            n = b.number.length;

        // If multiplying 1 digit numbers
        if(n == 1) {
            Bignum result = new Bignum(1);
            result.number[0] = (byte)(a.number[0] * b.number[0]);

            // If should be split into 2 digits
            if (result.number[0] > 9) {
                result.number = Arrays.copyOf(result.number, 2);
                result.number[1] = (byte) (result.number[0] / 10);
                result.number[0] = (byte) (result.number[0] % 10);
            }

            // Print digits
            for (int i = 0; i < result.number.length; i++) {
                //System.out.println("Digit: " + result.number[i]);
            }

            return result;
        }

       // System.out.println("Going in");
        //System.out.println(a.toString() + "\t\t" + b.toString());

        // split into 2
        Bignum al, ar, bl, br;

        int halfN = n % 2 == 0 ? n / 2 : (n+1) / 2;

        //System.out.println("N = " + n + " \t\t HN = " + halfN);

        byte[] alBytes = new byte[a.number.length - halfN];
        for (int i = 0; i < alBytes.length; i++) {
            alBytes[i] = a.number[i + halfN];
        }
        byte[] arBytes = new byte[halfN];
        for (int i = 0; i < arBytes.length; i++) {
            arBytes[i] = a.number[i];
        }

        byte[] blBytes = new byte[b.number.length - halfN];
        for (int i = 0; i < blBytes.length; i++) {
            blBytes[i] = b.number[i + halfN];
        }
        byte[] brBytes = new byte[halfN];
        for (int i = 0; i < brBytes.length; i++) {
            brBytes[i] = b.number[i];
        }

        //System.out.println(alBytes.length);
        //System.out.println(arBytes.length);
        //System.out.println(blBytes.length);
        //System.out.println(brBytes.length);

        al = new Bignum(alBytes);
        ar = new Bignum(arBytes);
        bl = new Bignum(blBytes);
        br = new Bignum(brBytes);

      //  System.out.println("Out");
    //    System.out.println(al.toString() + " : " + ar.toString() + "  ::  " + bl.toString() + " : " + br.toString());

        Bignum p1 = al.mulBigNum(bl);
        Bignum p2 = ar.mulBigNum(br);
        Bignum p3l = al.addBigNum(ar);
        Bignum p3r = bl.addBigNum(br);
        Bignum p3 = p3l.mulBigNum(p3r);

        //System.out.println("Big subtraction");
        //System.out.println(p3.toString() + " - " + p1.toString() + " - " + p2.toString());

        Bignum pcol = p3.subBigNum(p1).subBigNum(p2);
//        System.out.println("Pcol: " + pcol.toString());

        Bignum res = (p1.shiftLeft(halfN * 2)).addBigNum(pcol.shiftLeft(halfN)).addBigNum(p2);
  //      System.out.println("OUT: " + res.toString());
        return res;
    }

    public Bignum shiftLeft(int count) {
        byte[] newNumbers = new byte[number.length+count];
        for (int i = 0; i < count; i++) {
            newNumbers[i] = 0;
        }
        for (int i = 0; i < number.length; i++) {
            newNumbers[i+count] = number[i];
        }
        return new Bignum(newNumbers);
    }

    public void clrMulCounter() {
        mulCounter = 0;
    }


    public int rclMulCounter() {
        return (mulCounter);
    }
}
