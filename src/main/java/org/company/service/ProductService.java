package org.company.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.solr.client.solrj.SolrServerException;
import org.company.model.Product;
import org.company.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() throws SolrServerException, IOException {
        return productRepository.getAllProducts();
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }
}
