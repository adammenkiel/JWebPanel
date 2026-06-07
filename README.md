# JWebPanel
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) ![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white) ![Last Commit](https://img.shields.io/github/last-commit/adammenkiel/AEP?style=for-the-badge) ![Activity](https://img.shields.io/github/commit-activity/m/adammenkiel/AEP?style=for-the-badge)
# Table of Contents
- [Project description](#project-description)
- [RestAPI documentation](#restapi-documentation)
  - [POST /api/auth/login](#post-apiauthlogin)
  - [POST /api/auth/register](#post-apiauthregister)
  - [POST /api/auth/status](#post-apiauthstatus)
  - [GET /api/data/panel](#get-apidatapanel)
- [WebSocket](#websocket)
- [Potential problems](#potential-problems)
- [Build + Run](#build--run)
  - [Backend](#backend)
  - [Frontend](#frontend)
- [Tests](#tests)
- [Technologies](#technologies)
  - [Frontend](#frontend)
  - [Backend](#backend)
- [Environment](#environment)
- [Licence](#licence)
- [Contact](#contact)
# Project description

This is a full-stack web panel for players with a backend implemented in ``Java`` and a frontend implemented in ``TypeScript`` (with ``React``). Currently the project supports auth and reading chat messages from minecraft server in real-time. As the project is still in progress (although it works at a basic level now) in the near future the following features will be added:
- Email verification
- The ability to pin player to specific panel account.
- Access for players to manage their own accounts and get information about their own accounts and broadcasts.
- Access for manage other accounts for administrators etc.


# RestAPI documentation
## POST /api/auth/login

Description: Request for login into account, sends login data.
Requires:
  - ``username``: account name
  - ``password``: account password
Returns:
  - User data response:
    - Sets cookie HTTP only with name: ``webpanel``
    - ``User id`` - Identificator of user from database
    - ``Username`` - Username
    - ``Email`` - User's registeration email
    - ``Roles`` - Roles of user, for example `admin`

## POST /api/auth/register

Description: Request for create a new account, sends register data.
Requires:
  - ``username``: account name
  - ``email``: account email
  - ``password``: account password	
Returns:
  - Response.ok message with information

## POST /api/auth/status

Description: Unused yet. Allows to check if player is logged in, maybe it will be helpful in the future to check if specific ProtectedRoute should be show or not, and after some changes it will be able to validate frontend UX data.
Returns:
  - ``True`` if account is logged in
  - ``False`` if account isn't logged in

## GET /api/data/panel
  Requires:
    - Session is authenticated.
  Returns: 20 last player chat messages.

# WebSocket
WebSocket is available at ``/ws`` endpoint. While there is a connection between WebSocket client and WebSocket server, the server will send new chat messages in real time.
# Potential problems.
There is no implemented validation for which message is already received by frontend, so while connection starts, when web browser will receives ``GET`` response (20 messages) and someone will send message at the same time, that message may not be received or duplicated.
As ``AsyncChatEvent`` is asynchronous, ``ChatQueue`` may potentially throw ``ConcurrentModificationException``
These issues have not been verified yet.

# Build + Run

## Backend
To build this project use `./gradlew :PWebsitePanel:bootJar` for the Spring panel and `./gradlew shadowJar` to build the panel plugin.  

Result files should be located in `JWebPanel\PPanelPlugin\build\libs` and `JWebPanel\PWebsitePanel\build\libs`.

The result file of the PWebsitePanel module is a Spring application, so it needs to be run using `java -jar filename.jar` in Command Prompt.

The PPanelPlugin module build output is a Minecraft Spigot plugin file. Installation is the same as for other Minecraft plugins.

## Frontend
To run frontend as dev move use:
```
npm install
npm run dev
```
For build please use:
``npm run build``

# Tests
To test backend please use `./gradlew clean test` - it mainly tests of netty protocol, it will be developed yet.

# Technologies
- CI/CD
## FrontEnd:
- React
- TypeScript
- Shadcn
- react-router-dom
## Backend:
- Java
- Spring-Boot (Security, Validation, WebSocket, JPA, Test)
- Gradle
- SLF4J
- JUnit
- RestAPI
- JJWT
- Hibernate
- Jakarta
- Netty
- Spigot

# Environment
These modules were tested on ``Windows 11`` and Minecraft server engine: ``Paper version 1.21.1-34-master.``

# Licence
Project is based on MIT Licence. Licence could be found in ``LICENCE.md`` file.
# Contact
Email: akmenkiel@gmail.com

