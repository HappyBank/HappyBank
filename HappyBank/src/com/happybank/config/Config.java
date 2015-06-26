package com.happybank.config;

public class Config {
	// 每天每次登录时更新幸福币
	public static final float DEPOSIT_LIST_TO_HAPPYBI_LESS_THAN_10_RATE = (float) 0.1;
	public static final float DEPOSIT_LIST_TO_HAPPYBI_10_TO_30_RATE = (float) 0.05;
	public static final float DEPOSIT_LIST_TO_HAPPYBI_30_TO_100_RATE = (float) 0.02;
	public static final float DEPOSIT_LIST_TO_HAPPYBI_MORE_THAN_100_RATE = (float) 0.01;
	// 删除银行时
	public static final float MOVE_DEPOSIT_FROM_BANK_TO_HAPPYBI = (float) 1;
	// 取款时,与删除银行保持一致
	public static final float WITHDRAW = MOVE_DEPOSIT_FROM_BANK_TO_HAPPYBI;
	// 查看不愉快存款时
	public static final int VIEW_BAD_MOOD_DEPOSIT = 50;
	// 存款产生利息的利率
	public static final float DEPOSIT_GENERATE_INTEREST = (float) 0.1;
	// 存款时获得的本金
	public static final float DEPOSIT_BEGIN = (float) 1;
	// 存款的额外本金
	public static final float DEPOSIT_CONTENT_MORE_THAN_100_WORDS = (float) 0.2;
	public static final float DEPOSIT_WITH_PICTURE = (float) 0.5;
	public static final float DEPOSIT_WITH_VIDEO = (float) 0.8;
	// 存款为愉快心情时直接带来幸福币
	public static final float DEPOSIT_GENERATE_HAPPYBI = (float) 0.5;
	// 删除不愉快心情时直接带来幸福币
	public static final float DELETE_BAD_MOOD_DEPOSIT_TO_GENERATE_HAPPYBI = (float) 0.5;
	//软件版本
	public static final double LOCAL_VERSION = 1.0;
	//软件更新信息地址
	public static final String APP_VERSION_XML = "http://59.65.171.175:8080/Android/version.xml";
	//public static final String APP_VERSION_XML = "http://172.28.19.127:8080/Android/version.xml";
}
