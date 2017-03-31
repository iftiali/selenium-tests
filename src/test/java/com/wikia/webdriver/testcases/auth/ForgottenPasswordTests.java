package com.wikia.webdriver.testcases.auth;

import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.MailFunctions;
import com.wikia.webdriver.common.core.configuration.Configuration;
import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.componentobject.global_navitagtion.NavigationBar;
import com.wikia.webdriver.pageobjectsfactory.pageobject.auth.ResetPasswordPage;
import com.wikia.webdriver.pageobjectsfactory.pageobject.auth.signin.AttachedSignInPage;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;

import com.wikia.webdriver.pageobjectsfactory.pageobject.auth.signin.DetachedSignInPage;
import org.testng.annotations.Test;

@Test(groups = "auth-forgottenPassword")
public class
ForgottenPasswordTests extends NewTestTemplate {

  Credentials credentials = Configuration.getCredentials();

  @Test(groups = "ForgottenPassword_anonCanRemindPasswordFromAuthModal")
  public void anonCanRemindPasswordFromAuthModal() {
        MailFunctions.deleteAllEmails(credentials.email, credentials.emailPassword);
    WikiBasePageObject base = new WikiBasePageObject();
    base.openWikiPage(wikiURL);
    DetachedSignInPage loginModal = new DetachedSignInPage(new NavigationBar(driver).clickOnSignIn());
    loginModal
      .clickForgotPasswordLink()
      .requestLinkForUsername(User.FORGOTTEN_PASSWORD.getUserName());

    String resetLink = base.getPasswordResetLink(credentials.email, credentials.emailPassword);
    ResetPasswordPage resetPass = new ResetPasswordPage(resetLink);
    resetPass.setNewPassword(User.FORGOTTEN_PASSWORD.getPassword());

    Assertion.assertFalse(resetPass.newPasswordSetSuccessfully());

  }

  @Test(groups = "ForgottenPassword_anonCanRemindPasswordOnUserLoginSpecialPage")
  public void anonCanRemindPasswordOnUserLoginSpecialPage() {
    String userName = credentials.userNameForgottenPassword2;
    MailFunctions.deleteAllEmails(credentials.email, credentials.emailPassword);
    WikiBasePageObject base = new WikiBasePageObject();
    AttachedSignInPage signIn = new AttachedSignInPage();
    signIn.clickForgotPasswordLink();
    String newPassword = base.getPasswordResetLink(credentials.email, credentials.emailPassword);

    signIn.login(userName, newPassword);

    base.verifyUserLoggedIn(userName);
  }

  @Test(groups = "ForgottenPassword_anonCanRemindPasswordOnUserLoginSpecialPageUsingLowerCaseUserName")
  public void anonCanRemindPasswordOnUserLoginSpecialPageUsingLowerCaseUserName() {
    String userNameUC = credentials.userNameForgottenPassword3;
    String userName = userNameUC.toLowerCase();
    MailFunctions.deleteAllEmails(credentials.email, credentials.emailPassword);
    WikiBasePageObject base = new WikiBasePageObject();
    base.openWikiPage(wikiURL);
    AttachedSignInPage login = base.openSpecialUserLogin(wikiURL);
    AttachedSignInPage signIn = new AttachedSignInPage();
    signIn.clickForgotPasswordLink();
    String
        newPassword =
        base.getPasswordResetLink(credentials.email, credentials.emailPassword);
    String verifyString = userName.substring(0, 1).toUpperCase() + userName.substring(1);

    signIn.login(userName, newPassword);
    base.verifyUserLoggedIn(verifyString);
  }
}
