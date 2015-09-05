package com.guo.duoduo.httpserver.http;


import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
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

        HttpEntity entity = new StringEntity("", Constant.ENCODING);

        String contentType = "text/html;charset=" + Constant.ENCODING;

        if (!file.exists())
        {
            Log.d(tag, " file is not exist");
            httpResponse.setStatusCode(HttpStatus.SC_NOT_FOUND);
        }
        else if (file.canRead())
        {
            httpResponse.setStatusCode(HttpStatus.SC_OK);
            if (file.isDirectory())
            {
                Log.d(tag, " file is directory");
                String msg = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; "
                    + "charset=utf-8\"><title>文件服务</title></head><body><h1>Directory "
                    + target + "</h1><br/>";

                String[] files = file.list();
                if (files != null)
                {
                    for (int i = 0; i < files.length; i++)
                    {
                        File curFile = new File(file, files[i]);
                        boolean isDir = curFile.isDirectory();
                        if (isDir)
                        {
                            msg += "<b>";
                            files[i] += "/";
                        }
                        msg += "<a href=\"" + encodeUri(target + files[i]) + "\">"
                            + files[i] + "</a>";
                        // Show file size
                        if (curFile.isFile())
                        {
                            long len = curFile.length();
                            msg += " &nbsp;<font size=2>(";
                            if (len < 1024)
                                msg += len + " bytes";
                            else if (len < 1024 * 1024)
                                msg += len / 1024 + "." + (len % 1024 / 10 % 100) + " KB";
                            else
                                msg += len / (1024 * 1024) + "." + len % (1024 * 1024)
                                    / 10 % 100 + " MB";

                            msg += ")</font>";
                        }
                        msg += "<br/>";
                        if (isDir)
                            msg += "</b>";
                    }
                }
                msg += "</body></html>";
                entity = new StringEntity(msg, Constant.ENCODING);
            }
            else
            {
                Log.d(tag, " file is real file");

            }
        }
        else
        {
            Log.d(tag, " file is forbidden");
            httpResponse.setStatusCode(HttpStatus.SC_FORBIDDEN);
        }

        httpResponse.setHeader("Content-Type", contentType);
        httpResponse.setEntity(entity);
    }

    private String encodeUri(String uri)
    {
        String newUri = "";
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens())
        {
            String tok = st.nextToken();
            if (tok.equals("/"))
                newUri += "/";
            else if (tok.equals(" "))
                newUri += "%20";
            else
            {
                newUri += URLEncoder.encode(tok);
            }
        }
        return newUri;
    }

}
