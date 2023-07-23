package com.stpg.distrinet.controllers;



import com.stpg.distrinet.models.DocumentType;
import com.stpg.distrinet.services.DocumentTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/document_type")
public class DocumentTypeController {

    @Autowired
    DocumentTypeService documentTypeService;

    @GetMapping("")
    public List<DocumentType> getDocuments() {
        return documentTypeService.getDocumentTypes();
    }

    @GetMapping("/{id}")
    public DocumentType getById(@PathVariable("id") Long id){
        return documentTypeService.getById(id);
    }


}
