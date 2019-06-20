package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Usabile;

public class GettoneTelefonico extends SimpleObj
{
	private static final long serialVersionUID = -2524544393393793499L;

	public GettoneTelefonico(String ID, String name, String description)	{super(ID, name, description);}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public boolean usa()	{return false;}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public boolean usa(pgGiocante pg)	{return false;}

	/**
	 * Prova a far interagire il gettono telefonico con l'oggetto in argomento, se è un telefono l'azione viene completata con successo ed il telefono viene reso utilizzabile.
	 */
	@Override
	public void usa(Usabile ogg)
	{
		if (ogg instanceof Telefono)
		{
			((Telefono) ogg).utilizzabile();
			Motore.write("Il telefono  e' adesso utilizzabile");
		}
		else
			Motore.write("Non posso usare "+super.description+" con "+ogg.toString());
	}
}