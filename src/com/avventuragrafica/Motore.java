package com.avventuragrafica;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.avventuragrafica.motoreGrafico.MainGui;
/**
 * Classe principale che si occupa di istanziare una partita, rappresenta l'anello di collegamento tra la logica del gioco e la parte grafica.
 * @author Giulio Salierno
 *
 */
public class Motore
{
	private static ArrayList<Personaggio> personaggi;	// Riferimento agli oggetti istanziate tramite parsing o caricati da un salvataggio preesistente.
	private static ArrayList<Locazione> locazioni;	// Riferimento alle locazioni istanziate tramite parsing o caricate da un salvataggio preesistente.
	private static pgGiocante pg;

	private static CreaMondo mondo;

	/**
	 * Istanzia il motore grafico del gioco.
	 * @param args
	 * @throws FileNotFoundException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public static void main(String[] args) throws FileNotFoundException, SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		@SuppressWarnings("unused")
		MainGui frame = new MainGui();
	}

	/**
	 * Stampa la stringa passata in argomento sulla console istanziata dal motore grafico.
	 * @param str
	 */
	public static void write(String str)	{System.out.println(" > " + str);}

	/**
	 * Ritorna il personaggio giocante.
	 * @return
	 */
	public static pgGiocante getPg()	{return (pgGiocante) personaggi.get(0);}

	/**
	 * Ritorna la locazione corrente del personaggio giocante.
	 * @return
	 */
	public static Locazione getCurrentLocation()	{return pg.getLocCurr();}

	/**
	 * Ritorna l'ArrayList dei personaggi istanziati.
	 * @return
	 */
	public static ArrayList<Personaggio> getPersonaggi()	{return personaggi;}

	/**
	 * Ritorna l'ArrayList delle locazioni istanziate.
	 * @return
	 */
	public static ArrayList<Locazione> getLocazioni()	{return locazioni;}

	/**
	 * Salva nel motore del gioco riferimenti ai personaggi e alle locazioni istanziate (utile in fase di parsing).
	 */
	public static void saveObject()
	{
		personaggi = mondo.getPersonaggi();
		locazioni = mondo.getLocazioni();
		pg = (pgGiocante) personaggi.get(0);
	}

	/**
	 * Salva nel motore del gioco riferimenti ai personaggi e alle locazioni presenti nei rispettivi ArrayList passati in argomento (utile per il caricamento da un salvataggio preesistente).
	 * @param personaggiCaricati
	 * @param locazioniCaricate
	 */
	public static void loadObj(ArrayList<Personaggio> personaggiCaricati, ArrayList<Locazione> locazioniCaricate)
	{
		personaggi = personaggiCaricati;
		locazioni = locazioniCaricate;
		pg = (pgGiocante) personaggi.get(0);
	}

	/**
	 * Richiama il metodo che eseguirà il parsing del file passato in argomento.
	 * @param Path
	 */
	public static void caricaFile(File Path)
	{
		try	{mondo = new CreaMondo(Path);}
		catch(FileNotFoundException e1)	{e1.printStackTrace();}
		catch(SecurityException e1)	{e1.printStackTrace();}
		catch(IllegalArgumentException e1)	{e1.printStackTrace();}
		catch(ClassNotFoundException e1)	{e1.printStackTrace();}
		catch(InstantiationException e1)	{e1.printStackTrace();}
		catch(IllegalAccessException e1)	{e1.printStackTrace();}
		catch(NoSuchMethodException e1)	{e1.printStackTrace();}
		catch(InvocationTargetException e1)	{e1.printStackTrace();}
	}
}