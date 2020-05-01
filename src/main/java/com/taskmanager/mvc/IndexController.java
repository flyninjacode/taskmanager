package com.taskmanager.mvc;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@SessionAttributes({"currentUser"})
@Controller
public class IndexController {

    @Autowired
    public IndexController() {
    }

    @GetMapping({"/", "/index"})
    public String index(@NotNull final Model model) {
        model.addAttribute("message", "You are successfully logged in");
        return "index";
    }
}
