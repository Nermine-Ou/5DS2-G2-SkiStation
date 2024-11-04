pipeline {
    agent any
    environment {
        RELEASE_VERSION = "1.0"
        DOCKER_IMAGE_NAME = 'raniahmidet-5DS2-G2-stationski'
        DOCKER_IMAGE_TAG = "v${BUILD_NUMBER}"

    }
    stages {
        stage('Checkout') {
            steps {
                // Check out your source code from version control (e.g., Git).
                checkout scm
            }
        }
/*
        stage('Build') {
            steps {
                 sh "mvn clean package -DskipTests"
            }
        }


        stage("Mockito TEST") {
            steps {
                sh 'mvn test'
            }
        }


        stage('SonarQube ') {
                     steps {
                            sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Rania27615644.'
                           }
                     }

        stage('Deployment Artifacts to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
*/
       stage('building docker image')
               {
                    steps {
                       sh ' docker build -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG -f Dockerfile ./'
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
