package cn.sdk.exception;

import org.apache.commons.lang3.StringUtils;

public class HttpPingAnException extends Exception {
 
	private static final long serialVersionUID = 5131146868083616685L;
	private int code = 0;
    private String debug;

    public HttpPingAnException(final String message) {
        super(message);
    }

    public HttpPingAnException(final int code, final String message) {
        this(code, message, message);
    }

    public HttpPingAnException(final int code, final String message, final String debug) {
        super(message);
        this.code = code;
        this.debug = debug;
    }

    public HttpPingAnException(Throwable exception) {
        super(exception);
    }

    public HttpPingAnException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public HttpPingAnException(final int code, final String message, final Throwable exception) {
        this(code, message, exception, StringUtils.EMPTY);
    }

    public HttpPingAnException(final int code, final String message, final Throwable exception, final String debug) {
        super(message, exception);
        this.code = code;
        this.debug = debug;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

}
