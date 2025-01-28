import java.util.Scanner;

public class Vigenere {

    // Method to encrypt a plaintext message
    public static String encrypt(String plaintext, String keyword) {
        StringBuilder ciphertext = new StringBuilder();
        int keywordLength = keyword.length();
        keyword = keyword.toUpperCase(); // Ensure the keyword is in uppercase for consistency

        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);

            if (Character.isLetter(currentChar)) {
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
                int key = keyword.charAt(j % keywordLength) - 'A';
                char encryptedChar = (char) ((currentChar - base + key) % 26 + base);
                ciphertext.append(encryptedChar);
                j++; // Move to the next character in the keyword
            } else {
                // Keep non-alphabetic characters unchanged
                ciphertext.append(currentChar);
            }
        }
        return ciphertext.toString();
    }

    // Method to decrypt a ciphertext message
    public static String decrypt(String ciphertext, String keyword) {
        StringBuilder plaintext = new StringBuilder();
        int keywordLength = keyword.length();
        keyword = keyword.toUpperCase(); // Ensure the keyword is in uppercase for consistency

        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);

            if (Character.isLetter(currentChar)) {
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
                int key = keyword.charAt(j % keywordLength) - 'A';
                char decryptedChar = (char) ((currentChar - base - key + 26) % 26 + base);
                plaintext.append(decryptedChar);
                j++; // Move to the next character in the keyword
            } else {
                // Keep non-alphabetic characters unchanged
                plaintext.append(currentChar);
            }
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input plaintext and keyword
        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter the keyword: ");
        String keyword = scanner.nextLine();

        // Encrypt the plaintext
        String ciphertext = encrypt(plaintext, keyword);
        System.out.println("Encrypted ciphertext: " + ciphertext);

        // Decrypt the ciphertext
        String decryptedText = decrypt(ciphertext, keyword);
        System.out.println("Decrypted plaintext: " + decryptedText);

        scanner.close();
    }
}
