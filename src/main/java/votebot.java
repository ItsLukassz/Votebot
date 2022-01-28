import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static void main(String[] args) throws IOException {

        // setting everything up

        // opening the proxies.txt file
        //FileInputStream fstream = new FileInputStream("proxys.txt");
        //BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        // setting everything for for chrome
        System.setProperty("webdriver.chrome.driver", "src/webdriver/chromedriver97.exe");
        System.out.println("\n" + "Kiek votes?");
        Scanner in = new Scanner(System.in);
        int amount = in.nextInt();
        int proxynr = 5;
        while (amount > 0) {
            String proxylist = Files.readAllLines(Paths.get("proxys.txt")).get(proxynr);

            ChromeOptions chromeOptions = new ChromeOptions();

            Proxy proxy = new Proxy();
            proxy.setAutodetect(false);
            proxy.setHttpProxy(proxylist);
            proxy.setSslProxy(proxylist);
            proxy.setNoProxy("no_proxy-var");

            chromeOptions.setCapability("proxy", proxy);

            //creating the browser window and opening m-craft page
            ChromeDriver browser = new ChromeDriver(chromeOptions);
            browser.get("https://m-craft.lt/vote/mc-meemso-eu-factions-mcmmo/c46fcd272434e15974679b53cde90381");


            // waiting for page to load for 3 sec
            try {
                // laukiame 3 sec
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            // clicking balsuoti button to open the main voting page
            // WebElement click2 = browser.findElement(By.xpath("//button[contains(.,'Balsuoti')]"));
            //JavascriptExecutor executor2 = (JavascriptExecutor) browser;
            //executor2.executeScript("arguments[0].click();", click2);

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

            try {
                // laukiame 2 sec
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }


            WebElement click = browser.findElement(By.xpath("//button[contains(.,'Balsuoti')]"));
            JavascriptExecutor executor = (JavascriptExecutor) browser;
            executor.executeScript("arguments[0].click();", click);


            // palaukiame kol rankiniu budu ispresime captcha
            try {
                // laukiame 10 sec
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            browser.close();

            amount = amount - 1;
            proxynr++;

        }

        }


    }
