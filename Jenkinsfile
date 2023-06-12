
pipeline {
    agent any
    stages{
        stage('build gradle service'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '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', url: 'https://github.com/Ashish-200/jenkins-springboot.git']])
                sh './gradlew clean build'
            }
        }
        stage('build docker image')
        {
            steps{
                script{
                    sh 'docker build -t ashish131318/config .'
                }
            }
        }
        stage('push to dockerhub')
{
    steps{
        script{
            
                sh 'docker login -u ashish131318 -p Axelblaze255@'
                sh 'docker push ashish131318/config'
            
        }
    }
}

	stage('kubernetes')
        {
            steps{
                script{
                    sh 'kubectl apply -f deployment.yaml'
                    sh 'kubectl apply -f service.yaml'
                }
            }
        }
       
    }
}
