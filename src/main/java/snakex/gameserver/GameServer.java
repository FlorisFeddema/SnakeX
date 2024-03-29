package snakex.gameserver;

import snakex.model.game.GameModel;
import snakex.model.game.IsGameModel;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import snakex.rest.GameRestEndPoint;

import javax.websocket.server.ServerContainer;


public class GameServer {

    static int port;
    static IsGameModel gameModel;

    public static void main(String[] args) {


        port = 9905;
        try {
            port =  Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException e){
            //ignore
        }
        String url = "ws://217.105.43.173:" + port + "/snake/game/";
        gameModel = new GameModel(url, new GameRestEndPoint());


        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try {
            ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);
            container.addEndpoint(GameEndPoint.class);
            server.start();
            server.join();
        }
        catch(Exception t) {
            //ignore
        }
    }
}
