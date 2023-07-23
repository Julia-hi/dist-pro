package com.stpg.distrinet.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangeVariousRequest {
    @NotBlank
    private String name;

    private String title;

    private String phoneNumber;

}
