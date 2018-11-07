package com.github.alexkirnsu.controller;

import com.github.alexkirnsu.dto.UserDto;
import com.github.alexkirnsu.service.UserService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger logger = LogManager.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @ApiOperation("Registration page")
    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Registration")
    })
    public String getRegistrationPage(Model model) {
        return "registration";
    }

    @ApiOperation("Register a new user")
    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful registration")
    })
    public String registerUserAccount(Model model,
                                      @ModelAttribute UserDto userDto) {
        logger.info("Request for registration of user with login " + userDto.getLogin());
        if (userService.add(userDto)) {
            return "redirect:/login";
        }
        model.addAttribute("user", userDto);
        return "registration";
    }
}