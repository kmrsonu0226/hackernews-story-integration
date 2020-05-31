# Getting Started

#Application name : story-app

Technology Used : Java8, SpringBoot, In-memory db(H2Database), In-memory cache

#URLs
*context-path : localhost:9001/v1/hackernews/

*swagger url : localhost:9001/v1/hackernews/swagger-ui.html

*h2-database console : localhost:9001/v1/hackernews/h2

#Application properties :
 1. Defined database configuration, cache configuration in "src/main/resource/application.yml".
 2. Cache configuration in "src/main/java/com/story/integration/config/CacheConfig.java";
 2. Used In-memory database(H2databse)  and predefined schema("src/main/resources.schema.sql")
 3. Used In-memory cache
 4. Docker configuration file in "/Dockerfile"
 
 
# Maven build and run application
--BUILD-- 
-cd PROJECT
-- "mvn clean install -DskipTests"

--RUN--
--"java -jar target/java -jar target/hackernews-integration-0.0.1-SNAPSHOT.jar"

# Docker build and run application
--BUILD--
  -- either user docker maven pluging command "mvn clean install -DskipTests docker:build" or use docker build command "docker build -t hackernews-integration ." 
  
  --RUN--
  --docker run docker run -p 9001:9001 hackernews-integration:latest

#Useful urls
--for h2 database console
  -- localhost:9001/v1/hackernews/h2
   * use JDBC url as : "jdbc:h2:~/test"
   * username : "sa"
   * password : ""
   
 