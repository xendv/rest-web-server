package rest.web.server.api;

import com.google.inject.Inject;
import rest.web.server.content.managers.ProductsDBManager;
import rest.web.server.entities.Manufacturer;
import rest.web.server.entities.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public final class ProductsREST {

    @Inject
    private ProductsDBManager productsDBManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> root() {
        return productsDBManager.getAllProducts();
    }

    @GET
    @Path("/manufacturer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getManufacturersProducts(Manufacturer manufacturer) {
        return productsDBManager.getManufacturersProducts(manufacturer.getId());
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProduct(Product product) {
        if (productsDBManager.deleteProduct(product.getName()) == 0) {
            return Response.ok().header(HttpHeaders.CACHE_CONTROL, "no-cache").build();
            // or to redirect:
            // return Response.status(Response.Status.FOUND).location(new URI("/products")).build();
        } else throw new NotFoundException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        productsDBManager.addNewProduct(product);
        try {
            return Response.status(Response.Status.FOUND).location(new URI("/products")).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        throw new BadRequestException();
    }
}
