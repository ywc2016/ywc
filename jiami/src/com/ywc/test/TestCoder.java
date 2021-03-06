package com.ywc.test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import com.ywc.base64.Coder;
import com.ywc.des.DESCoder;
import com.ywc.pbe.PBECoder;

public class TestCoder {
	@Test
	public void testCoder() throws Exception {
		String inputStr = "简单加密";
		System.err.println("原文:\n" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code = Coder.encryptBASE64(inputData);

		System.err.println("BASE64加密后:\n" + code);

		byte[] output = Coder.decryptBASE64(code);

		String outputStr = new String(output);

		System.err.println("BASE64解密后:\n" + outputStr);

		// 验证BASE64加密解密一致性
		assertEquals(inputStr, outputStr);

		// 验证MD5对于同一内容加密是否一致
		assertArrayEquals(Coder.encryptMD5(inputData), Coder.encryptMD5(inputData));

		// 验证SHA对于同一内容加密是否一致
		assertArrayEquals(Coder.encryptSHA(inputData), Coder.encryptSHA(inputData));

		String key = Coder.initMacKey();
		System.err.println("Mac密钥:\n" + key);

		// 验证HMAC对于同一内容，同一密钥加密是否一致
		assertArrayEquals(Coder.encryptHMAC(inputData, key), Coder.encryptHMAC(inputData, key));

		BigInteger md5 = new BigInteger(Coder.encryptMD5(inputData));
		System.err.println("MD5:\n" + md5.toString(16));

		BigInteger sha = new BigInteger(Coder.encryptSHA(inputData));
		System.err.println("SHA:\n" + sha.toString(32));

		BigInteger mac = new BigInteger(Coder.encryptHMAC(inputData, key));
		System.err.println("HMAC:\n" + mac.toString(16));
	}

	@Test
	public void testDESCoder() throws Exception {
		String inputStr = "DES";
		String key = DESCoder.initKey();
		System.err.println("原文:\t" + inputStr);

		System.err.println("密钥:\t" + key);

		byte[] inputData = inputStr.getBytes();
		inputData = DESCoder.encrypt(inputData, key);

		System.err.println("加密后:\t" + DESCoder.encryptBASE64(inputData));

		byte[] outputData = DESCoder.decrypt(inputData, key);
		String outputStr = new String(outputData);

		System.err.println("解密后:\t" + outputStr);

		assertEquals(inputStr, outputStr);
	}

	@Test
	public void testPBECoder() throws Exception {
		String inputStr = "abc";
		System.err.println("原文: " + inputStr);
		byte[] input = inputStr.getBytes();

		String pwd = "efg";
		System.err.println("密码: " + pwd);

		byte[] salt = PBECoder.initSalt();

		byte[] data = PBECoder.encrypt(input, pwd, salt);

		System.err.println("加密后: " + PBECoder.encryptBASE64(data));

		byte[] output = PBECoder.decrypt(data, pwd, salt);
		String outputStr = new String(output);

		System.err.println("解密后: " + outputStr);
		assertEquals(inputStr, outputStr);
	}
}