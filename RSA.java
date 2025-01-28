import java.math.BigInteger;
import java.util.Scanner;

public class RSA {

    // Function to compute the greatest common divisor (gcd) of two numbers
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    // Function to compute the modular inverse of a number
    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger m0 = m;
        BigInteger x0 = BigInteger.ZERO;
        BigInteger x1 = BigInteger.ONE;
        if (m.equals(BigInteger.ONE)) {
            return BigInteger.ZERO;
        }
        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger q = a.divide(m);
            BigInteger temp = m;
            m = a.mod(m);
            a = temp;
            temp = x0;
            x0 = x1.subtract(q.multiply(x0));
            x1 = temp;
        }
        if (x1.compareTo(BigInteger.ZERO) < 0) {
            x1 = x1.add(m0);
        }
        return x1;
    }

    // Key generation method for RSA
    public static void generateKeys(BigInteger p, BigInteger q, BigInteger e) {
        // Compute n = p * q
        BigInteger n = p.multiply(q);

        // Compute φ(n) = (p-1)*(q-1)
        BigInteger phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Choose e such that 1 < e < φ(n) and e is coprime with φ(n)
        while (gcd(e, phiN).compareTo(BigInteger.ONE) != 0) {
            e = e.add(BigInteger.ONE); // If e is not coprime with φ(n), increment e
        }

        // Calculate d such that e * d ≡ 1 (mod φ(n)), i.e., d is the modular inverse of e
        BigInteger d = modInverse(e, phiN);

        // Print public and private keys
        System.out.println("Public key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private key (d, n): (" + d + ", " + n + ")");
        
        // Encrypt and decrypt a message
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter plaintext message: ");
        String plaintext = scanner.nextLine();
        
        // Encrypt the message
        BigInteger encryptedMessage = encrypt(plaintext, e, n);
        System.out.println("Encrypted message: " + encryptedMessage);
        
        // Decrypt the message
        String decryptedMessage = decrypt(encryptedMessage, d, n);
        System.out.println("Decrypted message: " + decryptedMessage);
    }

    // Encrypt a message using the public key (e, n)
    public static BigInteger encrypt(String message, BigInteger e, BigInteger n) {
        BigInteger messageBigInt = new BigInteger(message.getBytes());
        return messageBigInt.modPow(e, n); // C = P^e mod n
    }

    // Decrypt a message using the private key (d, n)
    public static String decrypt(BigInteger encryptedMessage, BigInteger d, BigInteger n) {
        BigInteger decryptedBigInt = encryptedMessage.modPow(d, n); // P = C^d mod n
        return new String(decryptedBigInt.toByteArray());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Take user inputs for p, q, and e
        System.out.print("Enter a prime number p: ");
        BigInteger p = new BigInteger(scanner.nextLine());
        System.out.print("Enter a prime number q: ");
        BigInteger q = new BigInteger(scanner.nextLine());
        System.out.print("Enter the value of e: ");
        BigInteger e = new BigInteger(scanner.nextLine());

        // Generate keys and perform encryption and decryption
        generateKeys(p, q, e);
    }
}
