package uk.co.bbc.bbcrmsinfo.steps;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import resources.testdata.TestBase;

/**
 * @RunWith annotation tells JUnit that tests should run using
 * CucumberWithSerenity class
 * The @CucumberOptions can be used to provide additional
 * configuration to the runner.
 */
//inheritance
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/java/resources/feature")
public class CucumberRunner extends TestBase {
}
