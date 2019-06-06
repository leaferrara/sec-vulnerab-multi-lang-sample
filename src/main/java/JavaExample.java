import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class JavaExample {

    private static byte[] plainTextIn;

    public static void main(String[] args, HttpServletRequest request) throws ClassNotFoundException, IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        System.out.println("Hello World");

        // CWE-494
        URL[] classURLs= new URL[]{
                new URL("file:subdir/")
        };
        URLClassLoader loader = new URLClassLoader(classURLs);
        Class loadedClass = Class.forName("loadMe", true, loader);

        // CWE-807
        Cookie[] cookies = request.getCookies();
        for (int i =0; i< cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("role")) {
                String userRole = c.getValue();
            }
        }

        // CWE-311
        try {
            URL u = new URL("http://www.secret.example.org/");
            HttpURLConnection hu = (HttpURLConnection) u.openConnection();
            hu.setRequestMethod("PUT");
            hu.connect();
            OutputStream os = hu.getOutputStream();
            hu.disconnect();
        }
        catch (IOException e) {

            //...
        }

        // CWE-73 - #1
        String rName = request.getParameter("reportName");
        File rFile = new File("/usr/local/apfr/reports/" + rName);
        //...
        rFile.delete();

        // CWE-73 - #2
        Properties cfg = null;
        FileInputStream fis = new FileInputStream(cfg.getProperty("sub") + ".txt");
        byte[] arr = new byte[0];
        int amt = fis.read(arr);
        System.out.println(arr);

        // CWE-327
        Cipher des=Cipher.getInstance("DES...");
        des.getAlgorithm();

        // CWE-759
        String password = request.getParameter("password");
        String plainText = new String(plainTextIn);
        MessageDigest encer = MessageDigest.getInstance("SHA");
        encer.update(plainTextIn);
        /*
        byte[] digest = password.digest();
        //Login if hash matches stored hash
        if (equal(digest,secret_password())) {
            login_user();
        }
        */
    }

    // CWE-732
    private void readConfig(File configFile) throws IOException {
        if (!configFile.exists()) {
            // Create an empty config file
            configFile.createNewFile();
            // Make the file writable for all
            configFile.setWritable(true, false);
        }
        // Now read the config
        configFile.length();
    }




}