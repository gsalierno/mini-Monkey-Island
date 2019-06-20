package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Locazione;
import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;

public class Botola extends Apertura
{
	private static final long serialVersionUID = -7644285064447580611L;

	public Botola(String ID, String nome, String descrizione, Locazione loc1, Locazione loc2)	{super(ID, nome, descrizione, loc1, loc2);}

	/**
	 * Se la botola è aperta cambia la locazione del personaggio.
	 */
	public void entra(pgGiocante pg)
	{
		if (super.getStato())
		{
			if (pg.getLocCurr().idLoc().equals(loc1.idLoc()))
				pg.cambiaLocazione(loc2);
			else
				pg.cambiaLocazione(loc1);
		}
		else
			Motore.write(super.getDescr()+" sembrerebbe essere chiusa...");
	}
}