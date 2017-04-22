/**   
 * filename：StatusCode.java
 *   
 * date：2016年4月21日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.util;

public class MyStatus {


	
	
	/**   
	 * OK:成功
	 */
	public static final int OK = 0;
	/**   
	 * SERVICE_ERROR:服务器错误
	 */
	public static final int SERVICE_ERROR = -1;
	/**   
	 * PASSWORD_ERROR:密码错误
	 */
	public static final int PASSWORD_ERROR = 1;
	/**   
	 * USER_UNKNOW:未知用户
	 */
	public static final int USER_UNKNOW = 2;
	/**   
	 * TOKEN_UNKNOW:未知token
	 */
	public static final int TOKEN_UNKNOW = 3;
	
	/**   
	 * PARAMETER_EXCEPTION:参数异常
	 */
	public static final int PARAMETER_EXCEPTION = 4;
	
	/**   
	 * FILE_SUFFIX_EXCEPTION:文件格式非法
	 */
	public static final int FILE_SUFFIX_EXCEPTION = 5;

	/**   
	 * USERNAME_EXISTED:用户名已存在
	 */
	public static final int USERNAME_EXISTED = 6;
	
	/**   
	 * VERIFICATION_ERROR:验证码错误
	 */
	public static final int VERIFICATION_ERROR = 7;
	
	/**   
	 * PHONE_EXISTED:手机号已存在
	 */
	public static final int PHONE_EXISTED = 8;
	
	/**   
	 * NO_PERMISSION:无权限
	 */
	public static final int NO_PERMISSION = 9;
	

	/**   
	 * NOT_FOUND_RESOURCES:资源未找到
	 */
	public static final int NOT_FOUND_RESOURCES = 10;
	
	
	
	
	private int code;
	
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String  getDescription () {
		return codeToDescription(code);
	}
	
	
	/**   
	 * Status.
	 */   
	    
	public MyStatus() {
		super();
	}

	
	/**   
	 * 请求Status.
	 * @param code 状态码
	 */   
	    
	public MyStatus(int code) {
		super();
		this.code = code;
	}

	public static String codeToDescription(int code){
		switch (code) {
		case OK:
			return "成功";
		case PASSWORD_ERROR:
			return "密码错误";
		case USER_UNKNOW:
			return "用户不存在";
		case TOKEN_UNKNOW:
			return "口令无效";
		case PARAMETER_EXCEPTION:
			return "参数异常";
		case FILE_SUFFIX_EXCEPTION:
			return "文件格式非法";
		case VERIFICATION_ERROR:
			return "验证码错误";
		case SERVICE_ERROR:
			return "服务器错误，请联系管理员";
		case PHONE_EXISTED:
			return "手机号已存在";
		case NO_PERMISSION:
			return "无权限";
		case NOT_FOUND_RESOURCES:
			return "资源未找到";
		default:
			return "未知错误，请联系管理员";
		}
		
	}
	
	
}
