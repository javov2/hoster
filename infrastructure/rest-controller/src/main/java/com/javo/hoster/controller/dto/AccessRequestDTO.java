package com.javo.hoster.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccessRequestDTO {

    private String name;
    private String company;
    private Long accessTime;

}
