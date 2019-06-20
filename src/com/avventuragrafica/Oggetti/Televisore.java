package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Accendibile;
import com.avventuragrafica.Interfacce.Usabile;

public class Televisore extends SimpleObj implements Accendibile
{
	private static final long serialVersionUID = -3713325266190158144L;

	private boolean stato = false;	// Boolean di controllo che indica se il televisore e' acceso (True = ACCESO / False = Spento).

	public Televisore(String ID, String name, String description)	{super(ID, name, description);}

	/**
	 * Se possibile, stampa nella console di gioco un messaggio che indica l'impossibilita' di compiere l'azione USA sull'oggetto televisore.
	 */
	@Override
	public boolean usa()
	{
		if (stato)
			Motore.write("Oggi non c'e' nulla di interessante da guardare in TV...");
		else
			Motore.write("Non posso utilizzare un televisore spento! Dovrei prima accenderlo!");
		return true;
	}

	/**
	 * Se possibile, accende il Televisore modificandone l'apposito attributo.
	 */
	public void accendi()
	{
		if (stato)
			Motore.write("Il televisore e' gia' acceso!");
		else {
			stato = true;
			Motore.write("Il televisore e' ora acceso!");
		}
	}

	/**
	 * Se possibile, spegne il Televisore modificandone l'apposito attributo.
	 */
	public void spegni()
	{
		if (stato)
		{
			Motore.write("Il televisore e' ora spento!");
			stato = false;
		}
		else
			Motore.write("Il televisore e' gia' spento!");
	}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public boolean usa(pgGiocante pg)	{return false;}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public void usa(Usabile ogg)	{}
}