package com.epam.esm.util.mappers.interfaces;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface HateoasMapperInterface<T> {
    EntityModel<T> getEntityModel(T entity) throws Exception;
    CollectionModel<EntityModel<T>> getCollectionModel(List<T> entity) throws Exception;
}
