package mx.tec.lab;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class TesthtmlApplicationTests {

    private WebClient webClient;

    @BeforeEach
    public void init() throws Exception {
        webClient = new WebClient();
    }

    @AfterEach
    public void close() throws Exception {
        webClient.close();
    }

    @Test
    public void givenAClient_whenEnteringAutomationPractice_thenPageTitleIsCorrect()
        throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");

        assertEquals("My Store", page.getTitleText());
    }

    @Test
    public void givenAClient_whenEnteringLoginCredentials_thenAccountPageIsDisplayed()
        throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
        emailField.setValueAttribute("kwxwxoipvxbazqtjsa@ttirv.org");
        HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
        passwordField.setValueAttribute("QWERTY");
        HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
        HtmlPage resultPage = submitButton.click();

        assertEquals("My account - My Store", resultPage.getTitleText());
    }

    @Test
    public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticationPageIsDisplayed()
            throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
        emailField.setValueAttribute("kwxwxoipvxbazqtjsa@ttirv.org");
        HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
        passwordField.setValueAttribute("WRONG");
        HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
        HtmlPage resultPage = submitButton.click();

        assertEquals("Login - My Store", resultPage.getTitleText());
    }

    @Test
    public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed()
            throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
        emailField.setValueAttribute("kwxwxoipvxbazqtjsa@ttirv.org");
        HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
        passwordField.setValueAttribute("WRONG");
        HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
        HtmlPage resultPage = submitButton.click();

        HtmlListItem item = (HtmlListItem) resultPage.getFirstByXPath("//div[@class='alert alert-danger']/ol/li");

        assertEquals("Authentication failed.", item.getTextContent());
    }

    @Test
    public void givenAClient_whenSearchingNotExistingProduct_thenNoResultsDisplayed()
            throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
        HtmlTextInput searchField = (HtmlTextInput) page.getElementById("search_query_top");
        searchField.setValueAttribute("Not Exists");
        HtmlButton submitButton = (HtmlButton) page.getElementByName("submit_search");
        HtmlPage resultPage = submitButton.click();

        HtmlSpan counter = (HtmlSpan) resultPage.getFirstByXPath("//span[@class='heading-counter']");

        assertEquals("0 results have been found.", counter.getTextContent().trim());
    }

    @Test
    public void givenAClient_whenSearchingEmptyString_thenPleaseEnterDisplayed()
            throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
        HtmlButton submitButton = (HtmlButton) page.getElementByName("submit_search");
        HtmlPage resultPage = submitButton.click();

        HtmlParagraph p = (HtmlParagraph) resultPage.getFirstByXPath("//p[@class='alert alert-warning']");


        assertEquals("Please enter a search keyword", p.getTextContent().trim());
    }

    @Test
    public void givenAClient_whenSigningOut_thenAuthenticationPageIsDisplayed()
            throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
        emailField.setValueAttribute("kwxwxoipvxbazqtjsa@ttirv.org");
        HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
        passwordField.setValueAttribute("QWERTY");
        HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
        HtmlPage resultPage = submitButton.click();
        HtmlAnchor logoutLink = (HtmlAnchor) resultPage.getFirstByXPath("//a[@class='logout']");
        resultPage = logoutLink.click();

        assertEquals("Login - My Store", resultPage.getTitleText());
    }

}
