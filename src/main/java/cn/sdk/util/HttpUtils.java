package cn.sdk.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public final class HttpUtils {

    public static final int JS_URL_ENCODE_MODE = 1;

    public static Map<String, String> parseQueryString(final String quereyString) {

        final Map<String, String> resultMap = new HashMap<>();

        if (!StringUtils.isBlank(quereyString)) {

            String[] blocks = StringUtils.split(quereyString, "&");

            for (String block : blocks) {

                String[] kv = StringUtils.split(block, "=");
                if (kv.length == 2) {

                    resultMap.put(kv[0], kv[1]);
                }
            }
        }

        return resultMap;
    }

    public static String generateQueryString(final Map<String, String> map) {

        return generateQueryString(map, -1);
    }

    public static String generateQueryString(final Map<String, String> map, final int urlEncodeMode) {

        String queryString = StringUtils.EMPTY;
        for (Map.Entry<String, String> entry : map.entrySet()) {

            queryString += entry.getKey() + "=";

            if (urlEncodeMode >= 0 && StringUtils.isNotEmpty(entry.getValue())) {
                
                String encodedValue = StringUtils.EMPTY;
                
                try {
                    encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {}
                
                if (urlEncodeMode == JS_URL_ENCODE_MODE) {
                    encodedValue = encodedValue.replaceAll("\\+", "%20")
                            .replaceAll("\\%21", "!")
                            .replaceAll("\\%27", "'")
                            .replaceAll("\\%28", "(")
                            .replaceAll("\\%29", ")")
                            .replaceAll("\\%7E", "~");
                }
                
                queryString += encodedValue;
                
            } else {
                queryString += entry.getValue();
            }

            queryString += "&";
        }

        return StringUtils.removeEnd(queryString, "&");
    }
    
    public static String findRealIp(final HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        
        ip = request.getHeader("X-Real-IP");

        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        } else {
            return request.getRemoteAddr();
        }
    }
}
