FROM maven:3-jdk-8
VOLUME /tmp
ADD . /
RUN apt-get -y update && apt-get install -y python-pip && pip install awscli && mvn package
CMD aws s3 cp $CONFIG_URL/application.properties application.properties && java -jar target/springCRUD-1.0-SNAPSHOT.jar
