package com.af.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class representing the result of GitHub repository popularity check.
 */
@Data
@AllArgsConstructor
public class GitHubPopularityResult {

    private boolean popular;
    private int numStars;
    private int numForks;

}
