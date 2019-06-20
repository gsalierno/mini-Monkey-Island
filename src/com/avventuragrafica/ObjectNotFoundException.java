package com.avventuragrafica;

public class ObjectNotFoundException extends Exception
{
	private static final long serialVersionUID = 8110105985302585123L;
	public ObjectNotFoundException(String str)	{Motore.write(str);}
}