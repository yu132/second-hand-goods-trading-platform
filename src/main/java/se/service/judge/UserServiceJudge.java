package se.service.judge;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJudge {
	private final int USERNAME_MIN_LENGTH=6;
	private final int USERNAME_MAX_LENGTH=20;
	private final int PASSWORD_MIN_LENGTH=10;
	private final int PASSWORD_MAX_LENGTH=30;

	public Map<String,Object> judgeUserName(String userName){
		Map<String,Object> result=new HashMap<>();
		if("".equals(userName)){
			result.put("State", "ERROR");
			result.put("Reason", "USERNAME_NOT_EXIST");
		}else if(userName.length()<USERNAME_MIN_LENGTH){
			result.put("State", "ERROR");
			result.put("Reason", "USERNAME_TOO_SHORT");
		}else if(userName.length()>USERNAME_MAX_LENGTH){
			result.put("State", "ERROR");
			result.put("Reason", "USERNAME_TOO_LONG");
		}else if(!judgeUserNameChar(userName)){
			result.put("State", "ERROR");
			result.put("Reason", "USERNAME_ILLEAGAL_CHARACTER");
		}else{
			result.put("State", "SUCCESS");
		}
		
		return result;
	}
	public Map<String,Object> judgePassword(String password){
		Map<String,Object> result=new HashMap<>();
		if("".equals(password)){
			result.put("State", "ERROR");
			result.put("Reason", "PASSWORD_NOT_EXIST");
		}else if(password.length()<PASSWORD_MIN_LENGTH){
			result.put("State", "ERROR");
			result.put("Reason", "PASSWORD_TOO_SHORT");
		}else if(password.length()>PASSWORD_MAX_LENGTH){
			result.put("State", "ERROR");
			result.put("Reason", "PASSWORD_TOO_LONG");
		}else if(!judgePasswordChar(password)){
			result.put("State", "ERROR");
			result.put("Reason", "PASSWORD_ILLEAGAL_CHARACTER");
		}else{
			result.put("State", "SUCCESS");
		}
		return result;
	}
	public Map<String,Object> judgeNickName(String nickName){
		Map<String,Object> result=new HashMap<>();
		if("".equals(nickName)){
			result.put("State", "ERROR");
			result.put("Reason", "NICKNAME_NOT_EXIST");
		}else if(nickName.length()<2){
			result.put("State", "ERROR");
			result.put("Reason", "NICKNAME_TOO_SHORT");
		}else if(nickName.length()>30){
			result.put("State", "ERROR");
			result.put("Reason", "NICKNAME_TOO_LONG");
		}else{
			result.put("State", "SUCCESS");
		}
		return result;
	}
	public Map<String,Object> judgeEmail(String email){
		Map<String,Object> result=new HashMap<>();
		if(!judgeEmailChar(email)){
			result.put("State", "ERROR");
			result.put("Reason", "EMAIL_ILLEGAL");
		}else if(email.length()>100){
			result.put("State", "ERROR");
			result.put("Reason", "EMAIL_TOO_LONG");
		}else{
			result.put("State", "SUCCESS");
		}
		return result;
	}
	public Map<String,Object> judgePhoneNumber(String phoneNumber){
		Map<String,Object> result=new HashMap<>();
		if(phoneNumber.length()>30){
			result.put("State", "ERROR");
			result.put("Reason", "PHONE_NUMBER_TOO_LONG");
		}else if(!judgePhoneNumberChar(phoneNumber)){
			result.put("State", "ERROR");
			result.put("Reason", "PHONE_NUMBER_ILLEGAL");
		}else{
			result.put("State", "SUCCESS");
		}
		return result;
	}
	public Map<String,Object> judgeAddress(String address){
		Map<String,Object> result=new HashMap<>();
		if(address.length()>100){
			result.put("State", "ERROR");
			result.put("Reason", "ADDERSS_TOO_LONG");
		}else{
			result.put("State", "SUCCESS");
		}
		return result;
	}
	private boolean judgeUserNameChar(String userName){
		String pattern = "(?:[a-z]|[A-Z]|_|[0-9])+";
		boolean isMatch = Pattern.matches(pattern, userName);
	    return isMatch;
	}
	private boolean judgePasswordChar(String password){
		String pattern = "(?:[a-z]|[A-Z]|_|[0-9]|!|@|#|\\$|%|\\^|&|\\*|\\(|\\)|\\+| |~|)+";
		boolean isMatch = Pattern.matches(pattern, password);
	    return isMatch;
	}
	private boolean judgeEmailChar(String Email){
		String pattern = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
		boolean isMatch = Pattern.matches(pattern, Email);
	    return isMatch;
	}
	private boolean judgePhoneNumberChar(String phoneNumber){
		String pattern = "(?:[0-9])+";
		boolean isMatch = Pattern.matches(pattern, phoneNumber);
	    return isMatch;
	}
}
