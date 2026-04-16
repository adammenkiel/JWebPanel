# JWebPanel



## About project

This project is a simple panel for observing server chat on a web panel website.



## Build

To build this project use `./gradlew :PWebsitePanel:bootJar` for the Spring panel and `./gradlew shadowJar` to build the panel plugin.  

Result files should be located in `JWebPanel\PPanelPlugin\build\libs` and `JWebPanel\PWebsitePanel\build\libs`.

The result file of the PWebsitePanel module is a Spring application, so it needs to be run using `java -jar filename.jar` in Command Prompt.

The PPanelPlugin module build output is a Minecraft Spigot plugin file. Installation is the same as for other Minecraft plugins.



## Environment

These modules were tested on Windows 11 and Minecraft engine: Paper version 1.21.1-34-master.



## Project isn't ended yet...

