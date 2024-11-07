import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoOrangeLoginTestCasesAutomation {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver"); // Replace with your ChromeDriver path
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // DataProvider for the 9 test cases
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
            {"Admin", "admin123", true, "Dashboard"},
            {"InvalidUser", "admin123", false, "Invalid credentials"},
            {"Admin", "wrongpass", false, "Invalid credentials"},
            {"InvalidUser", "wrongpass", false, "Invalid credentials"},
            {"Admin", "", false, "Password cannot be empty"},
            {"", "admin123", false, "Username cannot be empty"},
            {"InvalidUser", "", false, "Invalid credentials"},
            {"", "wrongpass", false, "Username cannot be empty"},
            {"", "", false, "Username cannot be empty"}
        };
    }

    // Test case to verify login functionality based on different scenarios
    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, boolean shouldLoginSucceed, String expectedMessage) throws InterruptedException {
        // Locate username and password fields, and login button
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));

        // Input the username and password
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();

        // Wait for the response
        Thread.sleep(2000); // Not recommended for real testing; use explicit waits instead

        // Validate outcomes
        if (shouldLoginSucceed) {
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "User should be redirected to the dashboard");
        } else {
            WebElement errorMessageElement = driver.findElement(By.xpath("//p[@class='oxd-alert-content-text']"));
            String actualErrorMessage = errorMessageElement.getText();
            Assert.assertEquals(actualErrorMessage, expectedMessage, "Error message should match expected outcome");
        }
    }
}
