package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Accendibile;
import com.avventuragrafica.Interfacce.Usabile;

public class Computer extends SimpleObj implements Accendibile
{
	private static final long serialVersionUID = -8796208109052284748L;

	/**
	 * Boolean di controllo che indica se il computer è spento o acceso (True = ACCESO / False = SPENTO).
	 */
	private boolean stato = false;

	public Computer(String ID, String name, String description)	{super(ID, name, description);}

	/**
	 * Dopo aver controllato lo stato del computer, se possibile stampa nella console di gioco un messaggio per indicare all'utente che e' necessario utilizzare il computer con un dischetto.
	 */
	public boolean usa()
	{
		if(stato)
			Motore.write("Non c'e' nulla su questo computer, piuttosto potrei utilizzarlo con un dischetto...");

		else
			Motore.write("Non posso utilizzare un computer spento! Dovrei prima accenderlo!");

		return true;
	}

	/**
	 * Ritorna lo stato del computer.
	 * @return
	 */
	public boolean stato()	{return stato;}

	/**
	 * Se possibile, accende il computer modificando l'apposito attributo.
	 */
	public void accendi()
	{
		if (stato == false)
		{
			stato = true;
			Motore.write("Il computer e' ora acceso!");
		}
		else
			Motore.write("Il computer e' gia' acceso!");
	}
	
	/**
	 * Se possibile, spegne il computer modificando l'apposito attributo.
	 */
	public void spegni()
	{
		if (stato)
		{
			stato = false;
			Motore.write("Il computer e' ora spento!");
		}
		else
			Motore.write("Il computer e' gia' spento!");
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