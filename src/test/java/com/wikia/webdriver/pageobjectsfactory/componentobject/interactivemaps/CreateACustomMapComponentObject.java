package com.wikia.webdriver.pageobjectsfactory.componentobject.interactivemaps;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.BasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.interactivemaps.InteractiveMapsPageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Rodrigo 'RodriGomez' Molinero
 * @author Lukasz Jedrzejczak
 * @author Lukasz Nowak (Dyktus)
 */

public class CreateACustomMapComponentObject extends BasePageObject {

  public CreateACustomMapComponentObject(WebDriver driver) {
    super(driver);
  }

  @FindBy(css = "#intMapUpload")
  private WebElement browseForFileInput;
  @FindBy(css = "#intMapTileSetSearch")
  private WebElement searchField;
  @FindBy(css = "#intMapBack")
  private WebElement backButton;
  @FindBy(css = ".modalEvent>img")
  private List<WebElement> templateList;
  @FindBy(css = "#intMapError")
  private WebElement errorField;
  @FindBy(css = ".tile-set-thumb")
  private List<WebElement> thumbCollection;
  @FindBy(css = "#intMapTileSetsList")
  private WebElement templatesBox;
  @FindBy(css = ".close")
  private WebElement closeButton;

  String beforeImageName = "116x116-";

  public void clearSearchTitle() {
    wait.forElementVisible(searchField);
    searchField.clear();
  }

  public InteractiveMapsPageObject clickCloseButton() {
    wait.forElementVisible(closeButton);
    closeButton.click();
    return new InteractiveMapsPageObject(driver);
  }

  public CreateAMapComponentObject clickBack() {
    wait.forElementVisible(backButton);
    backButton.click();
    PageObjectLogging.log("clickCustomMap", "custom map link clicked", true, driver);
    return new CreateAMapComponentObject(driver);
  }

  public String getSelectedTemplateImageName(int selectedImageIndex) {
    int
        imageNameIndex =
        templateList.get(selectedImageIndex).getAttribute("src").indexOf(beforeImageName);
    String
        selectedTemplateImageName =
        templateList.get(selectedImageIndex).getAttribute("src").substring(imageNameIndex
                                                                           + beforeImageName
            .length());
    return selectedTemplateImageName;
  }

  public TemplateComponentObject selectFileToUpload(String file) {
    browseForFileInput
        .sendKeys(getAbsolutePathForFile(PageContent.IMAGE_UPLOAD_RESOURCES_PATH + file));
    PageObjectLogging.log("typeInFileToUploadPath", "type file " + file + " to upload it", true);
    return new TemplateComponentObject(driver);
  }

  public TemplateComponentObject selectTemplate(int templateId) {
    wait.forElementVisible(templateList.get(templateId));
    templateList.get(templateId).click();
    return new TemplateComponentObject(driver);
  }

  public void typeSearchTile(String templateName) {
    wait.forElementVisible(searchField);
    searchField.sendKeys(templateName);
    PageObjectLogging
        .log("typeTilesetName", "title (" + templateName + ") for template is typed in", true);
  }

  public void verifyErrorExists() {
    wait.forElementVisible(errorField);
    Assertion.assertEquals(isElementOnPage(errorField), true);
  }

  public void verifyThereIsNoError() {
    waitForElementNotVisibleByElement(errorField);
    Assertion.assertEquals(isElementOnPage(errorField), false);
  }

  public void verifyTemplateWasFound() {
    wait.forElementVisible(templatesBox);
    wait.forElementVisible(thumbCollection.get(0));
    Assertion.assertEquals(isElementOnPage(thumbCollection.get(0)), true);
  }

  public void verifyTemplateListElementVisible(int element) {
    wait.forElementVisible(templateList.get(element));
    PageObjectLogging.log("verifyTemplateListElementVisible", "Template element is visible ", true);
  }
}
