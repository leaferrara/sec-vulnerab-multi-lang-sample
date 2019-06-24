import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Signature;

object HeapInspection {

    // CWE-78

    def sampleCommandInjection(HttpServletRequest request) throws IOException {

        // Sample 1
        String script = System.getProperty("SCRIPTNAME");
        if (script != null)
            Runtime.getRuntime().exec(script);


        // Sample 2 ---
        String btype = request.getParameter("backuptype");
        String cmd = new String("cmd.exe /K \"" +
                "c:\\util\\rmanDB.bat "
                +btype+
                "&&c:\\utl\\cleanup.bat\"");

        Runtime.getRuntime().exec(cmd);
        // --- //


    }
}
