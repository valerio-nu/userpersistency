package com.valerio.userpersistency.controller;

import com.valerio.userpersistency.security.service.ProcessingUserService;
import com.valerio.userpersistency.security.service.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/persistence")
public class HomeController {

    @Autowired
    ProcessingUserService usProcessingService;

    @RequestMapping(method = RequestMethod.GET)
    public String rememberMeLogin() {
        return "customLogin";
    }

    @RequestMapping("/homepage/dashboard")
    public String adminHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedUserName", authentication.getName());

        List<UserDTO> userDtoList = usProcessingService.getFormUsersList();
        model.addAttribute("listOfUsers", userDtoList);

        return "Dashboard";
    }

    @RequestMapping("/homepage/user")
    public String userHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedUserName", authentication.getName());

        return "userPage";
    }

    @RequestMapping("/redirect")
    public String redirectPage() {
        return "redirect:https://www.w3.org/";
    }

    @RequestMapping("/accessDenied")
    public String authorizationErrorPage() {
        return "authorizationError";
    }
}
