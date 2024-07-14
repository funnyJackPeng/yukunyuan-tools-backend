pipeline {
  agent any
  environment {
    CODING_DOCKER_REG_HOST = "${CCI_CURRENT_TEAM}-docker.pkg.${CCI_CURRENT_DOMAIN}"
    CODING_DOCKER_IMAGE_NAME = "${PROJECT_NAME.toLowerCase()}/${DOCKER_REPO_NAME}/${DOCKER_IMAGE_NAME}"
  }
  stages {

    //stage('单元测试') {

    //  steps {
    //    sh "./gradlew test"
    //  }
    //  post {
    //    always {
    //      // 收集测试报告
    //      junit 'build/test-results/**/*.xml'
    //    }
    //  }
    //}

    stage('编译') {
      steps {
        sh "./build-images.sh"
      }
    }

    stage('构建镜像并推送到 CODING Docker 制品库') {
      steps {
        script {
            docker.withRegistry(
              "${CCI_CURRENT_WEB_PROTOCOL}://${CODING_DOCKER_REG_HOST}",
              "${CODING_ARTIFACTS_CREDENTIALS_ID}"
            ) {
             sh "./push-images.sh"
            }
        }
      }
    }

  }
}
