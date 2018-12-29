package com.example.demo.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
*  <p>Title：WebAppAESUtil.java</p>
*  <p>Description：Web与APP后台之间的加密解密工具类</p>
*  @author 张泽武
*/
public class WebAppAESUtil {

	/**
	 * 加密
	 * 
	 * @param sSrc 原字符
	 * @param sKey 16位字符的key
	 * @throws Exception
	 * @return String
	 * @exception 异常描述
	 */
	private static String encrypt128(String sSrc, String sKey) throws Exception {
		if (sKey == null)
			return null;
		if (sKey.length() != 16)
			throw new IllegalArgumentException("Key length must be 16.");
		return doEncrypt(sSrc, sKey);
	}

	private static String doEncrypt(String sSrc, String sKey) throws Exception {
		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		cipher.init(1, skeySpec);// 初始化
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));// 加密
		// 字节转换成十六进制的字符串
		return byte2hex(encrypted).toLowerCase();
	}

	/**
	 * 字节转换成十六进制的字符串
	 * 
	 * @param b
	 * @return String
	 * @exception 异常描述
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * 解密
	 * 
	 * @param sSrc 原字符
	 * @param sKey 16位字符的key
	 * @throws Exception
	 * @return String
	 * @exception 异常描述
	 */
	private static String decrypt128(String sSrc, String sKey) throws UnsupportedEncodingException {
		if (sKey == null || sSrc == null)
			return null;
		if (sKey.length() != 16)
			throw new IllegalArgumentException("Key length must be 16.");
		return doDecrypt(sSrc, sKey);
	}

	private static String doDecrypt(String sSrc, String sKey) throws UnsupportedEncodingException {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, skeySpec);
			byte[] encrypted1 = hex2byte(sSrc);

			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, "utf-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 十六进制的字符串转换成字节
	 * 
	 * @param strhex
	 * @return byte[]
	 * @exception 异常描述
	 */
	private static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	/**
	 * 解密入口
	 * 
	 * @param string 解密对象字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(String string)
			throws UnsupportedEncodingException {
		return decrypt128(string, GfAppWebConfig.getValue("app.aes.key"));
	}

	/**
	 * 加密入口
	 * 
	 * @param string 加密对象字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String encrypt(String string) throws Exception {
		return encrypt128(string, GfAppWebConfig.getValue("app.aes.key"));
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException,
			Exception {
		System.out.println(GfAppWebConfig.getValue("app.aes.key"));
		System.out.println("工具类加密“易联通达”密文：" + encrypt("admin"));
		System.out.println("工具类解密从前台获得的密文："+ decrypt("93b6c23fcfcccfc2ecb79793c3117aaf"));
		System.out.println("工具类解密从前台获得的密文："+ decrypt("bcc891813de330defef6e5127e7493d026ae376a15bed06bd3152a0fd65dc54a637eee3360213d0af26e623f01199236f66cfa09b30d67ed07648bd82cc6d585ec656787f8df30192f8bafcbd34a75ecf8f170219453d3235a9c5bcaa269eaa877c65a6c7cd6779d1450da9cbd518190"));
		/*System.out.println("**************工具类解密“工具类加密的密文”***********************");
		System.out.println("工具类解密工具类加密的密文：" + decrypt(encrypt("")));
		System.out.println("工具类解密工具类加密的密文"+ decrypt("342a036f8fc1f75e0f3e6120891baa8c"));
	    System.out.println("1121:" + decrypt("f4d656c25bee72870e3ba2c98d7763a4"));*/
	}

}
