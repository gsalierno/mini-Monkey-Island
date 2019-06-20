package com.avventuragrafica;

import java.io.Serializable;
import java.util.ArrayList;

import com.avventuragrafica.Oggetti.Oggetto;
/**
 *  Definisce il tipo Locazione.
 * @author Giulio Salierno
 *
 */
public class Locazione implements Serializable
{
	private static final long serialVersionUID = -6609704034710228440L;
	private String name = "";
	private String ID = "";
	private ArrayList<Personaggio> pg;
	private ArrayList<Oggetto> inventarioObj;

	public Locazione(String ID, String name)
	{
		this.name = name;
		this.ID = ID;
		pg = new ArrayList<Personaggio>();
		inventarioObj = new ArrayList<Oggetto>();
	}

	/**
	 * Aggiunge l'oggetto passato in argomento all'ArrayList inventario della locazione.
	 * @param ogg
	 */
	public void addObj(Oggetto ogg)	{inventarioObj.add(ogg);}

	/**
	 * Rimuove l'oggetto passato in argomento dall'ArrayList inventario della locazione.
	 * @param ogg
	 */
	public void removeObj(Oggetto ogg)	{inventarioObj.remove(ogg);}

	/**
	 * Aggiunge il personaggio passato in argomento all'ArrayList dei personaggi presenti nella locazione.
	 * @param personaggio
	 */
	public void addPg(Personaggio personaggio)	{pg.add(personaggio);}

	/**
	 * Rimuove il personaggio passato in argomento dall'ArrayList dei personaggi presenti nella locazione.
	 * @param personaggio
	 */
	public void remPg(Personaggio personaggio)	{pg.remove(personaggio);}

	public String toString()	{return name;}

	/**
	 * Ritorna l'ID della locazione.
	 * @return
	 */
	public String idLoc()	{return ID;}

	/**
	 * Ritorna l'ArrayList inventario della locazione.
	 * @return
	 */
	public ArrayList<Oggetto> getInventario() {return inventarioObj;}

	/**
	 * Ritorna l'ArrayList contenente i personaggi presenti all'interno della locazione.
	 * @return
	 */
	public ArrayList<Personaggio> getPersonaggi() {return pg;}
}