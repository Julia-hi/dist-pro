package com.stpg.distrinet.controllers;


import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.services.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;


    @GetMapping("")
    public List<Document> getDocuments() {
        List<Document> documentList = documentService.getDocuments();
        return documentList;

    }

    @GetMapping("/{id}")
    public Document getById(@PathVariable("id") Long id){

        return documentService.getById(id);
    }


}
