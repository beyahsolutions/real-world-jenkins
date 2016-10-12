def seedJobs = [
  [
      name: 'gradle',
      ownerAndProject: 'beyahsolutions/real-world-jenkins',
      dslScripts: 'seedjobs/jobs/seed/gradle.groovy',
      credentialId: 'git',
      protocol: 'https'
  ],
  [
      name: 'docker',
      ownerAndProject: 'beyahsolutions/real-world-jenkins',
      dslScripts: 'seedjobs/jobs/seed/docker.groovy',
      credentialId: 'git',
      protocol: 'https'
  ]
]

seedJobs.each { jobConfig ->
  def name = "${jobConfig.name}-seed"

  job(name).with {
    scm {
      git {
        remote {
          credentials(jobConfig['credentialId'])
          github(jobConfig['ownerAndProject'])
        }
      }
    }
    steps {
      dsl {
        external(jobConfig['dslScripts'])
        additionalClasspath 'seedjobs/src/main/groovy'
        removeAction('DELETE')
      }
    }
  }
}