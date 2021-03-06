/**   
 * filename：HttpException.java
 *   
 * date：2017年3月23日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.resolver;
public class HttpException extends Exception {

	private static final long serialVersionUID = 4790642019499595070L;
	
	private int code;
	
	public HttpException(int code, String msg) {  
        super(msg);
        this.setCode(code);
    }

	/**   
	 * code
	 *   
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**   
	 * @param code the code to set   
	 */
	public void setCode(int code) {
		this.code = code;
	} 
	
}
