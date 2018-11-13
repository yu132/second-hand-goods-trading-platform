package se.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;
@Service
public class DateUtil {

	/**
	 * 得到当前Date
	 * @return
	 */
	public Date getCurrentDate(){
		Date d=new Date(System.currentTimeMillis());
		return d; 
	}
	/**
	 * 
	 * @param d
	 * @return
	 * 格式化为日期/时间字符串
	 */
	public String getFormatDate(Date d){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cc=sdf.format(d);
        return cc;
	}
}
