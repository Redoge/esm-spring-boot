package com.epam.esm.services.interfaces;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.TagIsExistException;
import com.epam.esm.exceptions.TagNotFoundException;

import java.util.List;
import java.util.Optional;

 public interface TagServiceInterface {
     List<Tag> getAll();

     Optional<Tag> getById(long id) throws TagNotFoundException;

     Optional<Tag> getByName(String name) throws TagNotFoundException;

     void deleteById(long id) throws TagNotFoundException;

     void save(String tagName) throws TagIsExistException;

}
