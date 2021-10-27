package com.bobocode.service;

import com.bobocode.dto.PictureSubmissionRequest;
import com.bobocode.dto.User;
import com.bobocode.exception.CorrectPictureAlreadySubmittedException;
import com.bobocode.exception.IncorrectPictureException;
import com.bobocode.exception.InvalidUserDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class PictureService {
    private final NasaClientService nasaClientService;
    private Map<String, User> users = new ConcurrentHashMap<>();

    public void submit(PictureSubmissionRequest request) {
        validateUserAlreadySubmittedCorrectPicture(request);
        User user = validateUserData(request);
        validatePicture(request, user);
    }

    public List<User> getUsersThatSubmittedCorrectPicture() {
        return users.values()
                .stream()
                .toList();
    }

    private void validatePicture(PictureSubmissionRequest request, User user) {
        var picture = request.pictureSubmission().picture();
        log.info("Calling NASA to get the largest picture URL...");
        long start = System.nanoTime();
        var correctPicture = nasaClientService.getLargestPicture();
        log.info("Got picture in " + (System.nanoTime() - start) / 1000_000_000 + "seconds");
        log.info("Correct picture data: " + correctPicture);
        if (picture.equals(correctPicture)) {
            users.put(request.address(), user);
        } else {
            throw new IncorrectPictureException();
        }
    }

    private User validateUserData(PictureSubmissionRequest request) {
        var user = request.pictureSubmission().user();
        if (ObjectUtils.isEmpty(user.firstName()) || ObjectUtils.isEmpty(user.lastName())) {
            log.warn("Invalid user data provided: " + user);
            throw new InvalidUserDataException();
        }
        return user;
    }

    private void validateUserAlreadySubmittedCorrectPicture(PictureSubmissionRequest request) {
        if (users.containsKey(request.address())) {
            log.warn("Correct picture has been already submitted from address: " + request.address());
            throw new CorrectPictureAlreadySubmittedException();
        }
    }
}
