package resources.testdata;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import uk.co.bbc.constants.Path;
import uk.co.bbc.utils.PropertyReader;

public class TestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        RestAssured.basePath = Path.BBCrms;

    }
}
