package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Locazione;
import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;

public class Porta extends Apertura
{
	private static final long serialVersionUID = -2036928683732101630L;

	public Porta(String ID, String nome, String descrizione, Locazione loc1, Locazione loc2)	{super(ID, nome, descrizione, loc1, loc2);}

	/**
	 * Se possibile cambia la locazione del personaggio.
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