package com.af.controller;

import com.af.model.GitHubPopularityResult;
import com.af.service.GitHubPopularityCheckerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling requests related to GitHub repository popularity.
 */
@RestController
@AllArgsConstructor
@Slf4j
public class PopularityController {

    private final GitHubPopularityCheckerService popularityCheckerService;

    /**
     * Endpoint to check the popularity of a GitHub repository.
     * @param owner The owner of the GitHub repository.
     * @param repositoryName The name of the GitHub repository.
     * @return GitHubPopularityResult object indicating the popularity of the repository.
     */
    @GetMapping("/popularity")
    public GitHubPopularityResult checkRepositoryPopularity(@RequestParam String owner, @RequestParam String repositoryName) {
        StopWatch stopWatch = new StopWatch();//Can be replaced with Micrometer Tracing or jaegertracing distributed tracking
        stopWatch.start();
        GitHubPopularityResult result =  popularityCheckerService.checkRepositoryPopularity(owner, repositoryName);
        stopWatch.stop();
        log.info("Execution time for checkRepositoryPopularity: {} ms", stopWatch.getTotalTimeMillis());//Change log level to debug

        return result;
    }
}
