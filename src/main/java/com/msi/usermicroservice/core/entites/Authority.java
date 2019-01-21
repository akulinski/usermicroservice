package com.msi.usermicroservice.core.entites;

public enum Authority {

    PATIENT("PATIENT"),
    DOCTOR("DOCTOR"),
    ADMIN("ADMIN");

    private String value;
    private Authority(String value){
        this.value = value;
    }
}
