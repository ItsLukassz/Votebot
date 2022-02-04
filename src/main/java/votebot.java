import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

import java.io.File;
import java.util.Scanner;


public class votebot {

    public static final String USERNAME_ID = "player";
    public static final String Link = "https://m-craft.lt/vote/mc-meemso-eu-factions-mcmmo/7a851a5b36e54f052ed5144ce506104e";
    // using absolute path cuz i cant be bothered with solving this issue for now
    public static final String AbsoluteSolverPath = "C:\\Users\\PC\\IdeaProjects\\votebot\\src\\Images\\solver";

    static ChromeDriver browser;

    public static void main(String[] args) throws FindFailed, InterruptedException {
        // setting everything up
        ImagePath.add(System.getProperty("src/Images/solver.png"));
        System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", "src/webdriver/chromedriver97.exe");
        System.out.println("\n" + "How many votes?");
        Scanner in = new Scanner(System.in);
        int amount = in.nextInt();
        while (amount > 0) {

            // Sets up chrome and opens it
            OpenChrome();

            // waiting for page to load for 3 sec
            Wait(3000);

            // username field
            UsernameInput();

            // recaptcha
            CaptchaClick();

            // Wait 2 seconds to load
            Wait(2000);

            // in case of recaptcha asking you to actually solve the challenge
            //clicking buster extension button
            CaptchaSolve();

            // Waiting for the captcha to be solved (or not xD)
            Wait(12000);
            // Clicking Vote button
            ClickVote();

            // Waiting for everything to finish
            Wait(5000);

            browser.close();
            amount--;

        }

    }

    public static void CaptchaSolve() throws FindFailed {
        System.out.println("clicking captcha solve button");
        Screen s = new Screen();
        s.doubleClick(AbsoluteSolverPath);
    }

    public static void ClickVote() {
        System.out.println("Clicking the vote button");
        WebElement click = browser.findElement(By.xpath("//button[contains(.,'Balsuoti')]"));
        JavascriptExecutor executor = browser;
        executor.executeScript("arguments[0].click();", click);
    }

    public static void Wait(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public static void OpenChrome() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addExtensions(new File("solver.crx"));

        //creating the browser window and opening m-craft page
        browser = new ChromeDriver(chromeOptions);
        browser.get(Link);
    }

    public static void UsernameInput() {

        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        WebElement username = browser.findElement(By.id(USERNAME_ID));
        username.sendKeys(firstName + "_" + lastName);
    }

    public static void CaptchaClick() throws InterruptedException {
        Actions builder = new Actions(browser);
        builder.sendKeys(Keys.TAB).perform();
        builder.sendKeys(Keys.SPACE).perform();
        Wait(1000);
    }


}
