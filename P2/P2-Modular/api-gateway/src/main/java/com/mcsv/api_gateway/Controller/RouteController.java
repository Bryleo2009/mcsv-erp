package com.mcsv.api_gateway.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.cloud.gateway.route.RouteDefinition;

@RestController()
@RequestMapping("/api")
public class RouteController {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @GetMapping("/routes")
    public Flux<RouteDefinition> getRoutes() {
        return routeDefinitionLocator.getRouteDefinitions();
    }
}
