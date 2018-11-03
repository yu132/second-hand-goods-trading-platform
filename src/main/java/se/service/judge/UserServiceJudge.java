package se.service.judge;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.model.UserInfo;
import se.repositories.UserInfoRepository;
import se.stateEnum.State;
import se.reasonEnum.Reason;

@Service
public class UserServiceJudge {
	private final int USERNAME_MIN_LENGTH=6;
	private final int USERNAME_MAX_LENGTH=20;
	private final int PASSWORD_MIN_LENGTH=10;
	private final int PASSWORD_MAX_LENGTH=30;
	private final int NICKNAME_MIN_LENGTH=2;
	private final int NICKNAME_MAX_LENGTH=30;
	private final int EMAIL_MAX_LENGTH=100;
	private final int PHONE_NUMBER_MAX_LENGTH=30;
	private final int ADDERSS_MAX_LENGTH=100;

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public Map<String,Object> judgeUserName(String userName){
		Map<String,Object> result=new HashMap<>();
		UserInfo userInfo=userInfoRepository.findByUserName(userName);
		if("".equals(userName)||userName==null){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.USERNAME_IS_NULL);
		}else if(userInfo!=null){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.USERNAME_EXIST);
		}else if(userName.length()<USERNAME_MIN_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.USERNAME_TOO_SHORT);
		}else if(userName.length()>USERNAME_MAX_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.USERNAME_TOO_LONG);
		}else if(!judgeUserNameChar(userName)){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.USERNAME_ILLEAGAL_CHARACTER);
		}else{
			result.put("State", State.SUCCESS);
		}
		
		return result;
	}
	public Map<String,Object> judgePassword(String password){
		Map<String,Object> result=new HashMap<>();
		if("".equals(password)||password==null){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.PASSWORD_IS_NULL);
		}else if(password.length()<PASSWORD_MIN_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.PASSWORD_TOO_SHORT);
		}else if(password.length()>PASSWORD_MAX_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.PASSWORD_TOO_LONG);
		}else if(!judgePasswordChar(password)){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.PASSWORD_ILLEAGAL_CHARACTER);
		}else{
			result.put("State", State.SUCCESS);
		}
		return result;
	}
	public Map<String,Object> judgeNickName(String nickName){
		Map<String,Object> result=new HashMap<>();
		if("".equals(nickName)||nickName==null){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.NICKNAME_IS_NULL);
		}else if(nickName.length()<NICKNAME_MIN_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.NICKNAME_TOO_SHORT);
		}else if(nickName.length()>NICKNAME_MAX_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.NICKNAME_TOO_LONG);
		}else{
			result.put("State", State.SUCCESS);
		}
		return result;
	}
	public Map<String,Object> judgeEmail(String email){
		Map<String,Object> result=new HashMap<>();
		if("".equals(email)||email==null){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.EMAIL_IS_NULL);
		}else if(!judgeEmailChar(email)){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.EMAIL_ILLEGAL);
		}else if(email.length()>EMAIL_MAX_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.EMAIL_TOO_LONG);
		}else{
			result.put("State", State.SUCCESS);
		}
		return result;
	}
	public Map<String,Object> judgePhoneNumber(String phoneNumber){
		Map<String,Object> result=new HashMap<>();
		if(phoneNumber.length()>PHONE_NUMBER_MAX_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.PHONE_NUMBER_TOO_LONG);
		}else if(!judgePhoneNumberChar(phoneNumber)){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.PHONE_NUMBER_ILLEGAL);
		}else{
			result.put("State", State.SUCCESS);
		}
		return result;
	}
	public Map<String,Object> judgeAddress(String address){
		Map<String,Object> result=new HashMap<>();
		if(address.length()>ADDERSS_MAX_LENGTH){
			result.put("State", State.ERROR);
			result.put("Reason", Reason.ADDERSS_TOO_LONG);
		}else{
			result.put("State", State.SUCCESS);
		}
		return result;
	}
	private boolean judgeUserNameChar(String userName){
		String pattern = "(?:[a-z]|[A-Z]|_|[0-9])+";
		boolean isMatch = Pattern.matches(pattern, userName);
	    return isMatch;
	}
	private boolean judgePasswordChar(String password){//!@#$%^&*(),<.>:;?~`-'+|"/=[]{}和数字、大小写字母、下划线、空格
		String pattern = "(?:[a-z]|[A-Z]|_|[0-9]|!|@|#|\\$|%|\\^|&|\\*|\\(|\\)| |,|<|.|>|:|;|\\?|~|`|-|'|\\+|\\||\"|/|=|[|]|\\{|\\})+";
		boolean isMatch = Pattern.matches(pattern, password);
	    return isMatch;
	}
	private boolean judgeEmailChar(String Email){
		String pattern = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
		boolean isMatch = Pattern.matches(pattern, Email);
	    return isMatch;
	}
	private boolean judgePhoneNumberChar(String phoneNumber){
		String pattern = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[06-8])\\d{8}$";
		boolean isMatch = Pattern.matches(pattern, phoneNumber);
	    return isMatch;
	}
}
