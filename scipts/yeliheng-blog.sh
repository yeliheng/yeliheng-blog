#!/bin/bash

APP_NAME="blog-web"
LOGGING_LEVEL="-Dlogging.level.com.yeliheng=info"
SPRING_ACTIVE_PROFILE="-Dspring.profiles.active=prod"
JVM_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -XX:MetaspaceSize=256M"

start() {
  # 检查是否已经运行
  # shellcheck disable=SC2009
  PID=$(ps -ef |grep java|grep $APP_NAME|grep -v grep|awk '{print $2}')
  if [ x"$PID" != x"" ]; then
    echo "$APP_NAME is already running in pid $PID"
    exit 0
  fi

  # 启动应用
  echo "Running params: java $SPRING_ACTIVE_PROFILE $JVM_OPTS $LOGGING_LEVEL -jar $APP_NAME.jar"
  # shellcheck disable=SC2086
  nohup java $SPRING_ACTIVE_PROFILE $JVM_OPTS $LOGGING_LEVEL -jar $APP_NAME.jar > /dev/null 2>&1 &

  echo "$APP_NAME running successfully in process $PID"
}

stop() {
  # 停止应用
  # shellcheck disable=SC2009
  PID=$(ps -ef |grep java|grep $APP_NAME|grep -v grep|awk '{print $2}')
  if [ x"$PID" == x"" ]; then
    echo "$APP_NAME is not running."
    exit 0
  fi

  kill "$PID"

  echo "$APP_NAME pid $PID stopped successfully."
}

status() {
  # 检查应用状态
  # shellcheck disable=SC2009
  PID=$(ps -ef |grep java|grep $APP_NAME|grep -v grep|awk '{print $2}')
  if [ x"$PID" != x"" ]; then
    echo "$APP_NAME is running in pid $PID"
  else
    echo "$APP_NAME is not running."
  fi
}

case $1 in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  *)
    echo "Usage: $0 {start|stop|status}"
    exit 1
    ;;
esac
