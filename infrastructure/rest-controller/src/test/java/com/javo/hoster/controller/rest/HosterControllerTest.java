package com.javo.hoster.controller.rest;

import com.javo.hoster.model.Access;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;
import com.javo.hoster.usecase.CheckAccessUseCase;
import com.javo.hoster.usecase.ClaimForAccessUseCase;
import com.javo.hoster.usecase.FindAllAccessRequestToReviewUseCase;
import com.javo.hoster.usecase.RespondAccessUseCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HosterController.class)
class HosterControllerTest {

    public static final String CHECK_ACCESS_PATH = "/check-access";
    public static final String CLAIM_ACCESS_PATH = "/claim-access";
    public static final String RESPOND_ACCESS_PATH = "/respond-access";
    public static final String FIND_ACCESS_REQUEST_TO_REVIEW_PATH = "/find-access-request-to-review";

    public static final String REQUESTED_AT = "2022-08-02T19:45:40";
    public static final Long ACCESS_TIME = 10L;
    public static final String VALID_EMAIL = "test@test.test";
    public static final String UUID_EXAMPLE = "123e4567-e89b-12d3-a456-556642440000";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private CheckAccessUseCase checkAccessUseCase;

    @MockBean
    private ClaimForAccessUseCase claimForAccessUseCase;

    @MockBean
    private RespondAccessUseCase respondAccessUseCase;

    @MockBean
    private FindAllAccessRequestToReviewUseCase findAllAccessRequestToReviewUseCase;

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

        when(claimForAccessUseCase.process(accessRequest)).thenReturn(Mono.just(accessRequestConfirmation));

        webTestClient.post()
                .uri(CLAIM_ACCESS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(accessRequestAsJson.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(accessRequestConfirmationAsJson.toString());

        verify(claimForAccessUseCase, times(1)).process(accessRequest);

    }

    @ParameterizedTest
    @MethodSource("invalidClamAccessJSONRequest")
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

        verify(claimForAccessUseCase, times(0)).process(accessRequest);

    }

    private static Stream<String> invalidClamAccessJSONRequest() throws JSONException {
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

        verify(claimForAccessUseCase, times(0)).process(any());

    }

    @Test
    void respondAccessWhenRequestURLAndParamsAreOkStatus200AndAccessDTOExpected() throws JSONException {

        String RequestURI = RESPOND_ACCESS_PATH+"?id="+UUID_EXAMPLE+"&isGranted="+ Boolean.TRUE;
        UUID id = UUID.fromString(UUID_EXAMPLE);
        LocalDateTime reviewedAt = LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS);

        var access = Access.builder()
                .isGranted(Boolean.TRUE)
                .id(id)
                .reviewedAt(reviewedAt)
                .build();

        JSONObject accessDTOAsJson = new JSONObject();
        accessDTOAsJson.put("id", id);
        accessDTOAsJson.put("reviewedAt", reviewedAt);
        accessDTOAsJson.put("granted", Boolean.TRUE);

        when(respondAccessUseCase.process(id, Boolean.TRUE)).thenReturn(Mono.just(access));

        webTestClient.post()
                .uri(RequestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(accessDTOAsJson.toString());

        verify(respondAccessUseCase, times(1)).process(id, Boolean.TRUE);

    }

    @ParameterizedTest
    @MethodSource("invalidRespondAccessURIs")
    void respondAccessWhenRequestURLIsOkButParamsAreNotValidExpectedStatus400BadRequest(String RequestURI) {

        UUID id = UUID.fromString(UUID_EXAMPLE);

        webTestClient.post()
                .uri(RequestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();;

        verify(respondAccessUseCase, times(0)).process(any(), eq(Boolean.TRUE));

    }

    private static Stream<String> invalidRespondAccessURIs() {
        return Stream.of(
                RESPOND_ACCESS_PATH+"?id="+UUID_EXAMPLE+"&isGranted="+ 5,
                RESPOND_ACCESS_PATH+"?id="+123+"&isGranted="+ Boolean.TRUE
        );
    }

    @Test
    void respondAccessWhenRequestURLAndParamsAreOkUseCaseThrowsAnExceptionStatus500Expected() {

        String RequestURI = RESPOND_ACCESS_PATH+"?id="+UUID_EXAMPLE+"&isGranted="+ Boolean.TRUE;
        UUID id = UUID.fromString(UUID_EXAMPLE);
        LocalDateTime reviewedAt = LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS);

        var access = Access.builder()
                .isGranted(Boolean.TRUE)
                .id(id)
                .reviewedAt(reviewedAt)
                .build();

        when(respondAccessUseCase.process(id, Boolean.TRUE)).thenReturn(Mono.error(RuntimeException::new));

        webTestClient.post()
                .uri(RequestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(respondAccessUseCase, times(1)).process(id, Boolean.TRUE);

    }

    @Test
    void checkAccessWhenAccessWasReviewedAndAcceptedExpectStatus200AndAccessDTOBodyResponse() throws JSONException {

        String RequestURI = CHECK_ACCESS_PATH+"?id="+UUID_EXAMPLE;
        UUID id = UUID.fromString(UUID_EXAMPLE);
        LocalDateTime reviewedAt = LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS);

        var access = Access.builder()
                .isGranted(Boolean.TRUE)
                .id(id)
                .reviewedAt(reviewedAt)
                .build();

        JSONObject accessDTOAsJson = new JSONObject();
        accessDTOAsJson.put("id", id);
        accessDTOAsJson.put("reviewedAt", reviewedAt);
        accessDTOAsJson.put("granted", Boolean.TRUE);

        when(checkAccessUseCase.process(id)).thenReturn(Mono.just(access));

        webTestClient.get()
                .uri(RequestURI)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(accessDTOAsJson.toString());

        verify(checkAccessUseCase, times(1)).process(id);

    }

