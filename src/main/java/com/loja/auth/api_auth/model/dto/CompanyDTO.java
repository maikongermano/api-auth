package com.loja.auth.api_auth.model.dto;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String address;
    private String clientDescription;
    private String titleHmtl;
    private String clientDomain;
    private String clientLogo;
    private String clientName;
    private Boolean useBackgroundDefaultPage;
    private String clientBackground;
}
