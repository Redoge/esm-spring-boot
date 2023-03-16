package com.epam.esm.util.filters;

import com.epam.esm.entities.Tag;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class TagFilter {
    public List<Tag> isExistByName(List<Tag> tags, List<String> names) {
        if(isEmpty(names))
            return new ArrayList<>();
        return tags.stream()
                .filter(tag -> names.contains(tag.getName()))F
                .toList();
    }

    public List<Tag> isNotExistByName(List<Tag> tags, List<String> names) {
        if(isEmpty(names))
            return new ArrayList<>();
        var existTagName = tags.stream()
                .map(Tag::getName)
                .toList();
        return names.stream()
                .filter(tag -> !existTagName.contains(tag))
                .map(Tag::new)
                .toList();
    }
}
