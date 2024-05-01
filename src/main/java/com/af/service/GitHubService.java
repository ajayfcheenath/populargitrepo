package com.af.service;

import com.af.exception.RepositoryNotFoundException;
import com.af.model.GitHubRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Service class for interacting with GitHub API to retrieve repository information.
 */
@Service
@Slf4j
public class GitHubService {

    @Value("${github.api.base-url}")
    private String githubApiBaseUrl;

    @Value("${github.api.access-token}")
    private String githubAccessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Retrieves information about a GitHub repository.
     * @param owner The owner of the GitHub repository.
     * @param repositoryName The name of the GitHub repository.
     * @return GitHubRepository object containing information about the repository.
     */
    public GitHubRepository getRepository(String owner, String repositoryName) {
        String url = githubApiBaseUrl + "/repos/" + owner + "/" + repositoryName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubAccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<GitHubRepository> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    GitHubRepository.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("Repository information retrieved successfully for: {}/{}", owner, repositoryName);
                return response.getBody();
            } else {
                log.warn("Repository not found: {}/{}", owner, repositoryName);
                return null; // Handle error response here if needed
            }
        } catch (Exception e) {
            throw new RepositoryNotFoundException("Repository not found: " + owner + "/" + repositoryName);
        }
    }
}
