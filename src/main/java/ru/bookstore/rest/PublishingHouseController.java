package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.cache.PublishingHouseCache;
import ru.bookstore.domain.PublishingHouse;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;

@Api(tags = "PublishingHouseController", description = "Контроллер для работы с издательствами")
@RestController
public class PublishingHouseController {


    private PublishingHouseCache publishingHouseCache;


    @Autowired
    public PublishingHouseController(PublishingHouseCache publishingHouseCache) {
        this.publishingHouseCache = publishingHouseCache;
    }


    @ApiOperation(value = "Получение списка издателей", response = List.class, tags = "getPublishingHouseRepository")
    @GetMapping("/publishinghouse/list")
    public ResponseEntity<List<PublishingHouse>> getPublishingHouseRepository() {
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
        pHouse = publishingHouseCache.updatePublishingHouse(pHouse);
        return ResponseEntity.ok().body(pHouse);
    }

    @ApiOperation(value = "Изменение инвормации о издателе", response = PublishingHouse.class, tags = "updatePublishingHouse")
    @PutMapping("/publishinghouse/update/{pHouseId}")
    public ResponseEntity<PublishingHouse> updatePublishingHouse(@PathVariable("pHouseId") long pHouseId,
                                                                 @RequestBody(required = true) PublishingHouse pHouse) {
        pHouse.setId(pHouseId);
        pHouse = publishingHouseCache.updatePublishingHouse(pHouse);
        return ResponseEntity.ok().body(pHouse);
    }

    @ApiOperation(value = "Удаление издателя из БД", response = PublishingHouse.class, tags = "deletePublishingHouse")
    @DeleteMapping("/publishinghouse/delete/{pHouseId}")
    public ResponseEntity deletePublishingHouse(@PathVariable("pHouseId") long pHouseId) {
        publishingHouseCache.deletePublishingHouse(pHouseId);
        return ResponseEntity.ok().build();
    }
}
