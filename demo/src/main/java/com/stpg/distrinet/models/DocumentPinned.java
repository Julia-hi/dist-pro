package com.stpg.distrinet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name="document_pinned")
public class DocumentPinned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
//    @JsonIncludeProperties("id")
    private Document document;

    @ManyToOne
    @JsonIgnore
    private User user;

    public DocumentPinned(){}


    public DocumentPinned(User u, Document d){
        user = u;
        document = d;
    }
}
