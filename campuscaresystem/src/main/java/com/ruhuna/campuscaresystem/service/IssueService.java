package com.ruhuna.campuscaresystem.service;

import com.ruhuna.campuscaresystem.model.Issue;
import com.ruhuna.campuscaresystem.repository.IssueRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository repo;

    public IssueService(IssueRepository repo) { this.repo = repo; }

    public List<Issue> getAllIssues() { return repo.findAll(); }

    public List<Issue> searchIssues(String term) { return repo.search(term); }

    public Optional<Issue> getIssueById(Long id) { return repo.findById(id); }

    public void deleteIssue(Long id) { repo.deleteById(id); }

    public Issue saveIssue(Issue issue) {
        if (issue.getTitle() == null || issue.getTitle().trim().isEmpty())
            throw new IllegalArgumentException("Issue title must not be blank");
        if (issue.getCategory() == null || issue.getCategory().trim().isEmpty())
            throw new IllegalArgumentException("Issue category must not be blank");
        if (issue.getDate() == null)
            throw new IllegalArgumentException("Issue date must not be null");
        String st = issue.getStatus() == null ? "" : issue.getStatus().trim();
        if (!st.equals("Pending") && !st.equals("Finished"))
            throw new IllegalArgumentException("Issue status must be Pending or Finished");

        return repo.save(issue);
    }
}