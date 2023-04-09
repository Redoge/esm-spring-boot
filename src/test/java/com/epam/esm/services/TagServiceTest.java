package com.epam.esm.services;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.Tag;
import com.epam.esm.entities.User;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.TagRepository;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.util.filters.TagFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {


    @Mock
    private TagRepository tagRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TagFilter tagFilter;
    @InjectMocks
    private TagService tagService;
    private final List<Tag> testTags = getTestTags();
    private final List<Order> testOrders = getTestOrders();
    private final Long testId = 1L;
    private static final String TAG_NAME_1 = "tag1";
    private static final String TAG_NAME_2 = "tag2";
    private static final String TAG_NAME_3 = "tag3";
    private final String TAG_NAME_4 = "tag4";

    @Test
    void testGetAll() {
        Pageable pageable = Pageable.unpaged();
        List<Tag> expectedTags = testTags;
        Page<Tag> expectedPage = new PageImpl<>(expectedTags, pageable, expectedTags.size());

        when(tagRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Tag> actualPage = tagService.getAll(pageable);
        assertEquals(expectedPage, actualPage);
    }
    @Test
    void testGetByPartName() {
        Pageable pageable = Pageable.unpaged();
        List<Tag> expectedTags = testTags.subList(0,0);
        Page<Tag> expectedPage = new PageImpl<>(expectedTags, pageable, expectedTags.size());

        when(tagRepository.findByNameContaining(TAG_NAME_1, pageable)).thenReturn(expectedPage);

        Page<Tag> actualPage = tagService.getByPartName(TAG_NAME_1, pageable);
        assertEquals(expectedPage, actualPage);
    }
    @Test
    void testGetAllNoPageable() {
        List<Tag> expectedTags = testTags;
        when(tagRepository.findAll()).thenReturn(expectedTags);

        List<Tag> actualTags = tagService.getAll();

        assertEquals(expectedTags, actualTags);
    }

    @Test
    void testGetById() throws ObjectNotFoundException {

        Tag expectedTag = testTags.get(0);
        when(tagRepository.findById(testId)).thenReturn(Optional.of(expectedTag));

        Optional<Tag> actualTag = tagService.getById(testId);

        assertEquals(expectedTag, actualTag.get());
    }

    @Test
    void testGetByIdNotFound() {
        when(tagRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> tagService.getById(testId));
    }

    @Test
    void testGetTagsByTagName(){
        List<String> tagNames = testTags
                .stream()
                .map(Tag::getName)
                .toList();
        List<Tag> allTags = Arrays.asList(new Tag(testTags.get(0).getName()), new Tag(TAG_NAME_4));
        List<Tag> expectedTags = new ArrayList<>(testTags.stream().map(Tag::getName).map(Tag::new).toList());
        expectedTags.add(new Tag(TAG_NAME_4));

        when(tagRepository.findAll()).thenReturn(allTags);
        when(tagRepository.saveAll(any())).thenReturn(Arrays.asList(new Tag(TAG_NAME_2), new Tag(TAG_NAME_3)));

        when(tagFilter.isExistByName(allTags, tagNames)).thenReturn(Arrays.asList(new Tag(TAG_NAME_1), new Tag(TAG_NAME_4)));
        when(tagFilter.isNotExistByName(allTags, tagNames)).thenReturn(Arrays.asList(new Tag(TAG_NAME_2), new Tag(TAG_NAME_3)));


        List<Tag> resultTags = tagService.getTagsByTagName(tagNames);
        resultTags = resultTags.stream().sorted(Comparator.comparing(Tag::getName)).toList();

        assertEquals(expectedTags, resultTags);

    }

    @Test
    void getByNameTest() throws ObjectNotFoundException {
        when(tagRepository.findByName(TAG_NAME_1)).thenReturn(Optional.of(testTags.get(0)));
        when(tagRepository.findByName(TAG_NAME_4)).thenReturn(Optional.empty());

        var tag = tagService.getByName(TAG_NAME_1).get();

        assertEquals(testTags.get(0), tag);
        assertThrows(ObjectNotFoundException.class, ()->tagService.getByName(TAG_NAME_4));
    }

    @Test
    void deleteByIdTest(){
        when(tagRepository.existsById(1L)).thenReturn(true);
        when(tagRepository.existsById(4L)).thenReturn(false);

        assertDoesNotThrow(()->tagService.deleteById(testId));
        assertThrows(ObjectNotFoundException.class, ()->tagService.deleteById(4L));
    }


    @Test
    void saveTest() throws ObjectIsExistException {
        when(tagRepository.existsByName(TAG_NAME_1)).thenReturn(true);
        when(tagRepository.existsByName(TAG_NAME_4)).thenReturn(false);

        when(tagRepository.save(new Tag(TAG_NAME_4))).thenReturn(testTags.get(0));

        var tag = tagService.save(TAG_NAME_4);
        assertEquals(testTags.get(0), tag);
        assertThrows(ObjectIsExistException.class, ()->tagService.save(TAG_NAME_1));
    }

    @Test
    void getByUserIdTest() throws ObjectNotFoundException {
        Pageable pageable = Pageable.unpaged();

        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(false);

        var ordersPage = new PageImpl<>(testOrders, pageable, testOrders.size());

        when(orderRepository.findAllByOwnerId(1L, pageable)).thenReturn(ordersPage);

        var actualTags = tagService.getByUserId(1L, pageable).stream().toList();

        assertEquals(testTags, actualTags);
        assertThrows(ObjectNotFoundException.class, ()->tagService.getByUserId(2L, pageable));

    }
    public static List<Tag> getTestTags() {
        return Arrays.asList(
                new Tag(1L, TAG_NAME_1, List.of()),
                new Tag(2L, TAG_NAME_2, List.of()),
                new Tag(3L, TAG_NAME_3, List.of())
        );
    }

    public static List<Order> getTestOrders(){
        var gCert1 = new GiftCertificate();
        var gCert2 = new GiftCertificate();
        var gCert3 = new GiftCertificate();

        var tags = getTestTags();

        gCert1.setTags(tags);
        gCert2.setTags(List.of(
                tags.get(0),
                tags.get(1)
        ));
        gCert3.setTags(List.of(
                tags.get(1),
                tags.get(2)
        ));
        return Arrays.asList(
                new Order(1L, LocalDateTime.now(), BigDecimal.valueOf(10), gCert1, new User()),
                new Order(2L, LocalDateTime.now(), BigDecimal.valueOf(10), gCert2, new User()),
                new Order(3L, LocalDateTime.now(), BigDecimal.valueOf(10), gCert3, new User())
        );
    }
}