package io.github.w7mike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("projects")
class ProjectController {

    @GetMapping
    public String showProjects() {
        return "projects";
    }
}
