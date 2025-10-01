package com.ruhuna.campuscaresystem.repository;

import com.ruhuna.campuscaresystem.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query("SELECT i FROM Issue i WHERE " +
            "LOWER(i.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Issue> search(@Param("searchTerm") String searchTerm);
}