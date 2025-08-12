package org.chintanpatel.springbootthymeleaf.priority;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping("/priorities")
    public String listPriority(Model model) {
        List<Priority> priorityList = priorityService.getAllPriorityList();
        model.addAttribute("priorityList", priorityList);
        return "priority/priority-list";
    }

    @GetMapping("/priorities/showPriority")
    public String showPriorityForm(Model model) {
        Priority priority = new Priority();
        model.addAttribute("priority", priority);
        return "priority/priority-form";
    }

    @PostMapping("/priorities/insertOrUpdatePriority")
    public String insertOrUpdatePriority(@Valid @ModelAttribute("priority")Priority priority, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "priority/priority-form";
        }
        priorityService.addPriority(priority);
        return "redirect:/priorities";
    }

    @GetMapping("/priorities/managePriority/{priorityId}")
    public String managePriority(@PathVariable Long priorityId, Model model) {
        if (priorityId != null) {
            Priority priority = priorityService.getPriorityById(priorityId);
            model.addAttribute("priority", priority);
        }
        return "priority/priority-form";
    }

    @GetMapping("/priorities/deletePriority/{priorityId}")
    public String deletePriority(@PathVariable Long priorityId) {
        if (priorityId != null) {
            priorityService.deletePriorityById(priorityId);
        }
        return "redirect:/priorities";
    }

    @GetMapping("/priorities/searchPriority")
    public String searchByPriorityType(@RequestParam("priorityType") String priorityType, Model model) {
        List<Priority> priorityList = priorityService.searchByPriorityType(priorityType);
        model.addAttribute("priorityList", priorityList);
        model.addAttribute("priorityType", priorityType);
        model.addAttribute("searchType", "priorityType");
        return "priority/priority-list";
    }
}
