package carmax;

import com.github.javafaker.Faker;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class carMax {

    @Test
    public void main()  {

        // Chrome Functionality Set up.
        System.setProperty("webdriver.chrome.driver", "/Users/fmirzaev/Documents/Selenium Packages/drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // Step #1
        // Navigate to https://www.carmax.com/
        driver.get("https://www.carmax.com/");

        // Step #2
        // Choose VIN and fill out the form with the below info and click get started.
        js.executeScript("window.scrollBy(0,1999)");
        driver.findElement(By.xpath("//button[@id='button-VIN']")).click();
        driver.findElement(By.name("vin")).sendKeys("4T1BE46K67U162207");
        driver.findElement(By.id("ico-form-zipcode")).sendKeys("22182");
        driver.findElement(By.xpath("//button[@class='submit-button kmx-button--primary kmx-button']")).click();

        // Step #3
        // On the next page, choose the following info.
        WebElement radioButton = driver.findElement(By.xpath("//label[.='LE 4D Sedan 2.4L']"));
        wait.until(ExpectedConditions.elementToBeClickable(radioButton));
        radioButton.click();
        Select drive = new Select(driver.findElement(By.name("drive")));
        drive.selectByValue("4WD/AWD");

        // Step #4
        // For features, check all options.
        List<WebElement> allCBs = driver.findElements(By.xpath("//input[@type='checkbox']"));
        for (WebElement SelectedCB : allCBs)
            if (!SelectedCB.isSelected()) {
                js.executeScript("arguments[0].click();", SelectedCB);
            }

        // Step #5
        // Enter the following mileage and the choose the following options.
        driver.findElement(By.xpath("//button[@class='kmx-stepper--step-header StepHeader-module__stepHeader--216kh kmx-typography--headline-2']")).click();
        driver.findElement(By.xpath("//input[@class='kmx-text-field__input mdc-text-field__input']")).sendKeys("60000");
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-100-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-910-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-920-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-200-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-1000-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-300-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("checkbox-ico-yn-310-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("checkbox-ico-yn-310-2")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-410-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-420-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-500-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-600-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-700-1")));
        js.executeScript("arguments[0].click();", driver.findElement(By.id("radio-ico-r-800-1")));

        // Step #6
        // Verify that Vehicle Information table contains the following expected data for the below 2 columns.
        List<WebElement> vehicleInfo = driver.findElements(By.xpath("//div[@class='kmx-ico-vehicle-info VehicleProfileSummary-module__vehicleInfo--tAwir']"));
        System.out.println("Vehicle information");
        for (WebElement getInfo : vehicleInfo) {
            System.out.println(getInfo.getText());
        }
        System.out.println("===================");

        // Step #7
        // Click continue.
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@id='ico-continue-button']")));

        // Step #8
        // On the next page, verify that the appraisal amount is 6600.
        String parent=driver.getWindowHandle();
        List<WebElement> appraisalAmount = driver.findElements(By.xpath("//h1[@class='kmx-typography--display-6']"));
        System.out.println("Appraisal Amount");
        for (WebElement getAmount : appraisalAmount) {
            System.out.println(getAmount.getText());
        }

        // Step #9
        // Click continue.
        Set<String> windowHandles = driver.getWindowHandles();
        js.executeScript("arguments[0].click();",driver.findElement(By.xpath("//button[@id='ico-set-my-appointment']")));
        Set<String> updatedWindowHandles = driver.getWindowHandles();
        updatedWindowHandles.removeAll(windowHandles);
        for (String window: updatedWindowHandles) {
            driver.switchTo().window(window);
            if(driver.getTitle().equals("Appraisal Appointment | CarMax")) {
                break;
            }
        }
        System.out.println("===================");

        // Step #10
        // On the next page which opens in new window, write a code that chooses one of the locations randomly.
        Random random = new Random();
        int index = random.nextInt(17);
        System.out.println("Random Location By Index #"+index);
        WebElement listBox = driver.findElement(By.className("mdc-select__native-control"));
        Select list = new Select(listBox);
        list.selectByIndex(index);

        // Step #11
        // Choose the first available date.
        driver.findElement(By.xpath("//input[@id='react-datepicker']")).click();
        WebElement availableDate = driver.findElement(By.xpath("//div[@aria-disabled='false']"));
        availableDate.click();

        // Step #12
        // Choose the first available time.
        driver.findElement(By.xpath("//input[@id='react-timepicker']")).click();
        WebElement availableTime = driver.findElement(By.xpath("//li[@class='react-datepicker__time-list-item ']"));
        availableTime.click();

        // Step #13
        // Click next.
        js.executeScript("arguments[0].click();",driver.findElement(By.xpath("//button[@type='submit']")));

        // Step #14
        // On the next page, fill out the form with random info.
        Locale locale = new Locale("en-US");
        Faker faker = new Faker(locale);
        js.executeScript("arguments[0].click();", driver.findElement(By.name("Neither")));

        // Locates the input fields.
        WebElement fName = driver.findElement(By.xpath("//input[@name='fname']"));
        WebElement lName = driver.findElement(By.xpath("//input[@name='lname']"));
        WebElement eMail = driver.findElement(By.xpath("//input[@name='email']"));
        WebElement phoneN = driver.findElement(By.xpath("//input[@name='phone']"));

        // Generates Faker user information.
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String phone = faker.phoneNumber().phoneNumber();

        // Sends the generated information onto the located input fields.
        fName.sendKeys(firstName);
        lName.sendKeys(lastName);
        eMail.sendKeys(email);
        phoneN.sendKeys(phone);

        // Step #15
        // Click on Privacy policy link which opens the new tab and verify that the title.
        Set<String> windowThree = driver.getWindowHandles();
        js.executeScript("arguments[0].click();",driver.findElement(By.linkText("Privacy Policy")));
        Set<String> updatedWindowThree = driver.getWindowHandles();
        updatedWindowHandles.removeAll(windowThree);
        for (String window: updatedWindowThree) {
            driver.switchTo().window(window);
            if(driver.getTitle().equals("Privacy Policy | CarMax")) {
                break;
            }
        }

        // Step #16
        // Go back to previous window with the offer amount and click on Save this offer.
        driver.switchTo().window(parent);
        js.executeScript("arguments[0].click();",driver.findElement(By.xpath("//button[@id='ico-save-offer']")));

        // Step #17
        // On the pop-up window add random email and click send my offer.
        WebElement e_Mail = driver.findElement(By.xpath("//input[@inputmode='email']"));
        String e_mail = faker.internet().emailAddress();
        e_Mail.sendKeys(e_mail);
        js.executeScript("arguments[0].click();",driver.findElement(By.xpath("//button[@id='ico-send-offer-email']")));

        // Step #18
        // End the session by closing down all the windows.
        driver.quit();

    }
}



