package atipera.interview.service;

import atipera.interview.dto.RepositoryDto;
import atipera.interview.service.GithubResponse.Branch;
import atipera.interview.service.GithubResponse.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GithubService {

    private final WebClient webClient;

    public GithubService(@Autowired WebClient webClient){
        this.webClient = webClient;
    }

    private static final String GET_REPOS_FOR_USER_URL = "https://api.github.com/users/{USER}/repos";

    private static final String GET_BRANCHES_FOR_REPO = "https://api.github.com/repos/{OWNER}/{REPO}/branches";

    public Flux<RepositoryDto> getReposForUser(String user) {
        log.info("Get Repos for User: %s".formatted(user));
        return Flux.fromStream(
                getRepositoriesForUser(user)
                        .toStream()
                        .map(repo -> {
                                    List<Branch> branches = Optional.ofNullable(
                                                    getBranchesForRepository(user, repo.name())
                                                            .collectList()
                                                            .block())
                                            .orElseGet(List::of);
                                    return RepositoryDto.fromRepositoryAndBranches(repo, branches);
                                }
                        )
        );
    }

    private Flux<Repository> getRepositoriesForUser(String user) {
        return webClient.get()
                .uri(GET_REPOS_FOR_USER_URL, user)
                .retrieve()
                .bodyToFlux(Repository.class);
    }

    private Flux<Branch> getBranchesForRepository(String user, String repo) {
        return webClient.get()
                .uri(GET_BRANCHES_FOR_REPO, user, repo)
                .retrieve()
                .bodyToFlux(Branch.class);
    }
}


