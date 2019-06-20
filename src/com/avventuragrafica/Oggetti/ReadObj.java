package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Proprietario;
import com.avventuragrafica.Interfacce.Usabile;

public abstract class ReadObj extends Oggetto implements Proprietario, Usabile
{
	private static final long serialVersionUID = -7489733040225426198L;

	protected String txt;	// Contenuto del ReadObj.
	@SuppressWarnings("unused")
	private String proprietario;

	public ReadObj(String ID, String name, String description, String txt)	
	{
		super(ID, name, description);
		this.txt = txt;
		this.proprietario = "nessuno";
	}

	/**
	 * Stampa nella console di gioco un messaggio generico sul ReadObj esaminato.
	 */
	@Override
	public void esamina()	{Motore.write(description);}

	/**
	 * Cambia il proprietario del ReadObj modificandone l'apposito attributo.
	 */
	public void cambiaProprietario(String name)	{proprietario = name;}

	/**
	 * Stampa nella console di gioco in testo contenuto nel ReadObj
	 */
	public void leggi() 
	{
		Motore.write("Il contenuto di "+super.toString()+" Ž:"+" "+txt);
	}
	
	/**
	 * Metodo che non produce nessun effetto sui ReadObj
	 */
	public boolean usa()
	{
		Motore.write("Non so come usare "+super.toString()+" piuttosto sembra esserci scritto qualcosa!");
		return true;
	}

	/**
	 * Metodo non utilizzabile.
	 */
	public boolean usa(pgGiocante pg)	{return false;}

	/**
	 * Metodo non utilizzabile.
	 */
	public void usa(Usabile ogg)	{}
	
	
}