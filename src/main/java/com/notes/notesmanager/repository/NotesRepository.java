package com.notes.notesmanager.repository;

import com.notes.notesmanager.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, String> {

    Page<Notes> findAll(Specification<Notes> notesSpecification, Pageable pageable);

}
