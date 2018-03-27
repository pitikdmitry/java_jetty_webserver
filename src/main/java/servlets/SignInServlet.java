package servlets;

import com.google.gson.Gson;
import dbService.DBException;
import dbService.DBService;
import dbService.datasets.UsersDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
    private Gson gson = new Gson();
    private final DBService dbService;

    public SignInServlet(DBService dbService) {
        this.dbService = dbService;
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsersDataSet user = null;
        try {
            user = dbService.getUserByName(login);
        } catch (DBException e) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
        if (user == null || !user.getPassword().equals(password)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            String json = gson.toJson("Unauthorized");
            response.getWriter().println(json);
        } else {

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

            String json = gson.toJson("Authorized: " + login);
            response.getWriter().println(json);
        }
    }
}
