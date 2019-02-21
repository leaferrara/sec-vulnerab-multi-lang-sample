public class Cwe190JavaSample {
    public static void main(String[] args) {
        {
            int data = (new java.security.SecureRandom()).nextInt();

            // BAD: may overflow if data is large
            int scaled = data * 10;

            // ...

            // GOOD: use a guard to ensure no overflows occur
            //int scaled2;
            if (data < Integer.MAX_VALUE/10)
                scaled += data * 10;
            else
                scaled = Integer.MAX_VALUE;
        }
    }
}