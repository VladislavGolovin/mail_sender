package com.ss.mailsender.controllers;

import com.ss.mailsender.services.ThreadsList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThreadListController {

    @GetMapping("/threadlist")
    public String threadListView(Model model) {
        model.addAttribute("threads", ThreadsList.getThreads());
        return "threadList";
    }
}
