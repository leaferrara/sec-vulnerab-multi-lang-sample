public class CodeInjection {

    public static void sampleCodeInjection() {

        String script = System.getProperty("SCRIPTNAME");
        if (script != null)
            System.exec(script);


        // ---
        String btype = request.getParameter("backuptype");
        String cmd = new String("cmd.exe /K \"
                c:\\util\\rmanDB.bat "
                +btype+
                "&&c:\\utl\\cleanup.bat\"")

        System.Runtime.getRuntime().exec(cmd);
        // ---


    }
}