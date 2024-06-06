package com.oceans.pollution.controller;

import com.oceans.pollution.model.PlasticCollection;
import com.oceans.pollution.repository.PlasticCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class PlasticCollectionController {

    @Autowired
    private PlasticCollectionRepository repository;

    @GetMapping
    public List<PlasticCollection> getAllCollections() {
        return repository.findAll();
    }

    @PostMapping
    public PlasticCollection addCollection(@RequestBody PlasticCollection collection) {
        return repository.save(collection);
    }
}
