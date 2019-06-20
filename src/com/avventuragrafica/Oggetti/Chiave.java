package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.Interfacce.Usabile;

public class Chiave extends KeyObj
{
	private static final long serialVersionUID = 8859499144393175415L;

	public Chiave(String ID, String name, String description, Apertura ap)	{super(ID, name, description, ap);}

	/**
	 * Tenta di sbloccare l'oggetto in argomento avendo successo solo se questo è del tipo giusto e il suo ID corrisponde a quello associato al KeyObj in fase di parsing.
	 */
	@Override
	public void usa(Usabile ogg)
	{
		if (ogg instanceof Porta || ogg instanceof Botola || ogg instanceof PassaggioSegreto)
		{
			if (((Apertura) ogg).getId().equals(ap.getId()))
			{
				((Apertura) ogg).unLock();
				Motore.write(ogg.toString()+" e' adesso sbloccata!");
			}
			else
				Motore.write(description+" sembrerebbe non aprire "+ap.toString());
			}
	}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public boolean usa()	{return false;}
}