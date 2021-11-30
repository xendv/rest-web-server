package rest.web.server.api;

import com.google.inject.Inject;
import rest.web.server.content.managers.ContentGenerator;
import rest.web.server.content.managers.ProductsDBManager;
import rest.web.server.entities.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public final class ProductsREST {

    @Inject
    private ContentGenerator contentGenerator;

    @Inject
    private ProductsDBManager productsDBManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response root(){
        return Response.ok(contentGenerator.getProductsJSON())
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .build();
    }

    @GET
    @Path("/manufacturer")
    public String getManufacturersProducts(@QueryParam("man_id") Integer manufacturersId){
        return contentGenerator.getManufacturersProductsJSON(manufacturersId);
    }

    @POST
    @Path("/delete")
    public Response deleteProduct (@FormParam("nameToDelete") String productName){
        if (productsDBManager.deleteProduct(productName) == 0){
            return Response.ok().header(HttpHeaders.CACHE_CONTROL, "no-cache").build();
            // or to redirect:
            // return Response.status(Response.Status.FOUND).location(new URI("/products")).build();
        }
        else return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addProduct (@FormParam("name") String name,
                           @FormParam("quantity") Integer quantity,
                           @FormParam("man_id") Integer manufacturersId){
        productsDBManager.addNewProduct(new Product(
                name, manufacturersId, quantity)
        );
        try {
            return Response.status(Response.Status.FOUND).location(new URI("/products")).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
