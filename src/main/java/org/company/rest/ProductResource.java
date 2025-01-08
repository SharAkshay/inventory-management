package org.company.rest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.solr.client.solrj.SolrServerException;
import org.company.dto.MapperUtil;
import org.company.dto.ProductDto;
import org.company.model.Product;
import org.company.service.ProductService;

import java.io.IOException;
import java.util.List;

@Path("/products")
public class ProductResource {

    private final ProductService productService;
    private final MapperUtil mapper;

    @Inject
    public ProductResource(ProductService productService, MapperUtil mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Product>> getAllProducts() throws SolrServerException, IOException {
        return Uni.createFrom().item(productService.getAllProducts());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addProduct(ProductDto productDto) {
        productService.addProduct(mapper.toEntity(productDto));
        return Uni.createFrom().item(Response.ok().build());
    }

}
