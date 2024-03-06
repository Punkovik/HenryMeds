package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ScheduleAppointment
{
    private SelenideElement title = $("h2");


    public ScheduleAppointment()
    {
        title.shouldHave(Condition.exactText("Schedule your Appointment!"));
    }

    public NextAvailableTime selectState(String state)
    {
        $("button[data-testid=" + state.toLowerCase() + "]").click();

        return new NextAvailableTime();
    }
}
