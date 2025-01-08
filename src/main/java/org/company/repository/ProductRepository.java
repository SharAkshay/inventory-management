package org.company.repository;

import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.company.model.Product;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class ProductRepository {

    private final SolrClient solrClient;
    private final EventBus eventBus;

    @ConfigProperty(name = "eventbus.address")
    String eventBusAddress;

    public ProductRepository(SolrClient solrClient, EventBus eventBus){
        this.solrClient = solrClient;
        this.eventBus = eventBus;
        eventBus.<Product>consumer(eventBusAddress, message -> {
            updateProductInSolr(message.body());
        });
    }

    private void updateProductInSolr(Product product) {
        DocumentObjectBinder binder = new DocumentObjectBinder();
        SolrInputDocument doc = binder.toSolrInputDocument(product);
        try {
            solrClient.add(doc);
            solrClient.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product in Solr", e);
        }
    }

    public Product addProduct(Product product) {
        eventBus.publish(eventBusAddress, product);
        return product;
    }

    public List<Product> getAllProducts() throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*"); // Query to fetch all documents
        query.setRows(100);
        QueryResponse queryResponse = solrClient.query(query);
        return queryResponse.getBeans(Product.class);
    }
}
