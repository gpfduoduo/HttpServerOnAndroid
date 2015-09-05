package com.guo.duoduo.httpserver.utils;


/**
 * Created by Guo.Duo duo on 2015/9/4.
 */
public class Constant
{

    public static final String ENCODING = "UTF-8";

    public static interface MSG
    {
        public static final int GET_NETWORK_ERROR = -1;
        public static final int GET_NETWORK_OK = 0;
    }

    public static interface Config
    {
        public static final int PORT = 5000;
        public static final String Web_Root = "/";

    }

    public static interface Http
    {
        public static final String BROWSE = "*";
        public static final String DOWNLOAD = "/download";
    }
}
