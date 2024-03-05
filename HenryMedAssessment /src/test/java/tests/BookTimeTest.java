package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.example.Const;
import org.example.pages.ContactDetails;
import org.example.pages.NextAvailableTime;
import org.example.pages.ScheduleAppointment;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BookTimeTest
{
    @BeforeTest
    public void setUp() throws IOException
    {
        Configuration.browserSize = "1920x1080";

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config.properties"));

        Selenide.open(properties.getProperty(Const.MAIN_URL_PROP));
    }

    @Test
    public void bookTime()
    {
        ScheduleAppointment scheduleAppointment = new ScheduleAppointment();

        NextAvailableTime nextAvailableTime = scheduleAppointment.selectState("Massachusetts");

        ContactDetails contactDetails = nextAvailableTime.selectTime("12:15 AM").clickContinue();

        contactDetails.setFirstName("Ivan")
                      .setLastName("Ivanov")
                      .setEmail("junk@junk.com")
                      .setVerifyEmail("junk@junk.com")
                      .setDateOfBirth("03/06/1977")
                      .setPhoneNumber("111-333-33333")
                      .setSex("Male")
                      .setGenderPronouns("He/Him")
                      .checkTermsAndConditions()
                      .checkConsent();

        Selenide.sleep(1_000);
    }
}
