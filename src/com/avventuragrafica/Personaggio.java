package com.avventuragrafica;

import java.io.Serializable;
import java.util.ArrayList;

import com.avventuragrafica.Oggetti.KeyObj;
import com.avventuragrafica.Oggetti.Oggetto;
import com.avventuragrafica.Oggetti.TransportObj;
/**
 * Classe delegata di definire il tipo Personaggio , utile per istanziare personaggi non giocanti.
 * @author Giulio Salierno
 *
 */
public class Personaggio implements Serializable
{
	private static final long serialVersionUID = 1413098937727631606L;

	protected String name;
	protected ArrayList<Oggetto> inventario;
	protected ArrayList<Evento> memoria;
	protected final String ID;
	protected Locazione loc;
	protected MemorizzaEventi memorizza;

	public Personaggio(String ID, String name, Locazione loc)
	{
		this.name = name;
		this.loc = loc;
		this.ID = ID;
		inventario = new ArrayList<Oggetto>();
		memoria = new ArrayList<Evento>();
		memorizza = new MemorizzaEventi();
	}

	public String toString()	{return name;}

	/**
	 * Ritorna l'ID del personaggio.
	 * @return
	 */
	public String getID()	{return ID;}

	/**
	 * Ritorna il nome del personaggio.
	 * @return
	 */
	public String getNome()	{return name;}

	/**
	 * Ritorna l'ArrayList inventario del personaggio.
	 * @return
	 */
	public ArrayList<Oggetto> getInventario()	{return inventario;}

	/**
	 * Aggiunge l'evento passato in argomento alla memoria eventi del personaggio.
	 * @param a
	 */
	protected void regEvent(Evento a)	{memoria.add(a);}

	/**
	 * Rimuove l'evento passato in argomento dalla memoria eventi del personaggio.
	 * @param a
	 */
	protected void remEvent(Evento a)	{memoria.remove(a);}

	/**
	 * Aggiunge l'oggetto passato in argomento all'ArrayList inventario del personaggio.
	 * @param a
	 */
	public void addObj(Oggetto a)	{inventario.add(a);}

	/**
	 * Rimuove l'oggetto passato in argomento dall'ArrayList inventario del personaggio.
	 * @param a
	 */
	public void removeObj(Oggetto a)	{inventario.remove(a);}

	/**
	 * Stampa nella console del gioco un dialogo che indica il nome del personaggio e il nome della locazione corrente.
	 */
	public void dialogo()	{Motore.write("Ciao il mio nome e' "+name+" e ci troviamo in "+loc.toString());}

	/**
	 * A seconda dell'Integer passato in argomento (che rappresenta la domanda selezionata dall'utente nell'apposito frame) stampa nella console del gioco una risposta appropriata.
	 * @param domanda
	 */
	public void reply(Integer domanda)
	{
		switch(domanda)
		{
		case 1:
			try	{searchOnThis();}	// ### CONTROLLA SE IL PERSONAGGIO AL QUALE SI STA PONENDO LA DOMANDA POSSIEDE O MENO OGGETTI DI TIPO KEYOBJ O TRANSPORTOBJ ###
			catch(ObjectNotFoundException e)	{}
			break;

		case 2:
			try	{searchOnOthers();}	// ### CONTROLLA SE ALTRI PERSONAGGI ALL'INTERNO DELLA LOCAZIONE CORRENTE POSSIEDONO O MENO OGGETTI DI TIPO KEYOBJ O TRANSPORTOBJ ###
			catch (ObjectNotFoundException e)	{}
			break;
		}
	}

	/**
	 * Controlla se il personaggio possiede o meno oggetti di tipo KeyObj o TranportObj e genera un dialogo opportuno a seconda delle informazioni trovate.
	 * @throws ObjectNotFoundException
	 */
	private void searchOnThis() throws ObjectNotFoundException
	{
		ArrayList<Evento> eventiSelezionati = new ArrayList<Evento>();	// ###	ISTANZIA UN ARRAYLIST TEMPORANEO DOVE VERRANNO INSERITI DALLA MEMORIA EVENTI DEL PERSONAGGIO SOLO GLI EVENTI NECESSARI A GENERARE L'OPPORTUNO DIALOGO DI RISPOSTA ###

		for (Evento e : memoria)
		{
			
			/* ### CONTROLLA SE L'EVENTO SELEZIONATO DALLA MEMORIA EVENTI DEL PERSONAGGIO E' DEL TIPO OPPORTUNO A GENERARE IL DIALOGO DI RISPOSTA QUINDI LO AGGIUNGE ALL'ARRAYLIST TEMPORANEO ### */
			
			if (e.getConstant().toString().equals("POSSIEDE") && e.getPersonaggio().equals(this) && (e.getOggetto() instanceof KeyObj || e.getOggetto() instanceof TransportObj))
				eventiSelezionati.add(e);
		}

		/* ### SE NON E' STATO POSSIBILE TROVARE NESSUN EVENTO DEL TIPO CORRETTO ALL'INTERNO DELLA MEMORIA EVENTI DEL PERSONAGGI STAMPA SULLA CONSOLE DI GIOCO UN OPPORTUNO DIALOGO DI RISPOSTA ### */ 
		
		if (eventiSelezionati.isEmpty())
			throw new ObjectNotFoundException(this.getNome()+": Mi dispiace, non ho oggetti che ti possono essere utili.");

		/* ### SE SONO STATI TROVATI EVENTI DEL TIPO CORRETTO PRENDE L'OGGETTO CORRISPONDENTE A CIASCUNO DI QUESTI, LI SPOSTA NELL'INVENTARIO DEL PERSONAGGIO GIOCANTE E GENERA UN OPPORTUNO DIALOGO DI RISPOSTA ### */
		
		else
		{
			String str = "";
			
			for (Evento e : eventiSelezionati)
			{
				str += ""+e.getOggetto().toString()+", ";
				this.dai(e.getOggetto(), Motore.getPg());
			}

			Motore.write(this.getNome()+ ": Questi oggetti possono esserti utili: "+str+" Tieni! Adesso potrai trovarli nel tuo inventario!");
		}
	}

