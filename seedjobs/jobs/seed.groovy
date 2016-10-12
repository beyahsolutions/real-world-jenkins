def seedJobs = [
  [
      name: 'gradle',
      ownerAndProject: 'beyahsolutions/real-world-jenkins',
      dslScripts: 'jobs/seed/gradle.groovy',
      credentialId: 'git',
      protocol: 'https'
  ],
  [
      name: 'docker',
      ownerAndProject: 'beyahsolutions/real-world-jenkins',
      dslScripts: 'jobs/seed/docker.groovy',
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
      gradle {
        switches('clean build')
        rootBuildScriptDir('seedjobs')
      }
      dsl {
        external(jobConfig['dslScripts'])
        additionalClasspath 'src/main/groovy\nlib'
      }
    }
  }
}