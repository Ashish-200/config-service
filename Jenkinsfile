
pipeline {
    agent any
    stages{
        stage('build gradle service'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '820ac773-b638-494c-9b09-ac95c541db14', url: 'https://github.com/Ashish-200/jenkins-springboot.git']])
                sh './gradlew clean build'
            }
        }
        stage('build docker image')
        {
            steps{
                script{
                    sh 'docker build -t new-config-image2 .'
                }
            }
        }
        stage('push to dockerhub')
{
    steps{
        script{
            
             //   sh 'docker login -u ashish131318 -p Axelblaze255@'
             //   sh 'docker push ashish131318/config'
		sh 'docker login -u ashish131318 -p Axelblaze255@'
                sh 'docker tag new-config-image ashish131318/new-config-image2'
                sh 'docker push ashish131318/new-config-image2'
            
        }
    }
}

	stage('kubernetes')
        {
            steps{
                script{

                
                //  Start Minikube (if not already running)
                    sh 'minikube delete'
                    sh 'minikube start'
                    git 'https://github.com/Ashish-200/config-service.git'
                 // Set the context to Minikube.
                    sh 'kubectl config use-context minikube'

                    sh 'kubectl apply -f deployment.yaml'
                    sh 'kubectl apply -f service.yaml'

            
           
                }
            }
        }
       
    }
}
