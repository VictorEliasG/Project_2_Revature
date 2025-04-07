/*
   Clones both frontend and backend
   Builds the React app
   Copies the frontend into the Spring Boot static folder
   Since I gitignore application.properties. I need to inject application.properties from Jenkins credentials as a secret file.
   Builds the Spring Boot JAR
   Copies everything needed (including Dockerfile and backend code) to the EC2 instance
   Remotely builds the Docker image and runs the container
*/
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "victoreliasg/project_1_revature:v1"
        EC2_HOST = "52.87.229.131"
        EC2_USER = "ec2-user"
        SSH_KEY = credentials('jenkins-ec2-key')
        AWS_REGION = "us-east-1"
        S3_BUCKET = "my-frontend-bucket"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Frontend') {
            steps {
                dir('react/app') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Deploy Frontend to S3') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-s3-credentials']]) {
                    sh """
                        aws s3 sync react/app/build s3://${S3_BUCKET} --region ${AWS_REGION} --delete
                        aws s3 cp s3://${S3_BUCKET}/index.html s3://${S3_BUCKET}/index.html --cache-control no-cache --metadata-directive REPLACE
                    """
                }
            }
        }

        stage('Inject application.properties') {
            steps {
                withCredentials([file(credentialsId: 'spring-application-properties', variable: 'APP_PROPS')]) {
                    sh 'mkdir -p src/main/resources'
                    sh 'cp $APP_PROPS src/main/resources/application.properties'
                }
            }
        }

        stage('Build Backend (Maven)') {
            steps {
                sh 'mvn clean package -DskipTests'
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
