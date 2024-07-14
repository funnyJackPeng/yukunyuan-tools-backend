pipeline {
  agent any
  stages {
    stage('编译') {
      agent {
        docker {
          reuseNode 'true'
          registryUrl 'https://coding-public-docker.pkg.coding.net'
          image 'public/docker/openjdk:21-2024.03'
        }

      }
      steps {
        sh 'ls'
        sh './build-images.sh'
      }
    }
    stage('构建镜像并推送到 CODING Docker 制品库') {
      agent {
        docker {
          reuseNode 'true'
          registryUrl 'https://coding-public-docker.pkg.coding.net'
          image 'public/docker/openjdk:21-2024.03'
        }

      }
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
  environment {
    CODING_DOCKER_REG_HOST = "${CCI_CURRENT_TEAM}-docker.pkg.${CCI_CURRENT_DOMAIN}"
    CODING_DOCKER_IMAGE_NAME = "${PROJECT_NAME.toLowerCase()}/${DOCKER_REPO_NAME}/${DOCKER_IMAGE_NAME}"
  }
}