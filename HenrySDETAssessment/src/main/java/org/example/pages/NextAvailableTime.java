package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class NextAvailableTime
{
    private SelenideElement title = $("h2");


    public NextAvailableTime()
    {
        title.shouldHave(Condition.exactText("Next Available Time"));
    }

    public NextSteps selectTime(String time)
    {
        try
        {
            $$("button").filter(Condition.exactText(time)).get(0).click();
        }
        catch(ElementNotFound e)
        {
            System.err.println("Looks like that " + time + " is not presented in the list !");
        }


        return new NextSteps(time);
    }
}
