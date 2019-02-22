public class JavaExample {

    public static void main(String[] args) {
        System.out.println("Hello World");

        URL[] classURLs= new URL[]{
                new URL("file:subdir/")
        };
        URLClassLoader loader = new URLClassLoader(classURLs);
        Class loadedClass = Class.forName("loadMe", true, loader);

        Cookie[] cookies = request.getCookies();
        for (int i =0; i< cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("role")) {
                userRole = c.getValue();
            }
        }

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


        String rName = request.getParameter("reportName");
        File rFile = new File("/usr/local/apfr/reports/" + rName);
        //...
        rFile.delete();

        fis = new FileInputStream(cfg.getProperty("sub")+".txt");
        amt = fis.read(arr);
        out.println(arr);

        Cipher des=Cipher.getInstance("DES...");
        des.initEncrypt(key2);

        // CWE-759
        String plainText = new String(plainTextIn);
        MessageDigest encer = MessageDigest.getInstance("SHA");
        encer.update(plainTextIn);
        byte[] digest = password.digest();
        //Login if hash matches stored hash
        if (equal(digest,secret_password())) {
            login_user();
        }
    }

    // CWE-732
    private void readConfig(File configFile) {
        if (!configFile.exists()) {
            // Create an empty config file
            configFile.createNewFile();
            // Make the file writable for all
            configFile.setWritable(true, false);
        }
        // Now read the config
        loadConfig(configFile);
    }




}