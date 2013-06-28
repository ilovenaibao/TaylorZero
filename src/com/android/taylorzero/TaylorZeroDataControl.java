package com.android.taylorzero;

public class TaylorZeroDataControl {

	public final static byte[] mark_head = { (byte) 0xDE, (byte) 0xED };
	public final static byte[] mark_end = { (byte) 0xED, (byte) 0xDE };

	public static String bytes2String(byte[] buffer) {
		String ret = "";
		char oneByte = 0x0000;
		int j = 0;
		for (int i = 0; null != buffer && i < buffer.length; i++) {
			oneByte = (char) (buffer[i] > 0 ? buffer[i] : (buffer[i] & 0x00FF));
			j = oneByte;
			ret += j;
		}
		return ret;
	}
}
