import hudson.model.User

def currentUser = User.current()
def token = currentUser.allProperties.find { it instanceof jenkins.security.ApiTokenProperty }

def result = [
    user: currentUser.id,
    token: token.apiToken
]

println "${result.user}=${result.token}"