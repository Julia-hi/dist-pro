package com.stpg.distrinet.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.Getter;

//@Entity
//@Getter
//@Setter

@Getter
public class DocumentFavPinnedDTO {
    private Long id;
    @JsonIncludeProperties("id")
    private Document document;

    public DocumentFavPinnedDTO(DocumentPinned documentPinned) {
        id = documentPinned.getId();
        document = documentPinned.getDocument();
    }
    public DocumentFavPinnedDTO(DocumentFav documentFav) {
        id = documentFav.getId();
        document = documentFav.getDocument();
    }
}
