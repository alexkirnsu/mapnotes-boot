package com.github.alexkirnsu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexkirnsu.dto.NoteDto;
import com.github.alexkirnsu.service.NoteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @ApiOperation("Get available notes")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Available notes have been returned")
    })
    public String getNotes(Model model, Principal principal) throws JsonProcessingException {
        String login = principal.getName();
        List<NoteDto> notes = noteService.getAvailableByUserLogin(login);

        model.addAttribute("notes", new ObjectMapper().writeValueAsString(notes));
        return "map";
    }

    @ApiOperation("Add new note")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Note has been added")
    })
    public String saveNote(@ModelAttribute("note") NoteDto note,
                           Principal principal) {
        note.setUserLogin(principal.getName());
        noteService.add(note);
        return "redirect:/notes";
    }

    @ApiOperation("Delete note by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Note has been deleted")
    })
    public String deleteNote(Model model, @PathVariable("id") int id, Principal principal) {
        boolean availableToDelete = noteService.deleteById(id, principal.getName());

        model.addAttribute("availableToDelete", availableToDelete);
        return "redirect:/notes";
    }
}