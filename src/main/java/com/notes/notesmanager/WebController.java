package com.notes.notesmanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(value = "/")
    public String home() {
        return "notes-index";
    }
    @RequestMapping(value = "/sidebar")
    public String sidebar() {
        return "sidebar";
    }
    @RequestMapping(value = "/notes-view/{id}")
    public String notesView() {
        return "notes-view-details";
    }

}
