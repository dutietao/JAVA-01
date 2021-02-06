package com.netty.v2.router;

import java.util.List;

public interface HttpEndpointRouter {

    String route(List<String> endpoints);
}
