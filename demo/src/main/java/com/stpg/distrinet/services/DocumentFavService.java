package com.stpg.distrinet.services;

import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.models.DocumentFavPinnedDTO;
import com.stpg.distrinet.models.User;
import com.stpg.distrinet.models.test.*;
import com.stpg.distrinet.repository.DocumentFavRepository;
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
public class DocumentFavService {

    @Autowired
    private DocumentFavRepository documentFavRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    public DocumentFavRepository crud() {
        return documentFavRepository;
    }

    public DocumentFav getDocumentFav(long favId) {
        return documentFavRepository.getById(favId);
    }

    public Set<DocumentFav> getFavsFrom(long uid){
        return documentFavRepository.findAllByUser_Id(uid);
    }
    public Set<DocumentFavPinnedDTO> getFavsFromMin(long uid){

//        Set<DocumentFavPinnedDTO> documentFavPinnedDTOS = new HashSet<>();
//        Set<DocumentFav> documents = documentFavRepository.findAllByUser_Id(uid);
//        for (DocumentFav documentFav : documents) {
//            DocumentFavPinnedDTO documentFavPinnedDTO = new DocumentFavPinnedDTO(documentFav);
//            documentFavPinnedDTOS.add(documentFavPinnedDTO);
//        }
        return documentFavRepository.findAllByUser_Id(uid).stream().map(DocumentFavPinnedDTO::new)
                .collect(Collectors.toSet());
    }

    public DocumentFav getFavKnowing(long uid, long docid) {

        Set<DocumentFav> allTheirPins = documentFavRepository.findAllByUser_Id(uid);
        DocumentFav favDoc = null;

        for (DocumentFav dp : allTheirPins){
            if (dp.getDocument().getId() == docid){
                // found fav document
                favDoc = dp;
                break;
            }
        }

        return favDoc;
    }

    public List<DocumentFav> addFavs(long uid, List<Document> documents) {
        User user = userService.getUser(uid);
        ArrayList<DocumentFav> toAdd = new ArrayList<>();
        for (Document document : documents) {
            DocumentFav documentFav = new DocumentFav(user, document);
            toAdd.add(documentFav);
        }

        ArrayList<DocumentFav> documentFavList = (ArrayList<DocumentFav>) documentFavRepository.saveAll(toAdd);
        return documentFavList;
    }

    public List<DocumentFav> getDocumentsFav() {
        return documentFavRepository.findAll();
    }

    public DocumentFav addDocumentFav(long userId, Document document) {
        try {
            DocumentFav possibleFav = getFavKnowing(userId, document.getId());
            if (possibleFav == null) {
                User user = userService.getUser(userId);

                DocumentFav documentFav = new DocumentFav(user, document);


                documentFav = documentFavRepository.save(documentFav);
                return documentFav;
            }
            else return possibleFav;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public String deleteFav(Long id){
        documentFavRepository.deleteById(id);
        return "deleted";
    }
}
