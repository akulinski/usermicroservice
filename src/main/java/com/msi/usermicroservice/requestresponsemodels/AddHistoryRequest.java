package com.msi.usermicroservice.requestresponsemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddHistoryRequest {
    private String userId;
    private String value;
}
