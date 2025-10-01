package com.ruhuna.campuscaresystem.bdd;

import com.ruhuna.campuscaresystem.model.Issue;
import com.ruhuna.campuscaresystem.repository.IssueRepository;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest // Bootstraps the Spring context for Cucumber
public class IssueSteps {

    @Autowired
    private IssueRepository repo;

    private Issue issue;
    private Issue saved;

    @Given("an issue titled {string} in category {string} with status {string}")
    public void an_issue_titled_in_category_with_status(String title, String category, String status) {
        issue = new Issue();
        issue.setTitle(title);
        issue.setCategory(category);
        issue.setStatus(status);
        issue.setDate(LocalDate.now());
    }

    @When("I submit the issue")
    public void i_submit_the_issue() {
        saved = repo.save(issue);
    }

    @Then("the issue should be saved successfully")
    public void the_issue_should_be_saved_successfully() {
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getTitle()).isEqualTo(issue.getTitle());
    }
}