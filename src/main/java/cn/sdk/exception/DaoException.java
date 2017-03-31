package cn.sdk.exception;

/**
 * 
 * @author 斌宏
 * 数据访问层异常
 * @see http://wiki.choumei.me/pages/viewpage.action?pageId=1869079
 *
 */
public class DaoException extends CMBaseException {

	private static final long serialVersionUID = 864429653671407479L;

	public DaoException(Integer errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
	
	public DaoException(Integer errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}
	
	public DaoException(Integer errorCode, String message) {
		super(errorCode, message);
	}
	
    public DaoException() {
        super();
    }

    public DaoException(String message) {
	    super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DaoException(Throwable cause) {
        super(cause);
    }

}
