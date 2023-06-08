package com.notes.notesmanager.service_impl.impl;

import com.notes.notesmanager.*;
import com.notes.notesmanager.entity.Notes;
import com.notes.notesmanager.repository.NotesRepository;
import com.notes.notesmanager.service_impl.service.NotesService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
public class NotesImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final FormatterClass formatterClass = new FormatterClass();


    @Override
    public Results addActivity(DbActivity dbActivity) {

        String title = dbActivity.getTitle();
        String status = dbActivity.getStatus();
        String project = dbActivity.getProject();
        String content = dbActivity.getContent();
        String dueDate = dbActivity.getDueDate();
        boolean flagged = dbActivity.getFlagged();
        List<String> actors = dbActivity.getActors();


        Notes notes = new Notes(title, status, content, flagged, actors);
        if (dueDate != null){
            Date date = formatterClass.getDate(dueDate);
            notes.setDueDate(date);
        }
        if (project != null){
            notes.setProject(project);
        }


        Notes savedNotes = notesRepository.save(notes);

        return new Results(201, savedNotes);
    }

    @Override
    public Results listActivities(int pageSize, int pageNumber, String status, String filter, String search) {


        // Create Pageable object for pagination
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        // Create Specification object for filtering and searching
        Specification<Notes> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Apply filter if provided
            if (filter != null && !filter.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("dueDate"), filter));
            }

            // Apply search if provided
            if (search != null && !search.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("content"), "%" + search + "%"));
            }

            // Apply status if provided
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        // Fetch the paginated data based on the provided Specification and Pageable
        Page<Notes> notesPage = notesRepository.findAll(specification,pageable);

        List<Notes> notesList = notesPage.getContent();
        int totalPages = notesPage.getTotalPages();
        int totalElements = (int) notesPage.getTotalElements();

        DbResults dbResults = new DbResults(
                totalElements,
                pageNumber,
                notesList);

        return new Results(200, dbResults);
    }

    @Override
    public Results activityDetails(String id) {

        Optional<Notes> optionalNotes = notesRepository.findById(id);
        if (optionalNotes.isPresent()){
            Notes notes = optionalNotes.get();
            return new Results(200, notes);
        }

        return new Results(204, new DbDataDetails("Data could not be found."));
    }

    @Override
    public Results updateActivity(DbActivity dbActivity, String id) {

        String title = dbActivity.getTitle();
        String status = dbActivity.getStatus();
        String project = dbActivity.getProject();
        String content = dbActivity.getContent();
        String dueDate = dbActivity.getDueDate();
        boolean flagged = dbActivity.getFlagged();
        List<String> actors = dbActivity.getActors();

        Optional<Notes> optionalNotes = notesRepository.findById(id);
        if (optionalNotes.isPresent()){
            Notes notes = optionalNotes.get();
            if (dueDate != null){
                Date date = formatterClass.getDate(dueDate);
                notes.setDueDate(date);
            }
            if (project != null){
                notes.setProject(project);
            }
            if (title != null){
                notes.setTitle(title);
            }
            if (status != null){
                notes.setStatus(status);
            }
            if (content != null){
                notes.setContent(content);
            }
            if (!actors.isEmpty()){
                notes.setActors(actors);
            }
            notes.setFlagged(flagged);
            notesRepository.save(notes);

            return new Results(200, new DbDataDetails("The update has been made successfully."));
        }

        return new Results(400, new DbDataDetails("The provided data could not be updated"));
    }

    @Override
    public Results deleteActivity(String id) {

        Optional<Notes> optionalNotes = notesRepository.findById(id);
        if (optionalNotes.isPresent()){
            notesRepository.deleteById(id);
            return new Results(200, new DbDataDetails("Data has been deleted successfully."));
        }

        return new Results(204, new DbDataDetails("Data could not be found."));
    }
}
