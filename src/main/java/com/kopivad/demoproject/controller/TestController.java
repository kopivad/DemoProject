package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.dto.TestForm;
import com.kopivad.demoproject.model.Test;
import com.kopivad.demoproject.model.Topic;
import com.kopivad.demoproject.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/all/{id}")
    public String getAllTestsByTopicPage(@PathVariable(name = "id") Long id, Model model) {
        List<Test> allTestsByTopicId = testService.getAllTestsByTopicId(id);
        model.addAttribute("allTests", allTestsByTopicId);
        return "testAll";
    }

    @GetMapping("/add/{id}")
    public String getAddTestPage(@PathVariable(name = "id") Long id, Model model, TestForm testForm) {
        model.addAttribute("topicId", id);
        model.addAttribute("testForm", testForm);
        return "testAdd";
    }

    @PostMapping("/add")
    public String addNewTestToTopicById(TestForm testForm) {
        Topic currentTopic = testService.findCurrentTopicById(testForm.getTopicId());
        Test newTest = new Test();
        newTest.setTitle(testForm.getTitle());
        newTest.setTopic(currentTopic);

        testService.addNewTest(newTest);
        return "redirect:/testAll";
    }
}
