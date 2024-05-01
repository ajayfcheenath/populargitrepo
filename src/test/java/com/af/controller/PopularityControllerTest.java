package com.af.controller;

import com.af.exception.RepositoryNotFoundException;
import com.af.model.GitHubPopularityResult;
import com.af.service.GitHubPopularityCheckerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;

@DisplayName("PopularityController Tests")
@WebMvcTest(PopularityController.class)
class PopularityControllerTest {

    @MockBean
    private GitHubPopularityCheckerService popularityCheckerService;

    @InjectMocks
    private PopularityController popularityController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Setup mock behavior for GitHubPopularityCheckerService
    }

    @Test
    void testCheckRepositoryPopularity() throws Exception {
        // Mock the behavior of the service
        when(popularityCheckerService.checkRepositoryPopularity("owner", "repo"))
                .thenReturn(new GitHubPopularityResult(true, 1000, 500));

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/popularity")
                        .param("owner", "owner")
                        .param("repositoryName", "repo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.popular").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numStars").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numForks").value(500));
    }

    @Test
    void testCheckRepositoryPopularity_NotPopular() throws Exception {
        // Mock the behavior of the service
        when(popularityCheckerService.checkRepositoryPopularity("owner", "repo"))
                .thenReturn(new GitHubPopularityResult(false, 100, 50));

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/popularity")
                        .param("owner", "owner")
                        .param("repositoryName", "repo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.popular").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numStars").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numForks").value(50));
    }

    @Test
    void testCheckRepositoryPopularity_RepositoryNotFound() throws Exception {
        // Mock the behavior of the service to throw RepositoryNotFoundException
        when(popularityCheckerService.checkRepositoryPopularity("owner", "nonexistent-repo"))
                .thenThrow(new RepositoryNotFoundException("Repository not found"));

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/popularity")
                        .param("owner", "owner")
                        .param("repositoryName", "nonexistent-repo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