    @Test
    void checkAccessWhenAccessWasNotReviewedAccessDoesNotExistExpectStatus200EmptyBody() {

        String RequestURI = CHECK_ACCESS_PATH+"?id="+UUID_EXAMPLE;
        UUID id = UUID.fromString(UUID_EXAMPLE);
        LocalDateTime reviewedAt = LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS);

        var access = Access.builder()
                .isGranted(Boolean.TRUE)
                .id(id)
                .reviewedAt(reviewedAt)
                .build();

        when(checkAccessUseCase.process(id)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri(RequestURI)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(checkAccessUseCase, times(1)).process(id);

    }

    @Test
    void checkAccessUUIDRequestParamIsNotValidExpectStatus400BadRequest() {

        var requestURI = CHECK_ACCESS_PATH+"?id="+123;

        webTestClient.get()
                .uri(requestURI)
                .exchange()
                .expectStatus().isBadRequest();;

        verify(checkAccessUseCase, times(0)).process(any());

    }

    @Test
    void findAllAccessRequestToReviewWhenElementsAreReturned200Expected() throws JSONException {
        String uuidAsString = "123e4567-e89b-12d3-a456-556642440000";
        String uuidAsString2 = "000e4567-e89b-12d3-a456-556642440000";
        UUID uuid = UUID.fromString(uuidAsString);
        UUID uuid2 = UUID.fromString(uuidAsString2);

        AccessRequest accessRequest1 = AccessRequest.builder()
                .id(uuid)
                .name("")
                .company("")
                .requestedAt(convertFromString(REQUESTED_AT))
                .accessTime(ACCESS_TIME)
                .build();

        AccessRequest accessRequest2 = AccessRequest.builder()
                .id(uuid2)
                .name("")
                .company("")
                .requestedAt(convertFromString(REQUESTED_AT))
                .accessTime(ACCESS_TIME)
                .build();

        JSONObject accessRequestAsJson1 = new JSONObject();
        accessRequestAsJson1.put("id", uuidAsString);
        accessRequestAsJson1.put("name", "");
        accessRequestAsJson1.put("company", "");
        accessRequestAsJson1.put("requestedAt", timeStampTruncatedToSeconds(REQUESTED_AT));
        accessRequestAsJson1.put("accessTime", ACCESS_TIME);

        JSONObject accessRequestAsJson2 = new JSONObject();
        accessRequestAsJson2.put("id", uuidAsString2);
        accessRequestAsJson2.put("name", "");
        accessRequestAsJson2.put("company", "");
        accessRequestAsJson2.put("requestedAt", timeStampTruncatedToSeconds(REQUESTED_AT));
        accessRequestAsJson2.put("accessTime", ACCESS_TIME);

        JSONArray expectedJsonResponse = new JSONArray();
        expectedJsonResponse.put(accessRequestAsJson1);
        expectedJsonResponse.put(accessRequestAsJson2);

        when(findAllAccessRequestToReviewUseCase.process()).thenReturn(Flux.just(accessRequest1, accessRequest2));

        webTestClient.get()
                .uri(FIND_ACCESS_REQUEST_TO_REVIEW_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedJsonResponse.toString());

        verify(findAllAccessRequestToReviewUseCase, times(1)).process();
    }

    private LocalDateTime convertFromString(String timestamp){
        return LocalDateTime.parse(timestamp).truncatedTo(ChronoUnit.SECONDS);
    }

    private String timeStampTruncatedToSeconds(String timestamp){
        return LocalDateTime.parse(timestamp).truncatedTo(ChronoUnit.SECONDS).toString();
    }
}