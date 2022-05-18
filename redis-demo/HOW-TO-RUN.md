# How to Run

This is a step-by-step guide how to run the example:

## Installation

* The example is implemented in Java. See
   https://www.java.com/en/download/help/download_options.xml . The
   examples need to be compiled so you need to install a JDK (Java
   Development Kit). A JRE (Java Runtime Environment) is not
   sufficient. After the installation you should be able to execute
   `java` and `javac` on the command line.

* The example run in Docker Containers. You need to install Docker
  Community Edition, see https://www.docker.com/community-edition/
  . You should be able to run `docker` after the installation.

* The example need a lot of RAM. You should configure Docker to use 4
  GB of RAM. Otherwise Docker containers might be killed due to lack
  of RAM. On Windows and macOS you can find the RAM setting in the
  Docker application under Preferences/ Advanced.
  
* After installing Docker you should also be able to run
  `docker-compose`. If this is not possible, you might need to install
  it separately. See https://docs.docker.com/compose/install/ .

## Build

Change to the directory `redis-demo-app` and run `./mvnw clean
package` or `mvnw.cmd clean package` (Windows). This will take a while:

```
$ ./mvnw clean package -DskipTests=true

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  14.110 s
[INFO] Finished at: 2022-05-18T08:52:02+10:00
[INFO] ------------------------------------------------------------------------
```

If this does not work:

* Ensure that `settings.xml` in the directory `.m2` in your home
directory contains no configuration for a specific Maven repo. If in
doubt: delete the file.

* The tests use some ports on the local machine. Make sure that no
server runs in the background.
In particular make sure the Redis port and the HTTP port 8080 are
available. There might be other ports that are needed, too.

* Skip the tests: `./mvnw clean package -Dmaven.test.skip=true` or
  `mvnw.cmd clean package -Dmaven.test.skip=true` (Windows).

* In rare cases dependencies might not be downloaded correctly. In
  that case: Remove the directory `repository` in the directory `.m2`
  in your home directory. Note that this means all dependencies will
  be downloaded again.

## Run the containers

First you need to build the Docker images. Change to the directory
`docker` and run `docker-compose build`. This will download some base
images, install software into Docker images and will therefore take
its time:

```
$ docker-compose build
[+] Building 24.4s (8/8) FINISHED
 => [internal] load build definition from Dockerfile                                                                                                            0.0s
 => => transferring dockerfile: 218B                                                                                                                            0.0s
 => [internal] load .dockerignore                                                                                                                               0.0s
 => => transferring context: 2B                                                                                                                                 0.0s
 => [internal] load metadata for docker.io/library/openjdk:11.0.2-jre-slim                                                                                     22.3s
 => [auth] library/openjdk:pull token for registry-1.docker.io                                                                                                  0.0s
 => CACHED [1/2] FROM docker.io/library/openjdk:11.0.2-jre-slim@sha256:da5605d071e9e41803f8b45f41f8c24ad6c26056190a871c8d6471982407d05b                         0.0s
 => [internal] load build context                                                                                                                               0.6s
 => => transferring context: 55.43MB                                                                                                                            0.6s
 => [2/2] COPY target/*.jar app.jar                                                                                                                             1.1s
 => exporting to image                                                                                                                                          0.2s
 => => exporting layers                                                                                                                                         0.2s
 => => writing image sha256:82aedf732a824f1cde18502e2d69f8d5952432f8456ef86fac4f0640390a09dd                                                                    0.0s
 => => naming to docker.io/library/docker_redis-demo-app
```

Afterwards the Docker images should have been created:
```
$ docker images
REPOSITORY                           TAG                                                     IMAGE ID       CREATED              SIZE
docker_redis-demo-app                latest                                                  82aedf732a82   About a minute ago   329MB
```

Now you can start the containers using `docker-compose up -d`. The
`-d` option means that the containers will be started in the
background and won't output their stdout to the command line:

```
$ docker-compose up -d
[+] Running 2/2
 - Container docker-redis-1           Started                                                                                                       0.4s
 - Container docker-redis-demo-app-1  Started                                                                                                       0.7s

```

Check wether all containers are running:

```
$ docker ps
CONTAINER ID   IMAGE                   COMMAND                  CREATED         STATUS         PORTS                    NAMES
433540d43f89   docker_redis-demo-app   "/bin/sh -c '/usr/bi…"   7 minutes ago   Up 7 minutes   0.0.0.0:8080->8080/tcp   docker-redis-demo-app-1
8bb9b96640fa   redis                   "docker-entrypoint.s…"   7 minutes ago   Up 7 minutes   0.0.0.0:6379->6379/tcp   docker-redis-1
```

`docker ps -a`  also shows the terminated Docker containers. That is useful to see Docker containers that crashed right after they started.

If one of the containers is not running, you can look at its logs using e.g.  `docker logs docker-redis-1`. 
The name of the container is given in the last column of the output of `docker ps`. 
Looking at the logs even works after the container has been terminated. 

If the log says that the container has been `killed`, you need to increase the RAM assigned to Docker to e.g. 4GB. 
On Windows and macOS you can find the RAM setting in the Docker application under Preferences/ Advanced.

If you need to do more trouble shooting open a shell in the container using e.g.
 `docker exec -it docker-redis-demo-app-1 /bin/sh` or execute command using 
 `docker exec docker-redis-demo-app-1 /bin/ls`.


http://localhost:8080/api/v1/employee/find/all  end-point will return a list of employees. 

You can terminate all containers using `docker-compose down`.
You can remove the stopped containers using `docker-compose rm`.
So these two command give you a clean new start of the system.