# Emotecraft primitive proxy


This is a primitive implementation of [Emotecraft](https://github.com/KosmX/emotes) proxy API with [Netty](https://netty.io/)  


## Build
Use JDK 17 or newer  
`./gradlew build` to build and package every module.  
Fabric client jar: `client/build/libs/emotes-proxy-{version}.jar`
Server jar: `server/build/libs/server-{version}-shadow.jar`

## Install
Install [Fabric](https://fabricmc.net/),
Put the proxy JAR **and** Emotecraft jar into the `.minecraft/mods` folder


## Run the server

Check if you have __at least__ Java **17** with `java -version`  
If not, install it.

Run the server with `java -jar server.jar <args>`

## Server args:
```shell
usage: utility-name
 -h,--hideWarning     Hide no encryption warning
 -p,--port <arg>      Server port
```
-p is mandatory  
-a is unsupported  

### Example start

```shell
java -jar server.jar -p 1234
```

## Client commands
There is no config GUI or persistence  

`emotesProxy status` display the proxy status. (If connected, it will show an address)  
`emotesProxy connect <IP> <port>` connects to a proxy server
### Example connect
`emotesProxy connect 127.0.0.1 1234`

## Footnote

This project is an experiment, It may contain code for different functions.  
If you find any issue, please report it!  

Project is under CC0 license, you can port it to other version, embed in other project etc.

