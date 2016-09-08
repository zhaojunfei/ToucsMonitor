/**
 * Title:        SysManage
 * Description:  The System Manage Project.
 * Copyright:    Copyright (c) 2001
 * Company:      Adtec
 * @author:      �ƾ�
 * @Date         2001-10-23
 * @version 1.0
 */

package com.adtec.toucs.monitor.security;

import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.io.*;
import java.sql.*;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.common.*;

/**
 * ��ȫ���� DES�����㷨
**/
public class DESCryptography {

	private static String strDesKey="qqqqMhycZoDnZYFtZ9QudEP3qNyyMZCa";
	//���� �����㷨,���� DES,DESede,Blowfish
	private static String Algorithm = "DES";
	private static SecretKey deskey = null;

	public DESCryptography() {
		//����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Algorithm="DES";
		initDBDESKey();
	}

  /**
   * ���캯��
   * @param keyType  ȡ����Կ��ʽ
   * =0�������ݿ���ȡ����Կ
   * =1��ͨѶ��Կ���̶���Կ
   */
	public DESCryptography(int keyType) {
		//����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
		//Security.addProvider(new com.sun.crypto.provider.SunJCE());
		if(keyType==0){
			Algorithm="DES";
			initDBDESKey();
		}else{
			Algorithm="DESede";
			initDESKey();
		}
	}

	/* �����÷���*/
	public static void main(String[] args){
		DESCryptography DES = new DESCryptography(1);
		String tmp = "11222222222221";//52000000210�й�������0.100.100.7100-10-5A-76-61-26";
		Debug.println("����ǰ������String  "+tmp);
		String en = DES.encrypt(tmp);
		Debug.println("���ܺ������String  "+en);
		String de = DES.decrypt(en);
		Debug.println("���ܺ������  "+de);
		Debug.println("substring������  "+de.substring(0,5));
	}

	//������ת�ַ���
	public String byte2hex(byte[] b) {
		String hs="";
		String stmp="";
		for (int n=0;n<b.length;n++){
			stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length()==1) hs=hs+"0"+stmp;
			else hs=hs+stmp;
			if (n<b.length-1)  hs=hs+":";
		}
		return hs.toUpperCase();
	}



	//��ʼȡ����Կ
	private void initDESKey() {
		//��������Կ
		try{
			long t=System.currentTimeMillis();
			Debug.println(System.currentTimeMillis());
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			DESedeKeySpec spec=new DESedeKeySpec(strDesKey.getBytes());
			SecretKeyFactory keyFactory= SecretKeyFactory.getInstance("DESede");
			deskey = keyFactory.generateSecret(spec);
			long t2=System.currentTimeMillis();
			Debug.println(t2);
			long t3=t2-t;
			Debug.println("�ܹ�����ʱ�䣺"+t3);
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}

	//��ʼȡ����Կ
	private void initTestDESKey() {
		//��������Կ
		try{
			strDesKey="iJ1iMhycZoDnZYFtZ9QudEP3qNyyMZCa";
			strDesKey="qqqqMhycZoDnZYFtZ9QudEP3qNyyMZCa";
			byte[] entcry=Base64Decoder.decode(strDesKey);
			DESedeKeySpec spec=new DESedeKeySpec(strDesKey.getBytes());
			SecretKeyFactory keyFactory= SecretKeyFactory.getInstance("DESede");
			deskey = keyFactory.generateSecret(spec);
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}


	//�����ݿ��г�ʼ�����Կ
	public boolean initDBDESKey() {
		try {
			SqlAccess conn = SqlAccess.createConn();
			String sql = "select * from monit_key where c_flag = '1'";
			ResultSet rs = conn.queryselect(sql);
			String dbkey = "";
			if (rs.next()) {
				dbkey = rs.getString("c_key");
				dbkey = dbkey.trim();
			}
			conn.close();
			conn = null;
			if ((dbkey != null)&&(dbkey.length() > 0)) {
				byte[] key = Base64Decoder.decode(dbkey);
				ByteArrayInputStream buffer = new ByteArrayInputStream(key);
				ObjectInputStream in = new ObjectInputStream(buffer);
				deskey = (SecretKey)in.readObject();
				in.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//��������
	public String encrypt(String aCleanMess) {
        if ((aCleanMess == null)||(aCleanMess.length() == 0)||(deskey == null)){
            return null;
        }
        try {
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE,deskey);
            byte[] cipherByte = c1.doFinal(aCleanMess.getBytes());
            return Base64Encoder.encode(cipherByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	//��������
	public String decrypt(String aDESMess) {
		if ((aDESMess == null)||(deskey == null))
			return null;
		byte[] aMess =Base64Decoder.decode(aDESMess);
		try {
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE,deskey);
			byte[] cipherByte = c1.doFinal(aMess);
			return (new String(cipherByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String digest(String myinfo) {
		String mdmess = "";
		try {
			java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
			alg.update(myinfo.getBytes());
			byte[] digesta=alg.digest();
			mdmess = Base64Encoder.encode(digesta);
			mdmess = mdmess.substring(0,16);
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return mdmess;
	}

}