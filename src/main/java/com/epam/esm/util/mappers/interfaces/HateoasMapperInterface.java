package com.epam.esm.util.mappers.interfaces;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public interface HateoasMapperInterface<T extends RepresentationModel<? extends T>, K> {
    T getRepresentationModel(K entity) throws Exception;
    CollectionModel<T> getCollectionModel(List<K> entity) throws Exception;
}
