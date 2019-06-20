package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Usabile;

public class Divano extends SimpleObj
{	private static final long serialVersionUID = 1060168425723225300L;

	public Divano(String ID, String name, String description)	{super(ID, name, description);}

	/**
	 * Stampa nella console di gioco un messaggio che indica l'impossibilita' di compiere l'azione USA sull'oggetto Divano.
	 * @return
	 */
	public boolean usa()
	{
		Motore.write("Avete presente quei momenti nei quali ci si dimentica una cosa? Ecco, mi sono dimenticato come ci si siede!");
		return true;
	}

	/**
	 * Metodo non utilizzabile.
	 * @param pg
	 * @return
	 */
	@Override
	public boolean usa(pgGiocante pg)	{return false;}

	/**
	 * Metodo non utilizzabile.
	 * @param ogg
	 */
	@Override
	public void usa(Usabile ogg)	{}
}