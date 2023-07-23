package com.stpg.distrinet.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class PinDocRequest {
    @NotBlank
    private long user_id;

    @NotBlank
    private long document_id;
}
