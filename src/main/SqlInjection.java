public class SqlInjection {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType('text/html;charset=UTF-8');
        PrintWriter out = response.getWriter();
        try {

            String user = request.getParameter('user');
            Connection conn = null;
            String url = 'jdbc:mysql://192.168.2.128:3306/';
            String dbName = 'anvayaV2';
            String driver = 'com.mysql.jdbc.Driver';
            String userName = 'root';
            String password = '';
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);

                Statement st = conn.createStatement();
                String query = "SELECT * FROM  User where userId='" + user + "'";
                out.println('Query : ' + query);
                System.out.printf(query);
                ResultSet res = st.executeQuery(query);

                out.println('Results');
                while (res.next()) {
                    String s = res.getString('username');
                    out.println('\t\t' + s);
                }
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            out.close();
        }


    }

    // JPA
    public static String otherSqlInjection(String username) {
        EntityManager pm = getEM();

        TypedQuery<UserEntity> q = em.createQuery(
                String.format("select * from Users where name = %s", username),
                UserEntity.class);

        UserEntity res = q.getSingleResult();
    }
}
