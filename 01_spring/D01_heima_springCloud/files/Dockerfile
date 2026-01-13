FROM openjdk:11.0-jre-buster
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ../item/target/item-0.0.1-SNAPSHOT.jar /item.jar
ENTRYPOINT ["java","-jar","/item.jar"]