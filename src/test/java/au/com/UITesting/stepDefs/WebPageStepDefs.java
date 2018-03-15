package au.com.UITesting.stepDefs;

import org.junit.Assert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import au.com.UITesting.SharedDriver;
import au.com.UITesting.quickQuote.WebPageObjects;

import java.text.MessageFormat;

public class WebPageStepDefs {

    private WebPageObjects webPageObjects = null;
    private SharedDriver driver;

    public WebPageStepDefs(SharedDriver driver) {
        this.driver = driver;
        webPageObjects = new WebPageObjects(driver);
    }

    @Given("^The user is viewing the login page$")
    public void the_user_is_viewing_the_login_page() throws Throwable {
        Assert.assertTrue("Login button is missing on the home page", webPageObjects.verifyLoginButton());
        Assert.assertTrue("Username Textbox is missing", webPageObjects.verifyUsernameTextbox());
        Assert.assertTrue("Password Textbox is missing", webPageObjects.verifyPasswordTextbox());
    }

    @Then("^The user login by (\\S+) and (\\S+)$")
    public void user_login_by_uname_psw(String username, String password) throws Throwable {
        Assert.assertTrue("Login failed. Order cannot be found after user login.", webPageObjects.userLogin(username, password));
    }

    @Then("^The user is clicking on Order$")
    public void the_user_is_clicking_on_order() throws Throwable {
        Assert.assertTrue("Order page displayed incorrectlly", webPageObjects.clickOnOrder());
    }

    @Then("^User selects product (\\S+)$")
    public void user_selects_product(String product) throws Throwable {
        webPageObjects.selectsProduct(product);
    }

    @Then("^User updates Quantity (\\S+)$")
    public void user_update_quantity(String quantity) throws Throwable {
        webPageObjects.updateQuantity(quantity);
    }

    @Then("^User provide address infomation including (\\S+) and (\\S+)$")
    public void user_provide_address_info(String name, String state) throws Throwable {
        webPageObjects.provideAddressInfo(name, state);
    }

    @Then("^User provide payment infomation including (\\S+)$")
    public void user_provide_payment_info(String cardnumber) throws Throwable {
        webPageObjects.providePaymentInfo(cardnumber);
    }

    @Then("^User update payment infomation including (\\S+)$")
    public void user_update_payment_info(String cardnumber) throws Throwable {
        webPageObjects.updatePaymentInfo(cardnumber);
    }

    @Then("^User clicks on Process button$")
    public void clicks_on_Process_button() throws Throwable {
        Assert.assertTrue("New order success msg is missing", webPageObjects.clickOnProcessButton());
    }

    @Then("^User clicks on View all Orders$")
    public void clicks_on_View_All_Orders() throws Throwable {
        Assert.assertTrue("View all orders failed", webPageObjects.clickOnViewAllOrders());
    }

    @Then("^User clicks on Logout$")
    public void clicks_on_Logout() throws Throwable {
        Assert.assertTrue("User logout failed", webPageObjects.clickOnLogout());
    }

    @Then("^User clicks on Updated button$")
    public void clicks_on_update_button() throws Throwable {
        Assert.assertTrue("Update order failed", webPageObjects.clickOnUpdateButton());
    }

    @Then("^User clicks on Delete Selected button$")
    public void clicks_on_Delete_Selected_button() throws Throwable {
        webPageObjects.clickOnDeleteSelectedButton();
    }

    @Then("^User select to edit the new order by checking (\\S+), (\\S+), (\\S+) and (\\S+) form the order list$")
    public void clicks_on_new_Order(String product, String name, String state, String cardnumber) throws Throwable {
        Assert.assertTrue("Update button cannot be found", webPageObjects.clickEditTheNewOrder(product, name, state, cardnumber));
    }

    @Then("^User select order by checking (\\S+), (\\S+), (\\S+) and (\\S+) form the order list$")
    public void click_to_select_existing_Order(String product, String name, String state, String cardnumber) throws Throwable {
        webPageObjects.clickToSelectExisitngOrder(product, name, state, cardnumber);
    }

    @Then("^Verify order is not exist by checking (\\S+), (\\S+), (\\S+) and (\\S+) form the order list$")
    public void verify_order_is_not_existing(String product, String name, String state, String cardnumber) throws Throwable {
        webPageObjects.verifyOrderIsNotExist(product, name, state, cardnumber);
    }

    @Then("^Verify order is exist by checking (\\S+), (\\S+), (\\S+) and (\\S+) form the order list$")
    public void verify_order_is_existing(String product, String name, String state, String cardnumber) throws Throwable {
        webPageObjects.verifyOrderIsExist(product, name, state, cardnumber);
    }
}