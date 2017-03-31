package cn.sdk.exception;

/**
 * 臭美异常基类，关于异常的定义，请参考文档 
 * 【<a href="http://wiki.choumei.me/pages/viewpage.action?pageId=1869079">异常处理及系统消息的传递</a>】和
 * 【<a href="http://wiki.choumei.me/pages/viewpage.action?pageId=1277956">API错误代码及系统异常处理</a>】，
 * 关于{@link #errorCode}的取值，请参考{@link ResultCode}}
 * @author 斌宏
 */
public abstract class CMBaseException extends RuntimeException {

	private static final long serialVersionUID = 5616135743236755327L;
	
	/** 默认错误代码 */
	public static final Integer ERROR_CODE_GENERIC = ResultCode.SYS_UNKNOWN;
	/** 错误代码 */
	protected Integer errorCode;
	
	public Integer getErrorCode() {
		return errorCode;
	}
	
	/**
	 * exception designed with error code
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public CMBaseException(Integer errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public CMBaseException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	/**
	 * throw an exception with erroCode[{@link #ERROR_CODE_GENERIC}}] and message[null]
	 * @param errorCode
	 * @param cause
	 */
	public CMBaseException(Integer errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}

	/** Constructs a new runtime exception with <code>null</code> as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CMBaseException() {
        this(ERROR_CODE_GENERIC, ResultCode.SYS_UNKNOWN_MSG);
    }

    /** Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for 
     *          later retrieval by the {@link #getMessage()} method.
     */
    public CMBaseException(String message) {
    	this(ERROR_CODE_GENERIC, message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public CMBaseException(String message, Throwable cause) {
    	// 利用通用错误代码
    	this(ERROR_CODE_GENERIC, message, cause);
    }

    /** Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public CMBaseException(Throwable cause) {
    	this(ResultCode.SYS_UNKNOWN_MSG, cause);
    }

}
