package com.bobocode.controller;

import com.bobocode.dto.PictureSubmission;
import com.bobocode.dto.PictureSubmissionRequest;
import com.bobocode.dto.User;
import com.bobocode.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/pictures")
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submit(@RequestBody PictureSubmission pictureSubmission, HttpServletRequest httpServletRequest) {
        var request = new PictureSubmissionRequest(pictureSubmission, httpServletRequest.getRemoteAddr());
        pictureService.submit(request);
    }

    @GetMapping
    public List<User> getUsers() {
        return pictureService.getUsersThatSubmittedCorrectPicture();
    }

}
