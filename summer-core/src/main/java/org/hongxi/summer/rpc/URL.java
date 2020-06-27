package org.hongxi.summer.rpc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2020/6/14.
 */
public class URL {

    private String protocol;

    private String host;

    private int port;

    private String path;

    private Map<String, String> parameters;

    private volatile transient Map<String, Number> numbers;

    public URL(String protocol, String host, int port, String path) {
        this(protocol, host, port, path, new HashMap<>());
    }

    public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getParameter(String name, String defaultValue) {
        return parameters.getOrDefault(name, defaultValue);
    }

    public boolean getBooleanParameter(String name, boolean defaultValue) {
        String value = getParameter(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public int getIntParameter(String name, int defaultValue) {
        String value = getParameter(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }
}