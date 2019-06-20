package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.Interfacce.Usabile;

public class Bottone extends KeyObj
{
	private static final long serialVersionUID = -8332852683374012954L;

	public Bottone(String ID, String name, String description, Apertura ap)	{super(ID, name, description, ap);}

	/**
	 * Sblocca l'apertura associata al bottone in fase di parsing.
	 */
	@Override
	public boolean usa()
	{
		Motore.write("Non so come usare un bottone,piuttosto potrei premerlo!");
		return true;
	}

	/**
	 * Richiama il metodo usa.
	 */
	public void spingi()
	{
		ap.unLock();
		Motore.write(ap.toString() + " adesso e' aperta!");
	}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public void usa(Usabile ogg)	{}
}