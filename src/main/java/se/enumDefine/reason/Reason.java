package se.enumDefine.reason;

public enum Reason {
	/**
	 * 注册登录失败原因
	 */
	USERNAME_IS_NULL,
	USERNAME_NOT_EXIST,
	USERNAME_EXIST,
	USERNAME_TOO_SHORT,
	USERNAME_TOO_LONG,
	USERNAME_ILLEAGAL_CHARACTER,
	PASSWORD_IS_NULL,
	PASSWORD_INCORRECT,
	PASSWORD_TOO_SHORT,
	PASSWORD_TOO_LONG,
	PASSWORD_ILLEAGAL_CHARACTER,
	NICKNAME_IS_NULL,
	NICKNAME_TOO_SHORT,
	NICKNAME_TOO_LONG,
	EMAIL_IS_NULL,
	EMAIL_ILLEGAL,
	EMAIL_TOO_LONG,
	PHONE_NUMBER_TOO_LONG,
	PHONE_NUMBER_ILLEGAL,
	ADDERSS_TOO_LONG,
	
	USER_INFO_IS_NULL,
	
	
	
	/**
	 * 货品模块失败原因
	 */
	GOODS_EXIST,
	USER_ID_IS_NULL,
	GOODS_IS_NULL,
	GOODS_NAME_TOO_LONG,
	GOODS_NAME_IS_NULL,
	PRICE_IS_NULL,
	PRICE_IS_NEGATIVE_OR_ZERO,
	AMOUNT_IS_NULL,
	EMAIL_REMIND_IS_NULL,
	AMOUNT_IS_NEGATIVE_OR_ZERO,
	DESCRIPTION_TOO_LONG,
	
	GOODS_PAGE_OUT_OF_BOUNDS,
	GOODS_PAGE_IS_NEGATIVE_OR_ZERO,
	
	USER_AUTH_ILLEGAL,
	GOODS_ID_IS_NULL,
	GOODS_NOT_EXIST,
	
	
	
	GOODS_PAGE_IS_NULL,
	AMOUNT_EXCESSIVE,
	
	ILLEGAL_OPERATION_TO_OWN_GOODS,
}
