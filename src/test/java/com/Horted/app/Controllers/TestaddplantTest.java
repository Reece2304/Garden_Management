package com.Horted.app.Controllers;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class TestaddplantTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testaddplant() {
    driver.get("https://localhost:8443/login-form");
    driver.manage().window().setSize(new Dimension(1502, 1199));
    driver.findElement(By.cssSelector(".container2_login")).click();
    driver.findElement(By.name("username")).sendKeys("User");
    driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("garden")).click();
    driver.findElement(By.id("top")).click();
    driver.findElement(By.id("searchbar")).click();
    driver.findElement(By.id("searchbar")).sendKeys("rose");
    driver.findElement(By.cssSelector("#\\31 75818 label:nth-child(1)")).click();
    driver.findElement(By.id("url")).click();
  }
}
