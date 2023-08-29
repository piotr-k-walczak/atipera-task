# Atipera Interview

### Task
Acceptance criteria:

As an api consumer, given username and header “Accept: application/json”, I would like to list all his github repositories, which are not forks. Information, which I require in the response, is:

- Repository Name
- Owner Login

For each branch it’s name and last commit sha

As an api consumer, given not existing github user, I would like to receive 404 response in such a format:
```
{
“status”: ${responseCode}
“Message”: ${whyHasItHappened}
}
```
As an api consumer, given header “Accept: application/xml”, I would like to receive 406 response in such a format:
```
{
“status”: ${responseCode}
“Message”: ${whyHasItHappened}
}
```

### Run the project



#### Config
To prevent failing due to rate limits, you can use GitHub Personal Access Tokens. They can be set in **application properties**.

`github.pat=${YOUR_PERSONAL_ACCESS_TOKEN}`