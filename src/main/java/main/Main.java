package main;

import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;


public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = initDb();
        HandlerList handlers = initServlets(dbService);

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }

    private static DBService initDb() {
        DBService dbService = new DBService();
        dbService.printConnectInfo();
        return dbService;
    }

    private static HandlerList initServlets(DBService dbService) {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServlet(dbService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(dbService)), "/signin");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        return handlers;
    }
}
