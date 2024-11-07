package seleniumTest;
    
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class DemoOrangeLoginTest {

    public static void main(String[] args) {

         // Set the path to the geckodriver executable
         System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver.exe");

         // Initialize the FirefoxDriver instance
    	WebDriver driver = new FirefoxDriver();

        // Maximize the browser window
        driver.manage().window().maximize();

        // Set an implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            // Navigate to the OrangeHRM login page
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Locate the username field and enter username
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("Admin");

            // Locate the password field and enter password
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("admin123");

            // Locate the login button and click it
            WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
            loginButton.click();

            // Add a wait to allow the page to load
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Check if login was successful by locating a logout element or dashboard element
            boolean loginSuccess = driver.findElements(By.xpath("/html/body/div/div[1]/div[1]/aside/nav/div[2]/ul/li[8]/a/span")).size() > 0;

            // Print the test result
            if (loginSuccess) {
                System.out.println("Login test passed!");
            } else {
                System.out.println("Login test failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred during the test execution.");
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
