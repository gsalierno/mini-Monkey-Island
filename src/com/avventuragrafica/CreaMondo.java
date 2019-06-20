package com.avventuragrafica;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import com.avventuragrafica.Interfacce.Proprietario;
import com.avventuragrafica.Oggetti.Apertura;
import com.avventuragrafica.Oggetti.ContainerObj;
import com.avventuragrafica.Oggetti.KeyObj;
import com.avventuragrafica.Oggetti.Oggetto;
import com.avventuragrafica.Oggetti.ReadObj;
import com.avventuragrafica.Oggetti.SimpleObj;
import com.avventuragrafica.Oggetti.TransportObj;

/**
 * Classe delegata di eseguire il parsing da file per istanziare gli oggetti
 * @author Giulio Salierno
 */

public class CreaMondo 
{
	private Scanner scanner;
	private MemorizzaEventi eventi = new MemorizzaEventi();	// Classe che racchiude i metodi necessari all'aggiunta e alla cancellazione degli eventi dalla memoria dei personaggi.
	private ArrayList<Locazione> locazioni;	// ArrayList contenente le locazioni da passare al motore del gioco.
	private ArrayList<Personaggio> personaggi;	// ArrayList contenente i personaggi da passare al motore del gioco.
	private ArrayList<Apertura> aperture;	// ArrayList temporaneo delle Aperture reso necessario dall'ordine in cui vengono istanziati gli oggetti dal parser.
	private ArrayList<Oggetto> oggetti;	// ArrayList temporaneo degli Oggetti reso necessario dall'ordine in cui vengono istanziati gli oggetti dal parser.

	public CreaMondo(File path) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException 
	{
		scanner = new Scanner(path);
		scanner.useDelimiter("\n");
		exec();
	}

	/**
	 *  Metodo delegato di effettuare uno switch-case sulla sezione per richiamare il metodo appropriato.
	 */
	private void exec() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException
	{
		while(scanner.hasNext()) 
		{
			String stringa = scanner.nextLine();
			switch (Const.valueOf(stringa)) 
			{
			case LOCAZIONI:
				locazioni();
				break;

			case APERTURE:
				aperture();
				break;

			case PERSONAGGI:
				personaggi();
				break;

			case SENTIMENTI:
				sentimenti();
				break;

			case OGGETTI:
				oggetti();
				break;

			case INVENTARIO_PERSONAGGI:
				inventarioPg();
				break;

			case LOCAZIONE_OGGETTI:
				locazioneOggetti();
				break;

			case PROPRIETARI:
				proprietari();
				break;

			default:
				break;
			}
		}

		addApertureLocazioni(); // ### METODO INVOCATO IN QUESTO PUNTO PERCHE' E' NECESSARIO ATTENDERE CHE VENGANO ISTANZIATI I KEYOBJ CHE BLOCCANO LE APERTURE CUI SONO ASSOCIATI ###

		eventi.aggiornaEventoSiTrovaPossiede(locazioni); // ### METODO INVOCATO PER REGISTRARE NELLA MEMORIA DI CIASCUN PERSONAGGIO GLI OGGETTI CHE SI TROVANO NELLA LORO STESSA LOCAZIONE ED EVENTUALMENTE NELL'INVENTARIO DI UN ALTRO PERSONAGGIO ###

		scanner.close();	// ### METODO INVOCATO PER CHIUDERE LO SCANNER ###
	}

	/**
	 * Esegue il parsing delle locazioni.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void locazioni() throws ClassNotFoundException,	InstantiationException, IllegalAccessException, SecurityException,NoSuchMethodException, IllegalArgumentException, InvocationTargetException 
	{
		locazioni = new ArrayList<Locazione>();	// ArrayList che conterra' le locazioni istanziate dal parser.
		do
		{
			String str = scanner.nextLine();
			if (str.isEmpty())
				break;
			String[] temp = str.split("\t");
			String ID = temp[0];
			String name = temp[1];

			/* ### ISTANZIA LA LOCAZIONE TRAMITE REFLECTION ### */
			
			Class<?> loc = Class.forName("com.avventuragrafica.Locazione"); 
			Constructor<?> c = loc.getConstructor(String.class, String.class);
			Locazione loc1 = (Locazione) c.newInstance(ID, name);

