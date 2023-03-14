package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagIsExistException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.interfaces.TagServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;

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
            throw new TagNotFoundException(valueOf(id));
        return tag;
    }

    public Optional<Tag> getByName(String name) {
        return tagDao.findByName(name);
    }

    @Transactional
    public void deleteById(long id) throws TagNotFoundException {
        if (!tagDao.existsById(id)) {
            throw new TagNotFoundException(valueOf(id));
        }
        tagDao.deleteById(id);
    }

    @Transactional
    public void save(String tagName) throws TagIsExistException {
        if (tagDao.existsByName(tagName)) {
            throw new TagIsExistException(tagName);
        }
        tagDao.save(new Tag(tagName));
    }

    protected List<Tag> saveAll(List<Tag> tags) {
        return tagDao.saveAll(tags);
    }
}
