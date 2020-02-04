package ru.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bookstore.cache.GenreCache;
import ru.bookstore.domain.Genre;

import java.util.List;

@EnableAsync
@EnableCaching
@RestController
@SpringBootApplication
public class Main {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    GenreCache genreCache;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "caching service";
    }


//    @GetMapping("/genre1/{id}")
//    @ResponseBody
//    public ResponseEntity<Genre> getItem(@PathVariable long id) {
//
//        System.out.println("RestController 1 1..  " + cacheManager.getCacheNames());
//        long start = System.currentTimeMillis();
////        Optional<Genre> oenreOpt = genreRepository.findById(itemId);
//
//        Genre item = genreCache.getGenre(id);
//        long end = System.currentTimeMillis();
//        System.out.println("Took : " + ((end - start) / 1000 + " sec."));
//        return new ResponseEntity<Genre>(item, HttpStatus.OK);
//    }


//    @GetMapping("/genre/list1")
//    @ResponseBody
//    public ResponseEntity< List<Genre>> getAll() {
//        System.out.println("RestController ALl LIST..  " + cacheManager.getCacheNames());
//        long start = System.currentTimeMillis();
////        Optional<Genre> oenreOpt = genreRepository.findById(itemId);
//
//        List<Genre> result= genreCache.getAll();
//        result.forEach(g->{
//            genreCache.getGenre(g.getId());
//        });
//
//        long end = System.currentTimeMillis();
//        System.out.println("Took : " + ((end - start) / 1000 + " sec."));
//        return new ResponseEntity< List<Genre>>(result, HttpStatus.OK);
//    }

}
