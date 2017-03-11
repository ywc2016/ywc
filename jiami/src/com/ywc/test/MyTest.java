package com.ywc.test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

import com.ywc.des.AES;
import com.ywc.des.Constant;
import com.ywc.des.DES;
import com.ywc.des.TripleDES;
import com.ywc.util.EncryptUtil;

public class MyTest {
	@Test
	public void test3Encrypt() {
		new TripleDES().encrypt();
	}

	@Test
	public void test3Decrypt() {
		new TripleDES().decrypt();
	}

	@Test
	public void testPath() {
		File file = new File("files/1.txt");
		try {
			System.out.println(file.getCanonicalPath());
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getPath());
			System.out.println(file.getName());
			System.out.println(file.getParentFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDESEncrypt() {
		try {
			DES des = new DES();
			des.DESEncrypt();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDESDecrypt() {
		try {
			new DES().DESDecrypt();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAESEncrypt() {
		try {
			AES aes = new AES();
			aes.AESEncrypt();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAESDecrypt() {
		try {
			new AES().AESDecrypt();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMd5() {
		String string = EncryptUtil.md5s("123456");
		System.out.println(string);
	}

	@Test
	public void testGenerateKey() {
		try {
			new AES().generateSpecificKey("123456");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
