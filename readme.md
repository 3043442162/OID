
hallo 文章目录 /root/.halo2
nohup java -jar ./halo-2.17.0.jar  --spring.config.additional-location=optional:file:$HOME/.halo2/  >halo.log 2>&1 &

web 目录
/root/web/
nohup java -jar ./UserServer-1.0-SNAPSHOT.jar  >user.log 2>&1 &
