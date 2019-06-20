package com.avventuragrafica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe delegata della serializzazione e deserializzazione degli oggetti.
 * @author Giulio Salierno
 */

public class Serializza
{
	private ObjectOutputStream output;	// ObjectOutputStream su cui verrà salvato il flusso di byte.
	private List<ArrayList<Serializable>> salvataggio;
	private List<ArrayList<Serializable>> caricamento;

	private ArrayList<Personaggio> personaggi; // Riferimento all'arrayList personaggi utile in fase di caricamento.
	private ArrayList<Locazione> locazioni; // Riferimento all'arrayList locazioni utile in fase di caricamento.

	public Serializza()	{salvataggio = new ArrayList<ArrayList<Serializable>>();}

	/**
	 * Serializza lo stato attuale del gioco in uno stream di byte e lo salva in un file.
	 * @throws IOException
	 * @throws IllegalOperation
	 */
	public void serializza(File f) throws IOException, IllegalOperationException
	{
		salvataggio.add(0, castSerializable(Motore.getPersonaggi()));	// ### SALVA NELLA PRIMA POSIZIONE DELLA LIST SALVATAGGIO LO STATO ATTUALE DEI PERSONAGGI DOPO AVERNE FATTO UN CAST ESPLICITO A SERIALIZABLE ###
		salvataggio.add(1, castSerializable(Motore.getLocazioni()));	// ### SALVA NELLA SECONDA POSIZIONE DELLA LIST SALVATAGGIO LO STATO ATTUALE DELLE LOCAZIONI DOPO AVERNE FATTO UN CAST ESPLICITO A SERIALIZABLE ###
		
		if (salvataggio.isEmpty())
			throw new IllegalOperationException("Impossibile effettuare il salvataggio: non e' in corso nessuna partita!");

		output = new ObjectOutputStream(new FileOutputStream(f));
		output.writeObject(salvataggio);
		output.close();
	}

	/**
	 * Carica una partita deserializzando lo stato del gioco da un file salvato in precedenza.
	 * @param File
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void deserializza(File f) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(f));

		caricamento = (ArrayList<ArrayList<Serializable>>) input.readObject();

		personaggi = castPersonaggio(caricamento.get(0));
		locazioni = castLocazioni(caricamento.get(1));

		input.close();
	}

	/**
	 * Effettua un cast a Serializable degli oggetti contenuti nell'ArrayList generico in argomento.
	 * @param <T>
	 * @return
	 */
	private <T> ArrayList<Serializable> castSerializable(ArrayList<T> oggetti)
	{
		ArrayList<Serializable> salvataggio = new ArrayList<Serializable>();

		for (T oggetto : oggetti)
			salvataggio.add((Serializable) oggetto);

		return salvataggio;
	}

	/**
	 * Effettua un cast a Personaggio degli oggetti contenuti nell'ArrayList parametrizzato a Serializable in argomento.
	 * @param serialized
	 * @return
	 */
	private ArrayList<Personaggio> castPersonaggio(ArrayList<Serializable> serialized)
	{
		ArrayList<Personaggio> personaggi = new ArrayList<Personaggio>();

		for (Serializable ser : serialized)
			personaggi.add((Personaggio) ser);

		return personaggi;
	}

	/**
	 * Effettua un cast a Locazione degli oggetti contenuti nell'ArrayList parametrizzato a Srializable in argomento.
	 * @param serialize
	 * @return
	 */
	private ArrayList<Locazione> castLocazioni(ArrayList<Serializable> serialize)
	{
		locazioni = new ArrayList<Locazione>();

		for (Serializable ser : serialize)
			locazioni.add((Locazione) ser);

		return locazioni;
	}

	/**
	 * Ritorna l'ArrayList dei personaggi caricati da file.
	 * @return
	 */
	public ArrayList<Personaggio> getPersonaggi()	{return personaggi;}

	/**
	 * Ritorno l'ArrayList delle locazioni caricate da file.
	 * @return
	 */
	public ArrayList<Locazione> getLocazione()	{return locazioni;}
}