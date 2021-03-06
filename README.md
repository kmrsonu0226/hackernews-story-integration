# Getting Started

# Application name : story-app

Technology Used : Java8, SpringBoot, In-memory db(H2Database), In-memory cache

for documentation : Swagger2

# URLs
*context-path : localhost:9001/hackernews/

# Swagger Documentation
*swagger url : localhost:9001/hackernews/swagger-ui.html

# H2 database console
*h2-database console : localhost:9001/hackernews/h2

# Application properties :
 1. Defined database configuration, cache configuration in "src/main/resource/application.yml".
 2. Cache configuration in "src/main/java/com/story/integration/config/CacheConfig.java";
 2. Used In-memory database(H2databse)  and predefined schema("src/main/resources/schema.sql")
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

# Useful urls
--for h2 database console
  -- localhost:9001/hackernews/h2
   * use JDBC url as : "jdbc:h2:~/test"
   * username : "sa"
   * password : ""
   
# Test URLs
  * /top-stories
     http://localhost:9001/hackernews/v1/story/top-stories
     
  * /past-stories
     http://localhost:9001/hackernews/v1/story/past-stories
     
  *  /comment
     http://localhost:9001/hackernews/v1/story/comment?story-id=23374667
     
   
 