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

    public List<Genre> getAll(){
        return genreRepository.findAll();
    }

    @Cacheable(value="genreCache", key="#id")
    public Genre getGenre(Long id){
        System.out.println("In GenreCache Component..");
        Genre genre = null;
        try{
            Optional<Genre> genreOpt = genreRepository.findById(id);
            if(genreOpt.isPresent()){
                genre = genreOpt.get();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return genre;
    }

    @CacheEvict(value="genreCache",key = "#id")
    public void deleteGenre(Long id){
        System.out.println("In GenreCache Component..");
        genreRepository.deleteById(id);
    }

    @CachePut(value="genreCache")
    public Genre updateGenre(Genre genre){
        System.out.println("In GenreCache Component..");
        genre = genreRepository.save(genre);
        return genre;
    }


}
