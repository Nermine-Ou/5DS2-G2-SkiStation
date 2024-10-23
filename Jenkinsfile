pipeline {
    agent any

    tools { 
        jdk 'JAVA_HOME'  // Use standard single quotes
        maven 'M2_HOME'  // Use standard single quotes
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'main',  // Ensure this matches your branch name
                    url: 'https://github.com/Nermine-Ou/5DS2-G2-SkiStation.git'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }
    }
}
