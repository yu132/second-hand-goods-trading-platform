package se.service.judge;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.reason.Reason;
import se.model.UserInfo;
import se.repositories.UserInfoRepository;

@Service
public class UserServiceJudge {
	/**
	 * 用户名最小长度
	 */
	private final int USERNAME_MIN_LENGTH=6;
	/**
	 * 用户名最大长度
	 */
	private final int USERNAME_MAX_LENGTH=20;
	/**
	 * 密码最小长度
	 */
	private final int PASSWORD_MIN_LENGTH=10;
	/**
	 * 密码最大长度
	 */
	private final int PASSWORD_MAX_LENGTH=30;
	/**
	 * 昵称最短长度
	 */
	private final int NICKNAME_MIN_LENGTH=2;
	/**
	 * 昵称最大长度
	 */
	private final int NICKNAME_MAX_LENGTH=30;
	/**
	 * 邮件最大长度
	 */
	private final int EMAIL_MAX_LENGTH=100;
	/**
	 * 电话号最大长度
	 */
	private final int PHONE_NUMBER_MAX_LENGTH=30;
	/**
	 * 地址最大长度
	 */
	private final int ADDERSS_MAX_LENGTH=100;

	@Autowired
	private UserInfoRepository userInfoRepository;
	/**
	 * 判断用户名是否合法
	 * @param userName
	 * @return
	 */
	public Map<String,Object> judgeUserName(String userName){
		Map<String,Object> result=new HashMap<>();
		UserInfo userInfo=userInfoRepository.findByUserName(userName);
		if("".equals(userName)||userName==null){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USERNAME_IS_NULL);
		}else if(userInfo!=null){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USERNAME_EXIST);
		}else if(userName.length()<USERNAME_MIN_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USERNAME_TOO_SHORT);
		}else if(userName.length()>USERNAME_MAX_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USERNAME_TOO_LONG);
		}else if(!judgeUserNameChar(userName)){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USERNAME_ILLEAGAL_CHARACTER);
		}else{
			result.put("State", ExecuteState.SUCCESS);
		}
		
		return result;
	}
	/**
	 * 判断密码是否合法
	 * @param password
	 * @return
	 */
	public Map<String,Object> judgePassword(String password){
		Map<String,Object> result=new HashMap<>();
		if("".equals(password)||password==null){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PASSWORD_IS_NULL);
		}else if(password.length()<PASSWORD_MIN_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PASSWORD_TOO_SHORT);
		}else if(password.length()>PASSWORD_MAX_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PASSWORD_TOO_LONG);
		}else if(!judgePasswordChar(password)){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PASSWORD_ILLEAGAL_CHARACTER);
		}else{
			result.put("State", ExecuteState.SUCCESS);
		}
		return result;
	}
	/**
	 * 判断昵称是否合法
	 * @param nickName
	 * @return
	 */
	public Map<String,Object> judgeNickName(String nickName){
		Map<String,Object> result=new HashMap<>();
		if("".equals(nickName)||nickName==null){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.NICKNAME_IS_NULL);
		}else if(nickName.length()<NICKNAME_MIN_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.NICKNAME_TOO_SHORT);
		}else if(nickName.length()>NICKNAME_MAX_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.NICKNAME_TOO_LONG);
		}else{
			result.put("State", ExecuteState.SUCCESS);
		}
		return result;
	}
	/**
	 * 判断邮件是否合法
	 * @param email
	 * @return
	 */
	public Map<String,Object> judgeEmail(String email){
		Map<String,Object> result=new HashMap<>();
		if("".equals(email)||email==null){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.EMAIL_IS_NULL);
		}else if(!judgeEmailChar(email)){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.EMAIL_ILLEGAL);
		}else if(email.length()>EMAIL_MAX_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.EMAIL_TOO_LONG);
		}else{
			result.put("State", ExecuteState.SUCCESS);
		}
		return result;
	}
	/**
	 * 判断电话号是否合法
	 * @param phoneNumber
	 * @return
	 */
	public Map<String,Object> judgePhoneNumber(String phoneNumber){
		Map<String,Object> result=new HashMap<>();
		if(phoneNumber.length()>PHONE_NUMBER_MAX_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PHONE_NUMBER_TOO_LONG);
		}else if(!judgePhoneNumberChar(phoneNumber)){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PHONE_NUMBER_ILLEGAL);
		}else{
			result.put("State", ExecuteState.SUCCESS);
		}
		return result;
	}
	/**
	 * 判断地址是否合法
	 * @param address
	 * @return
	 */
	public Map<String,Object> judgeAddress(String address){
		Map<String,Object> result=new HashMap<>();
		if(address.length()>ADDERSS_MAX_LENGTH){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.ADDERSS_TOO_LONG);
		}else{
			result.put("State", ExecuteState.SUCCESS);
		}
		return result;
	}
	/**
	 * 判断用户名字符
	 * @param userName
	 * @return
	 */
	private boolean judgeUserNameChar(String userName){
		String pattern = "(?:[a-z]|[A-Z]|_|[0-9])+";
		boolean isMatch = Pattern.matches(pattern, userName);
	    return isMatch;
	}
	/**
	 * 判断密码字符
	 * @param password
	 * @return
	 */
	private boolean judgePasswordChar(String password){//!@#$%^&*(),<.>:;?~`-'+|"/=[]{}和数字、大小写字母、下划线、空格
		String pattern = "(?:[a-z]|[A-Z]|_|[0-9]|!|@|#|\\$|%|\\^|&|\\*|\\(|\\)| |,|<|\\.|>|:|;|\\?|~|`|-|'|\\+|\\||\"|/|=|[|]|\\{|\\})+";
		boolean isMatch = Pattern.matches(pattern, password);
	    return isMatch;
	}
	/**
	 * 判断邮件字符
	 * @param Email
	 * @return
	 */
	private boolean judgeEmailChar(String Email){
		String pattern = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
		boolean isMatch = Pattern.matches(pattern, Email);
	    return isMatch;
	}
	/**
	 * 判断电话号字符
	 * @param phoneNumber
	 * @return
	 */
	private boolean judgePhoneNumberChar(String phoneNumber){
		String pattern = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[06-8])\\d{8}$";
		boolean isMatch = Pattern.matches(pattern, phoneNumber);
	    return isMatch;
	}
}
