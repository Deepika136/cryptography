import java.util.Scanner;

public class affine {

    private static final int ALPHABET_SIZE = 26;

    // Method to calculate modular inverse
    private static int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("No modular inverse exists for " + a + " under modulus " + m);
    }

    // Encryption method
    public static String encrypt(String plaintext, int k1, int k2) {
        if (gcd(k1, ALPHABET_SIZE) != 1) {
            throw new IllegalArgumentException("Key k1 = " + k1 + " must have a multiplicative inverse under modulus " + ALPHABET_SIZE);
        }

        StringBuilder ciphertext = new StringBuilder();
        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int p = ch - base;
                int c = (p * k1 + k2) % ALPHABET_SIZE;
                ciphertext.append((char) (c + base));
            } else {
                ciphertext.append(ch); // Keep non-alphabetic characters unchanged
            }
        }
        return ciphertext.toString();
    }

    // Decryption method
    public static String decrypt(String ciphertext, int k1, int k2) {
        if (gcd(k1, ALPHABET_SIZE) != 1) {
            throw new IllegalArgumentException("Key k1 = " + k1 + " must have a multiplicative inverse under modulus " + ALPHABET_SIZE);
        }

        int k1Inverse = modInverse(k1, ALPHABET_SIZE);
        StringBuilder plaintext = new StringBuilder();
        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int c = ch - base;
                int p = (k1Inverse * (c - k2 + ALPHABET_SIZE)) % ALPHABET_SIZE;
                plaintext.append((char) (p + base));
            } else {
                plaintext.append(ch); // Keep non-alphabetic characters unchanged
            }
        }
        return plaintext.toString();
    }

    // Helper method to calculate GCD
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine();

        System.out.println("Enter the multiplicative key (key1):");
        int k1 = scanner.nextInt();

        System.out.println("Enter the additive key (key2):");
        int k2 = scanner.nextInt();

        // Encryption
        String ciphertext = encrypt(plaintext, k1, k2);
        System.out.println("Ciphertext: " + ciphertext);

        // Decryption
        String decryptedText = decrypt(ciphertext, k1, k2);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}
