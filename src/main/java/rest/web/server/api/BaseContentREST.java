package rest.web.server.api;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Path("/")
public final class BaseContentREST {

    @Inject
    private ProductsREST productsREST;

    @GET
    public String getRoot(){
        return getResource("/static/pages/main");
    }

    @GET
    @Path("/help")
    public String getHelp(){
        return getResource("/static/pages/help");
    }

    private String getResource(@NotNull String resourcePath){
        final URL resource = BaseContentREST.class.getResource(resourcePath);
        if (resource != null)
            try (InputStream in = resource.openStream()) {
                return new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        return null;
    }

    @Path("/products")
    public ProductsREST productsREST() {return productsREST;}
}
