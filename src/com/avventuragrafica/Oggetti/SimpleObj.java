package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Proprietario;
import com.avventuragrafica.Interfacce.Usabile;

public abstract class SimpleObj extends Oggetto implements Proprietario, Usabile
{
	private static final long serialVersionUID = -2923128925106212426L;
	@SuppressWarnings("unused")
	private String proprietario;

	public SimpleObj(String ID, String name, String description)
	{
		super(ID, name, description);
		this.proprietario = "nessuno";
	}

	/**
	 * Cambia il proprietario del SimpleObj modificandone l'apposito attributo.
	 */
	public void cambiaProprietario(String name)	{proprietario = name;}

	/**
	 * Stampa nella console di gioco un generico messaggio sul SimpleObj esaminato.
	 */
	@Override
	public void esamina()	{Motore.write(description);}

	abstract public boolean usa();

	abstract public boolean usa(pgGiocante pg);

	abstract public void usa(Usabile ogg);
}