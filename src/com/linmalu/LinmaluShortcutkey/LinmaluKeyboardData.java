package com.linmalu.LinmaluShortcutkey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class LinmaluKeyboardData implements Serializable
{
	private boolean alt;
	private boolean ctrl;
	private boolean shift;
	private LinmaluKeyboard key;

	public LinmaluKeyboardData(boolean alt, boolean ctrl, boolean shift, LinmaluKeyboard key)
	{
		this.alt = alt;
		this.ctrl = ctrl;
		this.shift = shift;
		this.key = key;
	}
	public boolean isAlt()
	{
		return alt;
	}
	public boolean isCtrl()
	{
		return ctrl;
	}
	public boolean isShift()
	{
		return shift;
	}
	public LinmaluKeyboard getLinmaluKeyboard()
	{
		return key;
	}
	public byte[] toBytes() throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		oos.flush();
		byte[] bytes = bos.toByteArray();
		oos.close();
		bos.close();
		return bytes;
	}
	public static LinmaluKeyboardData toLinmaluKeyboardData(byte[] bytes) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bis);
		LinmaluKeyboardData lkd = (LinmaluKeyboardData)ois.readObject();
		ois.close();
		bis.close();
		return lkd;
	}
}
