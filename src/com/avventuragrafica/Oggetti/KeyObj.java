package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Proprietario;
import com.avventuragrafica.Interfacce.Usabile;

public abstract class KeyObj extends Oggetto implements Proprietario, Usabile
{
	private static final long serialVersionUID = 5836152580182633614L;

	protected Apertura ap;
	@SuppressWarnings("unused")
	private String proprietario;

	public KeyObj(String ID, String name, String description, Apertura ap)
	{
		super(ID, name, description);
		this.ap = ap;
	}

	/**
	 * Stampa nella console di gioco un messaggio generico sul KeyObj esaminato.
	 */
	@Override
	public void esamina()	{Motore.write(description);}

	/**
	 * Cambia il proprietario del KeyObj mdoficandone l'apposito attributo.
	 */
	public void cambiaProprietario(String name)	{proprietario = name;}

	abstract public boolean usa();

	abstract public void usa(Usabile ogg);

	/**
	 * Metodo non utilizzabile.
	 */
	public boolean usa(pgGiocante pg)	{return false;}
}