import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssJavaSample extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // BAD: a request parameter is written directly to an error response page
        response.sendError(HttpServletResponse.SC_NOT_FOUND,
                "The page \"" + request.getParameter("page") + "\" was not found.");
    }

    protected void doGet2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String input1 = req.getParameter("input1");
        // ...
        resp.getWriter().write(input1);
    }
}