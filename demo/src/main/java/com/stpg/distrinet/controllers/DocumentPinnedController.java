package com.stpg.distrinet.controllers;

import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.models.DocumentFavPinnedDTO;
import com.stpg.distrinet.models.DocumentPinned;
import com.stpg.distrinet.security.services.UserDetailsImpl;
import com.stpg.distrinet.services.DocumentPinnedService;
import com.stpg.distrinet.services.DocumentService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/document_pinned")
public class DocumentPinnedController {

    @Autowired
    DocumentPinnedService documentPinnedService;
    @Autowired
    DocumentService documentService;


    @GetMapping("")
    public List<DocumentPinned> getDocumentsPinned() {
        return documentPinnedService.getDocumentsPinned();
    }

//    @GetMapping(path = "/from/{userId}/pinned/{docId}")
//    public DocumentPinned getPinKnowing(@PathVariable("userId") long userId, @PathVariable("docId") long docId) {
//        return documentPinnedService.getPinKnowing(userId, docId);
//    }

    @GetMapping(path = "/from/{userId}")
    public Set<DocumentPinned> getPinFrom(@PathVariable("userId") long userId) {
        return documentPinnedService.getPinsFrom(userId);
    }

    @GetMapping(path = "/myPins")
    public Set<DocumentPinned> getMyPins(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long uid = userDetails.getId();
        Set<DocumentPinned> temporary = documentPinnedService.getPinsFrom(uid);
        return temporary;
    }

    @GetMapping(path = "/myPins/min")
    public Set<DocumentFavPinnedDTO> getMyPinsMin(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long uid = userDetails.getId();
        Set<DocumentFavPinnedDTO> temporary = documentPinnedService.getPinsFromMin(uid);
        return temporary;
    }

    @PostMapping(path = "/addMyPin", consumes = "application/json")
    @Transactional
    //@PreAuthorize("hasAnyRole('ADMIN')")
    // VALIDATED NOT WORKING
    public DocumentPinned addMyPin(@RequestBody Document document, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        DocumentPinned docPinned = documentPinnedService.addDocumentPinned(userDetails.getId(), document);
        return docPinned;
    }

    @PostMapping(path = "/addPins", consumes = "application/json")
    @Transactional
    public List<DocumentPinned> addPins(@RequestBody List<Document> documentList, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<DocumentPinned> docPinned = documentPinnedService.addPins(userDetails.getId(), documentList);
        return docPinned;
    }

    @GetMapping( "/removeMyPin/{id}")
    public Document deleteMyPin(@PathVariable Long id, Authentication authentication) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        DocumentPinned pin = documentPinnedService.getDocumentPinned(id);
        if (pin.getUser().getUsername().equals(userDetails.getUsername())) {
            documentPinnedService.deletePin(id);
            return pin.getDocument();
        }
        else {
            throw new Exception("ERROR");
        }
    }


}
