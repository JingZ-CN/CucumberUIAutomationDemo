package au.com.UITesting.Base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import au.com.UITesting.SharedDriver;


public class BasePO {

    public SharedDriver driver;

    public BasePO(SharedDriver driver) {
        this.driver = driver;
    }

    public WebElement getElement(final By locator){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
        return element;
    }

    public WebElement waitForElementPresent(final By locator){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return element;
    }

    public WebElement waitForElementToBeClickable(WebElement element){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return webElement;
    }

    public WebElement waitFoVisibilityOfElement(WebElement element){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement webElement = wait.until(ExpectedConditions.visibilityOf(element));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return webElement;
    }

    public List<WebElement> getElements(final By locator){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return elements;
    }

    public List<WebElement> waitForElementsPresent(final By locator){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return elements;
    }

    public void scrollToElement(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, "+ element.getLocation().y + ")");
    }

    public void scrollToPageEnd(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("window.scrollTo(0, document.body.scrollHeight - 150)");
    }

    public void enterFormUrl(String formurl){
        driver.findElement(By.id("form_url")).clear();
        driver.findElement(By.id("form_url")).sendKeys(formurl);
    }

    public boolean isAlertPresent() {

        boolean presentFlag = false;
        try {
            Alert alert = driver.switchTo().alert();
            presentFlag = true;
            alert.accept();
        } catch (NoAlertPresentException ex) {
            ex.printStackTrace();
        }

        return presentFlag;
    }

    public boolean isItemsSame(List<String> items, List<WebElement> options) {
        boolean flag=false;
        options.get(0).getText();
        System.out.println(options.get(0).getText());
        if(options.get(0).getText().contains("Search By".toUpperCase())){
            options.remove(0);
            flag=true;
        }
        List<String> list = new ArrayList<String>(options.size());
        for(WebElement option:options){
            String txt = option.getText();
            while(txt.isEmpty()){
                txt = option.getText();
            }
            if(flag)
                list.add(txt.substring(2));
            else
                list.add(txt);
        }
        if(list.size() == items.size() && list.containsAll(items))
            return true;
        else
            return false;
    }

    public Date getDate() throws ParseException{
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date syddate = new Date();
        String formatsysdate = dateFormat.format(syddate);
        Date date = dateFormat.parse(formatsysdate);
        return date;
    }


}
