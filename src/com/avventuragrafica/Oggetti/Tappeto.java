package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Usabile;

public class Tappeto extends SimpleObj
{
	private static final long serialVersionUID = -5861490999309800595L;

	public Tappeto(String ID, String name, String description)	{super(ID, name, description);}

	/**
	 * Stampa nella console di gioco un messaggio che indica l'impossibilità di compiere l'azione USA sull'oggetto Tappeto.
	 */
	@Override
	public boolean usa()
	{
		Motore.write("Non so come usare un tappeto!");
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