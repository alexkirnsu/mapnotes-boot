package com.github.alexkirnsu.controller;

import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @ApiOperation("Login")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful login")
    })
    public String getLoginPage(Model model) {
        return "login";
    }
}