package com.nordskog.messengerpeep;

public class StringUtils
{
	public static final String INDENT = "    ";

	public static String addIndent( String string )
	{
		return string.replace("\n", "\n    ");
	}

}
