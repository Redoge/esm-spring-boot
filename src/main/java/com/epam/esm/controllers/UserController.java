package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.entities.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.UserSaveRequestPojo;
import com.epam.esm.services.UserService;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import com.epam.esm.services.interfaces.TagServiceInterface;
import com.epam.esm.services.interfaces.UserServiceInterface;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController{
    private final UserServiceInterface userService;
    private final TagServiceInterface tagService;
    private final HateoasMapperInterface<Tag> tagHateoasMapper;
    private final HateoasMapperInterface<GiftCertificate> gCertHateoasMapper;
    private final GiftCertificateServiceInterface giftCertificateService;
    private final HateoasMapperInterface<User> hateoasMapper;

    public UserController(UserService userService, TagServiceInterface tagService,
                          HateoasMapperInterface<Tag> tagHateoasMapper,
                          HateoasMapperInterface<GiftCertificate> gCertHateoasMapper,
                          GiftCertificateServiceInterface giftCertificateService,
                          HateoasMapperInterface<User> hateoasMapper) {
        this.userService = userService;
        this.tagService = tagService;
        this.tagHateoasMapper = tagHateoasMapper;
        this.gCertHateoasMapper = gCertHateoasMapper;
        this.giftCertificateService = giftCertificateService;
        this.hateoasMapper = hateoasMapper;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>> getAll() throws Exception {
        var users = userService.getAll();
        return hateoasMapper.getCollectionModel(users);
    }
    @GetMapping("/{id}")
    public EntityModel<User> getById(@PathVariable long id) throws Exception {
        var users = userService.getById(id);
        return hateoasMapper.getEntityModel(users.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable long id) throws ObjectNotFoundException {
        userService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<User> create(@RequestBody UserSaveRequestPojo userPojo) throws ObjectIsExistException, BadRequestException {
        var createdUser = userService.save(userPojo);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}/tags")
    public CollectionModel<EntityModel<Tag>> getTagsByUserId(@PathVariable Long id) throws Exception {
        var tags = tagService.getByUserId(id);
        return tagHateoasMapper.getCollectionModel(tags);
    }
    @GetMapping("/{id}/tags/top")
    public EntityModel<Tag> getTopTagsByUserId(@PathVariable Long id) throws Exception {
        var tag = tagService.getMostWidelyByUserId(id);
        return tagHateoasMapper.getEntityModel(tag.get());
    }

    @GetMapping("/{id}/certificates")
    public CollectionModel<EntityModel<GiftCertificate>> getCertificatesByUserId(@PathVariable Long id) throws Exception {
        var tags = giftCertificateService.getByUserId(id);
        return gCertHateoasMapper.getCollectionModel(tags);
    }
}
