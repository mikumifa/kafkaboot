# 使用OpenJDK 21作为基础镜像
FROM maven:latest AS builder


# 设置工作目录
WORKDIR /app
# 安装Maven
COPY /settings.xml /root/.m2/settings.xml

VOLUME /root/.m2

#copy pom
COPY pom.xml .

#resolve maven dependencies
RUN mvn clean package -Dmaven.main.skip -Dmaven.test.skip && rm -r target

#copy source
COPY src ./src

# build the app (no dependency download here)
RUN mvn clean package -Dmaven.test.skip


# 使用另一个阶段作为最终镜像
FROM openjdk:21

# 设置工作目录
WORKDIR /app

# 复制构建的jar文件
COPY --from=builder /app/target/core-1.0.0.jar /app/app.jar

# 运行应用
CMD ["java", "-jar", "app.jar"]
