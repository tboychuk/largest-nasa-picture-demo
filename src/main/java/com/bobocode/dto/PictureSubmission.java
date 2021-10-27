package com.bobocode.dto;

import java.util.Objects;

public final class PictureSubmission {
    private final User user;
    private final Picture picture;

    public PictureSubmission(User user, Picture picture) {
        this.user = user;
        this.picture = picture;
    }

    public User user() {
        return user;
    }

    public Picture picture() {
        return picture;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PictureSubmission) obj;
        return Objects.equals(this.user, that.user) &&
               Objects.equals(this.picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, picture);
    }

    @Override
    public String toString() {
        return "PictureSubmission[" +
               "user=" + user + ", " +
               "picture=" + picture + ']';
    }

}
