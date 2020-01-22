package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.cache.PublishingHouseCache;
import ru.bookstore.domain.PublishingHouse;

import java.util.List;

@Api(tags = "PublishingHouseController", description = "Контроллер для работы с издательствами")
@RestController
public class PublishingHouseController {


    private PublishingHouseCache publishingHouseCache;


    @Autowired
    public PublishingHouseController(PublishingHouseCache publishingHouseCache) {
        this.publishingHouseCache = publishingHouseCache;
    }


    @ApiOperation(value = "Получение издательства по id", response = List.class, tags = "getPublishingHouse")
    @GetMapping("/publishinghouse/{id}")
    public ResponseEntity<PublishingHouse> getPublishingHouse(@PathVariable(name = "id") long id) {
        PublishingHouse pHouse = publishingHouseCache.getPublishingHouse(id);
        return ResponseEntity.ok().body(pHouse);
    }


    @ApiOperation(value = "Получение списка издателей", response = List.class, tags = "getPublishingHouseList")
    @GetMapping("/publishinghouse/list")
    public ResponseEntity<List<PublishingHouse>> getPublishingHouseList() {
        List<PublishingHouse> result = publishingHouseCache.getAll();
        result.forEach(p -> {
            publishingHouseCache.getPublishingHouse(p.getId());
        });

        return ResponseEntity.ok().body(result);
    }


    @ApiOperation(value = "Добавление нового издателя", response = PublishingHouse.class, tags = "addPublishingHouse")
    @PostMapping("/publishinghouse/new")
    public ResponseEntity<PublishingHouse> addPublishingHouse(@RequestBody(required = true) PublishingHouse pHouse) {
        pHouse.setId(null);
        pHouse = publishingHouseCache.createPublishingHouse(pHouse);
        return ResponseEntity.ok().body(pHouse);
    }

    @ApiOperation(value = "Изменение инвормации о издателе", response = PublishingHouse.class, tags = "updatePublishingHouse")
    @PostMapping("/publishinghouse/update")
    public ResponseEntity<PublishingHouse> updatePublishingHouse(@RequestBody(required = true) PublishingHouse pHouse) {
        pHouse.setId(pHouse.getId());
        pHouse = publishingHouseCache.updatePublishingHouse(pHouse);
        return ResponseEntity.ok().body(pHouse);
    }

    @ApiOperation(value = "Удаление издателя из БД", response = PublishingHouse.class, tags = "deletePublishingHouse")
    @DeleteMapping("/publishinghouse/delete/{id}")
    public ResponseEntity deletePublishingHouse(@PathVariable("id") long id) {
        publishingHouseCache.deletePublishingHouse(id);
        return ResponseEntity.ok().build();
    }
}
