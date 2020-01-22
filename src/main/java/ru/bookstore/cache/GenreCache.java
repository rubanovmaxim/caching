package ru.bookstore.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.bookstore.domain.Genre;
import ru.bookstore.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@Component
//@CacheConfig(cacheNames={"genreCache"})
public class GenreCache {

    private GenreRepository genreRepository;

    @Autowired
    public GenreCache(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Cacheable(value = "genreCache", key = "#id")
    public Genre getGenre(Long id) {
        System.out.println("In GenreCache Get Component..");
        Genre genre = null;
        try {
            Optional<Genre> genreOpt = genreRepository.findById(id);
            if (genreOpt.isPresent()) {
                genre = genreOpt.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genre;
    }


    @Cacheable(value = "genreCache", key = "#ids")
    public List<Genre> getGenresByIds(List<Long> ids) {
        List<Genre> result = genreRepository.findAllById(ids);
        return result;
    }

    @CacheEvict(value = "genreCache", key = "#id")
    public void deleteGenre(Long id) {
        System.out.println("In GenreCache Delete Component..");
        genreRepository.deleteById(id);
    }

    @CachePut(value = "genreCache")
    public Genre createGenre(Genre genre) {
        System.out.println("In GenreCache Create Component..");
        genre = genreRepository.save(genre);
        return genre;
    }

    @CachePut(value = "genreCache", key = "#genre.id")
    public Genre updateGenre(Genre genre) {
        System.out.println("In GenreCache Update Component..");
        genre = genreRepository.save(genre);
        return genre;
    }

}
