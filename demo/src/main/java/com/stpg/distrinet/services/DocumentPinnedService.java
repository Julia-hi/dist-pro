package com.stpg.distrinet.services;

import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.models.DocumentFavPinnedDTO;
import com.stpg.distrinet.models.DocumentPinned;
import com.stpg.distrinet.models.User;
import com.stpg.distrinet.repository.DocumentPinnedRepository;
import com.stpg.distrinet.security.services.UserService;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentPinnedService {

    @Autowired
    private DocumentPinnedRepository documentPinnedRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    public DocumentPinnedRepository crud() {
        return documentPinnedRepository;
    }

    public DocumentPinned getDocumentPinned(long pinId) {
        return documentPinnedRepository.getById(pinId);
    }

    public Set<DocumentPinned> getPinsFrom(long uid) {
        return documentPinnedRepository.findAllByUser_Id(uid);
    }

    public Set<DocumentFavPinnedDTO> getPinsFromMin(long uid) {
//        Set<DocumentFavPinnedDTO> documentFavPinnedDTOS = new HashSet<>();
//        Set<DocumentFav> documents = documentFavRepository.findAllByUser_Id(uid);
//        for (DocumentFav documentFav : documents) {
//            DocumentFavPinnedDTO documentFavPinnedDTO = new DocumentFavPinnedDTO(documentFav);
//            documentFavPinnedDTOS.add(documentFavPinnedDTO);
//        }
        return documentPinnedRepository.findAllByUser_Id(uid).stream().map(DocumentFavPinnedDTO::new)
                .collect(Collectors.toSet());
    }

    public DocumentPinned getPinKnowing(long uid, long docid) {

        Set<DocumentPinned> allTheirPins = documentPinnedRepository.findAllByUser_Id(uid);
//        Set<DocumentPinned> allTheirPins = userService.getUserPinned(uid);
        DocumentPinned pinnedDoc = null;

        for (DocumentPinned dp : allTheirPins) {
            if (dp.getDocument().getId() == docid) {
                // found pinned document
                pinnedDoc = dp;
                break;
            }
        }

        return pinnedDoc;
    }

    public List<DocumentPinned> getDocumentsPinned() {
        return documentPinnedRepository.findAll();
    }

    public DocumentPinned addDocumentPinned(long uid, Document document) {

        User user = userService.getUser(uid);
        DocumentPinned documentPinned = new DocumentPinned(user, document);
        documentPinned = documentPinnedRepository.save(documentPinned);
        return documentPinned;

    }

    public List<DocumentPinned> addPins(long uid, List<Document> documents) {
        User user = userService.getUser(uid);
        ArrayList<DocumentPinned> toAdd = new ArrayList<>();
        for (Document document : documents) {
            DocumentPinned documentPinned = new DocumentPinned(user, document);
            toAdd.add(documentPinned);
        }

        ArrayList<DocumentPinned> documentPinnedList = (ArrayList<DocumentPinned>) documentPinnedRepository.saveAll(toAdd);
        return documentPinnedList;
    }

    public String deletePin(Long id) {
        // deleting child itself
        documentPinnedRepository.deleteById(id);
        return "deleted";
    }
}
