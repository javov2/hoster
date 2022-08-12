package com.javo.hoster.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccessRequestDTO {

    @NotNull
    private String name;
    @NotNull
    private String company;
    @NotNull
    @Email
    private String email;
    @NotNull
    private Long accessTime;

}
