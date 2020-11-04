package io.github.kimmking.gateway.router;

import java.util.List;

public class MyHttpEndpointRouter implements HttpEndpointRouter{
    @Override
    public String route(List<String> endpoints) {
        return endpoints.get((int) (Math.random()* endpoints.size()));
    }
}
