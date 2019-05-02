import java.io.IOException;

public class CommandInjection {

    // CWE-78

    public static void sampleCommandInjection() throws IOException {

        // Sample 1
        String script = System.getProperty("SCRIPTNAME");
        if (script != null)
            Runtime.exec(script);


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
