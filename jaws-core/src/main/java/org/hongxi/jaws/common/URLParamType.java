package org.hongxi.jaws.common;

import org.hongxi.jaws.config.RegistryConfig;

/**
 * Created by shenhongxi on 2020/6/27.
 */
public enum URLParamType {

    version("version", JawsConstants.DEFAULT_VERSION),

    requestTimeout("requestTimeout", 200),
    /** request id from http interface **/
    requestIdFromClient("requestIdFromClient", 0),

    connectTimeout("connectTimeout", 1000),

    minWorkerThreads("minWorkerThreads", 20),

    maxWorkerThreads("maxWorkerThreads", 200),

    maxContentLength("maxContentLength", 10 * 1024 * 1024),

    maxServerConnections("maxServerConnections", 100000),

    minClientConnections("minClientConnections", 2),
    maxClientConnections("maxClientConnections", 10),
    maxConnectionsPerGroup("maxConnectionsPerGroup", 0),

    registryRetryPeriod("registryRetryPeriod", 30 * JawsConstants.SECOND_MILLS),
    /* 注册中心不可用节点剔除方式 */
    excise("excise", RegistryConfig.Excise.excise_dynamic.getName()),
    cluster("cluster", JawsConstants.DEFAULT_VALUE),
    loadbalance("loadbalance", "activeWeight"),
    haStrategy("haStrategy", "failover"),
    protocol("protocol", JawsConstants.PROTOCOL_JAWS),
    path("path", ""),
    host("host", ""),
    port("port", 0),
    proxy("proxy", JawsConstants.PROXY_JDK),
    filter("filter", ""),

    /**
     * multi referer share the same channel
     */
    shareChannel("shareChannel", false),
    asyncInitConnection("asyncInitConnection", false),
    fusingThreshold("fusingThreshold", 10),

    heartbeatFactory("heartbeatFactory", "jaws"),

    /************************** SPI start ******************************/

    serialization("serialization", "hessian2"),

    codec("codec", "jaws"),
    endpointFactory("endpointFactory", "jaws"),

    switcherService("switcherService", "localSwitcherService"),

    /************************** SPI end ******************************/

    group("group", "default_rpc"),
    clientGroup("clientGroup", "default_rpc"),
    accessLog("accessLog", false),

    refreshTimestamp("refreshTimestamp", 0),
    nodeType("nodeType", JawsConstants.NODE_TYPE_SERVICE),

    gzip("gzip", false), // 是否开启gzip压缩
    minGzipSize("minGzipSize", 1000), // 进行gz压缩的最小数据大小。超过此阈值才进行gz压缩

    application("application", JawsConstants.FRAMEWORK_NAME),
    module("module", JawsConstants.FRAMEWORK_NAME),

    retries("retries", 0),
    mock("mock", "false"),
    mean("mean", "2"),
    p90("p90", "4"),
    p99("p99", "10"),
    p999("p999", "70"),
    errorRate("errorRate", "0.01"),
    check("check", "true"),
    registrySessionTimeout("registrySessionTimeout", 1 * JawsConstants.MINUTE_MILLS),
    directUrl("directUrl", ""),

    register("register", true),
    subscribe("subscribe", true),
    throwException("throwException", "true"),
    transExceptionStack("transExceptionStack", true),

    // 切换group时，各个group的权重比。默认无权重
    weights("weights", ""),

    // 消息处理分发策略
    providerProtectedStrategy("providerProtectedStrategy", "jaws"),

    workerQueueSize("workerQueueSize", 0);

    private String name;
    private String value;
    private int intValue;
    private long longValue;
    private boolean boolValue;

    URLParamType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    URLParamType(String name, int intValue) {
        this.name = name;
        this.value = String.valueOf(intValue);
        this.intValue = intValue;
    }

    URLParamType(String name, long longValue) {
        this.name = name;
        this.value = String.valueOf(longValue);
        this.longValue = longValue;
    }

    URLParamType(String name, boolean boolValue) {
        this.name = name;
        this.value = String.valueOf(boolValue);
        this.boolValue = boolValue;
    }

    public String getName() {
        return name;
    }

    public String value() {
        return value;
    }

    public int intValue() {
        return intValue;
    }

    public long longValue() {
        return longValue;
    }

    public boolean boolValue() {
        return boolValue;
    }
}
