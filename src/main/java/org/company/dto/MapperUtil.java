package org.company.dto;

import jakarta.enterprise.context.ApplicationScoped;
import org.company.model.Product;

@ApplicationScoped
public class MapperUtil {

    public static Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        return new Product(dto.id(), dto.name(), dto.description(), dto.price());
    }

    public static ProductDto toDto(Product entity) {
        if (entity == null) {
            return null;
        }
        return new ProductDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice());
    }
}
