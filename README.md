﻿# Chat-Mini-Programs
一个简单的聊天小程序。

分为客户端和服务器端两部分。

电脑客户端：
    是一个引用了JavaFX资源的Maven项目，打包文件中的“聊天客户端.jar”文件即是客户端的可执行程序。

电脑客户端的使用方式为：
    首先指定自己的UserId，UserId的格式为：十位字符,包括英文和数字，然后在连接互联网的条件下，就可以自动接收到所有发送给这个用户ID的消息。
    在指定了ToUserId的情况下，即可向对方发送消息。
    
安卓客户端：
    是将电脑客户端代码应用到安卓项目里，界面比较简单，主要实现功能。使用方式与电脑客户端相同。

服务器端：
    是一个纯后端提供接口服务的SpringBoot项目，已有打包文件，打包位置为：Chat-Mini-Programs/聊天服务器端/out/artifacts/_war_exploded。
    因为其中服务器端的数据库连接的信息被我删去了，所以在部署时服务器端项目时，需要自行添加数据库连接的信息。而数据库所需的SQL文件我也已经添加到服务器端项目中了。
