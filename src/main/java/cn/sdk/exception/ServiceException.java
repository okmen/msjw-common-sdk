package cn.sdk.exception;

/**
 * 服务层异常
 * @author 斌宏
 * @see CMBaseException
 */
public class ServiceException extends CMBaseException {

	private static final long serialVersionUID = 5948842096340784269L;

	public ServiceException(Integer errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public ServiceException(Integer errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}
	
	public ServiceException(Integer errorCode, String message) {
		super(errorCode, message);
	}
	
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
	    super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
