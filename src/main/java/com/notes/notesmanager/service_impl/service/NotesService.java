package com.notes.notesmanager.service_impl.service;

import com.notes.notesmanager.DbActivity;
import com.notes.notesmanager.Results;

public interface NotesService {

    Results addActivity(DbActivity dbActivity);
    Results listActivities(int limit, int pageNo, String status, String filter, String search);
    Results activityDetails(String id);
    Results updateActivity(DbActivity dbActivity, String id);
    Results deleteActivity(String id);


}
