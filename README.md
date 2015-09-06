# HttpServerOnAndroid
在Android下几种实现HttpServer的方法-总结

# 项目介绍 
之前开源的项目DLNA和Airplay中都用到了HttpServer。   
DLNA中需要实现HttpServer作为DMS等。  
Airplay中需要实现HttpServer来接受苹果设备作为客户端推送的get和post消息。   
因此现在总结以下HttpServer的实现方法。  
以在PC中通过浏览器浏览sdcard中的内容为例说明http server的使用方法。  

## 目前实现
通过在浏览器输入ip的方式，浏览你的手机中的文件及其文件夹（sdcard），对于文件，通过点击即可下载。


## 使用apache的HttpCore实现HttpServer
这应该是相对来说比较简单的方式，HttpCore将所有的http交互处理逻辑封装好了。    
你只需要实现继承并实现HttpRequestHandler的handle方法，然后懂得使用httpRequest和httpResonse就可以实现一个较好的http server。    
具体的：   
```JAVA
  //设置 http 参数
            params = new BasicHttpParams();
            params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
                    .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5 * 1000)
                    .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK,
                        false)
                    .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
                    .setParameter(CoreProtocolPNames.ORIGIN_SERVER, "HttpComponents/1.1");

            BasicHttpProcessor httpProcessor = new BasicHttpProcessor();//http协议处理器
            httpProcessor.addInterceptor(new ResponseDate());//http协议拦截器，响应日期
            httpProcessor.addInterceptor(new ResponseServer());//响应服务器
            httpProcessor.addInterceptor(new ResponseContent());//响应内容
            httpProcessor.addInterceptor(new ResponseConnControl());//响应连接控制

            //http请求处理程序解析器
            HttpRequestHandlerRegistry registry = new HttpRequestHandlerRegistry();

            //http请求处理程序，HttpFileHandler继承于HttpRequestHandler（http请求处理程序)
            registry.register(Constant.Http.BROWSE, new FileBrowseHandler(webRoot));
            registry.register(Constant.Http.DOWNLOAD, new FileDownLoadHandler(webRoot));

            httpService = new HttpService(httpProcessor,
                new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());
            httpService.setParams(params);
            httpService.setHandlerResolver(registry);//为http服务设置注册好的请求处理器。
```
手机端收到的浏览器的请求在FileBrowseHandler和FileDownLoadHandler中进行相应的处理即可。  

## 效果图
手机端：     
![image](https://github.com/gpfduoduo/HttpServerOnAndroid/blob/master/device-2015-09-05-220442.png "手机端效果图")     

PC端浏览器：   
![image](https://github.com/gpfduoduo/HttpServerOnAndroid/blob/master/http%20core%20browse%20sdcard.PNG "PC端浏览器效果图")   
