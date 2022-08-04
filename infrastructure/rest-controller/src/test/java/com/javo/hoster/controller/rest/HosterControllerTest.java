package com.javo.hoster.controller.rest;

import com.javo.hoster.usecase.CheckAccessRequestUseCase;
import com.javo.hoster.usecase.ClaimForAccessRequestUseCase;
import com.javo.hoster.usecase.RespondAccessRequestUseCase;
import com.javo.hoster.usecase.crud.FindAccessRequestByIdUseCase;
import com.javo.hoster.usecase.crud.FindAllAccessRequestUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HosterController.class)
class HosterControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private CheckAccessRequestUseCase checkAccessRequestUseCase;

    @MockBean
    private ClaimForAccessRequestUseCase claimForAccessRequestUseCase;

    @MockBean
    private RespondAccessRequestUseCase respondAccessRequestUseCase;

    @Test
    void checkAccess() {
    }

    @Test
    void testCheckAccess() {
    }

    @Test
    void respondAccess() {
    }
}