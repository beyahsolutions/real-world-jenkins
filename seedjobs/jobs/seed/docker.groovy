import io.beyah.jenkinsdemo.job.DockerJob

def repos = [
    [
        githubOwnerAndProject: 'beyahjenkinsdemo-docker/repo1',
        name: 'repo1',
        imageName: 'beyahdemo/repo1'
    ],
    [
        githubOwnerAndProject: 'beyahjenkinsdemo-docker/repo2',
        name: 'repo2',
        imageName: 'beyahdemo/repo2'
    ],
    [
        githubOwnerAndProject: 'beyahjenkinsdemo-docker/repo3',
        repoUrl: 'https://github.com/beyahjenkinsdemo-docker/repo1',
        name: 'repo3',
        imageName: 'beyahdemo/repo3'
    ]
]

repos.each { repo ->
    new DockerJob(repo).build(this)
}