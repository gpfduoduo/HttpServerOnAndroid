package com.guo.duoduo.httpserver.http;


import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;


/**
 * Created by Guo.Duo duo on 2015/9/5.
 */
public class FileDownLoadHandler implements HttpRequestHandler
{
    public FileDownLoadHandler(String webRoot)
    {

    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse,
            HttpContext httpContext) throws HttpException, IOException
    {

    }
}
