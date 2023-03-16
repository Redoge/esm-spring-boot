package com.epam.esm.services;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.TagIsExistException;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.repositories.TagRepository;
import com.epam.esm.services.interfaces.TagServiceInterface;
import com.epam.esm.util.filters.TagFilter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService implements TagServiceInterface {
    private final TagRepository tagDao;
    private final TagFilter tagFilter;

    public TagService(TagRepository tagDao, TagFilter tagFilter) {
        this.tagDao = tagDao;
        this.tagFilter = tagFilter;
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
    public Tag save(String tagName) throws TagIsExistException {
        if (tagDao.existsByName(tagName)) {
            throw new TagIsExistException("name " + tagName);
        }
        return tagDao.save(new Tag(tagName));
    }

    @Transactional
    public List<Tag> saveAll(List<Tag> tags) {
        return tagDao.saveAll(tags);
    }
    /**
     * Method receive List<String> with tags name and return List<Tag> by this name. If Tag not exist then will be created
     */
    @Transactional
    public List<Tag> getTagsByTagName(List<String> tags) {
        var allExistTag = getAll();
        var existTag = new ArrayList<>(tagFilter.isExistByName(allExistTag, tags));
        var notExistTag = tagFilter.isNotExistByName(allExistTag, tags);
        var createdTags = saveAll(notExistTag);
        existTag.addAll(createdTags);
        return existTag;
    }
}
