/* RC6算法加密解密类
 * CopyRight Easttalent Business Software co.,Ltd , All rights Reserved
 * FileName: RC6.java
 * @author   Tigerwang
 * @version  v1.0.0
 * $Date:    2001-07-16 $
*/
package com.adtec.toucs.monitor.security;

import com.adtec.toucs.monitor.common.*;
/* RC6算法加密解密类，用于加密和解密一个字符串 */
public class RC6 {
	private static final int ROUNDS     = 20 , BLOCK_SIZE = 16;
	private static final int P = 0xB7E15163 , Q = 0x9E3779B9;
	private static int[] S = new int[2*ROUNDS + 4];       //子密钥

	static {            //子密钥生成
		byte [] key = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
					   0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		int len = key.length;
		int c   = len/4;
		int[] L = new int[c];
		for(int off=0, i=0; i<c; i++)
			L[i] = ((key[off++]&0xFF)      ) |
				   ((key[off++]&0xFF) <<  8) |
                   ((key[off++]&0xFF) << 16) |
                   ((key[off++]&0xFF) << 24);
		S[0] = P;
		for(int i=1; i<=(2*ROUNDS+3); i++)
			S[i] = S[i-1] + Q;
		int A=0, B=0, i=0, j=0, v=3*(2*ROUNDS+4);
		for(int s=1; s<=v; s++){
			A = S[i] = rotl( S[i]+A+B, 3 );
			B = L[j] = rotl( L[j]+A+B, A+B );
			i = (i+1) % (2*ROUNDS+4);
			j = (j+1) % c;
		}
	}   //子密钥生成结束

	/* 整数值向左旋转 */
	private static int rotl(int val, int amount){
		return (val << amount) | (val >>> (32-amount));
	}

	/* 整数值向右旋转 */
	private static int rotr(int val, int amount){
		return (val >>> amount) | (val << (32-amount));
	}

	/* 加密字符串数据，生成密文字符串 */
	public static String encrypt(String plainText) {
		byte [] plainByte, encryptByte, decryptByte;
		String  encryptString = "";
		int len;
		if ((plainText == null) || (plainText.length() == 0)) {
			return "";
		}

		plainByte = plainText.getBytes();
		len = plainByte.length;
		if ((len%16) > 0) {   //有余数
			len = (((int)len/16) + 1)*16;
		}
		decryptByte = new byte[len];
		for (int i = 0; i < len; i++) {
			decryptByte[i] = 0x00;
		}
		System.arraycopy(plainByte, 0, decryptByte, 0, plainByte.length);  //末尾补0

		//每16字节加密一次
		encryptByte = new byte[BLOCK_SIZE];
		for (int i = 0; i < len; i += BLOCK_SIZE) {
			coreCrypt(false, decryptByte, i, encryptByte, 0);
			encryptString += Base64Encoder.encode(encryptByte);
		}
		return encryptString;
	}

	/* 解密密文字符串，生成明文 */
	public static String decrypt(String cryptText) {
		byte [] cryptByte, decryptBlock;
		String  decryptString = "";
		int len, blanklen;

		if ((cryptText == null) || (cryptText.length() == 0)) {
			return "";
		}
		cryptByte = Base64Decoder.decode(cryptText);
		len = cryptByte.length;
		if ((len == 0) || (len%BLOCK_SIZE > 0)) {  //位数不是BLOCK_SIZE的整数倍
			return "";
		}
		//每16字节解密一次
		decryptBlock = new byte[BLOCK_SIZE];
		for (int i = 0; i < len; i += BLOCK_SIZE) {
			coreCrypt(true, cryptByte, i, decryptBlock, 0);
			for (blanklen = 0; blanklen < BLOCK_SIZE; blanklen++) {   //过滤掉末尾的0
				if (decryptBlock[BLOCK_SIZE - blanklen - 1] != 0x00) {
					break;
				}
			}
			decryptString += new String(decryptBlock, 0, BLOCK_SIZE - blanklen);
		}
		return decryptString;
	}


	private static void coreCrypt(boolean decrypt, byte[] in, int inOffset,byte[] out, int outOffset){
		int A = ((in[inOffset++] & 0xFF)      ) |
				((in[inOffset++] & 0xFF) <<  8) |
				((in[inOffset++] & 0xFF) << 16) |
				((in[inOffset++]       ) << 24);
		int B = ((in[inOffset++] & 0xFF)      ) |
				((in[inOffset++] & 0xFF) <<  8) |
				((in[inOffset++] & 0xFF) << 16) |
				((in[inOffset++]       ) << 24);
		int C = ((in[inOffset++] & 0xFF)      ) |
				((in[inOffset++] & 0xFF) <<  8) |
				((in[inOffset++] & 0xFF) << 16) |
				((in[inOffset++]       ) << 24);
		int D = ((in[inOffset++] & 0xFF)      ) |
				((in[inOffset++] & 0xFF) <<  8) |
				((in[inOffset++] & 0xFF) << 16) |
				((in[inOffset  ]       ) << 24);
		int t, u;
		if(decrypt){
			C -= S[2*ROUNDS+3];
			A -= S[2*ROUNDS+2];
			for(int i=2*ROUNDS+2; i>2; ){
				t = D; D = C; C = B; B = A; A = t;
				u = rotl( D*(2*D+1), 5 );
				t = rotl( B*(2*B+1), 5 );
				C = rotr( C-S[--i], t ) ^ u;
				A = rotr( A-S[--i], u ) ^ t;
			}
			D -= S[1];
			B -= S[0];
		} else {
			B += S[0];
			D += S[1];
			for(int i=1; i<=2*ROUNDS; ){
				t = rotl( B*(2*B+1), 5 );
				u = rotl( D*(2*D+1), 5 );
				A = rotl( (A^t), u ) + S[++i];
				C = rotl( (C^u), t ) + S[++i];
				t = A; A=B; B=C; C=D; D=t;
			}
			A += S[2*ROUNDS+2];
			C += S[2*ROUNDS+3];
		}
		out[outOffset++] = (byte)(A       );
		out[outOffset++] = (byte)(A >>>  8);
		out[outOffset++] = (byte)(A >>> 16);
		out[outOffset++] = (byte)(A >>> 24);
		out[outOffset++] = (byte)(B       );
		out[outOffset++] = (byte)(B >>>  8);
		out[outOffset++] = (byte)(B >>> 16);
		out[outOffset++] = (byte)(B >>> 24);

		out[outOffset++] = (byte)(C       );
		out[outOffset++] = (byte)(C >>>  8);
		out[outOffset++] = (byte)(C >>> 16);
		out[outOffset++] = (byte)(C >>> 24);
		out[outOffset++] = (byte)(D       );
		out[outOffset++] = (byte)(D >>>  8);
		out[outOffset++] = (byte)(D >>> 16);
		out[outOffset  ] = (byte)(D >>> 24);
	}


	public static void main(String[] args) {
		double dt=23.288888899999999999;
		Debug.println(formatDoubleToString(dt));
	}
	private static String formatDoubleToString(double dou) {
		java.text.NumberFormat form;
		form = new java.text.DecimalFormat("#,##0.00");
		return form.format(dou);
	}
}

