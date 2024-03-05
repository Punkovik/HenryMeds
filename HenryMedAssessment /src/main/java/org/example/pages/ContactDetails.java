package org.example.pages;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ContactDetails
{
    private SelenideElement title = $("h3");

    private SelenideElement firstName = $("input[name='firstName']");
    private SelenideElement lastName = $("input[name='lastName']");
    private SelenideElement email = $("input[name='email']");
    private SelenideElement verifyEmail = $("input[data-testid='verifyEmail']");
    private SelenideElement dateOfBirth = $("input[data-testid='dob']");
    private SelenideElement phoneNumber = $("input[data-testid='phoneNumber']");
    private SelenideElement sexAssignedAtBirth = $("select[data-testid='sex']");
    private SelenideElement genderPronouns = $("select[data-testid='preferredPronouns']");
    private SelenideElement termsAndConditionsCheckbox = $("input[data-testid='tosConsent']");
    private SelenideElement consentCheckbox = $("input[data-testid='marketingConsent']");


    public ContactDetails()
    {
        title.shouldHave(Condition.exactText("Contact Details"));
    }

    public ContactDetails setFirstName(String fn)
    {
        firstName.setValue(fn);

        return this;
    }

    public ContactDetails setLastName(String ln)
    {
        lastName.setValue(ln);

        return this;
    }

    public ContactDetails setEmail(String e)
    {
        email.setValue(e);

        return this;
    }

    public ContactDetails setVerifyEmail(String ve)
    {
        verifyEmail.setValue(ve);

        return this;
    }

    /**
     * Set Date of Birth - mm/dd/yyyy
     * @return
     */
    public ContactDetails setDateOfBirth(String dob)
    {
        dateOfBirth.setValue(dob);

        return this;
    }

    public ContactDetails setPhoneNumber(String pn)
    {
        phoneNumber.setValue(pn);

        return this;
    }

    /**
     *
     * @param sex can only have values Male or Female
     * @return
     */
    public ContactDetails setSex(String sex)
    {
        sexAssignedAtBirth.selectOption(sex);

        return this;
    }

    public ContactDetails setGenderPronouns(String gp)
    {
        genderPronouns.selectOption(gp);

        return this;
    }

    public ContactDetails checkTermsAndConditions()
    {
        termsAndConditionsCheckbox.scrollTo();

        // only label is clickable with offset, otherwise 'Terms and Conditions' link will be open
        termsAndConditionsCheckbox.parent().parent().click(ClickOptions.withOffset(-50, 0));

        return this;
    }

    public ContactDetails checkConsent()
    {
        consentCheckbox.scrollTo();

        // only label is clickable
        consentCheckbox.parent().parent().click();

        return this;
    }

}
