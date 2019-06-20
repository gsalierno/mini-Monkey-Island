package com.avventuragrafica.Oggetti;

import java.io.Serializable;

public abstract class Oggetto implements Serializable
{
	private static final long serialVersionUID = 4561658235891192419L;

	private String name;
	protected String description;
	protected final String ID;

	public Oggetto(String ID, String name, String description)
	{
		this.name = name;
		this.description = description;
		this.ID = ID;
	}

	abstract public void esamina();

	public String toString()	{return description;}

	/**
	 * Ha come valore di ritorno l'ID dell'oggetto.
	 * @return
	 */
	public String getId()	{return ID;}

	/**
	 * Ha come valore di ritorno il nome dell'oggetto
	 * @return
	 */
	public String getNome()	{return name;}
}