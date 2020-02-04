package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.cache.GenreCache;
import ru.bookstore.domain.Genre;
import ru.bookstore.domain.PublishingHouse;

import java.util.List;

@Api(tags = "GenreController", description = "Контроллер для работы жанрами")
@RestController
public class GenreController {

    private GenreCache genreCache;


    @Autowired
    public GenreController(GenreCache genreCache) {
        this.genreCache = genreCache;
    }


    @ApiOperation(value = "Получение жанров по id", response = List.class, tags = "getGenre")
    @GetMapping("/genre/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable(name = "id") long id) {
        Genre genre = genreCache.getGenre(id);
        return ResponseEntity.ok().body(genre);
    }

    @ApiOperation(value = "Получение жанра по нескольким id", response = List.class, tags = "getGenresByIds")
    @PostMapping("/genre/list/ids")
    public ResponseEntity<List<Genre>> getGenresByIds(@RequestBody(required = true) List<Long> ids) {
        List<Genre> result = genreCache.getGenresByIds(ids);
        result.forEach(g -> {
            genreCache.getGenre(g.getId());
        });
        return ResponseEntity.ok().body(result);
    }


    @ApiOperation(value = "Получение списка жанров", response = List.class, tags = "getGenreList")
    @GetMapping("/genre/list")
    public ResponseEntity<List<Genre>> getGenreList() {
        List<Genre> result = genreCache.getAll();
        result.forEach(g -> {
            genreCache.getGenre(g.getId());
        });
        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "Добавление нового жанра", response = Genre.class, tags = "addGenre")
    @PostMapping("/genre/new")
    public ResponseEntity<Genre> addGenre(@RequestBody(required = true) Genre genre) {
        genre.setId(null);
        genre = genreCache.createGenre(genre);
        return ResponseEntity.ok().body(genre);
    }

    @ApiOperation(value = "Редактирование жанра", response = Genre.class, tags = "updateGenre")
    @PostMapping("/genre/update")
    public ResponseEntity<Genre> updateGenre(@RequestBody(required = true) Genre genre) {
        genre = genreCache.updateGenre(genre);
        return ResponseEntity.ok().body(genre);
    }

    @ApiOperation(value = "Удаление жанра из БД", response = PublishingHouse.class, tags = "deleteGenre")
    @DeleteMapping("/genre/delete/{id}")
    public ResponseEntity deleteGenre(@PathVariable("id") long id) {
        genreCache.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

}
