package atipera.interview.dto;

import atipera.interview.service.GithubResponse;

public record BranchDto(String branchName, String lastCommitSHA) {

    public static BranchDto fromBranch(GithubResponse.Branch branch) {
        return new BranchDto(branch.name(), branch.commit().sha());
    }
}
