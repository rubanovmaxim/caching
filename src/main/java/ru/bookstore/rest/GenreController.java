package ru.bookstore.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bookstore.cache.GenreCache;
import ru.bookstore.domain.Genre;
import ru.bookstore.domain.PublishingHouse;
import ru.bookstore.repositories.GenreRepository;

import java.util.List;

@Api(tags = "GenreController", description = "Контроллер для работы жанрами")
@RestController
public class GenreController {

    private GenreCache genreCache;


    @Autowired
    public GenreController(GenreCache genreCache) {
        this.genreCache = genreCache;
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
        genre = genreCache.updateGenre(genre);
        return ResponseEntity.ok().body(genre);
    }

    @ApiOperation(value = "Редактироване  жанра", response = Genre.class, tags = "updateGenre")
    @PutMapping("/genre/update/{genreId}")
    public ResponseEntity<Genre> updateGenre(@PathVariable("genreId") long genreId,
                                             @RequestBody(required = true) Genre genre) {
        genre.setId(genreId);
        genre = genreCache.updateGenre(genre);
        return ResponseEntity.ok().body(genre);
    }

    @ApiOperation(value = "Удаление жанра из БД", response = PublishingHouse.class, tags = "deleteGenre")
    @DeleteMapping("/publishinghouse/delete/{genreId}")
    public ResponseEntity deleteGenre(@PathVariable("genreId") long genreId) {
        genreCache.deleteGenre(genreId);
        return ResponseEntity.ok().build();
    }

}
