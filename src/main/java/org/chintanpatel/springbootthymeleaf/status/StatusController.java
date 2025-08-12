package org.chintanpatel.springbootthymeleaf.status;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/statuses/listStatus")
    public String listStatus(Model model) {
        List<Status> statusList = statusService.getAllStatusList();
        model.addAttribute("statusList", statusList);
        return "status/status-list";
    }

    @GetMapping("/statuses/showStatus")
    public String showStatusForm(Model model) {
        Status status = new Status();
        model.addAttribute("status", status);
        return "status/status-form";
    }

    @PostMapping("/statuses/insertOrUpdateStatus")
    public String insertOrUpdateStatus(@Valid @ModelAttribute("status")Status status, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "status/status-form";
        }
        statusService.addStatus(status);
        return "redirect:/statuses";
    }

    @GetMapping("/statuses/manageStatus/{statusId}")
    public String manageStatus(@PathVariable Long statusId, Model model) {
        if (statusId != null) {
            Status status = statusService.getStatusById(statusId);
            model.addAttribute("status", status);
        }
        return "status/status-form";
    }

    @GetMapping("/statuses/deleteStatus/{statusId}")
    public String deleteStatus(@PathVariable Long statusId) {
        if (statusId != null) {
            statusService.deleteStatusById(statusId);
        }
        return "redirect:/statuses";
    }

    @GetMapping("/statuses/searchStatus")
    public String searchByStatusType(@RequestParam("statusType") String statusType, Model model) {
        List<Status> statusList = statusService.searchByStatusType(statusType);
        model.addAttribute("statusList", statusList);
        model.addAttribute("statusType", statusType);
        model.addAttribute("searchType", "statusType");
        return "status/status-list";
    }
}
