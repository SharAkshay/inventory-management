package org.company.lifecycle;

import jakarta.enterprise.event.Observes;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import org.company.model.Product;
import org.company.utils.JsonMessageCodec;

public class AppLifecycle {

    @Inject
    Vertx vertx;

    void onStart(@Observes StartupEvent ev) {
        EventBus eventBus = vertx.eventBus();

        eventBus.registerDefaultCodec(Product.class, new JsonMessageCodec<>(Product.class));
    }
}
