package com.happybank.config;

public class Config {
	// ÿ��ÿ�ε�¼ʱ�����Ҹ���
	public static final float DEPOSIT_LIST_TO_HAPPYBI_LESS_THAN_10_RATE = (float) 0.1;
	public static final float DEPOSIT_LIST_TO_HAPPYBI_10_TO_30_RATE = (float) 0.05;
	public static final float DEPOSIT_LIST_TO_HAPPYBI_30_TO_100_RATE = (float) 0.02;
	public static final float DEPOSIT_LIST_TO_HAPPYBI_MORE_THAN_100_RATE = (float) 0.01;
	// ɾ������ʱ
	public static final float MOVE_DEPOSIT_FROM_BANK_TO_HAPPYBI = (float) 1;
	// ȡ��ʱ,��ɾ�����б���һ��
	public static final float WITHDRAW = MOVE_DEPOSIT_FROM_BANK_TO_HAPPYBI;
	// �鿴�������ʱ
	public static final int VIEW_BAD_MOOD_DEPOSIT = 50;
	// ��������Ϣ������
	public static final float DEPOSIT_GENERATE_INTEREST = (float) 0.1;
	// ���ʱ��õı���
	public static final float DEPOSIT_BEGIN = (float) 1;
	// ���Ķ��Ȿ��
	public static final float DEPOSIT_CONTENT_MORE_THAN_100_WORDS = (float) 0.2;
	public static final float DEPOSIT_WITH_PICTURE = (float) 0.5;
	public static final float DEPOSIT_WITH_VIDEO = (float) 0.8;
	// ���Ϊ�������ʱֱ�Ӵ����Ҹ���
	public static final float DEPOSIT_GENERATE_HAPPYBI = (float) 0.5;
	// ɾ�����������ʱֱ�Ӵ����Ҹ���
	public static final float DELETE_BAD_MOOD_DEPOSIT_TO_GENERATE_HAPPYBI = (float) 0.5;
	//����汾
	public static final double LOCAL_VERSION = 1.0;
	//���������Ϣ��ַ
	public static final String APP_VERSION_XML = "http://59.65.171.175:8080/Android/version.xml";
	//public static final String APP_VERSION_XML = "http://172.28.19.127:8080/Android/version.xml";
}
