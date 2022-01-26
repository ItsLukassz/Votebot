import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.io.IOException;
import java.util.Scanner;


/*
                String PROXY = "88.198.24.108:3128";

        org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
        proxy.setHttpProxy(PROXY)
                .setFtpProxy(PROXY)
                .setSslProxy(PROXY);
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.PROXY, proxy);

        ChromeDriver browser = new ChromeDriver(cap);
         */
//Proxy proxy = new Proxy();
//proxy.setHttpProxy("78.47.195.69:8888");


public class votebot {

    public static final String USERNAME_ID = "player";
    public static final String VOTE_BTN_XPATH = "xpath=//form[@id='voteForm']/div[2]/button";


    public static void main(String[] args) throws IOException {

        System.setProperty("webdriver.chrome.driver", "src/webdriver/chromedriver97.exe");
        System.out.println("\n" + "Kiek votes?");
        Scanner in = new Scanner(System.in);
        int amount = in.nextInt();
        int proxynr = 2;
        while (amount > 0) {

            //asdasd
            ChromeDriver browser = new ChromeDriver();
            browser.get("https://m-craft.lt/vote/mc-meemso-eu-factions-mcmmo/191c2b33138bae018b0464d479d317e8");

            //WebElement element2 = browser.findElement(By.xpath("/html/body/div[2]/div[1]/button"));
            //JavascriptExecutor executor2 = (JavascriptExecutor)browser;
            //executor2.executeScript("arguments[0].click();", element2);



            // username field

            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();

            WebElement username = browser.findElement(By.id(USERNAME_ID));
            username.sendKeys(firstName + "_" + lastName);

            // recaptcha

            Actions builder = new Actions(browser);
            builder.sendKeys(Keys.TAB).perform();
            builder.sendKeys(Keys.SPACE).perform();

            // vote button confirm


            WebElement element = browser.findElement(By.xpath("//*[@id=\"voteForm\"]/div[3]/button"));
            JavascriptExecutor executor = (JavascriptExecutor)browser;
            executor.executeScript("arguments[0].click();", element);


            // Reikalingas tam kad nemestu socket exeption klaidos
            try {
                // laukiame 1 sec
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            browser.close();
            amount = amount - 1;

        }

        }


    }
