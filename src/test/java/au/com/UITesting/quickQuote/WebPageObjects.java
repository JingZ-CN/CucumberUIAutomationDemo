package au.com.UITesting.quickQuote;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import au.com.UITesting.SharedDriver;
import au.com.UITesting.Base.BasePO;

public class WebPageObjects extends BasePO {

    private WebElement element;

    public WebPageObjects(SharedDriver driver) {
        super(driver);
    }

    public boolean verifyLoginButton() {
        boolean flag = false;
        if (driver.findElement(By.cssSelector(".button[value=Login]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public boolean verifyUsernameTextbox() {
        boolean flag = false;
        if (driver.findElement(By.cssSelector("input.txt[id$=username]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public boolean verifyPasswordTextbox() {
        boolean flag = false;
        if (driver.findElement(By.cssSelector("input.txt[id$=password]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }


    public boolean clickOnOrder() {
        boolean flag = false;
        driver.findElement(By.cssSelector("a[href*=Process]")).click();
        if (driver.findElement(By.cssSelector("input[value=Reset]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public boolean clickOnViewAllOrders() {
        boolean flag = false;
        driver.findElement(By.xpath("//a[contains(text(),'View all orders')]")).click();
        if (driver.findElement(By.xpath("//a[contains(text(),'Check All')]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public boolean userLogin(String username, String password) {
        boolean flag = false;
        WebElement Password = driver.findElement(By.cssSelector("input.txt[id$=password]"));
        WebElement Username = driver.findElement(By.cssSelector("input.txt[id$=username]"));
        WebElement LoginButton = driver.findElement(By.cssSelector(".button[value=Login]"));
        Username.sendKeys(username);
        Password.sendKeys(password);
        LoginButton.click();
        if (driver.findElement(By.cssSelector("a[href*=Process]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public void selectsProduct(String product) {
        driver.findElement(By.cssSelector("select[id$=ddlProduct]")).sendKeys(product);
    }

    public void updateQuantity(String quantity) {
        driver.findElement(By.cssSelector("input[id$='Quantity']")).sendKeys(quantity);
    }

    public void provideAddressInfo(String name, String state) {
        //input name, street,city,state and Zip
        driver.findElement(By.xpath("//label[contains(text(), 'Customer name')]/../input[@type='text']")).sendKeys(name);
        driver.findElement(By.xpath("//label[contains(text(), 'Street')]/../input[@type='text']")).sendKeys("street001");
        driver.findElement(By.xpath("//label[contains(text(), 'City')]/../input[@type='text']")).sendKeys("City001");
        driver.findElement(By.xpath("//label[contains(text(), 'State')]/../input[@type='text']")).sendKeys(state);
        driver.findElement(By.xpath("//label[contains(text(), 'Zip')]/../input[@type='text']")).sendKeys("3030");
    }

    public void providePaymentInfo(String cardnumber) {
        driver.findElement(By.xpath("//label[contains(text(), 'Visa')]/..//input[@type='radio']")).click();
        driver.findElement(By.xpath("//label[contains(text(), 'Card Nr:')]/../input[@type='text']")).sendKeys(cardnumber);
        driver.findElement(By.xpath("//label[contains(text(), 'Expire date (mm/yy):')]/../input[@type='text']")).sendKeys("12/25");
    }

    public void updatePaymentInfo(String cardnumber) {
        driver.findElement(By.xpath("//span[contains(text(), 'Card Nr:')]/../input[@type='text']")).clear();
        driver.findElement(By.xpath("//span[contains(text(), 'Card Nr:')]/../input[@type='text']")).sendKeys(cardnumber);
    }


    public boolean clickOnProcessButton() {
        boolean flag = false;
        driver.findElement(By.xpath("//input[@type='reset']/..//a[contains(text(), 'Process')]")).click();
        if (driver.findElement(By.xpath("//strong[contains(text(), 'New order has been successfully added')]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public boolean clickEditTheNewOrder(String product, String name, String state, String cardnumber) {
        boolean flag = false;
        driver.findElement(By.xpath("//td[contains(text(),'" + product + "')]/../td[contains(text(),'" + name + "')]/../td[contains(text(),'" + state + "')]/../td[contains(text(),'" + cardnumber + "')]/..//input[@type='image' and @alt='Edit']")).click();
        if (driver.findElement(By.cssSelector("a[id$=UpdateButton]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public void clickToSelectExisitngOrder(String product, String name, String state, String cardnumber) {
        driver.findElement(By.xpath("//td[contains(text(),'" + product + "')]/../td[contains(text(),'" + name + "')]/../td[contains(text(),'" + state + "')]/../td[contains(text(),'" + cardnumber + "')]/..//input[@type='checkbox']")).click();
    }

    public boolean verifyOrderIsNotExist(String product, String name, String state, String cardnumber) throws InterruptedException {
        boolean flag = true;
        try {
            if (driver.findElement(By.xpath("//td[contains(text(),'" + product + "')]/../td[contains(text(),'" + name + "')]/../td[contains(text(),'" + state + "')]/../td[contains(text(),'" + cardnumber + "')]/..//input[@type='image' and @alt='Edit']")).isDisplayed()) {
                flag = false;
            }
        } catch (NoSuchElementException ex) {

        }
        return flag;
    }

    public boolean verifyOrderIsExist(String product, String name, String state, String cardnumber) throws InterruptedException {
        boolean flag = false;
        if (driver.findElement(By.xpath("//td[contains(text(),'" + product + "')]/../td[contains(text(),'" + name + "')]/../td[contains(text(),'" + state + "')]/../td[contains(text(),'" + cardnumber + "')]/..//input[@type='image' and @alt='Edit']")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }


    public boolean clickOnUpdateButton() {
        boolean flag = false;
        driver.findElement(By.cssSelector("a[id$=UpdateButton]")).click();
        if (driver.findElement(By.xpath("//a[contains(text(),'Check All')]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }

    public boolean clickOnLogout() {
        boolean flag = false;
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
        if (driver.findElement(By.cssSelector(".button[value=Login]")).isDisplayed()) {
            flag = true;
        }
        return flag;
    }


    public void clickOnDeleteSelectedButton() {
        driver.findElement(By.cssSelector("input[value='Delete Selected']")).click();
    }
}
