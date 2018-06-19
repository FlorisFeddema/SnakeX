package snakex.managerserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;

public class ManagerServer {

    public static void main(String[] args) {
        int port = 9900;
        try {
            port =  Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException e){
            //ignore
        }
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try{
            ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);
            container.addEndpoint(ManagerEndPoint.class);
            server.start();
            server.join();
        }
        catch(Exception t){
            //ignore
        }
    }
}
