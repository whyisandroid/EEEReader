package com.ereader.common.util.aes;

import java.security.MessageDigest;

public class Md5 {
	public static String digist(String text) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(text.getBytes());
			byte[] b = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
