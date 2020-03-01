package com.box.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class EncryptionUtil {
	public static String encryption(String plaintext) {
		String ciphertext = new SimpleHash("md5", plaintext, null, 10).toString();
		return ciphertext;
	}
	
	public static void main(String[] args) {
		System.out.println(EncryptionUtil.encryption("123456"));
		System.out.println(new SimpleHash("md5", "123456", null, 10).toString());
	}
}
