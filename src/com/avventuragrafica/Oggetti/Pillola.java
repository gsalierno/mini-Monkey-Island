package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Locazione;
import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;

public class Pillola extends TransportObj
{
	private static final long serialVersionUID = -8494547772314056240L;

	public Pillola(String ID, String name, String description, Locazione arrivo)	{super(ID, name, description, arrivo);}

	/**
	 * Cambia la locazione del personaggio con quella cui la Pillola e' stata associata in fase di parsing.
	 */
	@Override
	public boolean usa(pgGiocante pg)
	{
		pg.cambiaLocazione(arrivo);
		Motore.write("Wow! Non pensavo esistesse il teletrasporto stile Matrix!");
		return true;
	}
}