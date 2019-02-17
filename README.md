# Chat-Mini-Programs
一个简单的聊天小程序。

分为客户端和服务器端两部分。

电脑客户端：
    是一个引用了JavaFX资源的Maven项目，打包文件中的“聊天客户端.jar”文件即是客户端的可执行程序。

电脑客户端的使用方式为：
    首先指定自己的UserId，UserId的格式为：十位字符,包括英文和数字，然后在连接互联网的条件下，就可以自动接收到所有发送给这个用户ID的消息。
    在指定了ToUserId的情况下，即可向对方发送消息。
    
安卓客户端：
    是将电脑客户端代码应用到安卓项目里，界面比较简单，主要实现功能。使用方式与电脑客户端相同。项目的最低API版本为:API18，可兼容至Android4.3操作系统。

服务器端：
    是一个纯后端提供接口服务的SpringBoot项目，已有打包文件，打包位置为：Chat-Mini-Programs/聊天服务器端/out/artifacts/_war_exploded。
    因为其中服务器端的数据库连接的信息被我删去了，所以在部署时服务器端项目时，需要自行添加数据库连接的信息。而数据库所需的SQL文件我也已经添加到服务器端项目中了。

____________________________________________________________________________________________________________________________________________

A simple chat Mini Programs. 

It is divided into two parts: client side and server side. 

Computer client: is a reference to JavaFX resources of the Maven project, packaged files in the "聊天客户端.jar" file is the client executable program. 

The computer client is used in such a way that it first specifies that its own UserId,UserId format is: ten characters, including English and numbers, and then automatically receives all messages sent to that user under the condition of connecting to the Internet. When ToUserId is specified, a message can be sent to the other party. 

Android client: is to apply computer client code to Android projects, the interface is relatively simple, the main implementation of the function. Use in the same way as a computer client. The minimum API version of the project is: API18, is compatible with the Android4.3 operating system. 

Server-side: is a pure back-end interface services SpringBoot project, there are packaging files, packaging location: Chat-Mini-Programs/聊天服务器端/out/artifacts/_war_exploded. Because I deleted the information about the server-side database connection, you needed to add the database connection information yourself when deploying the server-side project. And I've added the SQL files needed for the database to the server-side project.
