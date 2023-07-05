FROM adoptopenjdk/openjdk17

MAINTAINER Kyungtak Park <qkrrudxkr77@naver.com>

# Docker 컨테이너가 실행될 때 실행되는 기본 명령어 'java -jar app.jar'
ENTRYPOINT ["java", "-jar", "app.jar"]
