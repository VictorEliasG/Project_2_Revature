pipeline {
    agent any

    environment {
        BUILD_NUMBER = "1";
        DOCKER_IMAGE = "victoreliasg/project_1_revature:v${BUILD_NUMBER}"
        EC2_HOST = "52.87.229.131"
        EC2_USER = "ec2-user"
        SSH_KEY = credentials('jenkins-ec2-key')
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                        sh "docker push ${DOCKER_IMAGE}"
                    }
                }
            }
        }

        stage('Deploy to AWS EC2') {
            steps {
                sshagent(['jenkins-ec2-key']) {
                    sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} \"docker pull ${DOCKER_IMAGE} && docker stop project-backend || true && docker rm project-backend || true && docker run -d -p 8080:8080 --name project-backend ${DOCKER_IMAGE}\""
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful!'
        }
        failure {
            echo 'Deployment Failed!'
        }
    }
}