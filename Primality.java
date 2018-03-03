/*Primality Tester
 * An implementation of a primality testing algorithm for my algorithms class. Takes in a text file named primalityTest.txt,
 * reads in values (assumes they are seperated by newline), and efficiently calculates their primality. Additionally computes
 * random values to demonstrate its accuracy by comparing the output to a naive primality testing algorithm.
 */

import java.math.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
public class Primality {
    public static void main (String [] arg) {
        //Read in file:
        String file = "primalityTest.txt";
        //Initialize big integer to store number
        BigInteger n;
        //Initialize a primality tester object
        Primality tester = new Primality();
        try {
            FileReader read = new FileReader(file);
            Scanner kb = new Scanner(read);
            while (kb.hasNextBigInteger()) {
                n = kb.nextBigInteger();
                //Primality test defined below
                System.out.println(tester.primalityTest(n, 2));
            }
            read.close();
        } catch (Exception e) {
            System.out.println("Error");
        }

        //Initializes values for randomly generating primes
        int nInt;
        String correct;
        String primaInt;
        String naive;
        int countTotal = 0;
        int countIncorrect = 0;
        for (int i = 500; i < 100000; i++) {
            n = BigInteger.valueOf(i);
            //Loop tests primality using fermat's little theorem versus a naive test, demonstrating
            //the rarity of its incorrectly classifying a non-prime as prime
            if (tester.naiveTest(i) == false && tester.primalityTest(n, 20).equals("Prime")) {
                countIncorrect++;
                System.out.println("Number incorrectly classified: " + n.toString());
            }
            countTotal++;
        }
        System.out.println("Count Total: " + countTotal + " Count Incorrect: " + countIncorrect);
    }

    public String primalityTest(BigInteger n, int k) {
        //Initializes constants as big integers to be used in the for loop
        BigInteger randomInt = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        for (int i = 0; i < k; i++) {
            //
            randomInt = randomBigInt(n);
            if (!(randomInt.modPow(n.subtract(one), n).compareTo(one.mod(n)) == 0)) {
                //System.out.println(randomInt.toString());
                return ("Composite");
            }
        }
        return ("Prime");
    }

    //Big number implementation from Thomas Pornin on Stack Overflow:
    //stackoverflow.com/questions/2290057/how-to-generate-a-random-biginteger-value-in-java
    public BigInteger randomBigInt(BigInteger n) {
        BigInteger r;
        Random rnd = new Random();
        do {
            r = new BigInteger(n.bitLength(), rnd);
            r = r.mod(n);
        } while(r.compareTo(n) >= 0);
        return r;
    }

    public int randomInt(int rangeLow, int rangeHigh) {
        Random rg = new Random();
        int r = rg.nextInt(rangeHigh - rangeLow) + rangeLow;
        return r;
    }

    public boolean naiveTest(int prime) {
        for (int i = (int)(Math.sqrt(prime)); i > 1 ; i--) {
            if (prime % i == 0) {
                return false;
            }
        }
        return true;
    }

}