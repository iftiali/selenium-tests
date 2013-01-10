package com.wikia.webdriver.TestCases.BlogTests;

import org.testng.annotations.Test;

import com.wikia.webdriver.Common.ContentPatterns.PageContent;
import com.wikia.webdriver.Common.Core.CommonFunctions;
import com.wikia.webdriver.Common.Core.Global;
import com.wikia.webdriver.Common.Properties.Properties;
import com.wikia.webdriver.Common.Templates.TestTemplate;
import com.wikia.webdriver.PageObjects.PageObject.SignUp.UserProfilePageObject;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.BlogPageObject;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.SpecialCreateBlogPageObject;
import com.wikia.webdriver.PageObjects.PageObject.WikiPage.WikiArticlePageObject;

public class BlogTests extends TestTemplate{
	
//	private String blogContent = "blogContent";
//	private String blogContentEdit = "blogContentEdit";
//	private String blogComment = "blogComment";
//	private String blogCommentEdit = "blogCommentEdit";
	
	@Test(groups = { "BlogTests_001", "BlogTests" })
	public void BlogTests_001_CreateBlogPost(){
		WikiArticlePageObject home = new WikiArticlePageObject(driver, Global.DOMAIN, "");
		home.openWikiPage();
//		String blogPostTitle = "blogPost"+home.getTimeStamp(); 
		String blogPostTitle = PageContent.blogPostName+home.getTimeStamp(); 
		CommonFunctions.logInCookie(Properties.userName, Properties.password, driver);	
		UserProfilePageObject userProfile = home.navigateToProfilePage(Global.DOMAIN, Properties.userName);
		userProfile.clickOnBlogTab();
		SpecialCreateBlogPageObject createBlogPage = userProfile.clickOnCreateBlogPost();
		createBlogPage.typeBlogPostTitle(blogPostTitle);
		createBlogPage.clickOk();
		createBlogPage.typeInContent(PageContent.blogContent);
		BlogPageObject blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContent);
		blogPage.verifyPageTitle(blogPostTitle);
		blogPage.verifyUsernameFieldPresent(Properties.userName);
		blogPage.categories_verifyCategoryPresent("Blog posts");	
	}
	
	@Test(groups = { "BlogTests_002", "BlogTests" })
	public void BlogTests_002_EditBlogPost(){
		WikiArticlePageObject home = new WikiArticlePageObject(driver, Global.DOMAIN, "");
		home.openWikiPage();
		String blogPostTitle = PageContent.blogPostName+home.getTimeStamp();
		CommonFunctions.logInCookie(Properties.userName, Properties.password, driver);	
		UserProfilePageObject userProfile = home.navigateToProfilePage(Global.DOMAIN, Properties.userName);
		userProfile.clickOnBlogTab();
		SpecialCreateBlogPageObject createBlogPage = userProfile.clickOnCreateBlogPost();
		createBlogPage.typeBlogPostTitle(blogPostTitle);
		createBlogPage.clickOk();
		createBlogPage.typeInContent(PageContent.blogContent);
		BlogPageObject blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContent);
		blogPage.verifyPageTitle(blogPostTitle);
		blogPage.verifyUsernameFieldPresent(Properties.userName);
		blogPage.categories_verifyCategoryPresent("Blog posts");	
		createBlogPage = blogPage.editBlog();
		createBlogPage.deleteArticleContent();
		createBlogPage.typeInContent(PageContent.blogContentEdit);
		blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContentEdit);
	}

	@Test(groups = { "BlogTests_003", "BlogTests" })
	public void BlogTests_003_DeleteBlogPost(){
		WikiArticlePageObject home = new WikiArticlePageObject(driver, Global.DOMAIN, "");
		home.openWikiPage();
		String blogPostTitle = PageContent.blogPostName+home.getTimeStamp();
		CommonFunctions.logInCookie(Properties.userNameStaff, Properties.passwordStaff, driver);	
		UserProfilePageObject userProfile = home.navigateToProfilePage(Global.DOMAIN, Properties.userNameStaff);
		userProfile.clickOnBlogTab();
		SpecialCreateBlogPageObject createBlogPage = userProfile.clickOnCreateBlogPost();
		createBlogPage.typeBlogPostTitle(blogPostTitle);
		createBlogPage.clickOk();
		createBlogPage.typeInContent(PageContent.blogContent);
		BlogPageObject blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContent);
		blogPage.verifyPageTitle(blogPostTitle);
		blogPage.verifyUsernameFieldPresent(Properties.userNameStaff);
		blogPage.categories_verifyCategoryPresent("Blog posts");	
		blogPage.deleteBlogPost(blogPostTitle);
	}
	
	@Test(groups = { "BlogTests_004", "BlogTests" })
	public void BlogTests_004_DeleteUndeleteBlogPost(){
		WikiArticlePageObject home = new WikiArticlePageObject(driver, Global.DOMAIN, "");
		home.openWikiPage();
		String blogPostTitle = PageContent.blogPostName+home.getTimeStamp();
		CommonFunctions.logInCookie(Properties.userNameStaff, Properties.passwordStaff, driver);	
		UserProfilePageObject userProfile = home.navigateToProfilePage(Global.DOMAIN, Properties.userNameStaff);
		userProfile.clickOnBlogTab();
		SpecialCreateBlogPageObject createBlogPage = userProfile.clickOnCreateBlogPost();
		createBlogPage.typeBlogPostTitle(blogPostTitle);
		createBlogPage.clickOk();
		createBlogPage.typeInContent(PageContent.blogContent);
		BlogPageObject blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContent);
		blogPage.verifyPageTitle(blogPostTitle);
		blogPage.verifyUsernameFieldPresent(Properties.userNameStaff);
		blogPage.categories_verifyCategoryPresent("Blog posts");	
		blogPage.deleteBlogPost(blogPostTitle);
		blogPage.undeleteArticle();
	}
	
	@Test(groups = { "BlogTests_005", "BlogTests" })
	public void BlogTests_005_PostReply(){
		WikiArticlePageObject home = new WikiArticlePageObject(driver, Global.DOMAIN, "");
		home.openWikiPage();
		String blogPostTitle = PageContent.blogPostName+home.getTimeStamp();
		CommonFunctions.logInCookie(Properties.userName, Properties.password, driver);	
		UserProfilePageObject userProfile = home.navigateToProfilePage(Global.DOMAIN, Properties.userName);
		userProfile.clickOnBlogTab();
		SpecialCreateBlogPageObject createBlogPage = userProfile.clickOnCreateBlogPost();
		createBlogPage.typeBlogPostTitle(blogPostTitle);
		createBlogPage.clickOk();
		createBlogPage.typeInContent(PageContent.blogContent);
		BlogPageObject blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContent);
		blogPage.verifyPageTitle(blogPostTitle);
		blogPage.verifyUsernameFieldPresent(Properties.userName);
		blogPage.categories_verifyCategoryPresent("Blog posts");	
		blogPage.triggerCommentArea();
		blogPage.writeOnCommentArea(PageContent.blogComment);
		blogPage.clickSubmitButton();
		blogPage.verifyComment(PageContent.blogComment, Properties.userName);
	}
	
	@Test(groups = { "BlogTests_006", "BlogTests" })
	public void BlogTests_006_PostReplyEdit(){
		WikiArticlePageObject home = new WikiArticlePageObject(driver, Global.DOMAIN, "");
		home.openWikiPage();
		String blogPostTitle = PageContent.blogPostName+home.getTimeStamp();
		CommonFunctions.logInCookie(Properties.userName, Properties.password, driver);	
		UserProfilePageObject userProfile = home.navigateToProfilePage(Global.DOMAIN, Properties.userName);
		userProfile.clickOnBlogTab();
		SpecialCreateBlogPageObject createBlogPage = userProfile.clickOnCreateBlogPost();
		createBlogPage.typeBlogPostTitle(blogPostTitle);
		createBlogPage.clickOk();
		createBlogPage.typeInContent(PageContent.blogContent);
		BlogPageObject blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContent);
		blogPage.verifyPageTitle(blogPostTitle);
		blogPage.verifyUsernameFieldPresent(Properties.userName);
		blogPage.categories_verifyCategoryPresent("Blog posts");	
		blogPage.triggerCommentArea();
		blogPage.writeOnCommentArea(PageContent.blogComment);
		blogPage.clickSubmitButton();
		blogPage.verifyComment(PageContent.blogComment, Properties.userName);
		blogPage.editComment(PageContent.blogComment);
		blogPage.writeOnCommentArea(PageContent.blogCommentEdit);
		blogPage.clickSubmitButton(Properties.userName);
		blogPage.verifyComment(PageContent.blogCommentEdit, Properties.userName);
	}
	
	@Test(groups = { "BlogTests_007", "BlogTests" })
	public void BlogTests_007_PostReplyDelete(){
		WikiArticlePageObject home = new WikiArticlePageObject(driver, Global.DOMAIN, "");
		home.openWikiPage();
		String blogPostTitle = PageContent.blogPostName+home.getTimeStamp();
		CommonFunctions.logInCookie(Properties.userNameStaff, Properties.passwordStaff, driver);	
		UserProfilePageObject userProfile = home.navigateToProfilePage(Global.DOMAIN, Properties.userNameStaff);
		userProfile.clickOnBlogTab();
		SpecialCreateBlogPageObject createBlogPage = userProfile.clickOnCreateBlogPost();
		createBlogPage.typeBlogPostTitle(blogPostTitle);
		createBlogPage.clickOk();
		createBlogPage.typeInContent(PageContent.blogContent);
		BlogPageObject blogPage = createBlogPage.clickOnPublishButton();
		blogPage.verifyArticleText(PageContent.blogContent);
		blogPage.verifyPageTitle(blogPostTitle);
		blogPage.verifyUsernameFieldPresent(Properties.userNameStaff);
		blogPage.categories_verifyCategoryPresent("Blog posts");	
		blogPage.triggerCommentArea();
		blogPage.writeOnCommentArea(PageContent.blogComment);
		blogPage.clickSubmitButton();
		blogPage.verifyComment(PageContent.blogComment, Properties.userNameStaff);
		blogPage.deleteComment(PageContent.blogComment);
	}
}
