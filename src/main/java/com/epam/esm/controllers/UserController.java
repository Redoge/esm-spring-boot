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
import com.epam.esm.util.mappers.hateoas.OrderHateoasMapper;
import com.epam.esm.util.mappers.hateoas.UserHateoasMapper;
import com.epam.esm.util.mappers.hateoas.models.GiftCertificateRepresentationModel;
import com.epam.esm.util.mappers.hateoas.models.OrderRepresentationModel;
import com.epam.esm.util.mappers.hateoas.models.TagRepresentationModel;
import com.epam.esm.util.mappers.hateoas.models.UserRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController{
    private final UserServiceInterface userService;
    private final TagServiceInterface tagService;
    private final HateoasMapperInterface<TagRepresentationModel, Tag> tagHateoasMapper;
    private final HateoasMapperInterface<GiftCertificateRepresentationModel, GiftCertificate> gCertHateoasMapper;
    private final GiftCertificateServiceInterface giftCertificateService;
    private final UserHateoasMapper hateoasMapper;
    private final OrderService orderService;
    private final OrderHateoasMapper orderHateoasMapper;

    @GetMapping
    public PagedModel<UserRepresentationModel> getAll(Pageable pageable) throws Exception {
        var users = userService.getAll(pageable);
        return hateoasMapper.getPagedModel(users, pageable);
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
    public CollectionModel<TagRepresentationModel> getTagsByUserId(@PathVariable Long id, Pageable pageable) throws Exception {
        var tags = tagService.getByUserId(id, pageable);
        return tagHateoasMapper.getPagedModel(tags, pageable);
    }
    @GetMapping("/{id}/tags/top")
    public TagRepresentationModel getTopTagsByUserId(@PathVariable Long id) throws Exception {
        var tag = tagService.getMostWidelyByUserId(id);
        return tagHateoasMapper.getRepresentationModel(tag.get());
    }

    @GetMapping("/{id}/certificates")
    public CollectionModel<GiftCertificateRepresentationModel> getCertificatesByUserId(@PathVariable Long id, Pageable pageable) throws Exception {
        var tags = giftCertificateService.getByUserId(id, pageable);
        return gCertHateoasMapper.getPagedModel(tags, pageable);
    }
    @GetMapping("/{id}/orders")
    public CollectionModel<OrderRepresentationModel> getOrdersByUserId(@PathVariable Long id, Pageable pageable) throws Exception {//TODO:representation
        var orders = orderService.getByUserId(id, pageable);
        return orderHateoasMapper.getPagedModel(orders, pageable);
    }
}
