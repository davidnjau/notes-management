package com.notes.notesmanager.controller;

import com.notes.notesmanager.DbActivity;
import com.notes.notesmanager.FormatterClass;
import com.notes.notesmanager.Results;
import com.notes.notesmanager.service_impl.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/")
@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final NotesService notesService;
    FormatterClass formatterClass = new FormatterClass();


    @PostMapping("add-activity")
    public  ResponseEntity<?> addActivity(@RequestBody DbActivity dbActivity)  {

        Results results = notesService.addActivity(dbActivity);
        ResponseEntity<?> resp = formatterClass.getResponse(results);
        return resp;

    }

    @PutMapping("update-activity/{id}")
    public ResponseEntity<?> updateActivity(@RequestBody DbActivity dbActivity,
                                         @PathVariable("id") String id)  {

        Results results = notesService.updateActivity(dbActivity, id);
        return formatterClass.getResponse(results);

    }

    @GetMapping("list-activity")
    public ResponseEntity<?> getSubscribedList(
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "pageNo", required = false) String pageNo,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "search", required = false) String search
    ) {
        int limitNo;
        int pageNumber;

        if (limit == null || limit.equals("")){
            limitNo = 10;
        }else {
            limitNo = Integer.parseInt(limit);
        }

        if (pageNo == null || pageNo.equals("")){
            pageNumber = 1;
        }else {
            pageNumber = Integer.parseInt(pageNo);
        }

        Results results = notesService.listActivities(limitNo, pageNumber, status, filter, search);
        return formatterClass.getResponse(results);
    }

    @PutMapping("view-activity-details/{id}")
    public ResponseEntity<?> getActivityDetails(@PathVariable("id") String id)  {

        Results results = notesService.activityDetails(id);
        return formatterClass.getResponse(results);

    }
    @DeleteMapping("delete-activity/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") String id)  {

        Results results = notesService.deleteActivity(id);
        return formatterClass.getResponse(results);

    }

}
