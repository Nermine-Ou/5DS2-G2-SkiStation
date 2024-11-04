pipeline {
    agent any
    environment {
        RELEASE_VERSION = "1.0"
        DOCKER_IMAGE_NAME = 'raniahmidet-5ds2-g2-stationski'
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


       stage('dockerhub') {
                steps {

                sh "docker login -u raniahmidet -p Rania27615644."
                sh "docker tag $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG raniahmidet/raniahmidet-5ds2-g2-stationski:$DOCKER_IMAGE_TAG"
                sh "docker push  raniahmidet/raniahmidet-5ds2-g2-stationski:$DOCKER_IMAGE_TAG"
                   }
                }

        stage('Set Up & Build Working Environment') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }
}
