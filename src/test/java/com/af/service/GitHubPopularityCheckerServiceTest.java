package com.af.service;

import com.af.exception.RepositoryNotFoundException;
import com.af.model.GitHubPopularityResult;
import com.af.model.GitHubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@DisplayName("GitHub Popularity Checker Service Tests")
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class GitHubPopularityCheckerServiceTest {

    @Value("${github.repository.score.stars.weight}")
    private int starsWeight;

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    @Autowired
    private GitHubPopularityCheckerService popularityCheckerService;

    @BeforeEach
    void setUp() {
        // Setup mock behavior for GitHubService
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(popularityCheckerService, "gitHubService", gitHubService);
    }

    @Test
    void testCheckRepositoryPopularity() {
        System.out.println(starsWeight);
        // Mock the behavior of the service
        when(gitHubService.getRepository("owner", "repo"))
                .thenReturn(new GitHubRepository(400, 50));


        // Call the method under test
        GitHubPopularityResult result = popularityCheckerService.checkRepositoryPopularity("owner", "repo");

        // Verify the result
        assertEquals(true, result.isPopular());
        assertEquals(400, result.getNumStars());
        assertEquals(50, result.getNumForks());
    }

    @Test
    void testCheckRepositoryNotPopular() {
        // Mock the behavior of the service
        when(gitHubService.getRepository("owner", "repo"))
                .thenReturn(new GitHubRepository(399, 50));

        // Call the method under test
        GitHubPopularityResult result = popularityCheckerService.checkRepositoryPopularity("owner", "repo");

        // Verify the result
        assertEquals(false, result.isPopular());
        assertEquals(399, result.getNumStars());
        assertEquals(50, result.getNumForks());
    }
    @Test
    void testCheckRepositoryPopularity_RepositoryNotFound() {
        // Mock the behavior of the service to throw RepositoryNotFoundException
        when(gitHubService.getRepository("owner", "nonexistent-repo"))
                .thenThrow(new RepositoryNotFoundException("Repository not found"));

        // Call the method under test and verify the exception
        assertThrows(RepositoryNotFoundException.class,
                () -> popularityCheckerService.checkRepositoryPopularity("owner", "nonexistent-repo"));
    }
}
