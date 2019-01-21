
 # Prerequisites
  ```$xslt
1. Docker installed on computer
2. jdk 8
3. maven
```
#Important

```
If you are on linux please use sudo for docker commands
```

# Pulling from docker repo 

```$xslt
    1. docker pull akulinski/usermicroservice 
```
```$xslt
    2. docker run -p 8080:8080 akulinski/usermicroservice
```

# How to start
   
```$xslt
1. Enter root of project in terminal
```
```$xslt
2. mvn clean install
```
```$xslt
3. docker login
```

```$xslt
4. docker build -t msi .
```

```$xslt
5. docker-compose up
```

This will start service on port 8080

# Examples

```
To see examples please import msi.postman_collection.json to postman
``` 
