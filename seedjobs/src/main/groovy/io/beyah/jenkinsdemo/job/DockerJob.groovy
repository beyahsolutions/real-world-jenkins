package io.beyah.jenkinsdemo.job

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


/**
 * Created by antoniobeyah on 10/12/16.
 */
class DockerJob {
  String jobName
  String githubOwnerAndProject
  String imageName
  String repoUrl
  String githubHost = 'github.com'
  String githubScheme = 'https'

  Job build(DslFactory dslFactory) {
    dslFactory.folder('docker')
    dslFactory.folder('docker/' + imageName.split('/')[0])

    def jobToCreate = 'docker/' + imageName.split('/')[0] + '/' + jobName

    dslFactory.pipelineJob(jobToCreate).with {
      definition {
        cps {
          script(this.pipelineCode())
          sandbox()
        }
      }
    }
  }

  private def pipelineCode() {
    return """
      node('docker') {
        checkout([\$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: '${repoUrl()}']]])

        stage 'Build'
        def image = docker.build(env.BUILD_TAG)

        stage 'Test'
        image.inside {
          sh 'bash --version | grep 4.3.33'
        }
      }

    """.stripIndent().trim()
  }

  private def repoUrl() {
    def url

    if (githubScheme == 'ssh') {
      url = "git@${githubHost}:${githubOwnerAndProject}.git"
    } else {
      url = "https://${githubHost}/${githubOwnerAndProject}/"
    }

    return url
  }
}
