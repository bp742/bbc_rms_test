package uk.co.bbc.bbcrmsinfo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import uk.co.bbc.constants.Endpoints;


public class BbcRmsSteps {
    // BbcRmsSteps class to write methods, which have been used within cucumber steps
    // @Step annotation shows the step info in serenity report
    @Step("Getting all music tracks and metadata information")
    public Response getAllMusicTrackAndMetadata()
    {
        return SerenityRest .given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(Endpoints.GET_ALL_MUSIC_TRACKS_METADATA);
//                .then();
    }

}