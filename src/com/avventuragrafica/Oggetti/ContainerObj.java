package com.avventuragrafica.Oggetti;

import java.util.ArrayList;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Proprietario;
import com.avventuragrafica.Interfacce.Usabile;

public abstract class ContainerObj extends Oggetto implements Proprietario, Usabile
{
	private static final long serialVersionUID = 8387302963030290945L;
	private ArrayList<Oggetto> contenuto;
	@SuppressWarnings("unused")
	private String proprietario;

	public ContainerObj(String ID, String name, String description)
	{
		super(ID, name, description);
		contenuto = new ArrayList<Oggetto>();
		this.proprietario = "nessuno";
	}

	/**
	 * Aggiunge l'oggetto in argomento all'inventario del ContainerObj.
	 * @param a
	 */
	public void addObj(Oggetto a)	{contenuto.add(a);}

	/**
	 * Rimuove l'oggetto in argomento dall'inventario del ContainerObj.
	 * @param a
	 */
	public void removeObj(Oggetto a)	{contenuto.remove(a);}

	/**
	 * Ritorna l'ArrayList inventario del ContainerObj.
	 * @return
	 */
	public ArrayList<Oggetto> getContenuto()	{return contenuto;}

	/**
	 * Stampa un messaggio nella console di gioco che indica il contenuto del ContainerObj.
	 */
	public void printContenuto()	{Motore.write(this.toString()+" contiene: "+contenuto.toString());}

	public String toString()	{return description;}

	/**
	 * Stampa nella console di gioco un messaggio generico sul ContainerObj.
	 */
	public void esamina()	{Motore.write(description+" potrebbe contenere altri oggetti...");}

	/**
	 * Stampa nella console di gioco un messaggio che indica l'impossibile di compiere l0azione USA su qualsiasi tipo di ContainerObj.
	 */
	public boolean usa()
	{
		Motore.write("Non so come usare "+description+" piuttosto potrebbe contenere qualcosa al suo interno!");
		return true;
	}

	/**
	 * Metodo non utilizzabile.
	 */
	public boolean usa(pgGiocante pg)	{return false;}

	/**
	 * Metodo non utilizzabile
	 */
	public void usa(Usabile ogg)	{}

	/**
	 * Cambia il proprietario dell'oggetto modificandone l'apposito attributo.
	 */
	public void cambiaProprietario(String name)	{proprietario = name;}
}