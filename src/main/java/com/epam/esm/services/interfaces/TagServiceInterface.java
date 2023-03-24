package com.epam.esm.services.interfaces;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;


import java.util.List;
import java.util.Optional;

 public interface TagServiceInterface {
     List<Tag> getAll();

     Optional<Tag> getById(long id) throws ObjectNotFoundException;

     Optional<Tag> getByName(String name) throws ObjectNotFoundException;

     void deleteById(long id) throws ObjectNotFoundException;

     Tag save(String tagName) throws  ObjectIsExistException;
     List<Tag> saveAll(List<Tag> tags);
     List<Tag> getTagsByTagName(List<String> tags);
     List<Tag> getByUserId(long userId);
     Optional<Tag> getMostWidelyByUserId(long userId);
}
