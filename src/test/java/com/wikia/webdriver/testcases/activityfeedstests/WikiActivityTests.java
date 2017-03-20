package com.wikia.webdriver.testcases.activityfeedstests;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.api.ArticleContent;
import com.wikia.webdriver.common.core.configuration.Configuration;
import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.editmode.VisualEditModePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.diffpage.DiffPagePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.signup.UserProfilePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialCreatePage;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialWikiActivityPageObject;
import com.wikia.webdriver.pageobjectsfactory.componentobject.activity.Activity;
import com.wikia.webdriver.pageobjectsfactory.componentobject.activity.EditActivity;
import com.wikia.webdriver.pageobjectsfactory.pageobject.wikipage.blog.BlogPageObject;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "activityFeeds-wikiActivity")
public class WikiActivityTests extends NewTestTemplate {

  Credentials credentials = Configuration.getCredentials();

  /**
   * https://wikia-inc.atlassian.net/browse/DAR-1617
   */
  @Test(groups = "WikiActivity_001")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_001_newEditionIsRecordedOnActivityModule() {
    String articleContent = PageContent.ARTICLE_TEXT + DateTime.now().getMillis();
    ArticlePageObject article =
        new ArticlePageObject().open("NewEditionIsRecordedOnActivityModule");
    String articleName = article.getArticleName();
    VisualEditModePageObject visualEditMode = article.navigateToArticleEditPage();
    visualEditMode.addContent(articleContent);
    visualEditMode.submitArticle();
    article.verifyContent(articleContent);

    new SpecialWikiActivityPageObject(driver)
        .open()
        .verifyRecentEdition(articleName, credentials.userName);
  }

  /**
   * https://wikia-inc.atlassian.net/browse/DAR-1617
   */
  @Test(groups = "WikiActivity_002")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_002_newPageCreationIsRecordedOnActivityModule() {
    SpecialCreatePage specialCreatePage = new SpecialCreatePage().open();
    String articleContent = PageContent.ARTICLE_TEXT;
    String articleTitle = PageContent.ARTICLE_NAME_PREFIX + DateTime.now().getMillis();
    VisualEditModePageObject visualEditMode = specialCreatePage.populateTitleField(articleTitle);
    visualEditMode.addContent(articleContent);
    ArticlePageObject article = visualEditMode.submitArticle();
    String articleName = article.getArticleName();
    article.verifyContent(articleContent);
    article.verifyArticleTitle(articleTitle);

    new SpecialWikiActivityPageObject(driver)
        .open()
        .verifyRecentNewPage(articleName, credentials.userName);
  }

  /**
   * https://wikia-inc.atlassian.net/browse/DAR-1617
   */
  @Test(groups = "WikiActivity_003")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_003_newBlogCreationIsRecordedOnActivityModule() {
    String blogTitle = PageContent.BLOG_POST_NAME_PREFIX + DateTime.now().getMillis();
    String blogContent = PageContent.BLOG_CONTENT + DateTime.now().getMillis();
    UserProfilePageObject userProfile =
        new WikiBasePageObject().openProfilePage(credentials.userName, wikiURL);
    userProfile.clickOnBlogTab();
    SpecialCreatePage createBlogPage = userProfile.clickOnCreateBlogPost();
    VisualEditModePageObject visualEditMode = createBlogPage.populateTitleField(blogTitle);
    visualEditMode.addContent(blogContent);
    BlogPageObject blogPage = visualEditMode.submitBlog();
    blogPage.verifyBlogTitle(blogTitle);
    blogPage.verifyContent(blogContent);

    new SpecialWikiActivityPageObject(driver)
        .open()
        .verifyRecentNewBlogPage(blogContent, blogTitle, credentials.userName);
  }

  /**
   * https://wikia-inc.atlassian.net/browse/DAR-1617
   */
  @Test(groups = "WikiActivity_004")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_004_newCategorizationIsRecordedOnActivityModule() {
    ArticlePageObject article =
        new ArticlePageObject().open("NewCategorizationIsRecordedOnActivityModule");
    String articleName = article.getArticleName();
    String categoryName = PageContent.CATEGORY_NAME_PREFIX + article.getTimeStamp();
    article.addCategory(categoryName);
    article.submitCategory();
    article.verifyCategoryPresent(categoryName);

    new SpecialWikiActivityPageObject(driver)
        .open()
        .verifyRecentNewCategorization(articleName, credentials.userName);
  }

  @Test(groups = "WikiActivity_005")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_005_newEditionWoVisualChangeNotRecordedOnActivityModule() {
    String articleTitle = "newEditionWoVisualChangeNotRecordedOnActivityModule";
    new ArticleContent().push("content", articleTitle);

    ArticlePageObject article = new ArticlePageObject().open(articleTitle);
    String articleName = article.getArticleName();
    String articleContent = article.getContent();
    VisualEditModePageObject visualEditMode = article.navigateToArticleEditPage();
    visualEditMode.addContent(articleContent);
    visualEditMode.submitArticle();
    article.verifyContent(articleContent);

    new SpecialWikiActivityPageObject(driver)
        .open()
        .verifyNoRecentEditionIsPresent(articleName, credentials.userName);
  }

  @Test(groups = "WikiActivity_006")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_006_clickingTitleRedirectsToArticle() {

    SpecialWikiActivityPageObject activityPage = new SpecialWikiActivityPageObject(driver);
    activityPage.open();
    List<Activity> activityList = activityPage.getActivities();

    String title = activityList.stream().findFirst().get().getTitle();
    ArticlePageObject article = activityList.stream().findFirst().get().clickOnTitle();
    String articleName = article.getArticleName();
    Assertion.assertEquals(articleName, title);
  }

  @Test(groups = "WikiActivity_008")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_008_clickingUserRedirectsToUserPage() {
    String articleTitle = "clickingUserRedirectsToUserPage";
    new ArticleContent().push("content", articleTitle);

    ArticlePageObject article = new ArticlePageObject().open(articleTitle);
    SpecialWikiActivityPageObject activityPage = new SpecialWikiActivityPageObject(driver);
    activityPage.open();
    List<Activity> activityList = activityPage.getActivities();

    Activity chosenActivity = activityList.stream().findFirst().get();
    String expectedUserName = chosenActivity.getUser();
    UserProfilePageObject userPage = chosenActivity.clickOnUserLink();
    String currentUserName = userPage.getUserName();

    Assertion.assertEquals(currentUserName, expectedUserName);
  }

  @Test(groups = "WikiActivity_010")
  @Execute(asUser = User.USER)
  public void WikiActivityTests_010_clickingIconNextToArticleRedirectsToDiff() {

    SpecialWikiActivityPageObject activityPage = new SpecialWikiActivityPageObject(driver);
    activityPage.open();
    List<Activity> activityList = activityPage.getActivities();
    EditActivity editActivity = (EditActivity) activityList
        .stream()
        .filter(activity -> activity instanceof EditActivity)
        .findFirst().get();

    DiffPagePageObject diffPage = editActivity.showChanges();
    Assertion.assertEquals(diffPage.isDiffTableVisible(), true);
  }
}
