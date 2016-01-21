package com.linmalu.LinmaluShortcutkeyAPI.Config;

import com.linmalu.LinmaluShortcutkey.LinmaluKeyboardData;

public class ShortcutkeyData
{
	private boolean alt;
	private boolean ctrl;
	private boolean shift;
	private int keyCode;
	private String chat;

	public ShortcutkeyData(boolean alt, boolean ctrl, boolean shift, int keyCode, String chat)
	{
		this.alt = alt;
		this.ctrl = ctrl;
		this.shift = shift;
		this.keyCode = keyCode;
		this.chat = chat;
	}
	public boolean equals(boolean alt, boolean ctrl, boolean shift, int key)
	{
		return this.alt == alt && this.ctrl == ctrl && this.shift == shift && this.keyCode == key;
	}
	public boolean equals(ShortcutkeyData sd)
	{
		return alt == sd.isAlt() && ctrl == sd.isCtrl() && shift == sd.isShift() && keyCode == sd.getKeyCode();
	}
	public boolean equals(LinmaluKeyboardData lkd)
	{
		return alt == lkd.isAlt() && ctrl == lkd.isCtrl() && shift == lkd.isShift() && keyCode == lkd.getLinmaluKeyboard().getKeyCode();
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
	public int getKeyCode()
	{
		return keyCode;
	}
	public String getChat()
	{
		return chat;
	}
	public void setChat(String chat)
	{
		this.chat = chat;
	}
}
