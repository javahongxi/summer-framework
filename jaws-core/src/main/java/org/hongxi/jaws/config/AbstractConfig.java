package org.hongxi.jaws.config;

import org.apache.commons.lang3.StringUtils;
import org.hongxi.jaws.common.JawsConstants;
import org.hongxi.jaws.config.annotation.ConfigDesc;
import org.hongxi.jaws.exception.JawsErrorMsgConstants;
import org.hongxi.jaws.exception.JawsFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * Created by shenhongxi on 2021/3/5.
 */
public class AbstractConfig implements Serializable {
    private static final long serialVersionUID = 6221123514996466731L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractConfig.class);

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 按顺序进行config 参数append and override，按照configs出现的顺序，后面的会覆盖前面的相同名称的参数
     *
     * @param parameters
     * @param configs
     */
    protected static void collectConfigParams(Map<String, String> parameters, AbstractConfig... configs) {
        for (AbstractConfig config : configs) {
            if (config != null) {
                config.appendConfigParams(parameters);
            }
        }
    }

    protected static void collectMethodConfigParams(Map<String, String> parameters, List<MethodConfig> methods) {
        if (methods == null || methods.isEmpty()) {
            return;
        }
        for (MethodConfig mc : methods) {
            if (mc != null) {
                mc.appendConfigParams(parameters, JawsConstants.METHOD_CONFIG_PREFIX + mc.getName() + "(" + mc.getArgumentTypes() + ")");
            }
        }
    }

    protected void appendConfigParams(Map<String, String> parameters) {
        appendConfigParams(parameters, null);
    }

    /**
     * 将config 参数录入Map中
     *
     * @param parameters
     */
    @SuppressWarnings("unchecked")
    protected void appendConfigParams(Map<String, String> parameters, String prefix) {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            try {
                String name = method.getName();
                if (isConfigMethod(method)) {
                    int idx = name.startsWith("get") ? 3 : 2;
                    String key = name.substring(idx, idx + 1).toLowerCase() + name.substring(idx + 1);
                    ConfigDesc configDesc = method.getAnnotation(ConfigDesc.class);
                    if (configDesc != null && !StringUtils.isBlank(configDesc.key())) {
                        key = configDesc.key();
                    }

                    Object value = method.invoke(this);
                    if (value == null || StringUtils.isBlank(String.valueOf(value))) {
                        if (configDesc != null && configDesc.required()) {
                            throw new JawsFrameworkException(String.format("%s.%s should not be null or empty", this.getClass()
                                    .getSimpleName(), key), JawsErrorMsgConstants.FRAMEWORK_INIT_ERROR);
                        }
                        continue;
                    }
                    if (prefix != null && prefix.length() > 0) {
                        key = prefix + "." + key;
                    }
                    parameters.put(key, String.valueOf(value).trim());
                } else if ("getParameters".equals(name) && Modifier.isPublic(method.getModifiers())
                        && method.getParameterTypes().length == 0 && method.getReturnType() == Map.class) {
                    Map<String, String> map = (Map<String, String>) method.invoke(this);
                    if (map != null && map.size() > 0) {
                        String pre = prefix != null && prefix.length() > 0 ? prefix + "." : "";
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            parameters.put(pre + entry.getKey(), entry.getValue());
                        }
                    }
                }
            } catch (Exception e) {
                throw new JawsFrameworkException(String.format("Error when append params for config: %s.%s", this.getClass()
                        .getSimpleName(), method.getName()), e, JawsErrorMsgConstants.FRAMEWORK_INIT_ERROR);
            }
        }
    }

    private boolean isConfigMethod(Method method) {
        boolean checkMethod =
                (method.getName().startsWith("get") || method.getName().startsWith("is")) && !"isDefault".equals(method.getName())
                        && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0
                        && isPrimitive(method.getReturnType());

        if (checkMethod) {
            ConfigDesc configDesc = method.getAnnotation(ConfigDesc.class);
            if (configDesc != null && configDesc.excluded()) {
                return false;
            }
        }
        return checkMethod;
    }

    private boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == String.class || type == Character.class || type == Boolean.class || type == Byte.class
                || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class;
    }

    @Override
    public String toString() {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append("<jaws:");
            buf.append(getTagName(getClass()));
            Method[] methods = getClass().getMethods();
            for (Method method : methods) {
                try {
                    String name = method.getName();
                    if ((name.startsWith("get") || name.startsWith("is")) && !"getClass".equals(name) && !"get".equals(name)
                            && !"is".equals(name) && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0
                            && isPrimitive(method.getReturnType())) {
                        int i = name.startsWith("get") ? 3 : 2;
                        String key = name.substring(i, i + 1).toLowerCase() + name.substring(i + 1);
                        Object value = method.invoke(this);
                        if (value != null) {
                            buf.append(" ");
                            buf.append(key);
                            buf.append("=\"");
                            buf.append(value);
                            buf.append("\"");
                        }
                    }
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            buf.append(" />");
            return buf.toString();
        } catch (Throwable t) { // 防御性容错
            logger.warn(t.getMessage(), t);
            return super.toString();
        }
    }

    private static final String[] SUFFIXS = new String[] {"Config", "Bean"};

    private static String getTagName(Class<?> cls) {
        String tag = cls.getSimpleName();
        for (String suffix : SUFFIXS) {
            if (tag.endsWith(suffix)) {
                tag = tag.substring(0, tag.length() - suffix.length());
                break;
            }
        }
        tag = tag.toLowerCase();
        return tag;
    }
}
