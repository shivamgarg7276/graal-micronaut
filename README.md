# graal-micronaut  

[![versionjava](https://img.shields.io/badge/graalvm_ce-21.0.0.2_JDK8-orange.svg?logo=java)](https://www.graalvm.org/)  

[![versionsmicronaut](https://img.shields.io/badge/micronaut-2.5.1-blue)](https://github.com/micronaut-projects)  

This example project shows a Micronaut application without AOT, and then convert it to a Native Image using GraalVM Native Image plugin.  


https://user-images.githubusercontent.com/49524850/118152586-805ee800-b432-11eb-92b8-ccd8773a5189.mov


# Install GraalVM with SDKMAN

Let's install GraalVM with the help of SDKMAN. Therefore, you need to [install SDKMAN itself](https://sdkman.io/install):

```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

If SDKMAN has been installed successfully, the following command should work:

```
$ sdk list java

================================================================================
Available Java Versions
================================================================================
 Vendor        | Use | Version      | Dist    | Status     | Identifier
--------------------------------------------------------------------------------
 AdoptOpenJDK  |     | 14.0.0.j9     | adpt    |            | 14.0.0.j9-adpt
               |     | 14.0.0.hs     | adpt    |            | 14.0.0.hs-adpt
               |     | 13.0.2.j9     | adpt    |            | 13.0.2.j9-adpt
... 
 GraalVM       | >>> | 21.0.0.2.r8   | grl     | installed  | 20.0.0.2.r8-grl
               |     | 20.2.0.r8     | grl     |            | 20.2.0.r8-grl
               |     | 20.1.0.r11    | grl     |            | 20.1.0.r11-grl
               |     | 20.1.0.r8     | grl     |            | 20.1.0.r8-grl
               |     | 20.0.0.r11    | grl     |            | 20.0.0.r11-grl
               |     | 20.0.0.r8     | grl     |            | 20.0.0.r8-grl
               |     | 19.3.1.r11    | grl     |            | 19.3.1.r11-grl
               |     | 19.3.1.r8     | grl     |            | 19.3.1.r8-grl
...
```

Now to install GraalVM based on JDK8, simply run:

```
sdk install java 21.0.0.2.r8-grl
``` 

SDKMAN now installs GraalVM for us.
To have the correct `PATH` configuration in place, you may need to restart your console. If everything went fine, you should see `java -version` react like this:

```
$ java -version
openjdk version "1.8.0_282"
OpenJDK Runtime Environment (build 1.8.0_282-b07)
OpenJDK 64-Bit Server VM GraalVM CE 21.0.0.2 (build 25.282-b07-jvmci-21.0-b06, mixed mode)
```


### Install GraalVM Native Image

GraalVM brings a special tool `gu` - the GraalVM updater. To list everything that is currently installed, run

```
$ gu list
ComponentId              Version             Component name      Origin
--------------------------------------------------------------------------------
graalvm                  21.0.0.2            GraalVM Core
```

Now to install GraalVM Native image, simply run:

```
gu install native-image
```

After that, the `native-image` command should work for you:

```
$ native-image --version
GraalVM Version 21.0.0.2 (Java Version 1.8.0_282-b07)
```

# Build the Project

This project has a controller that has two REST endpoints which will accept HTTP GET requests at 
http://localhost:8080/users/{githubUserName} and http://localhost:8080/contributors/{githubOrgName}/{githubRepoName}


As an example, http://localhost:8080/users/shivamgarg7276 will responsd with a JSON representation of a Github User, as follows - 

```
{
    "login": "shivamgarg7276",
    "name": "Shivam Garg",
    "company": "Nutanix",
    "avatarUrl": "https://avatars.githubusercontent.com/u/49524850?v=4",
    "blogUrl": "https://www.linkedin.com/in/shivam-garg-067b46141/",
    "numPublicRepos": 2,
    "htmlUrl": "https://github.com/shivamgarg7276"
}
```

and http://localhost:8080/contributors/shivamgarg7276/graal-micronaut will respond with a list of Github Contributors -

```
[
    {
        "login": "shivamgarg7276",
        "avatarUrl": "https://avatars.githubusercontent.com/u/49524850?v=4",
        "numContributions": 6,
        "htmlUrl": "https://github.com/shivamgarg7276"
    },
    ...
    ...
]
```

## Build without AOT

For building without AOT, you can build the module simple by using this command -  
`mvn clean install`

Once the build succeeds, run -  
`mvn mn:run`

This will start the Micronaut server at port `8080`.   

The startup will be approximately `600 ms`, and the memory consumption should be around `180 MB`


## Build with AOT

For building with AOT, you can build the module simply by using this command -  
`mvn package -Dpackaging=native-image`  

This will create a native executable comprising the Micronaut application under the `target` folder of the module.

Simply invoke -  
`target/rest-service`  

This will start the Micronaut server at port `8080`.   

The startup will be approximately `25 ms` compared to the `600 ms` when using the application without AOT.  
Also, the memory consumption this time should be around `35 MB` compared to `180 MB` in the JIT variant.

# Comparison with Spring Boot

For comparison with Spring Boot, checkout https://github.com/shivamgarg7276/graal-spring-native
