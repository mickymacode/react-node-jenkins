def gv

pipeline {
  agent any
  
  stages {
    stage("init") {
      steps {
        script {
          gv = load "script.groovy"
        }
      }
    }
    stage("build app") {
      steps {
          script {
              echo "building app"
          }
      }
    }
    stage("build image") {
      steps {
        script {
          echo "building image"
          gv.buildImage()
        }
      }
    }
    stage("deploy") {
      steps {
        script {
          echo "deploying to AWS EC2..."
          //这就是之前在terminal里运行docker image的指令啊, 这里改成在4000运行
          //换行这么写是对的，但是记得这个指令要写在这里，不要写上script或者steps上
          def dockerCmd = '''
            #!/bin/bash
            docker pull mickyma22/my-repo:1.4
            docker run -p 4000:80 -d mickyma22/my-repo:1.4
            docker ps -a
            '''
          //这个ec2-server-key就是将aws下载的.pem文件里的private key拷贝到jenkins里加入一个global Credentials
          sshagent(['ec2-server-key']) {
            sh "echo '${dockerCmd}' > docker-script.sh"
            sh "scp -o StrictHostKeyChecking=no docker-script.sh ec2-user@13.211.190.16:/home/ec2-user"
            sh "ssh -o StrictHostKeyChecking=no ec2-user@13.211.190.16 'bash /home/ec2-user/docker-script.sh'"
            //这个位置卡了很久，不知道为什么用下面的写法，docker pull和run的指令只会在Jenkins server里运行，跳ec2 instance，虽然明明连ec2是成功的
            //但是换成上面这么写，把指令写在script里，拷贝到ec2里，再运行，就可以了。
            //chatgpt写的是确保运行的环境在ec2里
            // sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.211.190.16 hostname ${dockerCmd}'
          }
        }
      }
    }
  }
}