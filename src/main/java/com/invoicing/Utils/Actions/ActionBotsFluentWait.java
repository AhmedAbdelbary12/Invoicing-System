package com.invoicing.Utils.Actions;

import com.invoicing.Utils.Logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ActionBotsFluentWait {
    WebDriver driver;
    WebDriverWait wait;
    WaitManager waitManager;

    public ActionBotsFluentWait(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitManager = new WaitManager(driver);
    }

    public void click(By by) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = findElement(by);
                if (element == null) return false;
                scrollToElementUsingJS(element);
                element.click();
                LogsManager.info("Clicked on Element: " + by + " Successfully");
                return true;
            } catch (StaleElementReferenceException e) {
                LogsManager.debug("Retrying click - Stale element: " + by);
                return false;
            } catch (Exception e) {
                LogsManager.warn("Retrying click: " + by + " - " + e.getMessage());
                return false;
            }
        });
    }

    public void type(By by, String text) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = findElement(by); // ✅ مرة واحدة
                if (element == null) return false;
                scrollToElementUsingJS(element);
                element.clear();
                element.sendKeys(text);
                LogsManager.info("Entered Text on Element: " + by + " Successfully");
                return true;
            } catch (Exception e) {
                LogsManager.error("Failed To Type: " + e.getMessage());
                return false;
            }
        });
    }

    public void typeAndPressENTER(By by, String text) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = findElement(by);
                        if (element == null) return null;
                        scrollToElementUsingJS(element);
                        element.clear();
                        element.sendKeys(text);
                        element.sendKeys(Keys.ENTER);
                        LogsManager.info("Press Enter On Element " + by + " " + "Successfully");
                        return true;
                    } catch (Exception e) {
                        LogsManager.error("File To Press Enter : " + e.getMessage());
                        return false;
                    }

                }
        );

    }

    public String getText(By by) {
        return waitManager.fluentWait().until(d -> {
            try {
                WebElement element = findElement(by); // ✅ مرة واحدة
                if (element == null) return null;
                scrollToElementUsingJS(element);
                String text = element.getText();
                LogsManager.info("Got Text from Element: " + by + " Successfully");
                return !text.isEmpty() ? text : null;
            } catch (Exception e) {
                LogsManager.error("Failed To Get Text: " + e.getMessage());
                return null;
            }
        });
    }

    // upload File
    public void uploadFile(By by, String filePath) {
        String path = System.getProperty("user.dir") + File.separator + filePath;

        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = findElement(by);
                        scrollToElementUsingJS(element);
                        element.sendKeys(path);
                        LogsManager.info("File is uploaded using Locator :  " + by + "Successfully");
                        return true;

                    } catch (Exception e) {
                        LogsManager.error("File To Click : " + e.getMessage());
                        return null;
                    }

                }
        );


    }

    public void selectByVisibleText(By by, String VisibleText) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = findElement(by);
                scrollToElementUsingJS(element);
                Select select = new Select(element);
                select.selectByVisibleText(VisibleText);
                LogsManager.info("Selected " + VisibleText + " on Element: " + by + " " + "Successfully");
                return true;
            } catch (Exception e) {
                LogsManager.warn("Could not Select " + VisibleText + " on Element: " + by + "Error: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    public void selectByIndex(By by, int index) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = findElement(by);
                scrollToElementUsingJS(element);
                Select select = new Select(element);
                select.selectByIndex(index);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void selectByValue(By by, String value) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = findElement(by);
                scrollToElementUsingJS(element);
                Select select = new Select(element);
                select.selectByValue(value);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void hoverOnElement(By by) {
        waitManager.fluentWait().until(d -> {
            try {
                Actions actions = new Actions(d);
                WebElement element = findElement(by);
                scrollToElementUsingJS(element);
                actions.moveToElement(element).perform();
                LogsManager.info("Hovered on Element: " + by + " " + "Successfully");
                return true;


            } catch (Exception e) {
                LogsManager.error("Couldn't hover to the Element");
                throw new RuntimeException(e);
            }
        });

    }

    // function to scroll to element using js
    /*
    public void scrollToElementUsingJS(By by) {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.presenceOfElementLocated(by)
            );
            if (element != null) {
                ((JavascriptExecutor) driver)
                        .executeScript("""
                                arguments[0].scrollIntoView({behavior:"auto", block:"center", inline:"center"});
                                """, element);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to scroll to element: " + by + " - " + e.getMessage());
        }
    }

     */

    // ✅ scroll بيقبل WebElement مباشرة - مش بيدور عليه تاني
    private void scrollToElementUsingJS(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior:'auto',block:'center',inline:'center'});",
                    element
            );
        } catch (Exception e) {
            LogsManager.error("Failed to scroll: " + e.getMessage());
        }
    }


    protected WebElement findElement(By by) {
        try {
            // return wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return driver.findElement(by);
        } catch (Exception e) {
            LogsManager.debug("Element not yet present, retrying: " + by);
            return null;
        }
    }


}
