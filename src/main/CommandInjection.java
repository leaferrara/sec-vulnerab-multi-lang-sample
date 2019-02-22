public class CommandInjection {

    public static void sampleCommandInjection() {

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