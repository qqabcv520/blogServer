/**   
 * filename：MyUtil.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.util;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


public class MyUtils {
	
//	//关闭fastjson循环引用检查
//	static {
//		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
//	}
	
	
	
	/**   
	 * 获取类的泛型类型
	 * @author 范子才
	 * @param clazz 被获取的类
	 * @param index
	 * @return
	 * @version 2016年4月14日 下午7:09:04
	 */
	@SuppressWarnings("unchecked")  
    public static Class<Object> getSuperClassGenricType(final Class<?> clazz, final int index) {  
          
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。  
        Type genType = clazz.getGenericSuperclass();  
  
        if (!(genType instanceof ParameterizedType)) {  
           return Object.class;  
        }  
        //返回表示此类型实际类型参数的 Type 对象的数组。  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
                     return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
              return Object.class;  
        }  
  
        return (Class<Object>) params[index];  
    }
	
	
    /**   
     * 判断字符串为空
     * @author 范子才
     * @param str
     * @return
     * @version 2016年5月11日 下午1:06:22
     */
    public static boolean isEmptyOrNull(String str) {
		return str == null || str.isEmpty();
	}
    
    
    public static float getExcelCellAutoHeight(String str, float fontNum, float defaultRowHeight) {
        float defaultCount = 0.00f;
        for (int i = 0; i < str.length(); i++) {
            float ff = getregex(str.substring(i, i + 1));
            defaultCount = defaultCount + ff;
        }
        return ((int) (defaultCount / fontNum) + 1) * defaultRowHeight;//计算
    }

    public static float getregex(String charStr) {
        
        if(charStr==" ")
        {
            return 0.5f;
        }
        // 判断是否为字母或字符
        if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
            return 0.5f;
        }
        // 判断是否为全角

        if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
            return 1.00f;
        }
        //全角符号 及中文
        if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
            return 1.00f;
        }
        return 0.5f;

    }
	
    
    /**   
     * 生成随机长度字符串
     * @author 范子才
     * @param minLength 大于等于minLenght
     * @param maxLength 小于maxnLenght
     * @return
     * @version 2016年7月23日 下午1:13:35
     */
    public static String getRandomString(int minLength, int maxLength) { //length表示生成字符串的长度  
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();     
        StringBuffer sb = new StringBuffer();
        
        int length;
        if(maxLength < minLength) {
        	throw new RuntimeException("maxLength must be greater than minLength");
        } else if(maxLength == minLength){
        	length = minLength;
        } else {
        	length  = random.nextInt(maxLength-minLength) + minLength;
        }
        
        for (int i = 0; i < length; i++) {     
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));     
        }     
        return sb.toString();     
    }
    
    public static String getRequestUrl(HttpServletRequest request) {
    	String requestUrl = request.getScheme() //当前链接使用的协议
    		    +"://" + request.getServerName()//服务器地址 
    		    + ":" + request.getServerPort() //端口号 
    		    + request.getContextPath(); //应用名称，如果应用名称为
    	return requestUrl;
	}
    
   
    
    
    
}
