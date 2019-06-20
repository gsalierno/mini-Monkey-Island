package com.avventuragrafica;

public class IllegalOperationException extends Exception
{
	private static final long serialVersionUID = 3582455049778839740L;

	public IllegalOperationException(String str) {Motore.write(str);}
}
