package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.interfaces.TagServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService implements TagServiceInterface {
    private final TagRepository tagDao;

    public TagService(TagRepository tagDao) {
        this.tagDao = tagDao;
    }

    public List<Tag> getAll() {
        return tagDao.findAll();
    }

    public Optional<Tag> getById(long id) {
        return tagDao.findById(id);
    }

    public Optional<Tag> getByName(String name) {
        return tagDao.findByName(name);
    }

    public void deleteById(long id) {
        tagDao.deleteById(id);
    }

    public void save(String tagName) {
        tagDao.save(new Tag(tagName));
    }
    protected List<Tag> saveAll(List<Tag> tags){
        return tagDao.saveAll(tags);
    }
}
