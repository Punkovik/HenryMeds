package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.Const;
import org.example.dto.GraphQLQuery;
import org.example.model.AllAvailableTimesResponseModel;
import org.example.model.TimeEntity;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class CheckAvailableTimeForMAState
{
    @BeforeTest
    public void setup() throws IOException
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config.properties"));

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(properties.getProperty(Const.GRAPHQL_URL_PROP))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        RestAssured.requestSpecification = requestSpec;
    }

    @Test
    public void checkAvailableTimeForMAState()
    {
        GraphQLQuery query = new GraphQLQuery();
        query.setOperationName("cappedAvailableTimes");

        Map<String, String> variablesObj = new HashMap<>();
        variablesObj.put("minimumDate", "2024-03-08T08:04:48.775Z");
        variablesObj.put("maximumDate", "2024-03-19T08:04:48.775Z");
        variablesObj.put("state", "massachusetts");
        variablesObj.put("treatmentShortId", "weightloss");
        query.setVariables(variablesObj);

        query.setQuery("query cappedAvailableTimes($state: String!, $treatmentShortId: String!, $minimumDate: timestamptz!, $maximumDate: timestamptz!) {\n  cappedAvailableTimes: appointment_capped_available_appointment_slots(\n    where: {start_time: {_gt: $minimumDate, _lt: $maximumDate}, state: {_eq: $state}, treatment_object: {short_id: {_eq: $treatmentShortId}}, language: {_eq: \"en-US\"}, provider: {_and: {id: {_is_null: false}}}}\n    order_by: {start_time: asc}\n  ) {\n    ...CappedAvailableSlotsFragment\n    __typename\n  }\n}\n\nfragment CappedAvailableSlotsFragment on appointment_capped_available_appointment_slots {\n  startTime: start_time\n  endTime: end_time\n  provider {\n    id\n    displayName: display_name\n    __typename\n  }\n  __typename\n}");

        // Expected
        TimeEntity lastTimeEntityExpected = new TimeEntity();
        lastTimeEntityExpected.setStartTime("2024-03-16T15:30:00+00:00");
        lastTimeEntityExpected.setEndTime("2024-03-16T15:45:00+00:00");
        lastTimeEntityExpected.setProvider(Map.of("id", "02e66266-c3f2-48b9-bbac-e7f0c6111457",
                                          "displayName", "Darth Vader, FNP",
                                          "__typename","provider_provider"));
        lastTimeEntityExpected.set__typename("appointment_capped_available_appointment_slots");

        // Validate response and last entry
        AllAvailableTimesResponseModel response = given().
                                                          body(query).
                                                  when().
                                                          post().
                                                  then().
                                                         statusCode(200).
                                                         extract().response().as(AllAvailableTimesResponseModel.class);

        long count = response.getData().getCappedAvailableTimes().stream().count();
        TimeEntity lastTimeEntityActual = response.getData().getCappedAvailableTimes().stream().skip(count - 1).findFirst().get();

        assertThat(lastTimeEntityActual.getStartTime(), equalTo(lastTimeEntityExpected.getStartTime()));
        assertThat(lastTimeEntityActual.getEndTime(), equalTo(lastTimeEntityExpected.getEndTime()));
        assertThat(lastTimeEntityActual.getProvider(), equalTo(lastTimeEntityExpected.getProvider()));
        assertThat(lastTimeEntityActual.get__typename(), equalTo(lastTimeEntityExpected.get__typename()));
    }
}
