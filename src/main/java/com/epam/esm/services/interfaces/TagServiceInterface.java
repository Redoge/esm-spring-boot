package com.epam.esm.service.interfaces;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.TagIsExistException;
import com.epam.esm.exception.TagNotFoundException;

import java.util.List;
import java.util.Optional;

 public interface TagServiceInterface {
     List<Tag> getAll();

     Optional<Tag> getById(long id) throws TagNotFoundException;

     Optional<Tag> getByName(String name) throws TagNotFoundException;

     void deleteById(long id) throws TagNotFoundException;

     void save(String tagName) throws TagIsExistException;

}
