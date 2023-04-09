package com.epam.esm.services;

import com.epam.esm.entities.Tag;

import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.TagRepository;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.services.interfaces.TagServiceInterface;
import com.epam.esm.util.filters.TagFilter;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TagService implements TagServiceInterface {
    private final TagRepository tagDao;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TagFilter tagFilter;

    public TagService(TagRepository tagDao, OrderRepository orderRepository, UserRepository userRepository, TagFilter tagFilter) {
        this.tagDao = tagDao;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.tagFilter = tagFilter;
    }

    public Page<Tag> getAll(Pageable pageable) {
        return tagDao.findAll(pageable);
    }
    public List<Tag> getAll() {
        return tagDao.findAll();
    }

    public Optional<Tag> getById(long id) throws ObjectNotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        if (tag.isEmpty())
            throw new ObjectNotFoundException("Tag", id);
        return tag;
    }

    public Optional<Tag> getByName(String name) throws ObjectNotFoundException {
        Optional<Tag> tag = tagDao.findByName(name);
        if (tag.isEmpty())
            throw new ObjectNotFoundException("Tag", name);
        return tag;
    }

    @Transactional
    public void deleteById(long id) throws ObjectNotFoundException {
        if (!tagDao.isExistsById(id)) {
            throw new ObjectNotFoundException("Tag", id);
        }
        tagDao.deleteById(id);
    }

    @Transactional
    public Tag save(String tagName) throws ObjectIsExistException {
        if (tagDao.isExistsByName(tagName)) {
            throw new ObjectIsExistException("Tag", tagName);
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

    @Override
    public Page<Tag> getByUserId(long userId, Pageable pageable) throws ObjectNotFoundException {
        if(!userRepository.existsById(userId)){
            throw new ObjectNotFoundException("User", userId);
        }
        var orders = orderRepository.findAllByOwnerId(userId, pageable);
        var tags = orders.stream()
                .flatMap(order -> order.getGiftCertificate().getTags().stream())
                .distinct()
                .toList();
        return new PageImpl<>(tags, orders.getPageable(), orders.getTotalElements());
    }

    @Override
    public Optional<Tag> getMostWidelyByUserId(long userId) throws ObjectNotFoundException {
        if(!userRepository.existsById(userId)){
            throw new ObjectNotFoundException("User", userId);
        }
        var tags =  orderRepository.findAllByOwnerId(userId, Pageable.unpaged()).stream()
                .flatMap(order -> order.getGiftCertificate().getTags().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());
        if (tags.isPresent()) {
            return Optional.ofNullable(tags.get().getKey());
        }
        throw new ObjectNotFoundException("Tag", "null");
    }


}
