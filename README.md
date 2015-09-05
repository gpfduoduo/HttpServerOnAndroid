# HttpServerOnAndroid
在Android下几种实现HttpServer的方法-总结

# 项目介绍 
之前开源的项目DLNA和Airplay中都用到了HttpServer。   
DLNA中需要实现HttpServer作为DMS等。  
Airplay中需要实现HttpServer来接受苹果设备作为客户端推送的get和post消息。   
因此现在总结以下HttpServer的实现方法。当然你也可以百度的得到这些开源实现方法。  
接下来将以通过http 方法访问你手机sdcrad中的文件为例，说明HttpServer的使用方法



