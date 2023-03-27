package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.Tag;
import com.epam.esm.entities.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.UserSaveRequestPojo;
import com.epam.esm.services.OrderService;
import com.epam.esm.services.UserService;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import com.epam.esm.services.interfaces.TagServiceInterface;
import com.epam.esm.services.interfaces.UserServiceInterface;
import com.epam.esm.util.mappers.hateoas.UserHateoasMapper;
import com.epam.esm.util.mappers.hateoas.models.GiftCertificateRepresentationModel;
import com.epam.esm.util.mappers.hateoas.models.TagRepresentationModel;
import com.epam.esm.util.mappers.hateoas.models.UserRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController{
    private final UserServiceInterface userService;
    private final TagServiceInterface tagService;
    private final HateoasMapperInterface<TagRepresentationModel, Tag> tagHateoasMapper;
    private final HateoasMapperInterface<GiftCertificateRepresentationModel, GiftCertificate> gCertHateoasMapper;
    private final GiftCertificateServiceInterface giftCertificateService;
    private final UserHateoasMapper hateoasMapper;
    private final OrderService orderService;

    public UserController(UserService userService, TagServiceInterface tagService,
                          HateoasMapperInterface<TagRepresentationModel, Tag> tagHateoasMapper,
                          HateoasMapperInterface<GiftCertificateRepresentationModel, GiftCertificate> gCertHateoasMapper,
                          GiftCertificateServiceInterface giftCertificateService,
                          UserHateoasMapper hateoasMapper, OrderService orderService) {
        this.userService = userService;
        this.tagService = tagService;
        this.tagHateoasMapper = tagHateoasMapper;
        this.gCertHateoasMapper = gCertHateoasMapper;
        this.giftCertificateService = giftCertificateService;
        this.hateoasMapper = hateoasMapper;
        this.orderService = orderService;
    }

    @GetMapping
    public CollectionModel<UserRepresentationModel> getAll() throws Exception {
        var users = userService.getAll();
        return hateoasMapper.getCollectionModel(users);
    }
    @GetMapping("/{id}")
    public UserRepresentationModel getById(@PathVariable long id) throws Exception {
        var users = userService.getById(id);
        return hateoasMapper.getRepresentationModel(users.get());
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
    public CollectionModel<TagRepresentationModel> getTagsByUserId(@PathVariable Long id) throws Exception {
        var tags = tagService.getByUserId(id);
        return tagHateoasMapper.getCollectionModel(tags);
    }
    @GetMapping("/{id}/tags/top")
    public TagRepresentationModel getTopTagsByUserId(@PathVariable Long id) throws Exception {
        var tag = tagService.getMostWidelyByUserId(id);
        return tagHateoasMapper.getRepresentationModel(tag.get());
    }

    @GetMapping("/{id}/certificates")
    public CollectionModel<GiftCertificateRepresentationModel> getCertificatesByUserId(@PathVariable Long id) throws Exception {
        var tags = giftCertificateService.getByUserId(id);
        return gCertHateoasMapper.getCollectionModel(tags);
    }
    @GetMapping("/{id}/orders")
    public List<Order> getOrdersByUserId(@PathVariable Long id) throws ObjectNotFoundException {//TODO:representation
        var orders = orderService.getByUserId(id);
        return orders;
    }
}
