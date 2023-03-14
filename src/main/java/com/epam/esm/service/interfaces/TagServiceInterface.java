package com.epam.esm.service.interfaces;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

 public interface TagServiceInterface {
     List<Tag> getAll();

     Optional<Tag> getById(long id);

     Optional<Tag> getByName(String name);

     void deleteById(long id);

     void save(String tagName);

}
