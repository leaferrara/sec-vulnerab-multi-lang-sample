
public class CommandInjection {

    // CWE-78

    public static void sampleCommandInjection() {

        // Sample 1
        String script = System.getProperty("SCRIPTNAME");
        if (script != null)
            System.exec(script);


        // Sample 2 ---
        String btype = request.getParameter("backuptype");
        String cmd = new String("cmd.exe /K \"
                c:\\util\\rmanDB.bat "
                +btype+
                "&&c:\\utl\\cleanup.bat\"")

        System.Runtime.getRuntime().exec(cmd);
        // --- //


    }
}
