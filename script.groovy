def buildImage() {
  echo "building the docker image for react-node-app..."
  withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
    //就是之前在terminal里build image的指令啊
    sh 'docker build -t mickyma22/my-repo:1.4 .'
    sh "echo $PASS | docker login -u $USER --password-stdin"
    //就是之前在terminal里push image的指令啊
    sh 'docker push mickyma22/my-repo:1.4'
  }
}
return this