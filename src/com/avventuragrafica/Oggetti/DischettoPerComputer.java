package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.Interfacce.Usabile;

public class DischettoPerComputer extends ReadObj
{	private static final long serialVersionUID = -5139326789420492483L;

	public DischettoPerComputer(String ID, String name, String description, String txt)	{super(ID, name, description, txt);}

	/**
	 * Prova a far interagire il dischetto con l'oggetto in argomento, se questo e' un computer l'operazione e' completata e viene visualizzato il testo contenuto nella console di gioco.
	 */
	@Override
	public void usa(Usabile a)
	{
		if (a instanceof Computer)
		{
			if (((Computer) a).stato())
				Motore.write("Il contenuto di "+super.description+" e': "+txt);
			
			else
				Motore.write("Il computer e' spento! Dovrei prima accenderlo!");

		}
		else
			Motore.write("Non posso usare "+this.toString()+" con "+a.toString());
	}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public boolean usa()	{return false;}
}