/* 二进制数据解码类
 * CopyRight Easttalent Business Software co.,Ltd , All rights Reserved
 * FileName: Base64Decoder.java
 * @author   Tigerwang
 * @version  v1.0.0
 * $Date: 2002/09/02 05:34:32 $
*/
package com.adtec.toucs.monitor.security;

import com.adtec.toucs.monitor.common.*;

/* 二进制数据解码类，用于将编码过的可视字符串解码成为二进制数据 */
public class Base64Decoder {
	//初始化对照表
	static byte[] ByteTable = new byte[256];
	static {
		// Initialize the table
		for (int i = 0; i < 256; i++) ByteTable[i] = -1;
		// Initialize positions from 'A' to 'Z'
		for (int i = (int)'A'; i <= (int)'Z'; i++) ByteTable[i] = (byte)(     i - (int)'A');
		// Initialize positions from 'a' to 'z'
		for (int i = (int)'a'; i <= (int)'z'; i++) ByteTable[i] = (byte)(26 + i - (int)'a');
		// Initialize positions from '0' to '9'
		for (int i = (int)'0'; i <= (int)'9'; i++) ByteTable[i] = (byte)(52 + i - (int)'0');
		// Initialize misc. positions.
		ByteTable[(int)'+'] = (byte)62;
		ByteTable[(int)'/'] = (byte)63;
    	ByteTable[(int)'='] = (byte)64;
	}

	/* 从对照表中取出二进制数值 */
	private static int getByte(byte in) {
		return (int)ByteTable[in];
	}

	/* 解码四个字符组成的编码数据 */
	private static byte [] decode(byte A, byte B, byte C, byte D) {
		byte[] output = new byte[3];

		output[0] = (byte)((getByte(A) << 2) | ((getByte(B) & 0x30) >> 4));
		output[1] = (byte)(((getByte(B) & 0x0F) << 4) | ((getByte(C) & 0x3C) >> 2));
		output[2] = (byte)(((getByte(C) & 0x03) << 6) | getByte(D));
		return output;
	}

	/* 解码三个字符组成的编码数据 */
	private static byte [] decode(byte A, byte B, byte C) {
		byte[] output = new byte[2];

		output[0] = (byte)((getByte(A) << 2) | ((getByte(B) & 0x30) >> 4));
		output[1] = (byte)(((getByte(B) & 0x0F) << 4) | ((getByte(C)  & 0x3C) >> 2));
    	return output;
	}

	/* 解码两个字符组成的编码数据 */
	private static byte [] decode(byte A, byte B) {
		byte[] output = new byte[1];

		output[0] = (byte)((getByte(A) << 2) | ((getByte(B) & 0x30) >> 4));
		return output;
	}

	/* 解码一个字符组成的编码数据 */
	private static byte [] decode(byte A) {
		byte[] output = new byte[1];

		output[0] = (byte)(getByte(A) << 2);
		return output;
	}	

	/* 将一个字符串解码成二进制数组 */
	public static byte [] decode(String encString) {
		int len, i, j, writeCount;
		byte [] decByte = new byte[0];
		byte [] buf = new byte[4];
		byte [] ret = new byte[0];
		int c;
    
		if ((encString == null) || (encString.length() == 0)) {
			return decByte;
		}
		len = encString.length();
		writeCount = 0;
		j = 0;
		for (i = 0; i < len; i++) {
			c = (int)encString.charAt(i);
			if (c != '=') {
				buf[writeCount] = (byte)c;
				writeCount++;
			}	
			j++;
			if ((j == 4) || (i == len - 1)) {     //每四个字符解码一次
				switch(writeCount) {
				case 1:
					ret = decode(buf[0]);
					break;
				case 2:
					ret = decode(buf[0], buf[1]);
					break;
				case 3:
					ret = decode(buf[0], buf[1], buf[2]);
					break;
				case 4:
					ret = decode(buf[0], buf[1], buf[2], buf[3]);
					break;
				}
				byte [] origin = new byte[decByte.length];
				System.arraycopy(decByte, 0, origin, 0, decByte.length);
				decByte = new byte[origin.length + ret.length];
				System.arraycopy(origin, 0, decByte, 0, origin.length);
				System.arraycopy(ret, 0, decByte, origin.length, ret.length);
        
				j = 0;
				writeCount = 0;
			}
		}  //end for
		return decByte;
	}


	public static void main(String[] args) {
		byte testByte[] = { 0x12, 0x7f, 0x0f, 0x0f, 0x78};
		String testString = Base64Encoder.encode(testByte);
		Debug.println("encode ret: " + testString);
		byte [] dec = decode(testString);
		Debug.println("decode byte array length:" + dec.length);
    	Debug.println("decode ret: " + dec.toString());
	}
}