			locazioni.add(loc1); // ### AGGIUNGE LA LOCAZIONE APPENA ISTANZIATA ALL'APPOSITO ARRAYLIST ###
		}
		while (scanner.hasNext());

	}

	/**
	 * Esegue il parsing delle aperture.
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void aperture() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		aperture = new ArrayList<Apertura>();	// ArrayList che conterra' le aperture istanizate dal parser.
		do
		{
			String str = scanner.nextLine();
			if (str.isEmpty())
				break;
			String[] temp = str.split("\t");
			String ID = temp[0];
			String nomeclasse = temp[1];
			String descrizione = temp[2];
			String loc1 = temp[3];
			String loc2 = temp[4];

			/* ### ISTANZIA L'APERTURA TRAMITE REFLECTION ### */
			
			Class<?> c = Class.forName("com.avventuragrafica.Oggetti."+ nomeclasse);
			Class<? extends Apertura> apertura = c.asSubclass(Apertura.class);
			Constructor<? extends Apertura> construttore = apertura.getConstructor(String.class, String.class, String.class, Locazione.class, Locazione.class);

			/* ### DATE LE STRINGHE LOC1 E LOC2, RESTITUISCE LE CORRISPONDENTI LOCAZIONI IN MODO TALE DA POTER ISTANZIARE L'APERTURA CON LE CORRETTE LOCAZIONI DI PARTENZA E DI ARRIVO ### */

			Locazione locazione1 = getLocazione(loc1);
			Locazione locazione2 = getLocazione(loc2);
			Apertura apertura1 = (Apertura) construttore.newInstance(ID, nomeclasse, descrizione, locazione1, locazione2);
			
			aperture.add(apertura1);	// ### AGGIUNGE L'APERTURA APPENA ISTANZIATA ALL'APPOSITO ARRAYLIST ###

		}
		while (scanner.hasNext());
	}

	/**
	 * Esegue il parsing dei personaggi.
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void personaggi() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		boolean pgGiocante = false;	// Boolean di controllo utile a verificare se il personaggio giocante sia stato o meno istanziato. Se il valore e' "true" i successivi metodi istanziano personaggi non giocanti.
		personaggi = new ArrayList<Personaggio>();	// ArrayList che conterra' i personaggi istanziati dal parser
		
		do 
		{
			String str = scanner.nextLine();
			if (str.isEmpty()) break;
			String[] temp = str.split("\t");
			String ID = temp[0];
			String name = temp[1];
			String locazione = temp[2];
			
			if (pgGiocante == false) 
			{

				/* ### ISTANZIA IL PERSONAGGIO GIOCANTE TRAMITE REFLECTION ### */
				
				Class<?> c = Class.forName("com.avventuragrafica.pgGiocante");
				Constructor<?> costruttore = c.getConstructor(String.class, String.class, Locazione.class);
				Locazione loc1 = getLocazione(locazione);	// ### DATA LA STRINGA LOC1, RESTITUISCE LA CORRISPONDENTE LOCAZIONE IN MODO TALE DA POTER ISTANZIARE IL PERSONAGGIO GIOCANTE CON LA CORRETTA LOCAZIONE ###
				pgGiocante pg = (pgGiocante) costruttore.newInstance(ID, name, loc1);

				personaggi.add(pg);	// ### AGGIUNGE IL PERSONAGGIO GIOCANTE APPENA ISTANZIATO ALL'APPOSITO ARRAYLIST ###
				loc1.addPg(pg);	// ### AGGIUNGE IL PERSONAGIO GIOCANTE APPENA ISTANZIATO ALL'INVENTARIO DELLA LOCAZIONE IN CUI ESSO SI TROVA ###
				pgGiocante = true;	// ### MODIFICA L'APPOSITO BOOLEAN DI CONTROLLO PER NOTIFICARE CHE IL PERSONAGGIO GIOCANTE E' STATO ISTANZIATO ###
			}
			else
			{

				/* ### ISTANZIA IL PERSONAGGIO TRAMITE REFLECTION ### */
				
				Class<?> c = Class.forName("com.avventuragrafica.Personaggio");
				Constructor<?> costruttore = c.getConstructor(String.class, String.class, Locazione.class);
				Locazione loc1 = getLocazione(locazione);	// ### DATA LA STRINGA LOC1, RESTITUISCE LA CORRISPONDENTE LOCAZIONE IN MODO TALE DA POTER ISTANZIARE IL PERSONAGGIO CON LA CORRETTA LOCAZIONE ###
				Personaggio pg = (Personaggio) costruttore.newInstance(ID, name, loc1); 

				personaggi.add(pg);	// ### AGGIUNGE IL PERSONAGGIO APPENA ISTANZIATO ALL'APPOSITO ARRAYLIST ###
				loc1.addPg(pg);	// ### AGGIUNGE IL PERSONAGIO APPENA ISTANZIATO ALL'INVENTARIO DELLA LOCAZIONE IN CUI ESSO SI TROVA ###
			}
		}
		while(scanner.hasNext());
	}
	
	/**
	 * Salta le righe corrispondenti ai sentimenti poiche' non e' richiesto implementarle.
	 */
	private void sentimenti()
	{
		do
		{
			String str = scanner.nextLine();
			if (str.isEmpty())
				break;
		}
		while(scanner.hasNext());
	}

	/**
	 * Esegue il parsing degli oggetti.
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void oggetti() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		oggetti = new ArrayList<Oggetto>();	// ArrayList che conterra' gli oggetti istanziati dal parser

		do
		{
			String str = scanner.nextLine();
			if (str.isEmpty())
				break;

			String[] temp = str.split("\t");
			if (instObj(temp[1]))	// ### METODO INVOCATO PER CONTROLLARE SE L'OGGETTO DI CUI SI STA FACENDO IL PARSING FA PARTE O MENO DEGLI OGGETTI ISTANZIABILI ###
			{
				
				/* ### ISTANZIA L'OGGETTO TRAMITE REFLECTION ### */
				
				Class<?> c = Class.forName("com.avventuragrafica.Oggetti."+temp[1]);
				Class<?> superclasse = c.getSuperclass();
				String pathsuperclasse = superclasse.getName(); 
				String superclasse1 = (String) pathsuperclasse.subSequence(29, pathsuperclasse.length());

				/* ### DATA LA STRINGA SUPERCLASSE1, ISTANZIA L'OGGETTO NELL'OPPORTUNA SUPERCLASSE ### */
				
				switch (Const.valueOf(superclasse1))
				{
				case SimpleObj:
					simpleObjParse(temp[0], temp[1], temp[2]);
					break;

				case ReadObj:
					readObjParse(temp[0], temp[1], temp[2], temp[3]);
					break;

				case TransportObj:
					TransportObjParse(temp[0], temp[1], temp[2], temp[3]);
					break;

				case KeyObj:
					keyObjParse(temp[0], temp[1], temp[2], temp[3]);
					break;

				case ContainerObj:
					containerObjParse(temp);
				
				default:
					break;
				}
			}
		}
		while (scanner.hasNext());

		Motore.write("Parsing da file effettuato con successo! Sono stati istanziati "+oggetti.size()+" oggetti, "+locazioni.size()+" locazioni e "+personaggi.size()+" personaggi.");
	}

	/**
	 * Esegue il parsing degli Oggetti della classe SimpleObj.
	 * @param ID
	 * @param name
	 * @param descrizione
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void simpleObjParse(String ID, String name, String descrizione) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		
		/* ### ISTANZIA IL SIMPLEOBJ TRAMITE REFLECTION ### */
	
		Class<?> c = Class.forName("com.avventuragrafica.Oggetti." + name);
		Class<? extends SimpleObj> simpleobj = c.asSubclass(SimpleObj.class);
		Constructor<? extends SimpleObj> costruttore = simpleobj.getConstructor(String.class, String.class, String.class);
		SimpleObj oggetto = costruttore.newInstance(ID, name, descrizione);

		oggetti.add(oggetto);	// ### AGGIUNGE IL SIMPLEOBJ APPENA ISTANZIATO ALL'APPOSITO ARRAYLIST ###
	}
    
	/**
	 * Esegue il parsing degli Oggetti della classe ReadObj.
	 * @param ID
	 * @param nome
	 * @param descrizione
	 * @param testo
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void readObjParse(String ID, String nome, String descrizione, String testo) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{

		/* ### ISTANZIA IL READOBJ TRAMITE REFLECTION ### */
		
		Class<?> c = Class.forName("com.avventuragrafica.Oggetti." + nome);
		Class<? extends ReadObj> readobj = c.asSubclass(ReadObj.class);
		Constructor<? extends ReadObj> costruttore = readobj.getConstructor(String.class, String.class, String.class, String.class);
		ReadObj oggetto = (ReadObj) costruttore.newInstance(ID, nome, descrizione, testo);

		oggetti.add(oggetto);	// ### AGGIUNGE IL READOBJ APPENA ISTANZIATO ALL'APPOSITO ARRAYLIST ###
	}
	
	/**
	 * Esegue il parsing degli Oggetti della clase TransportObj.
	 * @param ID
	 * @param nome
	 * @param descrizione
	 * @param loc
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void TransportObjParse(String ID, String nome, String descrizione,String loc) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		
		/* ### ISTANZIA IL TRANSPORTOBJ TRAMITE REFLECTION ### */
		
		Class<?> c = Class.forName("com.avventuragrafica.Oggetti." + nome);
		Class<? extends TransportObj> transportobj = c.asSubclass(TransportObj.class);
		Constructor<? extends TransportObj> costruttore = transportobj.getConstructor(String.class, String.class, String.class,	Locazione.class);
		Locazione loc1 = getLocazione(loc);	// ### DATA LA STRINGA LOC1 IL METODO RESTITUISCE LA CORRISPONDENTE LOCAZIONE PER POTER ISTANZIARE IL TRANSPORTOBJ CON LA CORRETTA LOCAZIONE D'ARRIVO ###
		TransportObj oggetto = (TransportObj) costruttore.newInstance(ID, nome, descrizione, loc1);

		oggetti.add(oggetto);	// ### AGGIUNGE IL TRANSPORTOBJ APPENA ISTANZIATO ALL'APPOSITO ARRAYLIST ###
	}

	
	/**
	 * Esegue il parsing degli Oggetti della classe KeyObj.
	 * @param ID
	 * @param nome
	 * @param descrizione
	 * @param apertura
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	private void keyObjParse(String ID, String nome, String descrizione, String apertura) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, ClassNotFoundException
	{
		
		/* ### ISTANZIA IL KEYOBJ TRAMITE REFLECTION ### */
		
		Class<?> c = Class.forName("com.avventuragrafica.Oggetti." + nome);
		Class<? extends KeyObj> keyobj = c.asSubclass(KeyObj.class);
		Constructor<? extends KeyObj> costruttore = keyobj.getConstructor(String.class, String.class, String.class, Apertura.class);
		Apertura aprt = getApertura(apertura);	// ### DATA LA STRINGA APRT1 IL METODO RESTITUISCE LA CORRISPONDENTE APERTURA PER POTER ISTANZIARE IL KEYOBJ CON LA CORRETTA APERTURA ASSOCIATA ###
		KeyObj oggetto = (KeyObj) costruttore.newInstance(ID, nome, descrizione, aprt);

		oggetti.add(oggetto);	// ### AGGIUNGE IL KEYOBJ APPENA ISTANZIATO ALL'APPOSITO ARRAYLIST ###

		aprt.lock(); // ### BLOCCA L'APERTURA ASSOCIATA AL KEYOBJ ISTANZIATO ###
	}

	/**
	 * Esegue il parsing degli Oggetti della classe ContainerObj.
	 * @param tmp
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void containerObjParse(String[] tmp) throws ClassNotFoundException,	SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{

		String ID = tmp[0];
		String name = tmp[1];
		String descr = tmp[2];

		// ### ISTANZIA IL CONTAINEROBJ TRAMITE REFLECTION ###

		Class<?> c = Class.forName("com.avventuragrafica.Oggetti." + name);
		Class<? extends ContainerObj> containerobj = c.asSubclass(ContainerObj.class); Constructor<? extends ContainerObj> costruttore = containerobj.getConstructor(String.class, String.class, String.class); ContainerObj contenitore = costruttore.newInstance(ID, name, descr);
		
		// ### CONTROLLA L'EVENTUALE CONTENUTO DEL CONTAINEROBJ ISTANZIATO ###

		if (tmp.length <= 3)	// ### CONTROLLO SUL NUMERO DI CAMPI RICEVUTI IN FASE DI PARSING: SE MINORE OD UGUALE DI 3 IL CONTAINEROBJ E' VUOTO ###
		{
			oggetti.add(contenitore);	// ### AGGIUNGE IL CONTAINEROBJ APPENA ISTANZIATO ALL'APPOSITO ARRAYLIST ###
			return;
		}
		else
		{
			
			/* ### SE IL RISULTATO DEL PRECEDENTE CONTROLLO E' MAGGIORE DI TRE RICERCA ED AGGIUNGE IL CONTENUTO AL CONTAINEROBJ ISTANZIATO ### */
		
			for (int i = 3; i < tmp.length; i++)
			{
				if (tmp[i].charAt(0) == '#')	// ### CONTROLLA IL CARATTERE INIZIALE DELLA STRINGA PER IGNORARE I COMMENTI (CONTRASSEGNATI DAL SIMBOLO #) ###
					break;

				/* ### AGGIUNGE NELL'ARRAYLIST INVENTARIO DEL CONTAINEROBJ GLI OGGETTI CHE NE COSTITUISCONO IL CONTENUTO PER POI ELIMINARLI DALL'ARRAYLIST DEGLI OGGETTI ### */
				
				Oggetto contenuto = getOggetto(tmp[i]);
				contenitore.addObj(contenuto);
				oggetti.remove(contenuto);
			}

			oggetti.add(contenitore); // ### AGGIUNGE IL CONTAINEROBJ ISTANZIATO ALL'APPOSITO ARRAYLIST ###
		}
	}

	/**
	 * Esegue il parsing dell'inventario dei personaggi.
	 */
	private void inventarioPg()
	{
		do
		{
			String str = scanner.nextLine();
			if (str.isEmpty())
				break;
			
			String[] tmp = str.split("\t");
			Oggetto ogg = getOggetto(tmp[0]);	// ### DATA LA STRINGA OGG RESTITUISCE L'OGGETTO CORRISPONDENTE PER POTERLO AGGIUNGERE ALL'INVENTARIO DELL'OPPORTUNO PERSONAGGIO ###
			
			if (ogg != null)	// ### CONTROLLA SE L'OGGETTO DA AGGIUNGERE SIA STATO O MENO ISTANZIATO ###
			{
				Personaggio pg = getPersonaggio(tmp[1]);	// ### DATA LA STRINGA PG RESTITUISCE IL PERSONAGGIO CORRISPONDENTE PER POTER AGGIUNGERE L'OGGETTO ALL'INVENTARIO DEL PERSONAGGIO CORRETTO ###
				pg.addObj(ogg);
			}
		}
		while (scanner.hasNext());
	}

	/**
	 * Posiziona gli oggetti all'interno dell'inventario della locazione appropriata.
	 */
	private void locazioneOggetti()
	{
		do
		{
			String str = scanner.nextLine();
			if (str.isEmpty())
				break;

			String[] tmp = str.split("\t");
			Oggetto ogg = getOggetto(tmp[0]);	// ### DATA LA STRINGA OGG RESTITUISCE L'OGGETTO CORRISPONDENTE PER POTERLO AGGIUNGERE ALL'INVENTARIO DELL'OPPORTUNA LOCAZIONE ###
			if (ogg != null)	// ### CONTROLLA SE L'OGGETTO DA AGGIUNGERE SIA STATO O MENO ISTANZIATO ###
			{
				Locazione loc = getLocazione(tmp[1]);	// ### DATA LA STRINGA LOC RESTITUISCE LA LOCAZIONE CORRISPONDENTE PER POTER AGGIUNGERE L'OGGETTO ALL'INVENTARIO DELLA LOCAZIONE CORRETTA ###
				loc.addObj(ogg);
			}
		}
		while (scanner.hasNext());
	}

	/**
	 * Imposta il proprietatio di un oggetto, qualora ne abbia uno.
	 */
	private void proprietari()
	{
		do
		{
			String str = scanner.nextLine();
			if (str.isEmpty())
				break;

			String[] tmp = str.split("\t");
			Proprietario ogg = (Proprietario) getOggetto(tmp[0]);	// ### DATA LA STRINGA OGG RESTITUISCE L'OGGETTO CORRISPONDENTE E NE EFFETTUA UN CAST A "PROPRIETARIO" PER PERMETTERGLI DI UTILIZZARE I METODI DI TALE INTERFACCIA ###
			if (ogg != null)	// ### CONTROLLA SE L'OGGETTO A CUI CAMBIARE PROPRIETARIO SIA STATO O MENO ISTANZIATO ###
			{
				Personaggio proprietario = getPersonaggio(tmp[1]);	// ### DATA LA STRINGA PROPRIETARIO RESTITUISCE IL PERSONAGGIO CORRISPONDENTE PER POTER IMPOSTARE AL PROPRIETARIO CORRETTO L'APPOSITO ATTRIBUTO DELL'OGGETTO ###
				String nomeProprietario = proprietario.getNome();
				ogg.cambiaProprietario(nomeProprietario);	// ### CAMBIA L'ATTRIBUTO "PROPRIETARIO" DELL'OGGETTO INVOCANDO IL METODO IMPLEMENTATO DALL'APPOSITA INTERFACCIA ###
			}
		}
		while(scanner.hasNext());
	}

	/**
	 * Data una stringa contentente l'ID di una locazione  ha come valore di ritorno la locazione corrispondente.
	 * @param locazione
	 * @return
	 */
	private Locazione getLocazione(String locazione)
	{
		
		/* ### SCORRE L'ARRAYLIST DELLE LOCAZIONI ISTANZIATE CONFRONTANDO L'ID DI CIASCUNA CON QUELLO RICEVUTO IN ARGOMENTO FINCHE' NON TROVA QUELLA CORRISPONDENTE ### */
		
		for (Locazione i : locazioni)
		{
			if (i.idLoc().equals(locazione))
				return i;
		}
		return null;
	}

	/**
	 * Data una stringa contentente l'ID di un'apertura ha come valore di ritorno l'apertura corrispondente.
	 * @param apertura
	 * @return
	 */
	private Apertura getApertura(String apertura)
	{
		
		/* ### SCORRE L'ARRAYLIST DELLE APERTURA ISTANZIATE CONFRONTANDO L'ID DI CIASCUNA CON QUELLO RICEVUTO IN ARGOMENTO FINCHE' NON TROVA QUELLA CORRISPONDENTE ### */
		
		for (Apertura i : aperture)
		{
			if (i.getId().equals(apertura))
				return i;
		}
		return null;
	}

	/**
	 * Data una stringa contenente l'ID di un personaggio  ha come valore di ritorno il personaggio corrispondente.
	 * @param personaggio
	 * @return
	 */
	private Personaggio getPersonaggio(String personaggio)
	{
		
		/* ### SCORRE L'ARRAYLIST DEI PERSONAGGI ISTANZIATI CONFRONTANDO L'ID DI CIASCUNO CON QUELLO RICEVUTO IN ARGOMENTO FINCHE' NON TROVA QUELLO CORRISPONDENTE ### */
		
		for (Personaggio i : personaggi)
		{
			if (i.getID().equals(personaggio))
				return i;
		}
		return null;
	}

	/**
	 * Data una stringa contenente l'ID di un oggetto  ha come valore di ritorno l'oggetto corrispondente.
	 * @param oggetto
	 * @return
	 */
	private Oggetto getOggetto(String oggetto)
	{
		
		/* ### SCORRE L'ARRAYLIST DEGLI OGGETTI ISTANZIATI CONFRONTANDO L'ID DI CIASCUNO CON QUELLO RICEVUTO IN ARGOMENTO FINCHE' NON TROVA QUELLO CORRISPONDENTE ### */
		
		for (Oggetto i : oggetti)
		{
			if (i.getId().equals(oggetto))
				return i;
		}
		return null;
	}

	/**
	 * Controlla se l'oggetto di cui si sta tentando di fare il parsing fa parte della lista degli oggetti istanziabili confrontandone il nome con una preesistente lista di costanti.
	 * @param obj
	 * @return
	 */
	private boolean instObj(String obj)
	{
		Const oggetti[] = Const.values();
		for (Const c : oggetti)
		{
			if (obj.equals(c.toString()))
				return true;
		}
		return false;
	}

	/**
	 *	Aggiunge le Aperture all'inventario delle corrispondenti locazioni di partenza e di arrivo.
	 */
	public void addApertureLocazioni()
	{
		for (Apertura apr : aperture)
		{
			Locazione loc1 = apr.locPartenza();
			Locazione loc2 = apr.locArrivo();
			loc1.addObj(apr);
			loc2.addObj(apr);
		}
	}
	
	/**
	 * Ha come valore di ritorno l'ArrayList dei personaggi istanziati.
	 * @return
	 */
	public ArrayList<Personaggio> getPersonaggi()	{return personaggi;}

	/**
	 * Ha  come valore di ritorno l'ArrayList delle locazioni istanziate.
	 * @return
	 */
	public ArrayList<Locazione> getLocazioni()	{return locazioni;}
}