package cn.sdk.exception;

import org.apache.commons.lang3.StringUtils;

public class WebServiceException extends Exception {

    private static final long serialVersionUID = -3177104139756150435L;

    private int code = 0;
    private String debug;

    public WebServiceException(final String message) {
        super(message);
    }

    public WebServiceException(final int code, final String message) {
        this(code, message, message);
    }

    public WebServiceException(final int code, final String message, final String debug) {
        super(message);
        this.code = code;
        this.debug = debug;
    }

    public WebServiceException(Throwable exception) {
        super(exception);
    }

    public WebServiceException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public WebServiceException(final int code, final String message, final Throwable exception) {
        this(code, message, exception, StringUtils.EMPTY);
    }

    public WebServiceException(final int code, final String message, final Throwable exception, final String debug) {
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
