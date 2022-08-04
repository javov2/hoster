package com.javo.hoster.controller.rest;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.usecase.crud.FindAccessRequestByIdUseCase;
import com.javo.hoster.usecase.crud.FindAllAccessRequestUseCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AccessRequestController.class)
class AccessRequestControllerTest {

    public static final String ACCESS_REQUEST_PATH = "/access-request";
    public static final String REQUEST_PARAM_ID = "?id=";

    public final String REQUESTED_AT = "2022-08-02T19:45:40.026626500";
    public final String GRANTED_UNTIL = "2022-08-03T19:45:40.026626500";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private FindAllAccessRequestUseCase findAllAccessRequestUseCase;

    @MockBean
    private FindAccessRequestByIdUseCase findAccessRequestByIdUseCase;

    @Test
    void getAllAccessRequestWhenThereIsNoElements() {
        when(findAllAccessRequestUseCase.process()).thenReturn(Flux.empty());

        webTestClient.get()
                .uri(ACCESS_REQUEST_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[]");

        verify(findAllAccessRequestUseCase, times(1)).process();
    }

    @Test
    void getAllAccessRequestWhenElementsAreReturned() throws JSONException {
        String uuidAsString = "123e4567-e89b-12d3-a456-556642440000";
        String uuidAsString2 = "000e4567-e89b-12d3-a456-556642440000";
        UUID uuid = UUID.fromString(uuidAsString);
        UUID uuid2 = UUID.fromString(uuidAsString2);

        AccessRequest accessRequest1 = AccessRequest.builder()
                .id(uuid)
                .name("")
                .company("")
                .requestedAt(convertFromString(REQUESTED_AT))
                .accessGrantedUntil(convertFromString(GRANTED_UNTIL))
                .build();

        AccessRequest accessRequest2 = AccessRequest.builder()
                .id(uuid2)
                .name("")
                .company("")
                .requestedAt(convertFromString(REQUESTED_AT))
                .accessGrantedUntil(convertFromString(GRANTED_UNTIL))
                .build();

        JSONObject accessRequestAsJson1 = new JSONObject();
        accessRequestAsJson1.put("id", uuidAsString);
        accessRequestAsJson1.put("name", "");
        accessRequestAsJson1.put("company", "");
        accessRequestAsJson1.put("requestedAt", timeStampTruncatedToSeconds(REQUESTED_AT));
        accessRequestAsJson1.put("accessGrantedUntil", timeStampTruncatedToSeconds(GRANTED_UNTIL));

        JSONObject accessRequestAsJson2 = new JSONObject();
        accessRequestAsJson2.put("id", uuidAsString2);
        accessRequestAsJson2.put("name", "");
        accessRequestAsJson2.put("company", "");
        accessRequestAsJson2.put("requestedAt", timeStampTruncatedToSeconds(REQUESTED_AT));
        accessRequestAsJson2.put("accessGrantedUntil", timeStampTruncatedToSeconds(GRANTED_UNTIL));

        JSONArray expectedJsonResponse = new JSONArray();
        expectedJsonResponse.put(accessRequestAsJson1);
        expectedJsonResponse.put(accessRequestAsJson2);

        when(findAllAccessRequestUseCase.process()).thenReturn(Flux.just(accessRequest1, accessRequest2));

        webTestClient.get()
                .uri(ACCESS_REQUEST_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedJsonResponse.toString());

        verify(findAllAccessRequestUseCase, times(1)).process();
    }

    @Test
    void getAccessRequestByIdWhenAccessRequestDoesNotExist() {
        String uuidAsString = "123e4567-e89b-12d3-a456-556642440000";
        UUID uuid = UUID.fromString(uuidAsString);

        when(findAccessRequestByIdUseCase.process(uuid)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri(ACCESS_REQUEST_PATH+REQUEST_PARAM_ID+uuidAsString)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(findAccessRequestByIdUseCase, times(1)).process(uuid);

    }

    @Test
    void getAccessRequestByIdWhenAccessRequestExist() throws JSONException {
        String uuidAsString = "123e4567-e89b-12d3-a456-556642440000";
        UUID uuid = UUID.fromString(uuidAsString);

        AccessRequest accessRequest = AccessRequest.builder()
                .id(uuid)
                .name("")
                .company("")
                .requestedAt(convertFromString(REQUESTED_AT))
                .accessGrantedUntil(convertFromString(GRANTED_UNTIL))
                .build();

        when(findAccessRequestByIdUseCase.process(uuid)).thenReturn(Mono.just(accessRequest));

        JSONObject accessRequestAsJson = new JSONObject();
        accessRequestAsJson.put("id", uuidAsString);
        accessRequestAsJson.put("name", "");
        accessRequestAsJson.put("company", "");
        accessRequestAsJson.put("requestedAt", timeStampTruncatedToSeconds(REQUESTED_AT));
        accessRequestAsJson.put("accessGrantedUntil", timeStampTruncatedToSeconds(GRANTED_UNTIL));

        webTestClient.get()
                .uri(ACCESS_REQUEST_PATH+REQUEST_PARAM_ID+uuidAsString)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(accessRequestAsJson.toString());

        verify(findAccessRequestByIdUseCase, times(1)).process(uuid);

    }

    @Test
    void getAccessRequestByIdWhenRequestParamIdIsEmptyStringExpectServerErrorStatus500() {
        String uuidAsString = "";

        webTestClient.get()
                .uri(ACCESS_REQUEST_PATH+REQUEST_PARAM_ID+uuidAsString)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(findAccessRequestByIdUseCase, times(0)).process(any());

    }

    @Test
    void getAccessRequestByIdWhenRequestParamIdIsNotValidUUIDExpectServerErrorStatus500() {
        String uuidAsString = "1234";

        webTestClient.get()
                .uri(ACCESS_REQUEST_PATH+REQUEST_PARAM_ID+uuidAsString)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(findAccessRequestByIdUseCase, times(0)).process(any());

    }

    private LocalDateTime convertFromString(String timestamp){
        return LocalDateTime.parse(timestamp).truncatedTo(ChronoUnit.SECONDS);
    }

    private String timeStampTruncatedToSeconds(String timestamp){
        return LocalDateTime.parse(timestamp).truncatedTo(ChronoUnit.SECONDS).toString();
    }

}