package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Usabile;

public class Denaro extends SimpleObj
{	private static final long serialVersionUID = -1953005631391675902L;

	public Denaro(String ID, String name, String description)	{super(ID, name, description);}

	/**
	 * Stampa nella console di gioco un messaggio che indica l'impossibilita' di compiere l'azione USA sull'oggetto Denaro.
	 */
	public boolean usa()
	{
		Motore.write("Non c'e' nulla da comprare...");
		return true;
	}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public boolean usa(pgGiocante pg)	{return false;}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public void usa(Usabile ogg)	{}
}