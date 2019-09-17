package com.pik.contact.cucumber;

import com.pik.contact.Application;
import com.pik.contact.domain.Contact;
import com.pik.contact.repository.ContactRepository;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@DirtiesContext
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class StepDefs {

    @Autowired
    private WebApplicationContext wac;

    private ResultActions mvcResult;

    @Autowired
    private ContactRepository contactRepository;

    private MockMvc getMockMvc() {
        return webAppContextSetup(wac).build();
    }

    @Before
    public void clear_db() {
        contactRepository.deleteAll();
    }

    @Given("^there is a contact with name = \"(.*?)\"$")
    public void there_is_a_contact_with_name(String name) {
        Contact contact = new Contact(name, "Doe", "Developer",null,null,null);
        contact.setId("123");
        contactRepository.save(contact);

    }

    @Then("^the status code should be (\\d+)$")
    public void the_status_code_should_be(int code) throws Throwable {
        mvcResult.andExpect(status().is(code));
    }


    @Then("^it should have the field \"([^\"]*)\" containing the value \"([^\"]*)\"$")
    public void it_should_have_the_field_containing_the_value(String field, String value) throws Throwable {
        mvcResult.andExpect(jsonPath("$." + field, is("test")));
    }

    @Then("^it should have the field \"([^\"]*)\" containing the value (\\d+)$")
    public void it_should_have_the_field_containing_the_value(String field, int value) throws Throwable {
        mvcResult.andExpect(jsonPath("$." + field, is(value)));
    }

    @Then("^it should have the field \"([^\"]*)\"$")
    public void it_should_have_the_field(String field) throws Throwable {
        mvcResult.andExpect(jsonPath("$." + field).exists());
    }

    @When("^I perform GET request on \"([^\"]*)\"$")
    public void I_perform_GET_request_on(String url) throws Throwable {
        mvcResult = getMockMvc().perform(get(url));
    }

    @And("^the response should be$")
    public void the_response_should_be(String responseJson) throws Throwable {
        String response = mvcResult.andReturn().getResponse().getContentAsString();
        Assertions.assertThat(response).contains(responseJson.replaceAll("\\s", "").replace("\n", "").replace("\t", ""));
    }


    @Given("^there is a contact with name = '([^\"]*)' and full name = '([^\"]*)'$")
    public void there_is_a_contact_with_name_John_and_full_name_Doe(String name, String fullName) {
        Contact contact = new Contact(name, fullName, null,null,null,null);
        contact.setId("123");
        contactRepository.save(contact);
    }
}
