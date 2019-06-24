import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;

public class UseOfHarcodedKey {



    public UseOfHarcodedKey() throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);

        SecretKey key = keyGenerator.generateKey();


    }





}
