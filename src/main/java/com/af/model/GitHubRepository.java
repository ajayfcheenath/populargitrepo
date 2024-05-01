package com.af.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class representing a GitHub repository.
 */
@Data
@AllArgsConstructor
public class GitHubRepository {

    @JsonProperty("stargazers_count")
    private int numStars;

    @JsonProperty("forks_count")
    private int numForks;

}
