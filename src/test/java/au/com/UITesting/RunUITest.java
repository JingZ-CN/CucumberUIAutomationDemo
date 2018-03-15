package au.com.UITesting;

import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;

import org.junit.runner.RunWith;
/*
 * @param args
 * @throws InterruptedException
 * @author Prasanna Jana
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "D:\\Testing\\Selenium\\CucumberTest\\example\\src\\test\\resourses\\au\\com\\UITesting\\feature",
        glue={"classpath:au.com.UITesting.stepDefs"},
        plugin = {"pretty", "html:target/cucumber-html-report/", "junit:target/cucumber-junit-report/report.xml",
                "json:target/cucumber-json-report/report.json"},
        tags= {"@quickQuote"},
        strict = true
)

public class RunUITest {
}
