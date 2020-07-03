package io.github.w7mike.controller;

import io.github.w7mike.model.projection.GroupWriteModel;
import io.github.w7mike.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("projects")
class ProjectController {

    @GetMapping
    public String showProjects(Model model) {
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }
}
