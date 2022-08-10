package com.javo.hoster.controller.rest;

import com.javo.hoster.controller.dto.AccessRequestDTO;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;
import com.javo.hoster.usecase.CheckAccessRequestUseCase;
import com.javo.hoster.usecase.ClaimForAccessRequestUseCase;
import com.javo.hoster.usecase.RespondAccessRequestUseCase;
import com.javo.hoster.usecase.crud.FindAccessRequestByIdUseCase;
import com.javo.hoster.usecase.crud.FindAllAccessRequestUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HosterController.class)
class HosterControllerTest {

    public static final String CHECK_ACCESS_PATH = "/check-access";
    public static final String CLAIM_ACCESS_PATH = "/claim-access";
    public static final String RESPOND_ACCESS_PATH = "/respond-access";

    public static final String REQUESTED_AT = "2022-08-02T19:45:40";
    public static final Long ACCESS_TIME = 10L;
    public static final String VALID_EMAIL = "test@test.test";
    public static final String UUID_EXAMPLE = "123e4567-e89b-12d3-a456-556642440000";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private CheckAccessRequestUseCase checkAccessRequestUseCase;

    @MockBean
    private ClaimForAccessRequestUseCase claimForAccessRequestUseCase;

    @MockBean
    private RespondAccessRequestUseCase respondAccessRequestUseCase;

    @Test
    void claimAccessWhenAccessRequestDTOIsValidAndAnAccessRequestConfirmationDTOIsReturned() throws JSONException {

        AccessRequest accessRequest = AccessRequest.builder()
                .name("")
                .company("")
                .accessTime(ACCESS_TIME)
                .email(VALID_EMAIL)
                .build();

        JSONObject accessRequestAsJson = new JSONObject();
        accessRequestAsJson.put("name", "");
        accessRequestAsJson.put("company", "");
        accessRequestAsJson.put("email", VALID_EMAIL);
        accessRequestAsJson.put("accessTime", ACCESS_TIME);

        AccessRequestConfirmation accessRequestConfirmation = AccessRequestConfirmation.builder()
                .id(UUID.fromString(UUID_EXAMPLE))
                .build();

        JSONObject accessRequestConfirmationAsJson = new JSONObject();
        accessRequestConfirmationAsJson.put("id", UUID_EXAMPLE);
        accessRequestConfirmationAsJson.put("receivedAt", JSONObject.NULL);

        when(claimForAccessRequestUseCase.process(accessRequest)).thenReturn(Mono.just(accessRequestConfirmation));

        webTestClient.post()
                .uri(CLAIM_ACCESS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(accessRequestAsJson.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(accessRequestConfirmationAsJson.toString());

        verify(claimForAccessRequestUseCase, times(1)).process(accessRequest);

    }

    @ParameterizedTest
    @MethodSource("invalidJSONRequest")
    void claimAccessWhenAccessRequestDTOIsAnInvalidJsonStatus400IsExpected(String bodyRequestJson) throws JSONException {

        AccessRequest accessRequest = AccessRequest.builder()
                .name("")
                .company("")
                .accessTime(ACCESS_TIME)
                .build();

        AccessRequestConfirmation accessRequestConfirmation = AccessRequestConfirmation.builder()
                .id(UUID.fromString(UUID_EXAMPLE))
                .build();

        JSONObject accessRequestConfirmationAsJson = new JSONObject();
        accessRequestConfirmationAsJson.put("id", UUID_EXAMPLE);
        accessRequestConfirmationAsJson.put("receivedAt", JSONObject.NULL);

        webTestClient.post()
                .uri(CLAIM_ACCESS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(bodyRequestJson)
                .exchange()
                .expectStatus().isBadRequest();

        verify(claimForAccessRequestUseCase, times(0)).process(accessRequest);

    }

    private static Stream<String> invalidJSONRequest() throws JSONException {
        JSONObject incompleteJson = new JSONObject();
        incompleteJson.put("name", "");
        incompleteJson.put("company", "");

        JSONObject invalidEmailJson = new JSONObject();
        invalidEmailJson.put("name", "");
        invalidEmailJson.put("company", "");
        invalidEmailJson.put("accessTime", ACCESS_TIME);
        invalidEmailJson.put("email", "test@");

        return Stream.of(
                incompleteJson.toString(),
                invalidEmailJson.toString()
        );
    }

    @Test
    void claimAccessWithGETInsteadOfPOSTStatus404Expected() throws JSONException {

        webTestClient.get()
                .uri(CLAIM_ACCESS_PATH)
                .exchange()
                .expectStatus().is4xxClientError();

        verify(claimForAccessRequestUseCase, times(0)).process(any());

    }

    @Test
    void respondAccess() {
    }

    @Test
    void checkAccess() {
    }


}