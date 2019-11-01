package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.dto.TopicForm;
import com.kopivad.demoproject.model.Test;
import com.kopivad.demoproject.model.Topic;
import com.kopivad.demoproject.model.User;
import com.kopivad.demoproject.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/all")
    public String topicAllPage(Model model) {
        Iterable<Topic> allTopics = topicService.getAllTopics();
        model.addAttribute("allTopics", allTopics);
        return "topicAll";
    }

    @GetMapping("/add")
    public String getTopicAddPage(Model model, TopicForm topicForm) {
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
    public String getAllTestsByTopicId(@PathVariable(name = "id") Long id, Model model) {
        List<Test> allTestsByTopicId = topicService.getAllTestsByTopicId(id);
        model.addAttribute("allTests", allTestsByTopicId);
        model.addAttribute("topicId", id);
        return "topic";
    }
}
