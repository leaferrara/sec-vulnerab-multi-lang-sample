import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Signature;

object WeakRandom {

    // Vulnerable line
    val portPickCounter =
            new AtomicLong(new Random().nextInt(NUM_RESERVED_PORTS))
}



