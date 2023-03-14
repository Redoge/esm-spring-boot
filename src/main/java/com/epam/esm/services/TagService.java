package com.epam.esm.services;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.TagIsExistException;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.repositories.TagRepository;
import com.epam.esm.services.interfaces.TagServiceInterface;
import jakarta.transaction.Transactional;
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

    public Optional<Tag> getById(long id) throws TagNotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        if (tag.isEmpty())
            throw new TagNotFoundException("id " + id);
        return tag;
    }

    public Optional<Tag> getByName(String name) throws TagNotFoundException {
        Optional<Tag> tag = tagDao.findByName(name);
        if (tag.isEmpty())
            throw new TagNotFoundException("name " + name);
        return tag;
    }

    @Transactional
    public void deleteById(long id) throws TagNotFoundException {
        if (!tagDao.existsById(id)) {
            throw new TagNotFoundException("id " + id);
        }
        tagDao.deleteById(id);
    }

    @Transactional
    public void save(String tagName) throws TagIsExistException {
        if (tagDao.existsByName(tagName)) {
            throw new TagIsExistException("name " + tagName);
        }
        tagDao.save(new Tag(tagName));
    }

    protected List<Tag> saveAll(List<Tag> tags) {
        return tagDao.saveAll(tags);
    }
}
