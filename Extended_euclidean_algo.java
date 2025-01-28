import java.util.Scanner;

public class Extended_euclidean_algo {

    public static void extendedEuclid(int a, int b) {
        // Initializing r1, r2, s1, s2, t1, t2
        int r1 = a, r2 = b, s1 = 1, s2 = 0, t1 = 0, t2 = 1;

        // Print the header for the table
        System.out.printf("%-5s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n", "q", "r1", "r2", "r", "s1", "s2", "t1", "t2", "t");

        while (r2 != 0) {
            // Calculate the quotient and remainder
            int q = r1 / r2;
            int r = r1 % r2;

            // Update r1, r2, s1, s2, t1, t2
            int s = s1 - q * s2;
            int t = t1 - q * t2;

            // Print the current step
            System.out.printf("%-5d%-10d%-10d%-10d%-10d%-10d%-10d%-10d%-10d\n", q, r1, r2, r, s1, s2, t1, t2, t);

            // Update for the next iteration
            r1 = r2;
            r2 = r;
            s1 = s2;
            s2 = s;
            t1 = t2;
            t2 = t;
        }
        
        // After the loop, r1 is the gcd and s1, t1 are the coefficients
        System.out.println("GCD = " + r1);
        System.out.println("s = " + s1 + ", t = " + t1);

        // If the final t is negative, adjust it
        if (t1 < 0) {
            t1 = (t1 + a) % a;  // Ensure t1 (the multiplicative inverse) is positive
        }

        System.out.println("Multiplicative Inverse = " + t1);
    }

    public static void main(String[] args) {
        // Create a scanner object to take user input
        Scanner scanner = new Scanner(System.in);
        
        // Prompt user to input the values for a and b
        System.out.print("Enter the value for a (r1): ");
        int a = scanner.nextInt();
        
        System.out.print("Enter the value for b (r2): ");
        int b = scanner.nextInt();
        
        // Calling the extended Euclidean function with user input values
        extendedEuclid(a, b);
        
        // Close the scanner object
        scanner.close();
    }
}
