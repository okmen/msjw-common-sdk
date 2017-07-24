package cn.sdk.pingan;

@SuppressWarnings("serial")
public class CryptException extends Exception {

	public final static int OK = 0;
	/**
	 * 签名验证错误
	 */
	public final static int ValidateSignatureError = -40001;
	/**
	 * SHA加密生成签名失败
	 */
	public final static int ComputeSignatureError = -40003;
	/**
	 * AES加密失败
	 */
	public final static int EncryptAESError = -40006;
	/**
	 * AES解密失败
	 */
	public final static int DecryptAESError = -40007;
	/**
	 * 解密后得到的buffer非法
	 */
	public final static int IllegalBuffer = -40008;

	private int code;

	private static String getMessage(int code) {
		switch (code) {
		case ValidateSignatureError:
			return "签名验证错误";
		case ComputeSignatureError:
			return "SHA加密生成签名失败";
		case EncryptAESError:
			return "AES加密失败";
		case DecryptAESError:
			return "AES解密失败";
		case IllegalBuffer:
			return "解密后得到的buffer非法";
		default:
			return null; // cannot be
		}
	}

	public int getCode() {
		return code;
	}

	CryptException(int code) {
		super(getMessage(code));
		this.code = code;
	}

}
