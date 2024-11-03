pipeline {
    agent any
    environment {
        RELEASE_VERSION = "1.0"
        DOCKER_IMAGE_NAME = 'raniahmidet-5DS2-G2-stationski'
        IMAGE_TAG = "${RELEASE_VERSION}-${env.BUILD_NUMBER}"
    }
    stages {
        stage('Checkout') {
            steps {
                // Check out your source code from version control (e.g., Git).
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage("Mockito TEST") {
            steps {
                sh 'mvn test'
            }
        }

        stage('Quality Test SonarQube') {
            steps {
                // Using SonarQube credentials securely
                withCredentials([usernamePassword(credentialsId: 'sonarqube-credentials', usernameVariable: 'SONAR_USER', passwordVariable: 'SONAR_PASS')]) {
                    sh 'mvn sonar:sonar -Dsonar.login=$SONAR_USER -Dsonar.password=$SONAR_PASS'
                }
            }
        }

        stage('Deployment Artifacts to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE_NAME:$IMAGE_TAG .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                // Using DockerHub credentials securely
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "docker login -u $DOCKER_USER -p $DOCKER_PASS"
                    sh "docker tag $DOCKER_IMAGE_NAME:$IMAGE_TAG $DOCKER_USER/$DOCKER_IMAGE_NAME:$IMAGE_TAG"
                    sh "docker push $DOCKER_USER/$DOCKER_IMAGE_NAME:$IMAGE_TAG"
                }
            }
        }

        stage('Set Up & Build Working Environment') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }
}