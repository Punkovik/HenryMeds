package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class NextSteps
{
    private SelenideElement title = $("h1");
    private SelenideElement continueButton = $("button[data-testid='appointmentOverviewContinue']");


    public NextSteps(String time)
    {
        // wait until preloader is gone
        $("svg[class^='MuiCircularProgress']").should(Condition.disappear, Duration.ofSeconds(10));

        title.shouldHave(Condition.exactText("Next Steps"));

        // Check that paragraph with time exists
        $$("p").filter(Condition.text("at " + time)).get(0).shouldBe(Condition.visible);
    }

    public ContactDetails clickContinue()
    {
        continueButton.click();

        return new ContactDetails();
    }
}
