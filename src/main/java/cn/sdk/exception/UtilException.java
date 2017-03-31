package cn.sdk.exception;

/**
 * 工具栏异常
 * @author wubinhong
 * @see CMBaseException
 */
public class UtilException extends CMBaseException {
	
	private static final long serialVersionUID = -1780441434978307296L;

	public UtilException(Integer errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public UtilException(Integer errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}
	
	public UtilException(Integer errorCode, String message) {
		super(errorCode, message);
	}
	
    public UtilException() {
        super();
    }

    public UtilException(String message) {
	    super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UtilException(Throwable cause) {
        super(cause);
    }
}
