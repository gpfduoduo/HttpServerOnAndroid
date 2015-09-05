# HttpServerOnAndroid
在Android下几种实现HttpServer的方法-总结

# 项目介绍 
之前开源的项目DLNA和Airplay中都用到了HttpServer。   
DLNA中需要实现HttpServer作为DMS等。  
Airplay中需要实现HttpServer来接受苹果设备作为客户端推送的get和post消息。   
因此现在总结以下HttpServer的实现方法。  
以在PC中通过浏览器浏览sdcard中的内容为例说明http server的使用方法。  

## 使用apache的HttpCore实现HttpServer
这应该是相对来说比较简单的方式，HttpCore将所有的http交互处理逻辑封装好了。你只需要实现继承并实现HttpRequestHandler的handle方法，然后懂得使用httpRequest和httpResonse就可以实现一个较好的http server。


## 效果图
手机端：     
![image](https://github.com/gpfduoduo/HttpServerOnAndroid/blob/master/device-2015-09-05-220442.png "手机端效果图")    
PC端浏览器：   
![image](https://github.com/gpfduoduo/HttpServerOnAndroid/blob/master/http%20core%20browse%20sdcard.PNG "PC端浏览器效果图")   
