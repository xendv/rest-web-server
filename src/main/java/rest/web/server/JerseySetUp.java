package rest.web.server;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletProperties;
import rest.web.server.api.BaseContentREST;

import java.util.HashMap;
import java.util.Map;

// not working
// (Resource configuration class rest.example.App is not a subclass of class javax.ws.rs.core.Application.)
// if extend Application, throws 404
public final class JerseySetUp {

    public static Map<String, String> getParams() {
        final Map<String, String> param = new HashMap<>();
        param.put(ServletProperties.JAXRS_APPLICATION_CLASS, App.class.getName());
        param.put(ServerProperties.PROVIDER_PACKAGES, BaseContentREST.class.getPackage().getName());
        param.put(ServerProperties.PROVIDER_CLASSNAMES, JacksonFeature.class.getName());
        return param;
    }
}
