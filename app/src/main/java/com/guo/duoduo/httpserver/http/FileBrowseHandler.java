package com.guo.duoduo.httpserver.http;


import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import android.util.Log;

import com.guo.duoduo.httpserver.utils.Constant;


/**
 * Created by Guo.Duo duo on 2015/9/5.
 */
public class FileBrowseHandler implements HttpRequestHandler
{
    private static final String tag = FileBrowseHandler.class.getSimpleName();

    private String webRoot;

    public FileBrowseHandler(String webRoot)
    {
        this.webRoot = webRoot;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse,
            HttpContext httpContext) throws HttpException, IOException
    {

        String target = URLDecoder.decode(httpRequest.getRequestLine().getUri(),
            Constant.ENCODING);

        Log.d(tag, "http request target = " + target);

        File file = new File(target);

        HttpEntity entity = null;
        String contentType = "text/html;charset=" + Constant.ENCODING;

        if (!file.exists())
        {
            httpResponse.setStatusCode(HttpStatus.SC_NOT_FOUND);
        }
        else if (file.canRead())
        {
            httpResponse.setStatusCode(HttpStatus.SC_OK);
            if (file.isDirectory())
            {

            }
            else
            {

            }
        }
        else
        {
            httpResponse.setStatusCode(HttpStatus.SC_FORBIDDEN);
        }

        httpResponse.setHeader("Content-Type", contentType);
        httpResponse.setEntity(entity);
    }
}
