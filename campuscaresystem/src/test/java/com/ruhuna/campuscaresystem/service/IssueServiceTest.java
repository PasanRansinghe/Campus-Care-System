package com.ruhuna.campuscaresystem.service;

import com.ruhuna.campuscaresystem.model.Issue;
import com.ruhuna.campuscaresystem.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IssueServiceTest {

    private IssueService issueService;
    private IssueRepository issueRepository;

    @BeforeEach
    void setUp() {
        issueRepository = mock(IssueRepository.class);
        issueService = new IssueService(issueRepository);
    }

    @Test
    void saveIssue_shouldRejectBlankTitle() {
        Issue i = new Issue();
        i.setTitle(" ");
        i.setCategory("Broken");
        i.setStatus("Pending");
        i.setDate(LocalDate.now());

        assertThatThrownBy(() -> issueService.saveIssue(i))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("title");
    }

    @Test
    void saveIssue_shouldRejectInvalidStatus() {
        Issue i = new Issue();
        i.setTitle("Window broken");
        i.setCategory("Broken");
        i.setStatus("Unknown");
        i.setDate(LocalDate.now());

        assertThatThrownBy(() -> issueService.saveIssue(i))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("status");
    }

    @Test
    void saveIssue_shouldPersistValidIssue() {
        Issue i = new Issue();
        i.setTitle("Window broken");
        i.setCategory("Broken");
        i.setStatus("Pending");
        i.setDate(LocalDate.of(2025,3,1));

        issueService.saveIssue(i);

        ArgumentCaptor<Issue> captor = ArgumentCaptor.forClass(Issue.class);
        verify(issueRepository, times(1)).save(captor.capture());
        Issue saved = captor.getValue();
        assertThat(saved.getTitle()).isEqualTo("Window broken");
        assertThat(saved.getStatus()).isEqualTo("Pending");
    }
}