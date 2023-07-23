package com.stpg.distrinet.services;

import com.stpg.distrinet.models.DocumentType;
import com.stpg.distrinet.repository.DocumentTypeRepository;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeService {
    @Autowired
    private DocumentTypeRepository documentTypeRepository;


    public DocumentTypeRepository crud() {
        return documentTypeRepository;
    }
    public DocumentType getById(Long id) {
        Optional<DocumentType> d = documentTypeRepository.findById(id);
        if (d.isEmpty()){
            throw new ServiceException(String.format("DocumentType with id = % does not exist"));
        }
        else {
            return d.get();
        }
    }

    public List<DocumentType> getDocumentTypes() {
        return documentTypeRepository.findAll();
    }
}
