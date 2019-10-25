package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.dto.TopicForm;
import com.kopivad.demoproject.model.Topic;
import com.kopivad.demoproject.model.User;
import com.kopivad.demoproject.service.TopicService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/topics")
public class TopicController {
    public final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/all")
    public String topicAllPage(Model model) { // It returns list of all disciplines
        Iterable<Topic> allTopics = topicService.getAllTopics();
        model.addAttribute("allTopics", allTopics);
        return "topicAll";
    }

    @GetMapping("/add")
    public String topicAddPage(Model model, TopicForm topicForm) { // It returns add discipline page
        model.addAttribute("topicForm", topicForm);
        return "topicAdd";
    }

    @PostMapping("/add")
    public String addTopic(@Valid TopicForm form, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "topicAdd";
        }
        System.out.println(user);
        Topic topic = new Topic();
        topic.setTitle(form.getTitle());
        topic.setAuthor(user);

        topicService.addNewTopic(topic);
        return "redirect:/topics/all";
    }

    @GetMapping("{id}")
    public String getTopicByIdPage(@PathVariable String id) {
        return "topic";
    }
}
