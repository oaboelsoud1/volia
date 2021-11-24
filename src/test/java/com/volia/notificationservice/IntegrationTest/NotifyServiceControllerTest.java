package com.volia.notificationservice.IntegrationTest;

import com.volia.notificationservice.core.common.dto.CreateNotificationRequestDto;
import com.volia.notificationservice.core.common.dto.GenericCreationResponse;
import com.volia.notificationservice.core.common.dto.NotifyCustomerRequestDto;
import com.volia.notificationservice.core.common.dto.NotifyCustomerResponseDto;
import com.volia.notificationservice.model.dto.CityCreationRequestDto;
import com.volia.notificationservice.model.dto.UserCreationRequestDto;
import com.volia.notificationservice.model.entity.NotificationType;
import com.volia.notificationservice.service.city.CityService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class NotifyServiceControllerTest extends BaseIntegrationTest{

    @Autowired
    CityService cityService;

    @Test
    void notifyCustomers_WhenWithoutCriteria_ShouldNotifyAllCustomers(){

        CityCreationRequestDto cityCreationRequestDto = new CityCreationRequestDto();

        cityCreationRequestDto.setName("Berlin");

        GenericCreationResponse cityCreationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(cityCreationRequestDto)
                        .when()
                        .post("/cities")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(GenericCreationResponse.class);


        UserCreationRequestDto userCreationRequestDto = new UserCreationRequestDto();
        userCreationRequestDto.setName("VoliaTest");
        userCreationRequestDto.setCityName(cityCreationRequestDto.getName());
        userCreationRequestDto.setNotificationType(NotificationType.SMS);

        given().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userCreationRequestDto)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());


        CreateNotificationRequestDto createNotificationRequestDto = CreateNotificationRequestDto.builder().text("The food is ready").build();

        GenericCreationResponse notificationCreationResponse
                =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(createNotificationRequestDto)
                        .when()
                        .post("/notifications")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(GenericCreationResponse.class);

        //notify Users
        NotifyCustomerRequestDto notifyCustomerRequestDto = NotifyCustomerRequestDto.builder()
                .notificationCode(notificationCreationResponse.getCode()).build();

        NotifyCustomerResponseDto notifyCustomerResponseDto =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(notifyCustomerRequestDto)
                        .when()
                        .post("/notifications/notify")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(NotifyCustomerResponseDto.class);


        Assertions.assertEquals(1,notifyCustomerResponseDto.getTotalNumberOfNotifiedCustomer());
    }

    @Test
    void notifyCustomers_WhenWithCityCriteria_ShouldNotifyCustomersInSpecifiedCity(){

        CityCreationRequestDto cityCreationRequestDto = new CityCreationRequestDto();

        cityCreationRequestDto.setName("Berlin");

        GenericCreationResponse cityCreationResponse =
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(cityCreationRequestDto)
        .when()
                .post("/cities")
        .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(GenericCreationResponse.class);


        UserCreationRequestDto userCreationRequestDto = new UserCreationRequestDto();
        userCreationRequestDto.setName("VoliaTest");
        userCreationRequestDto.setCityName(cityCreationRequestDto.getName());
        userCreationRequestDto.setNotificationType(NotificationType.SMS);

        given().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userCreationRequestDto)
        .when()
                .post("/users")
        .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());


        CreateNotificationRequestDto createNotificationRequestDto = CreateNotificationRequestDto.builder().text("The food is ready").build();

        GenericCreationResponse notificationCreationResponse
                =
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createNotificationRequestDto)
        .when()
                .post("/notifications")
        .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(GenericCreationResponse.class);

        //notify Users
        NotifyCustomerRequestDto notifyCustomerRequestDto = NotifyCustomerRequestDto.builder()
                .cityName(cityCreationRequestDto.getName())
                .notificationCode(notificationCreationResponse.getCode()).build();

        NotifyCustomerResponseDto notifyCustomerResponseDto =
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(notifyCustomerRequestDto)
        .when()
                .post("/notifications/notify")
        .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(NotifyCustomerResponseDto.class);


        Assertions.assertEquals(1,notifyCustomerResponseDto.getTotalNumberOfNotifiedCustomer());

    }

    @Test
    void notifyCustomers_WhenWithNameCriteria_ShouldNotifyAllCustomersStartsWithGivenLetter(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("userNameFirstLetter","O")
        .when()
                .post("/notifications")
        .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    void notifyCustomers_WhenWithNameAndCityCriteria_ShouldNotifyAllCustomersIn(){

        CityCreationRequestDto cityCreationRequestDto = new CityCreationRequestDto();

        cityCreationRequestDto.setName("Berlin");

        GenericCreationResponse cityCreationResponse =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(cityCreationRequestDto)
                        .when()
                        .post("/cities")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(GenericCreationResponse.class);


        UserCreationRequestDto userCreationRequestDto = new UserCreationRequestDto();
        userCreationRequestDto.setName("VoliaTest");
        userCreationRequestDto.setCityName(cityCreationRequestDto.getName());
        userCreationRequestDto.setNotificationType(NotificationType.SMS);

        given().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userCreationRequestDto)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());


        CreateNotificationRequestDto createNotificationRequestDto = CreateNotificationRequestDto.builder().text("The food is ready").build();

        GenericCreationResponse notificationCreationResponse
                =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(createNotificationRequestDto)
                        .when()
                        .post("/notifications")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(GenericCreationResponse.class);

        //notify Users
        NotifyCustomerRequestDto notifyCustomerRequestDto = NotifyCustomerRequestDto.builder()
                .cityName(cityCreationRequestDto.getName())
                .userNameFirstLetter("V")
                .notificationCode(notificationCreationResponse.getCode()).build();

        NotifyCustomerResponseDto notifyCustomerResponseDto =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(notifyCustomerRequestDto)
                        .when()
                        .post("/notifications/notify")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(NotifyCustomerResponseDto.class);


        Assertions.assertEquals(1,notifyCustomerResponseDto.getTotalNumberOfNotifiedCustomer());

    }
}
