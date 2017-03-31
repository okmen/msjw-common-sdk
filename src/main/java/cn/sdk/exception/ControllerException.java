package cn.sdk.exception;

/**
 * 控制层异常
 * @author 斌宏
 * @see {@link CMBaseException}}
 */
public class ControllerException extends CMBaseException {

	private static final long serialVersionUID = 6053308316086145363L;

	public ControllerException(Integer errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public ControllerException(Integer errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}
	
	public ControllerException(Integer errorCode, String message) {
		super(errorCode, message);
	}
	
    public ControllerException() {
        super();
    }

    public ControllerException(String message) {
	    super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ControllerException(Throwable cause) {
        super(cause);
    }
}
