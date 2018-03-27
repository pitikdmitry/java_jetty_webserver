package servlets;

import dbService.DBException;
import dbService.DBService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
    private final DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            dbService.addUser(login, password);
        } catch (DBException e) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
