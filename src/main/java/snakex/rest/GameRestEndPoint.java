package snakex.rest;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

public class GameRestEndPoint implements IsGameRestEndPoint {
    private static final String REST_API_URL = "http://217.105.43.173:9998/api/";
    private static final String SNAKE_API_URL = REST_API_URL + "snake/";

    @Override
    public void updateRating(int id, int rating, boolean won) {
        Form form = new Form();
        form.param("id",Integer.toString(id));
        form.param("rating",Integer.toString(rating));

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(SNAKE_API_URL);

        target.path("rating").request().put(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        form = new Form();
        form.param("id",Integer.toString(id));
        form.param("won", Boolean.toString(won));

        config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        target = client.target(SNAKE_API_URL);

        target.path("games").request().put(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
    }
}
