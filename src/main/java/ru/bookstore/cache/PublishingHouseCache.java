package ru.bookstore.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.bookstore.domain.PublishingHouse;
import ru.bookstore.repositories.PublishingHouseRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PublishingHouseCache {
    private PublishingHouseRepository publishingHouseRepository;

    @Autowired
    public PublishingHouseCache(PublishingHouseRepository publishingHouseRepository) {
        this.publishingHouseRepository = publishingHouseRepository;
    }


    public List<PublishingHouse> getAll() {
        return publishingHouseRepository.findAll();
    }


    @Cacheable(value = "publishingHouseCache", key = "#id")
    public PublishingHouse getPublishingHouse(Long id) {
        System.out.println("In PublishingHouseCache Component..");
        PublishingHouse pHouse = null;
        try {
            Optional<PublishingHouse> pHouseOpt = publishingHouseRepository.findById(id);
            if (pHouseOpt.isPresent()) {
                pHouse = pHouseOpt.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pHouse;
    }


//    @Cacheable(value = "publishingHouseCache", key = "#id")
//    public PublishingHouse getAllPublishingHouse(Long id) {
//        System.out.println("In PublishingHouseCache Component..");
//        PublishingHouse pHouse = null;
//        try {
//            Optional<PublishingHouse> pHouseOpt = publishingHouseRepository.findById(id);
//            if (pHouseOpt.isPresent()) {
//                pHouse = pHouseOpt.get();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return pHouse;
//    }

    @CacheEvict(value = "publishingHouseCache", key = "#id")
    public void deletePublishingHouse(Long id) {
        System.out.println("In PublishingHouseCache Component..");
        publishingHouseRepository.deleteById(id);
    }

    @CachePut(value = "publishingHouseCache")
    public PublishingHouse updatePublishingHouse(PublishingHouse pHouse) {
        System.out.println("In PublishingHouseCache Component..");
        pHouse = publishingHouseRepository.save(pHouse);
        return pHouse;
    }


}
