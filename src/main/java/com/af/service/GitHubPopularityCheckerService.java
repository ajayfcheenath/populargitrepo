package com.af.service;

import com.af.exception.RepositoryNotFoundException;
import com.af.model.GitHubPopularityResult;
import com.af.model.GitHubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for checking the popularity of GitHub repositories.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubPopularityCheckerService {

    private final GitHubService gitHubService;

    @Value("${github.repository.score.stars.weight}")
    private int starsWeight;

    @Value("${github.repository.score.forks.weight}")
    private int forksWeight;

    @Value("${github.repository.score.threshold}")
    private int threshold;

    /**
     * Checks the popularity of a GitHub repository based on predefined score calculation.
     *
     * @param owner          The owner of the GitHub repository.
     * @param repositoryName The name of the GitHub repository.
     * @return GitHubPopularityResult object indicating the popularity of the repository.
     * @throws RepositoryNotFoundException If the repository is not found.
     */
    public GitHubPopularityResult checkRepositoryPopularity(String owner, String repositoryName) {
        GitHubRepository repository = gitHubService.getRepository(owner, repositoryName);
        if (repository == null) {
            log.warn("Repository not found: {}/{}", owner, repositoryName);
            throw new RepositoryNotFoundException("Repository not found: " + owner + "/" + repositoryName);
        }

        int score = repository.getNumStars() * starsWeight + repository.getNumForks() * forksWeight;
        boolean isPopular = score >= threshold;

        log.debug("Popularity score for {}/{}: {}", owner, repositoryName, score);
        GitHubPopularityResult result = new GitHubPopularityResult(isPopular,
                repository.getNumStars(), repository.getNumForks());
        // Set any other key parameters received from GitHub API response

        return result;
    }
}
