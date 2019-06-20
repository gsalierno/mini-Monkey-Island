package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Locazione;
import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;

public class Telefono extends TransportObj
{
	private static final long serialVersionUID = -1122503777260216565L;

	private boolean utilizzabile;	// Boolean di controllo che indica se è stato o meno utilizzato un GettoneTelefonico per sbloccare il Telefono. (False = NON UTILIZZABILE / True = UTILIZZABILE)

	public Telefono(String ID, String name, String description, Locazione arrivo)
	{
		super(ID, name, description, arrivo);
		utilizzabile = false;
	}

	/**
	 * Se possibile, cambia la locazione del personaggio con quella a cui il Telefono e' stato associato in fase di parsing
	 */
	@Override
	public boolean usa(pgGiocante pg)
	{
		if (utilizzabile == false)
			Motore.write("Il telefono sembra non funzionare senza gettoni!");
		else
		{	
			Motore.write("Wow! Non pensavo esistesse il teletrasporto stile Matrix!");
			pg.cambiaLocazione(arrivo);
		}
		return true;
	}

	/**
	 * Cambia lo stato del telefono modificandone l'apposito attributo.
	 */
	public void utilizzabile()	{utilizzabile = true;}
}