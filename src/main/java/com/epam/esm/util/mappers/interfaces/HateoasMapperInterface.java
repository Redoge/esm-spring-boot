package com.epam.esm.util.mappers.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;


public interface HateoasMapperInterface<T extends RepresentationModel<? extends T>, K> {
    T getRepresentationModel(K entity) throws Exception;
    PagedModel<T> getPagedModel(Page<K> page, Pageable pageable) throws Exception;
}
