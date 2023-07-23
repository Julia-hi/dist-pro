package com.stpg.distrinet.services;

import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.repository.DocumentRepository;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;


    public DocumentRepository crud() {
        return documentRepository;
    }
    public Document getById(Long id) {
        Optional<Document> d = documentRepository.findById(id);
        if (d.isEmpty()){
            throw new ServiceException(String.format("Document with id = % does not exist"));
        }
        else {
            return d.get();
        }
    }

    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }
}
