# JWebPanel



## About project

This project is a simple panel for observing server chat on a web panel website.

Main page:
<img width="1000" height="500" alt="image" src="https://github.com/user-attachments/assets/de48d5cc-1cc2-4470-8004-9f1a37420586" />


Chat log:
<img width="1000" height="500" alt="image" src="https://github.com/user-attachments/assets/5a9df09b-7cb9-4034-a5b5-238192358fd2" />


## Build

To build this project use `./gradlew :PWebsitePanel:bootJar` for the Spring panel and `./gradlew shadowJar` to build the panel plugin.  

Result files should be located in `JWebPanel\PPanelPlugin\build\libs` and `JWebPanel\PWebsitePanel\build\libs`.

The result file of the PWebsitePanel module is a Spring application, so it needs to be run using `java -jar filename.jar` in Command Prompt.

The PPanelPlugin module build output is a Minecraft Spigot plugin file. Installation is the same as for other Minecraft plugins.



## Environment

These modules were tested on Windows 11 and Minecraft engine: Paper version 1.21.1-34-master.



## Project isn't ended yet...

