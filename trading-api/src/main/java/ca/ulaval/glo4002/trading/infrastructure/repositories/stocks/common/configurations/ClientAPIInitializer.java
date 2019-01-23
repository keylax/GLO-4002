package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.configurations;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class ClientAPIInitializer {
    public WebTarget generateClientWebTarget(String _urlApi) {
        ClientConfig configuration = new ClientConfig();
        UriBuilder builder = UriBuilder.fromUri(_urlApi);
        URI apiURI = builder.build();
        Client client = ClientBuilder.newClient(configuration);
        return client.target(apiURI);
    }
}
