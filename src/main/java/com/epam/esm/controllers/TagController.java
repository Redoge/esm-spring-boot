package com.epam.esm.controllers;

import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.TagIsExistException;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.services.interfaces.TagServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagServiceInterface tagService;

    public TagController(TagServiceInterface tagService) {
        this.tagService = tagService;
    }
    @GetMapping
    public CollectionModel<EntityModel<Tag>>getTags() throws TagNotFoundException, TagIsExistException {
        List<Tag> tags = tagService.getAll();
        List<EntityModel<Tag>> tagResources = new ArrayList<>();
        for (var tag : tags) {
            EntityModel<Tag> tagResource = EntityModel.of(tag);
            tagResource.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel());
            tagResource.add(linkTo(methodOn(TagController.class).removeTagById(tag.getId())).withRel("delete").withType(HttpMethod.DELETE.name()));
            tagResources.add(tagResource);
        }
        CollectionModel<EntityModel<Tag>> resources = CollectionModel.of(tagResources);
        resources.add(linkTo(methodOn(TagController.class).getTags()).withSelfRel());
        resources.add(linkTo(methodOn(TagController.class).createTag(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<Tag> getTagById(@PathVariable long id) throws TagNotFoundException {
        var tag = tagService.getById(id);
        EntityModel<Tag> tagResource = EntityModel.of(tag.get());
        tagResource.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
        tagResource.add(linkTo(methodOn(TagController.class).removeTagById(id)).withRel("delete").withType(HttpMethod.DELETE.name()));
        return tagResource;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTagById(@PathVariable long id) throws TagNotFoundException {
        tagService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) throws TagIsExistException{
        var createdTag = tagService.save(tag.getName());
        return ResponseEntity.ok(createdTag);
    }
}
