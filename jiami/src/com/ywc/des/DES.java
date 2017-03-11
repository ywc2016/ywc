package com.ywc.des;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

import com.ywc.util.EncryptUtil;

public class DES {

	// KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	// SecretKey 负责保存对称密钥
	private SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;

	public DES() throws NoSuchAlgorithmException, NoSuchPaddingException {
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
	public byte[] Encrytor(byte[] byteIn, byte[] bytK) throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		DESKeySpec desKS = new DESKeySpec(bytK);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.ENCRYPT_MODE, sk);
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
	public byte[] Decryptor(byte[] buff, byte[] bytK)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		// c.init(Cipher.DECRYPT_MODE, deskey);
		// cipherByte = c.doFinal(buff);
		// return cipherByte;
		try {
			DESKeySpec desKS = new DESKeySpec(bytK);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey sk = skf.generateSecret(desKS);
			Cipher cip = Cipher.getInstance("DES");
			cip.init(Cipher.DECRYPT_MODE, sk);
			return cip.doFinal(buff);
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
		return null;
	}

	public void encryptFile(String filePath, String key) {
		// System.out.println("秘钥:" + deskey.getEncoded());
		try {
			byte[] bytK = EncryptUtil.getKeyByStr(key.substring(0, 16));
			File fileIn = new File(Constant.PLAINTEXT_PATH + filePath);
			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = new byte[(int) fileIn.length()];
			for (int i = 0; i < fileIn.length(); i++) {
				bytIn[i] = (byte) fis.read();
			}
			byte[] bytOut = Encrytor(bytIn, bytK);
			String fileOut = Constant.CIPHERTEXT_PATH + "DES_" + fileIn.getName();
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
	public void decryptFile(String filePath, String key) {
		try {

			byte[] bytK = EncryptUtil.getKeyByStr(key.substring(0, 16));

			String strPath = Constant.PLAINTEXT_PATH + filePath.replaceAll("DES_", "");

			File fileIn = new File(Constant.CIPHERTEXT_PATH + filePath);
			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = new byte[(int) fileIn.length()];
			for (int i = 0; i < fileIn.length(); i++) {
				bytIn[i] = (byte) fis.read();
			}

			byte[] bytOut = Decryptor(bytIn, bytK);
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

	public void DESEncrypt() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入6位密码:\n");
		String pass = scan.next();// 输入6位密码
		System.out.println("请输入文件名:\n");
		String name = scan.next();// 文件名
		encryptFile(name, EncryptUtil.md5s(pass));
		System.out.println("加密成功!\n");
	}

	public void DESDecrypt() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入6位密码:\n");
		String pass = scan.next();// 输入6位密码
		System.out.println("请输入文件名:\n");
		String name = scan.next();// 文件名
		decryptFile(name, EncryptUtil.md5s(pass));
		System.out.println("解密成功!\n");
	}
}
