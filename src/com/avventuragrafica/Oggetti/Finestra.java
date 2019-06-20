package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Locazione;
import com.avventuragrafica.pgGiocante;

public class Finestra extends Apertura
{
	private static final long serialVersionUID = -753460876527798267L;

	public Finestra(String ID, String nome, String descrizione, Locazione loc1, Locazione loc2)	{super(ID, nome, descrizione, loc1, loc2);}

	/**
	 * Se possibile, cambia la locazione del personaggio.
	 */
	public void entra(pgGiocante pg)
	{
		if (pg.getLocCurr().idLoc().equals(loc1.idLoc()))
			pg.cambiaLocazione(loc2);
		else
			pg.cambiaLocazione(loc1);
	}
}