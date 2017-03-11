package com.ywc.des;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.ywc.util.EncryptUtil;

public class AES {

	// KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	// SecretKey 负责保存对称密钥
	private SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;

	public AES() throws NoSuchAlgorithmException, NoSuchPaddingException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
		// keygen = KeyGenerator.getInstance("DES");
		// 生成密钥
		// deskey = keygen.generateKey();

		// DESKeySpec desKS = new DESKeySpec("[B@22f71333".getBytes());
		// SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		// SecretKey sk = skf.generateSecret(desKS);
		// 生成Cipher对象,指定其支持的DES算法
		// c = Cipher.getInstance("DES");
	}

	/**
	 * 对字符串加密
	 * 
	 * @param bytK
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 */
	public byte[] Encrytor(byte[] byteIn, String pwd) throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		// DESKeySpec desKS = new DESKeySpec(bytK);
		// SecretKeyFactory skf = SecretKeyFactory.getInstance("AES");
		// SecretKey sk = skf.generateSecret(desKS);
		// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
		SecretKey secretKey = generateSpecificKey(pwd);
		Cipher cip = Cipher.getInstance("AES");
		cip.init(Cipher.ENCRYPT_MODE, secretKey);
		return cip.doFinal(byteIn);

		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		// c.init(Cipher.ENCRYPT_MODE, deskey);
		// 加密，结果保存进cipherByte
		// cipherByte = c.doFinal(byteIn);
		// return cipherByte;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @param bytK
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Decryptor(byte[] buff, String pwd)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		// c.init(Cipher.DECRYPT_MODE, deskey);
		// cipherByte = c.doFinal(buff);
		// return cipherByte;
		try {
			// DESKeySpec desKS = new DESKeySpec(bytK);
			// SecretKeyFactory skf = SecretKeyFactory.getInstance("AES");
			// SecretKey sk = skf.generateSecret(desKS);
			// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
			SecretKey secretKey = generateSpecificKey(pwd);
			Cipher cip = Cipher.getInstance("AES");
			cip.init(Cipher.DECRYPT_MODE, secretKey);
			return cip.doFinal(buff);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void encryptFile(String filePath, String pwd) {
		// System.out.println("秘钥:" + deskey.getEncoded());
		try {
			// byte[] bytK = EncryptUtil.getKeyByStr(key.substring(0, 16));
			File fileIn = new File(Constant.PLAINTEXT_PATH + filePath);
			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = new byte[(int) fileIn.length()];
			for (int i = 0; i < fileIn.length(); i++) {
				bytIn[i] = (byte) fis.read();
			}
			byte[] bytOut = Encrytor(bytIn, pwd);
			String fileOut = Constant.CIPHERTEXT_PATH + "AES_" + fileIn.getName();
			FileOutputStream fos = new FileOutputStream(fileOut);
			for (int i = 0; i < bytOut.length; i++) {
				fos.write((int) bytOut[i]);
			}
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public static void main(String[] args) throws Exception {
	// DES de1 = new DES();
	// String msg ="郭XX-搞笑相声全集";
	// byte[] encontent = de1.Encrytor(msg);
	// byte[] decontent = de1.Decryptor(encontent);
	// System.out.println("明文是:" + msg);
	// System.out.println("加密后:" + new String(encontent));
	// System.out.println("解密后:" + new String(decontent));
	// }
	public void decryptFile(String filePath, String pwd) {
		try {

			// byte[] bytK = EncryptUtil.getKeyByStr(key.substring(0, 16));

			String strPath = Constant.PLAINTEXT_PATH + filePath.replaceAll("AES_", "");

			File fileIn = new File(Constant.CIPHERTEXT_PATH + filePath);
			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = new byte[(int) fileIn.length()];
			for (int i = 0; i < fileIn.length(); i++) {
				bytIn[i] = (byte) fis.read();
			}

			byte[] bytOut = Decryptor(bytIn, pwd);
			File fileOut = new File(strPath);
			fileOut.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileOut);
			for (int i = 0; i < bytOut.length; i++) {
				fos.write((int) bytOut[i]);
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void AESEncrypt() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入6位密码:\n");
		String pass = scan.next();// 输入6位密码
		System.out.println("请输入文件名:\n");
		String name = scan.next();// 文件名
		encryptFile(name, pass);
		System.out.println("加密成功!\n");
	}

	public void AESDecrypt() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入6位密码:\n");
		String pass = scan.next();// 输入6位密码
		System.out.println("请输入文件名:\n");
		String name = scan.next();// 文件名
		decryptFile(name, pass);
		System.out.println("解密成功!\n");
	}

	/**
	 * 随机生成秘钥
	 */
	public SecretKey generateRandomKey() {
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128);// 要生成多少位，只需要修改这里即可128, 192或256
			SecretKey sk = kg.generateKey();
			byte[] b = sk.getEncoded();
			String s = byteToHexString(b);
			System.out.println(s);
			System.out.println("十六进制密钥长度为" + s.length());
			System.out.println("二进制密钥的长度为" + s.length() * 4);
			return sk;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("没有此算法。");
		}
		return null;

	}

	/**
	 * 使用指定的字符串生成秘钥
	 */
	public SecretKey generateSpecificKey(String password) {
		// 生成秘钥
		// String password = "testkey";
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			// kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
			// SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以生成的秘钥就一样。
			kg.init(128, new SecureRandom(password.getBytes()));
			SecretKey sk = kg.generateKey();
			byte[] b = sk.getEncoded();
			String s = byteToHexString(b);
			System.out.println(s);
			System.out.println("十六进制密钥长度为" + s.length());
			System.out.println("二进制密钥的长度为" + s.length() * 4);
			return sk;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("没有此算法。");
		}
		return null;
	}

	/**
	 * byte数组转化为16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String strHex = Integer.toHexString(bytes[i]);
			if (strHex.length() > 3) {
				sb.append(strHex.substring(6));
			} else {
				if (strHex.length() < 2) {
					sb.append("0" + strHex);
				} else {
					sb.append(strHex);
				}
			}
		}
		return sb.toString();
	}
}
