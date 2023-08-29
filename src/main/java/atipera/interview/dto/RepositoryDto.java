package atipera.interview.dto;

import atipera.interview.service.GithubResponse;

import java.util.List;

public record RepositoryDto(
        String repositoryName,
        String ownerLogin,
        List<BranchDto> branches
) {

    public static RepositoryDto fromRepositoryAndBranches(GithubResponse.Repository repository, List<GithubResponse.Branch> branches) {
        return new RepositoryDto(
                repository.name(),
                repository.name(),
                branches.stream().map(BranchDto::fromBranch).toList()
        );
    }
}
