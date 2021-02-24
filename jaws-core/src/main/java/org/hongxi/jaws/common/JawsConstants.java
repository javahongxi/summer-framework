package org.hongxi.jaws.common;

import java.io.File;

/**
 * Created by shenhongxi on 2020/6/26.
 */
public class JawsConstants {

    public static final String FRAMEWORK_NAME = "jaws";

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String PROTOCOL_SEPARATOR = "://";
    public static final String PATH_SEPARATOR = File.separator;

    public static final String NODE_TYPE_SERVICE = "service";

    /**
     * netty channel constants start
     */

    public static final short NETTY_MAGIC_TYPE = (short) 0xF1F1;

    public static final int NETTY_SHARE_CHANNEL_MIN_WORKER_THREADS = 40;
    public static final int NETTY_SHARE_CHANNEL_MAX_WORKER_THREADS = 800;
    public static final int NETTY_NOT_SHARE_CHANNEL_MIN_WORKER_THREADS = 20;
    public static final int NETTY_NOT_SHARE_CHANNEL_MAX_WORKER_THREADS = 200;

    public static final int NETTY_TIMEOUT_TIMER_PERIOD = 100;

    public static final String ASYNC_SUFFIX = "Async";// suffix for async call.

    public static final String DEFAULT_VERSION = "1.0";

    // netty client max concurrent request TODO 2W is suitable?
    public static final int NETTY_CLIENT_MAX_REQUEST = 20000;

    // ------------------ jaws protocol constants -----------------
    public static final String JAWS_GROUP = "S_g";
    public static final String JAWS_VERSION = "S_v";
    public static final String JAWS_PATH = "S_p";
    public static final String JAWS_METHOD = "S_m";
    public static final String JAWS_METHOD_DESC = "S_md";
    public static final String JAWS_AUTH = "S_a";
    public static final String JAWS_SOURCE = "S_s";// 调用方来源标识,等同与application
    public static final String JAWS_MODULE = "S_mdu";
    public static final String JAWS_PROXY_PROTOCOL = "S_pp";
    public static final String JAWS_INFO_SIGN = "S_is";
    public static final String JAWS_ERROR = "S_e";
    public static final String JAWS_PROCESS_TIME = "S_pt";

    public static final String CONTENT_LENGTH = "Content-Length";

    public static final String PROTOCOL_INJVM = "injvm";
    public static final String PROTOCOL_JAWS = "jaws";

    public static final String METHOD_CONFIG_PREFIX = "methodconfig.";

    public static final byte FLAG_REQUEST = 0x00;
    public static final byte FLAG_RESPONSE = 0x01;
    public static final byte FLAG_RESPONSE_VOID = 0x03;
    public static final byte FLAG_RESPONSE_EXCEPTION = 0x05;
    public static final byte FLAG_RESPONSE_ATTACHMENT = 0x07;
    public static final byte FLAG_OTHER = (byte) 0xFF;

}