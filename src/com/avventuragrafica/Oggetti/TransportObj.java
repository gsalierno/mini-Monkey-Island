package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Locazione;
import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Proprietario;
import com.avventuragrafica.Interfacce.Usabile;

public abstract class TransportObj extends Oggetto implements Proprietario, Usabile
{
	private static final long serialVersionUID = -3006913499916026922L;

	protected Locazione arrivo;
	protected String proprietario;

	public TransportObj(String ID, String name, String description, Locazione arrivo)
	{
		super(ID, name, description);
		this.arrivo = arrivo;
	}

	/**
	 * Stampa nella console di gioco un generico messaggio sul TransportObj esaminato.
	 */
	@Override
	public void esamina()	{Motore.write("Descrizione: " + description);}

	/**
	 * Metodo non utilizzabile.
	 */
	public boolean usa()	{return false;}

	abstract public boolean usa(pgGiocante pg);

	/**
	 * Metodo non utilizzabile.
	 */
	public void usa(Usabile ogg)	{}

	/**
	 * Cambia il proprietario del TransportObj modificandone l'apposito attributo.
	 */
	public void cambiaProprietario(String name)	{proprietario = name;}
}