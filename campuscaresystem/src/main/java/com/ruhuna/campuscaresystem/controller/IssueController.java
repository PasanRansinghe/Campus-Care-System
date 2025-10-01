package com.ruhuna.campuscaresystem.controller;


import com.ruhuna.campuscaresystem.model.Issue;
import com.ruhuna.campuscaresystem.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "http://localhost:3000") // Allow React app to connect
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public List<Issue> getAllIssues(@RequestParam(required = false) String search) {
        if (search != null) {
            return issueService.searchIssues(search);
        }
        return issueService.getAllIssues();
    }

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.saveIssue(issue);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        Optional<Issue> issue = issueService.getIssueById(id);
        return issue.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        if (issueService.getIssueById(id).isPresent()) {
            issueService.deleteIssue(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}