package atipera.interview.service;

import java.io.Serializable;

public final class GithubResponse {

    public record Repository(String name, Owner owner, boolean fork) implements Serializable {}

    public record Branch(String name, Commit commit) implements Serializable {}

    public record Commit(String sha) implements Serializable {}

    public record Owner(String login) implements Serializable {}
}
