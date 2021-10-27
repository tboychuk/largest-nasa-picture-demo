package com.bobocode.service;

import com.bobocode.dto.Picture;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class NasaClientService {
    private final RestTemplate restTemplate;
    @Value("${nasa.api.url}")
    private String nasaApiUrl;
    
    @Cacheable("largestPicture")
    public Picture getLargestPicture() {
        var jsonResponse = restTemplate.getForObject(nasaApiUrl, JsonNode.class);
        return StreamSupport.stream(jsonResponse.get("photos").spliterator(), true)
                .map(photo -> photo.get("img_src"))
                .map(JsonNode::asText)
                .map(this::createPicture)
                .max(Comparator.comparing(Picture::size))
                .orElseThrow();
    }

    private Picture createPicture(String pictureUrl) {
        var pictureLocation = restTemplate.headForHeaders(pictureUrl)
                .getLocation();
        var pictureSize = restTemplate.headForHeaders(pictureLocation)
                .getContentLength();
        return new Picture(pictureUrl, pictureSize);
    }
}
