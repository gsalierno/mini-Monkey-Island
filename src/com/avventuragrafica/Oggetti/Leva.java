package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.Interfacce.Usabile;

public class Leva extends KeyObj
{
	private static final long serialVersionUID = 9017850694522949266L;

	public Leva(String ID, String name, String description, Apertura ap)	{super(ID, name, description, ap);}

	/**
	 * Sblocca l'apertura corrispondente alla leva utilizzata.
	 */
	@Override
	public boolean usa()
	{
		Motore.write("Non so come usare una leva, piuttosto potrei tirarla!");
		return true;
	}
	
	/**
	 * Richiama il metodo usa.
	 */
	public void tira()
	{
		ap.unLock();
		Motore.write(ap.toString()+" adesso e' aperta!");
		
	}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public void usa(Usabile ogg)	{}
}