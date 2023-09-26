pipeline {
    agent any

    stages {
        stage('Git Repository Clone') {
            steps {
                dir('main') {
                    git branch: 'dev', url: 'https://github.com/jjan-project/jjan_back_renewal'
                    echo 'back-security Clone Success'
                }

                dir('security') {
                    git branch: 'main', credentialsId: 'jjan_UserPassword' , url: 'https://github.com/jjan-project/jjan-back-security'
                    echo 'back-security Clone Success'
                }
                echo 'Clone Success'
            }
        }

        stage('File merge') {
            steps {
                sh '''
                    rm -rf ./main/src/main/resources
                    mv ./security/resources ./main/src/main
                '''
                echo 'file merge Success'
            }
        }

        stage('Build & Test') {
            steps {
            dir('main') {
                    sh '''
                        chmod +x gradlew
                        ./gradlew clean bootJar
                        '''
                    echo 'Build and Test Success!'
                }
            }
        }

        stage('Deploy') {
            steps {
                dir('main/build/libs'){
                    sh '''
                    CURRENT_PID=$(ps -ef | grep java | grep jjan_back_renewal | grep -v nohup | awk '{print $2}')
                    if [ -z ${CURRENT_PID} ] ; then
                        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
                    else
                        echo "> 실행중인 어플리케이션을 종료합니다. : $CURRENT_PID"
                        sudo kill -9 $CURRENT_PID
                        sleep 10
                    fi

                    echo "> jjan 배포 작업 시작"
                    JENKINS_NODE_COOKIE=dontKillMe nohup java -jar -Dspring.profiles.active={dev} jjan_back_renewal-0.0.1-SNAPSHOT.jar &
                    '''
                }
            }
        }
    }
}
