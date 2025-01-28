import java.math.BigInteger;
import java.util.Scanner;

public class ElGamal {

    // Function to find the modular inverse using the Extended Euclidean Algorithm
    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger[] result = extendedGCD(a, m);
        return result[0].mod(m);
    }

    // Extended Euclidean Algorithm to find gcd and the modular inverse
    private static BigInteger[] extendedGCD(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[] {a, BigInteger.ONE, BigInteger.ZERO};
        }
        BigInteger[] result = extendedGCD(b, a.mod(b));
        BigInteger x = result[1];
        BigInteger y = result[2];
        return new BigInteger[] {result[0], y, x.subtract(a.divide(b).multiply(y))};
    }

    // Key Generation (ElGamal)
    public static BigInteger[] keyGeneration(BigInteger p, BigInteger e1, BigInteger d) {
        BigInteger e2 = e1.modPow(d, p);  // e2 = e1^d mod p
        return new BigInteger[] {e1, e2, p, d};
    }

    // Encryption (ElGamal)
    public static BigInteger[] encrypt(BigInteger e1, BigInteger e2, BigInteger p, BigInteger P, BigInteger r) {
        BigInteger C1 = e1.modPow(r, p);  // C1 = e1^r mod p
        BigInteger C2 = (P.multiply(e2.modPow(r, p))).mod(p);  // C2 = (P * e2^r) mod p
        return new BigInteger[] {C1, C2};
    }

    // Decryption (ElGamal)
    public static BigInteger decrypt(BigInteger d, BigInteger p, BigInteger C1, BigInteger C2) {
        // Compute C1^d mod p
        BigInteger C1_d = C1.modPow(d, p);  // C1^d mod p
        // Find modular inverse of C1^d mod p
        BigInteger inverseC1_d = modInverse(C1_d, p);
        // Recover plaintext P
        return (C2.multiply(inverseC1_d)).mod(p);  // P = (C2 * (C1^d)^-1) mod p
    }

    // Main method to interact with the user
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking user inputs
        System.out.print("Enter prime number p: ");
        BigInteger p = new BigInteger(scanner.nextLine());

        System.out.print("Enter primitive root e1: ");
        BigInteger e1 = new BigInteger(scanner.nextLine());

        System.out.print("Enter private key d: ");
        BigInteger d = new BigInteger(scanner.nextLine());

        System.out.print("Enter plaintext message P: ");
        BigInteger P = new BigInteger(scanner.nextLine());

        System.out.print("Enter random integer r: ");
        BigInteger r = new BigInteger(scanner.nextLine());

        // Key generation
        BigInteger[] keys = keyGeneration(p, e1, d);
        BigInteger e2 = keys[1];  // Public key component e2

        // Encryption
        BigInteger[] encrypted = encrypt(e1, e2, p, P, r);
        BigInteger C1 = encrypted[0];
        BigInteger C2 = encrypted[1];
        System.out.println("Encrypted ciphertext: C1 = " + C1 + ", C2 = " + C2);

        // Decryption
        BigInteger decrypted = decrypt(d, p, C1, C2);
        System.out.println("Decrypted plaintext: " + decrypted);
    }
}