	/**
	 * Controlla se i personaggi presenti nella locazione possiedono o meno oggetti di tipo KeyObj o TransportObj e genera un dialogo opportuno a secondo delle informazione trovate.
	 * @throws ObjectNotFoundException
	 */
	private void searchOnOthers() throws ObjectNotFoundException
	{
		ArrayList<Evento> eventiSelezionati = new ArrayList<Evento>();	// ### ISTANZIA UN ARRAYLIST TEMPORANEO DOVE VERRANNO INSERITI DALLA MEMORIA EVENTI DEI PERSONAGGI SOLO GLI EVENTI NECESSARI A GENERARE L'OPPORTUNO DIALOGO DI RISPOSTA ###
		
		/* ### CONTROLLA SE L'EVENTO SELEZIONATO E' DEL TIPO OPPORTUNO A GENERARE IL DIALOGO DI RISPOSTA QUINDI LO AGGIUNGE ALL'ARRAYLIST TEMPORANEO ### */
		
		for (Evento e : memoria)
		{
			if (e.getConstant().toString().equals("POSSIEDE") && (e.getOggetto() instanceof KeyObj || e.getOggetto() instanceof TransportObj) &&! (e.getPersonaggio().equals(Motore.getPg()) || (e.getPersonaggio().equals(this))))
				eventiSelezionati.add(e);
		}

		/* ### SE NON E' STATO POSSIBILE TROVARE NESSUN EVENTO DEL TIPO CORRETTO STAMPA SULLA CONSOLE DI GIOCO UN OPPORTUNO DIALOGO DI RISPOSTA ### */
		
		if (eventiSelezionati.isEmpty())
			throw new ObjectNotFoundException(this.getNome()+": Mi dispiace, ma all'interno di questa locazione non ci sono personaggi che hanno oggetti che ti potrebbero essere utili! Prova piuttosto a cercare all'interno della locazione...");

		/* ### SE SONO STATI TROVATI EVENTI DEL TIPO CORRETTO PRENDE IL PERSONAGGIO CORRISPONDENTE A CIASCUNO DI QUESTI, NE INSERISCE IL NOME IN UNA STRINGA DOPO AVER CONTROLLATO CHE QUESTA NON LO CONTIENE GIA' E GENERA UN OPPORTUNO DIALOGO DI RISPOSTA ### */
		
		else
		{
			String str = "";

			for (Evento e : eventiSelezionati)
			{
				if (str.contains((CharSequence) e.getPersonaggio().toString()))	{} // Se la stringa contiene giˆ il nome non lo aggiunge

				else
					str += ""+e.getPersonaggio().toString()+", ";
			}
			
			Motore.write(this.getNome()+": Questi personaggi potrebbero avere degli oggetti che possono tornarti utili: "+str+". Prova a parlare con loro!");
		}
	}

	/**
	 * Dopo aver controllato se e' possibile dare ad un altro personaggio l'oggetto passato in argomento lo sposta da un ArrayList inventario all'altro, aggiunge alla memoria dei personaggi presenti nella locazione gli eventi che registrano l'azione compiuta e il nuovo possessore dell'oggetto e ne rimuove l'evento relativo al vecchio possessore.
	 * @param a
	 * @param b
	 */
	public void dai(Oggetto a, Personaggio b)
	{
		if (inventario.contains(a))
		{
			inventario.remove(a);	// ### RIMUOVE L'OGGETTO DALL'INVENTARIO DEL VECCHIO POSSESSORE ###
			b.addObj(a);	// ### AGGIUNGE L'OGGETTO ALL'INVENTARIO DEL NUOVO POSSESSORE ###

			/* ### CREA E REGISTRA NELLA MEMORIA DEI PERSONAGGI PRESENTI NELLA LOCAZIONE GLI EVENTI CHE REGISTRANO L'AZIONE COMPIUTA E IL NUOVO POSSESSORE DELL'OGGETTO ### */
			
			Evento newevent1 = new Evento(b, Const.POSSIEDE, a);
			Evento newevent2 = new Evento(this, Const.DA, a, b);
			memorizza.aggiornaEvento(newevent1, loc.getPersonaggi());
			memorizza.aggiornaEvento(newevent2, loc.getPersonaggi());

			/* ### RIMUOVE DALLA MEMORIA DEI PERSONAGGI PRESENTI NELLA L'OCAZIONE L'EVENTO RELATIVO AL VECCHIO POSSESSORE DELL'OGGETTO ### */
			
			Evento oldevent = new Evento(this, Const.POSSIEDE, a);
			memorizza.rimuoviEvento(oldevent, loc.getPersonaggi());
		}
		else
			Motore.write("Non posso dare "+ a.toString()+" a "+b.getNome()+" poiche' non e' contenuto nel mio inventario! Dovrei prima prenderlo...");
	}
}