package com.stpg.distrinet.controllers;


import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.models.DocumentFavPinnedDTO;
import com.stpg.distrinet.models.DocumentPinned;
import com.stpg.distrinet.models.test.DocumentFav;
import com.stpg.distrinet.security.services.UserDetailsImpl;
import com.stpg.distrinet.services.DocumentFavService;
import com.stpg.distrinet.services.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/document_fav")
public class DocumentFavController {

    @Autowired
    DocumentFavService documentFavService;
    @Autowired
    DocumentService documentService;


    @GetMapping("")
    public List<DocumentFav> getDocumentsFav() {
        return documentFavService.getDocumentsFav();
    }


    @GetMapping(path = "/from/{userId}")
    public Set<DocumentFav> getFavFrom(@PathVariable("userId") long userId) {
        return documentFavService.getFavsFrom(userId);
    }

    @GetMapping(path = "/myFavs")
    public Set<DocumentFav> getMyFavs(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long uid = userDetails.getId();
        Set<DocumentFav> temporary = documentFavService.getFavsFrom(uid);
        return temporary;
    }
    @GetMapping(path = "/myFavs/min")
    public Set<DocumentFavPinnedDTO> getMyFavsMin(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long uid = userDetails.getId();
        Set<DocumentFavPinnedDTO> temporary = documentFavService.getFavsFromMin(uid);
        return temporary;
    }

    @PostMapping(path = "/addMyFav", consumes = "application/json")
    @Transactional
    //@PreAuthorize("hasAnyRole('ADMIN')")
    // VALIDATED NOT WORKING
    public DocumentFav addMyFav(@RequestBody Document document, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long uid = userDetails.getId();

        DocumentFav docFav = documentFavService.addDocumentFav(uid, document);
        return docFav;
    }

    @PostMapping(path = "/addFavs", consumes = "application/json")
    @Transactional
    public List<DocumentFav> addPins(@RequestBody List<Document> documentList, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<DocumentFav> docFav = documentFavService.addFavs(userDetails.getId(), documentList);
        return docFav;
    }

    @GetMapping( "/removeMyFav/{id}")
    public Document deleteMyFav(@PathVariable Long id, Authentication authentication) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        DocumentFav fav = documentFavService.getDocumentFav(id);
        if (fav.getUser().getUsername().equals(userDetails.getUsername())) {
            documentFavService.deleteFav(id);
            return fav.getDocument();
        }
        else {
            throw new Exception("ERROR");
        }
    }


}
