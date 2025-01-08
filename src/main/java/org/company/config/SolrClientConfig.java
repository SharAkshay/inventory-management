package org.company.config;

import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@DefaultBean
public class SolrClientConfig {

    @ConfigProperty(name = "solr.url")
    String solrUrl;

    @ConfigProperty(name = "solr.queueSize")
    Integer queueSize;

    @ConfigProperty(name = "solr.threadCount")
    Integer threadCount;

    @Produces
    public SolrClient createSolrClient() {
        return new ConcurrentUpdateSolrClient.Builder(solrUrl)
                .withQueueSize(queueSize)
                .withThreadCount(threadCount)
                .build();
    }
}
