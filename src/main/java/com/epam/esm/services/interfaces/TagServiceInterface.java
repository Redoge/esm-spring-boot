package com.epam.esm.services.interfaces;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

 public interface TagServiceInterface {
     Page<Tag> getAll(Pageable pageable);
     List<Tag> getAll();

     Optional<Tag> getById(long id) throws ObjectNotFoundException;

     Optional<Tag> getByName(String name) throws ObjectNotFoundException;

     void deleteById(long id) throws ObjectNotFoundException;

     Tag save(String tagName) throws  ObjectIsExistException;
     List<Tag> saveAll(List<Tag> tags);
     List<Tag> getTagsByTagName(List<String> tags);
     Page<Tag> getByUserId(long userId, Pageable pageable) throws ObjectNotFoundException;
     Optional<Tag> getMostWidelyByUserId(long userId) throws ObjectNotFoundException;
}
