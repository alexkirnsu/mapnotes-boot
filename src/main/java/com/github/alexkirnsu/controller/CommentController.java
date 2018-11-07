package com.github.alexkirnsu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexkirnsu.dto.CommentDto;
import com.github.alexkirnsu.service.CommentService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes/{id}/comments")
public class CommentController {

    private static final Logger logger = LogManager.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @ApiOperation("Get comments for note")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comments have been returned")
    })
    public String getCommentsForNoteId(Model model, @PathVariable("id") int id,
                                       Principal principal) throws JsonProcessingException {
        List<CommentDto> comments = commentService.getByNoteIdAndUserLogin(id, principal.getName());

        model.addAttribute("comments", new ObjectMapper().writeValueAsString(comments));
        return "comment";
    }

    @ApiOperation("Save comment")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment has been added")
    })
    public String saveCommentForNote(@PathVariable("id") int id,
                                     @ModelAttribute("comment") CommentDto commentDto, Principal principal) {
        logger.info("User with login " + principal.getName()
                + " wants to save comment in the note with id = " + id);
        commentDto.setNoteId(id);
        commentDto.setOwner(principal.getName());
        commentService.add(commentDto);
        return "redirect:/notes";
    }

    @ApiOperation("Update comment")
    @PutMapping("/{commentId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment has been updated")
    })
    public String updateCommentForNote(@PathVariable("commentId") int commentId,
                                     @RequestParam("text") String text, Principal principal) {
        logger.info("User with login " + principal.getName() +
                " wants to update his comment with id = " + commentId);
        commentService.updateText(commentId, text);
        return "redirect:/notes";
    }
}