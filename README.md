# Real-world Jenkins
Demo material for talk at [DevOps Minneapolis](https://www.meetup.com/DevOps-Minneapolis/events/233293499/).

The purpose of the project is to demonstrate concepts, not an exact prescription on how you should accomplish it.  Running Jenkins inside of docker has some limitations, such as plugin support when launching sibling containers.

## Components
| Folder | Purpose |
|-----------|---------|
| docker | Docker-compose master and agent, using hook configuration |
| presentation | Slides from presentation |
| seedjobs | Gradle project containing job-dsl items |

## Getting Started (Docker Compose)
To spin up the master and agent, run:

`docker compose up --build -d`

Access the master at http://{{ docker_ip }}:8080/, the username/password is `admin/jenkins4life`.  This can be found in `docker/master/config/init.groovy.override`

To demonstrate scaling, you can use docker-compose:

`docker-compose scale agent={{ count }}`

To tear down the environment, run:
`docker-compose down -v`

The `-v` will remove any data volumes, which is important if you want to be able to start over in a clean state.

### Important Notes
- Agent workspaces are mounted as a volume on your local machine `./tmp/{{ agent_hostname }}`
- Some plugins will not behave properly inside of docker

## Getting Started (Presentation)
Open `index.html` inside the `presentation` folder.  It is made using [RevealJS](https://github.com/hakimel/reveal.js/)
