import io.beyah.jenkinsdemo.job.DockerJob

def repos = [
    [
        githubOwnerAndProject: 'beyahjenkinsdemo-docker/repo1',
        imageName: 'beyahdemo/repo1',
        jobName: 'repo1-master'
    ],
    [
        githubOwnerAndProject: 'beyahjenkinsdemo-docker/repo2',
        imageName: 'beyahdemo/repo2',
        jobName: 'repo2-master'
    ],
    [
        githubOwnerAndProject: 'beyahjenkinsdemo-docker/repo3',
        imageName: 'beyahdemo/repo3',
        jobName: 'repo3-master'
    ]
]

repos.each { repo ->
    new DockerJob(repo).build(this)
}