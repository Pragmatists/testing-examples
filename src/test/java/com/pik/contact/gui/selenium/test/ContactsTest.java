package com.pik.contact.gui.selenium.test;

import com.pik.contact.Application;
import com.pik.contact.gui.selenium.pageobjects.ContactsPage;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.pik.contact.gui.selenium.setup.SeleniumDriver.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactsTest {

    @Value("${local.server.port}")
    int port;

    //@AfterClass
    public static void tearDown() {
        getDriver().close();
    }

    //@Test
    public void should_display_contact() throws Exception {
        //given
        ContactsPage contactsPage = new ContactsPage(port).open();
        //when
        contactsPage.find("John");
        //then
        assertThat(contactsPage.firstContactTitle()).isEqualTo("John");
    }
}
