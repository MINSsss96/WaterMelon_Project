package com.my.watermelon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class SpotifyController {
    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/spotify/me")
    @ResponseBody
    public String getUserInfo(Authentication authentication) {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        return "Hello, " + oauth2User.getAttributes().get("display_name");
    }

    @GetMapping("/spotify/playlists")
    @ResponseBody
    public String getPlaylists(Authentication authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "spotify",
                authentication.getName()
        );
        String token = client.getAccessToken().getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://api.spotify.com/v1/me/playlists";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    @GetMapping("/spotify/liked")
    @ResponseBody
    public String getLikedSongs(Authentication authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "spotify",
                authentication.getName()
        );
        String token = client.getAccessToken().getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://api.spotify.com/v1/me/tracks";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
