package uk.co.bbc.bbcrmsinfo.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import uk.co.bbc.bbcrmsinfo.BbcRmsSteps;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BbcRmsStepsTest {

    //variable declaration to store values of response, so that we can use it whenever needed
    static Response response;

    //@Steps to initialise object of BbcRmsSteps class
    @Steps
    BbcRmsSteps bbcRmsSteps;

    //Abstraction in BbcRmsSteps
    @Given("^User makes a GET request to 'https://testapi.io/api/rmstest/media'$")
    public void userMakesAGETRequestToHttpsTestapiIoApiRmstestMedia() {
        response = bbcRmsSteps.getAllMusicTrackAndMetadata();
        response.prettyPrint();
    }

    @Then("^The HTTP status code should be (\\d+)$")
    public void theHTTPStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("^The response time should be below (\\d+) milliseconds$")
    public void theResponseTimeShouldBeBelowMilliseconds(int responseTime) {
        assertTrue(response.getTime() < responseTime);
    }

    @Then("^The user verifies that the 'id' field should not be null or empty for all items$")
    public void theUserVerifiesThatTheIdFieldShouldNotBeNullOrEmptyForAllItems() {
        List<String> ids = response.jsonPath().getList("data.id");
        ids.forEach(id -> {
            assertEquals("'id' field is null or empty", false, id.isEmpty());
        });
    }

    @And("^The 'segment_type' field should be \"([^\"]*)\" for all items$")
    public void theSegment_typeFieldShouldBeForAllItems(String segmentName) {
        List<String> segmentTypeFields = response.jsonPath().getList("data.segment_type");
        for (String segmentTypeField : segmentTypeFields) {
            assertEquals(segmentName, segmentTypeField);
        }
    }

    @Then("^The 'primary' field in 'title_list' should not be null or empty for any track$")
    public void thePrimaryFieldInTitle_listShouldNotBeNullOrEmptyForAnyTrack() {
        List<String> primaryFieldTitleList = response.jsonPath().getList("data.title_list.primary");
        for (String primaryField : primaryFieldTitleList) {
            assertEquals("'primary' field in 'title_list' is null or empty", false, primaryField.isEmpty());
        }
    }

    @Then("^The user verifies that the only one track should have 'now_playing' field in 'offset' as true$")
    public void theUserVerifiesThatTheOnlyOneTrackShouldHaveNow_playingFieldInOffsetAsTrue() {
        int trackCount = 0;
        for (int i = 0; i < response.jsonPath().getList("data").size(); i++) {
            boolean nowPlayingValue = response.jsonPath().getBoolean("data[" + i + "].offset.now_playing");
            if (nowPlayingValue) {
                trackCount++;
            }
        }
        assertEquals("More than one track is 'now_playing'", 1, trackCount);
    }

    @Then("^The 'Date' value in response headers should be valid$")
    public void theDateValueInResponseHeadersShouldBeValid() {
        String dateHeaderValue = response.getHeader("Date");
        System.out.println("*************************************************");
        System.out.println("Header Date Value: " + dateHeaderValue);

        // Logic for current date and time
        LocalDateTime now = LocalDateTime.now();

        // Create a LocalDateTime object representing a date and time in BST
        LocalDateTime localDateTimeBST = LocalDateTime.parse(now.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Specify the time zone IDs for BST and GMT
        ZoneId bstZone = ZoneId.of("Europe/London"); // British Summer Time
        ZoneId gmtZone = ZoneId.of("GMT"); // Greenwich Mean Time

        // Convert from BST to GMT
        ZonedDateTime zonedDateTimeGMT = ZonedDateTime.of(localDateTimeBST, bstZone).withZoneSameInstant(gmtZone);

        // Format the converted ZonedDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss z");
        String formattedCurrentDateGMT = zonedDateTimeGMT.format(formatter);
        System.out.println("*************************************************");
        System.out.println("Current Date Value: " + formattedCurrentDateGMT);

        assertTrue(formattedCurrentDateGMT.equals(dateHeaderValue));
        assertEquals("Date is not valid", formattedCurrentDateGMT, dateHeaderValue);
    }
}






