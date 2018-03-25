package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
    private final AccountService accountService;
    private Gson gson = new Gson();

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
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

        UserProfile user = accountService.getUserByLogin(login);
        if (user == null || !user.getPass().equals(password)) {
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
