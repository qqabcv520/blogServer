/**   
 * filename：PasswordService.java
 *   
 * date：2016年4月22日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.util.encryption;


public class PasswordService {

	public static final String CHARS_32 = "CHARS_32";
	public static final String CHARS_16 = "CHARS_16";
	
	private String encryptionChars = CHARS_32;
	private int iteration = 1;
	
	/**   
	 * 加密
	 * @author 范子才
	 * @param password 需要加密的内容
	 * @param salt 加盐
	 * @return 密文
	 * @version 2016年4月22日 下午10:52:45
	 */
	public String encrypt(String password, String salt) {
		String cipherText = password;
		if(CHARS_16.equalsIgnoreCase(encryptionChars)){
			for (int i = 0; i < iteration; i++) {
				String tempPassword = addSalt(cipherText, salt);
				cipherText = EncryptionUtils.get16BitMd5(tempPassword);
			}
		} else {
			for (int i = 0; i < iteration; i++) {
				String tempPassword = addSalt(cipherText, salt);
				cipherText = EncryptionUtils.get32BitMd5(tempPassword);
			}
		}
    	return cipherText;
	}
	
	/**   
	 * 需加密内容和salt混合
	 * @author 范子才
	 * @param cipherText 需加密内容
	 * @param salt 
	 * @return
	 * @version 2016年4月22日 下午10:57:05
	 */
	protected String addSalt(String cipherText, String salt) {
		String newSalt = (salt != null) ? EncryptionUtils.get16BitMd5(salt) : "";
		return cipherText + newSalt;
	}
	
	
	public String getEncryptionChars() {
		return encryptionChars;
	}

	public void setEncryptionChars(String encryptionChars) {
		this.encryptionChars = encryptionChars;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
}
