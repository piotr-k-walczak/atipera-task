package atipera.interview.controller;

import atipera.interview.dto.RepositoryDto;
import atipera.interview.service.GithubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/github/")
public class GithubController {

    private final GithubService githubService;

    public GithubController(@Autowired GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(value = "/user/{user}/repos", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Flux<RepositoryDto> getRepositoriesForUser(
            @PathVariable String user,
            @RequestHeader(name = "Accept") String acceptHeader
    ) {
        log.info("Called /user/%s/repos endpoint. Getting Repos for User: %s".formatted(user, user));
        return githubService.getReposForUser(user);
    }
}