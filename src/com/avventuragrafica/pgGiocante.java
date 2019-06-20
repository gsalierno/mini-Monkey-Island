package com.avventuragrafica;

import com.avventuragrafica.Interfacce.Accendibile;
import com.avventuragrafica.Interfacce.Usabile;
import com.avventuragrafica.Oggetti.Apertura;
import com.avventuragrafica.Oggetti.Bottone;
import com.avventuragrafica.Oggetti.ContainerObj;
import com.avventuragrafica.Oggetti.KeyObj;
import com.avventuragrafica.Oggetti.Leva;
import com.avventuragrafica.Oggetti.Oggetto;
import com.avventuragrafica.Oggetti.ReadObj;
import com.avventuragrafica.Oggetti.SimpleObj;


/**
 * Classe estesa da personaggio utile per istanziare il protagonista del gioco.
 * @author Julio
 *
 */
public class pgGiocante extends Personaggio
{
	private static final long serialVersionUID = 7659119177313376295L;

	public pgGiocante(String ID, String name, Locazione loc)
	{
		super(ID, name, loc);
	
	}

	
	
																			/* Azioni del personaggio */
	
	/**
	 * Sposta l'oggetto passato in argomento dalla sua locazione originaria all'inventario del personaggio giocante, aggiunge alla memoria dei personaggi presenti l'evento relativo al nuovo possessore e ne elimina quello relativo alla sua precedente locazione
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void prendi(Oggetto a) throws IllegalOperationException
	{
		if (loc.getInventario().contains(a))
		{
			if (a instanceof ContainerObj || a instanceof KeyObj || a instanceof ReadObj || a instanceof SimpleObj)
			{
				
				/* ### AGGIUNGE ALLA MEMORIA DEI PERSONAGGI PRESENTI L'EVENTO RELATIVO AL NUOVO POSSESSORE ### */
				
				Evento evento = new Evento(this, Const.POSSIEDE, a);
				memorizza.aggiornaEvento(evento, loc.getPersonaggi());
				
				this.addObj(a);	// ### AGGIUNGE L'OGGETTO ALL'INVENTARIO DEL PERSONAGGIO GIOCANTE ###
				
				/* ### ELIMINA DALLA MEMORIA DEI PERSONAGGI PRESENTI L'EVENTO RELATIVO AL NUOVO POSSESSORE ### */
				
				Evento oldevent = new Evento(this, Const.SI_TROVA_IN, loc);
				memorizza.rimuoviEvento(oldevent, loc.getPersonaggi());

				loc.removeObj(a);	// ### RIMUOVE L'OGGETTO DALL'INVENTARIO DELLA LOCAZIONE ### */
			}
			else
				throw new IllegalOperationException("Non posso prendere "+a.toString());
		}
		else
			Motore.write("Non posso prendere "+a.toString()+" poiche' si trova nell'inventario di un altro personaggio! Potrei piuttosto chiederglielo...");
	}

	/**
	 * Stampa sulla console di gioco un dialogo contenente alcune infomrazioni sul personaggio passato in argomento e registra nella memoria dei personaggi presenti un evento relativo al compimento dell'azione.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void parlaCon(Personaggio a) throws IllegalOperationException
	{
		if (a instanceof Personaggio)
		{
			
			/* ### REGISTRA NNELLA MEMORIA EVENTI DEI PERSONAGGI PRESENTI UN EVENTO RELATIVO AL COMPIMENTO DELL'AZIONE ### */
			
			Evento newevent = new Evento(this, Const.PARLA_CON, a);
			memorizza.aggiornaEvento(newevent, loc.getPersonaggi());

			a.dialogo(); //	### STAMPA SULLA CONSOLE DI GIOCO LA RISPOSTA DEL PERSONAGGIO PASSATO IN ARGOMENTO ###

		}
		else
			throw new IllegalOperationException("Non posso parlare con: "+a.toString());
	}

	/**
	 * Se l'oggetto passato in argomento è un ContainerObj visualizza il suo contenuto sulla console di gioco e sposta gli oggetti che lo compongono fuori dallo stesso per permettere l'interazione con essi.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void cerca(Oggetto a) throws IllegalOperationException
	{
		if (a instanceof ContainerObj)
		{
			((ContainerObj) a).printContenuto();	// ### STAMPA SULLA CONSOLE DI GIOCO IL CONTENUTO DEL CONTAINEROBJ ###

			/* ### CONTROLLA LA POSIZIONE DEL CONTAINEROBJ PER POTER AGGIUNGERE IL SUO CONTENUTO ALL'INVENTARIO OPPORTUNO E GENERARE NELLA MEMORIA DEI PERSONAGGI PRESENTI GLI EVENTI CORRETTI ### */

			if (loc.getInventario().contains(a))	// ### CONTROLLA SE IL CONTAINEROBJ SI TROVA NELL'INVENTARIO DELLA LOCAZIONE CORRENTE  ###
			{
				for (Oggetto ogg : ((ContainerObj) a).getContenuto())
				{	
					loc.addObj(ogg);	// ### AGGIUNGE GLI OGGETTI CHE COMPONGONO IL CONTENUTO DEL CONTAINEROBJ ALL'INVENTARIO DELLA LOCAZIONE ###

					/* ### ELIMINA DALLA MEMORIA DEI PERSONAGGI PRESENTI L'EVENTO RELATIVO ALLA POSIZIONE DEL CONTAINEROBJ POICHE' SARA' ELIMINATO AL TERMINE DELL'OPERAZIONE ### */
					
					Evento oldevent = new Evento(a, Const.SI_TROVA_IN, loc);
					memorizza.rimuoviEvento(oldevent, loc.getPersonaggi());

					/* ### AGGIUNGE ALLA MEMORIA EVENTI DEI PERSONAGGI PRESENTI GLI EVENTI RELATIVI ALLA NUOVA LOCAZIONE DEGLI OGGETTI CHE COSTITUIVANO IL CONTENUTO DEL CONTAINEROBJ ### */
					
					Evento newevent = new Evento(ogg, Const.SI_TROVA_IN, loc);
					memorizza.aggiornaEvento(newevent, loc.getPersonaggi());
				}
				loc.removeObj(a);	// ### RIMUOVE IL CONTAINEROBJ DALL'INVENTARIO DELLA LOCAZIONE ###
			}
			else
			{
				for (Personaggio pg : loc.getPersonaggi())	// ### SE IL CONTAINEROBJ NON SI TROVA NELL'INVENTARIO DELLA LOCAZIONE CORRENTE CONTROLLA QUALE PERSONAGGIO LO POSSIEDE ###
				{
					if (pg.getInventario().contains(a))
					{
						for (Oggetto ogg : ((ContainerObj) a).getContenuto())
						{
							pg.addObj(ogg);	// ### AGGIUNGE GLI OGGETTI CHE COSTITUISCONO IL CONTENUTO DEL CONTAINEROBJ ALL'INVENTARIO DEL PERSONAGGIO CHE LO POSSEDEVA ###

							/* ### RIMUOVE DALLA MEMORIA DEI PERSONAGGI PRESENTI L'EVENTO RELATIVO AL POSSESSORE DEL CONTAINEROBJ POICHE' SARA' ELIMINATO AL TERMINE DELL'OPERAZIONE ### */
							
							Evento oldevent = new Evento(pg, Const.POSSIEDE, a);
							memorizza.rimuoviEvento(oldevent, loc.getPersonaggi());

							/* ### AGGIUNGE ALLA MEMORIA DEI PERSONAGGI PRESENTI GLI EVENTI RELATIVI AL NUOVO POSSESSORE DEGLI OGGETTI CHE COSTITUIVANO IL CONTENUTO DEL CONTAINEROBJ ### */
							
							Evento newevent = new Evento(pg, Const.POSSIEDE, ogg);
							memorizza.aggiornaEvento(newevent, loc.getPersonaggi());
						}
						pg.removeObj(a);	// ### RIMUOVE IL CONTAINEROBJ DALL'INVENTARIO DEL PERSONAGGIO CHE LO POSSEDEVA ###
					}
				}
			}
		}
		else
			throw new IllegalOperationException("Non posso cercare in "+a.toString());
	}

	/**
	 * Svuota completamente la memoria eventi del personaggio.
	 */
	public void perdiMemoria()	{memoria.removeAll(memoria);}

	/**
	 * Restituisce la descrizione dell'oggetto passato in argomento.
	 * @param a
	 */
	public void esamina(Oggetto a)	{a.esamina();}

	/**
	 * Cambia la locazione del personaggio registrando nella memoria eventi dei personaggi presenti nella locazione da cui esce l'evento ESCE_DA e in quella dei personaggi presenti nella locazione in cui entro l'evento SI_TROVA_IN.
	 * @param newloc
	 */
	public void cambiaLocazione(Locazione newloc)
	{
		
		/* ### AGGIUNGE ALLA MEMORIA DEI PERSONAGGI PRESENTI NELLA LOCAZIONE DA CUI ESCE L'EVENTO RELATIVO ALL'AZIONE DI USCITA ### */
		
		Evento event = new Evento(this, Const.ESCE_DA, loc);
		memorizza.aggiornaEvento(event, loc.getPersonaggi());

		/* ### ELIMINA DALLA MEMORIA DEI PERSONAGGI PRESENTI NELLA LOCAZIONE DA CUI ESCE L'EVENTO SI_TROVA_IN ### */
		
		Evento oldevent = new Evento(this, Const.SI_TROVA_IN, loc);
		memorizza.rimuoviEvento(oldevent, loc.getPersonaggi());

		loc.remPg(this);	// ### RIMUOVE IL PERSONAGGIO DALL'ARRAYLIST DEI PERSONAGGI PRESENTI NELLA LOCAZIONE DA CUI STA USCENDO ###

		loc = newloc;	// ### AGGIORNA L'ATTRIBUTO LOCAZIONE DEL PERSONAGGIO ###

		loc.addPg(this);	// ### AGGIUNGE IL PERSONAGGIO ALL'ARRAYLIST DEI PERSONAGGI PRESENTI NELLA LOCAZIONE IN CUI STA ENTRANDO ###

		/* ### AGGIUNGE ALLA MEMORIA DEI PERSONAGGI PRESENTI NELLA LOCAZIONE IN CUI STA ENTRANDO L'EVENTO SI_TROVA_IN ### */
		
		Evento newevent = new Evento(this, Const.SI_TROVA_IN, loc);
		memorizza.aggiornaEvento(newevent, loc.getPersonaggi());
	}

	/**
	 * Tenta sull'oggetto passato in argomento l'esecuzione di due dei tre diversi tipi di azione "USA" che è possibile compiere su un oggetto (usa da solo e usa su un personaggio) notificando attraverso il valore di ritorno se l'azione è stata completata con successo o se è necessario utilizzare il metodo "USA CON".
	 * @param ogg
	 * @return
	 */
	public boolean usa(Oggetto ogg)
	{
		if (ogg instanceof Oggetto)
		{
			
			/* ### AGGIUNGE ALLA MEMORIA DEI PERSONAGGI PRESENTI NELLA LOCAZIONE L'EVENTO RELATIVO ALL'AZIONE USA ### */
			
			Evento evento = new Evento(this, Const.USA, ogg);
			memorizza.aggiornaEvento(evento, loc.getPersonaggi());

			/* ### VIENE INVOCATO IL METODO USA DA SOLO DELL'OGGETTO IN ARGOMENTO. SE QUESTO HA COME VALORE DI RITORNO TRUE PER INDICARE CHE E' STATO POSSIBILE ESEGUIRLO TERMINA ANCHE QUESTO METODO RITORNANDO TRUE ### */
			
			if (((Usabile) ogg).usa())
				return true;

			/* ### SE IL PRECEDENTE TENTATIVO NON E' ANDATO A BUON FINE, VIENE INVOCATO IL METODO USA CON PERSONAGGIO  IN ARGOMENTO. SE QUESTO HA COME VALORE DI RITORNO TRUE PER INDICARE CHE E' STATO POSSIBILE ESEGUIRLO TERMINA ANCHE QUESTO METODO RITORNANDO TRUE ### */
			
			else if (((Usabile) ogg).usa(this))
				return true;

			/* ### SE NESSUNO DEI PRECEDENTI TENTATIVI E' ANDATO A BUON FINE, TERMINA IL METODO RITORNANDO FALSE PER INDICARE LA NECESSITA' DI INVOCARE IL METODO USA CON ### */
			
			else
				return false;
		}
		else
		{
			Motore.write("Non so come usare " + ogg.toString());
			return true;
		}
	}

	/**
	 * Invocato in caso di fallimento degli altri tipi di azione "USA" che è possibile compiere su un oggetto, permetto di utilizzare il primo oggetto in argomento facendolo interagire con il secondo.
	 * @param ogg1
	 * @param ogg2
	 */
	public void usaCon(Oggetto ogg1, Oggetto ogg2)	{((Usabile) ogg1).usa((Usabile) ogg2);}

	/**
	 * Aggiunge alla memoria dei personaggi presenti un evento relativo all'azione DOMANDA quindi invoca a secondo dell'Integer passato in argomento l'opportuno metodo di risposta del personaggio in argomento.
	 * @param pg
	 * @param domanda
	 */
	public void domanda(Personaggio pg, Integer domanda)
	{
		Evento e = null;
		
		/* ### AGGIUNGE ALLA MEMORIA DEI PERSONAGGI PRESENTI L'EVENTO LEGATO ALL'AZIONE DOMANDA APPROPRIATO A SECONDA DI QUANTO INDICAT DALL'INTEGER PASSATO IN ARGOMENTO ### */
		
		switch (domanda)
		{
		case 1:
			e = new Evento(Const.POSSIEDIOGGETTI);
			break;

		case 2:
			e = new Evento(Const.POSSIEDEOGGETTI);
			break;
		}
		Evento newevent = new Evento(this, Const.DOMANDA, e, pg);
		memorizza.aggiornaEvento(newevent, pg.loc.getPersonaggi());
		
		pg.reply(domanda);	// ### INVOCA IL METODO REPLY DEL PERSONAGGIO IN ARGOMENTO PASSANDO ANCHE L'INTEGER CHE IDENTIFICA IL TIPO DI DOMANDA POSTA ###
	}

	/**
	 * Invoca, se possibile, il metodo spingi dell'oggetto in argomento.
	 * @param ogg
	 * @throws IllegalOperationException
	 */
	public void spingi(Oggetto ogg) throws IllegalOperationException
	{
		if (ogg instanceof Bottone)
			((Bottone) ogg).spingi();

		else
			throw new IllegalOperationException("Non posso spingere "+ogg.toString());
	}

	/**
	 * Invoca, se possibile, il metodo tira dell'oggetto in argomento.
	 * @param ogg
	 * @throws IllegalOperationException
	 */
	public void tira(Oggetto ogg) throws IllegalOperationException
	{
		if (ogg instanceof Leva)
			((Leva) ogg).tira();

		else
			throw new IllegalOperationException("Non posso tirare "+ogg.toString());
	}

	/**
	 * Invoca, se possibile, il metodo entra dell'oggetto in argomento.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void entra(Oggetto a) throws IllegalOperationException
	{
		if (a instanceof Apertura)
			((Apertura) a).entra(this);

		else
			throw new IllegalOperationException("Non posso entrare in "+a.toString());
	}

	/**
	 * Invoca, se possibile, il metodo accendi dell'oggetto in argomento.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void accendi(Oggetto a) throws IllegalOperationException
	{
		if (a instanceof Accendibile)
			((Accendibile) a).accendi();
		else
			throw new IllegalOperationException("Non posso accendere "+a.toString());
	}

	/**
	 * Invoca, se possibile, il metodo spegni dell'oggetto in argomento.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void spegni(Oggetto a) throws IllegalOperationException
	{
		if (a instanceof Accendibile)
			((Accendibile) a).spegni();
		else
			throw new IllegalOperationException("Non posso spegnere "+a.toString());
	}

	/**
	 * Invoca, se possibile, il metodo apri dell'oggetto in argomento.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void apri(Oggetto a) throws IllegalOperationException
	{
		if (a instanceof Apertura)
			((Apertura) a).apri();

		else
			throw new IllegalOperationException("Non posso aprire "+a.toString());
	}

	/**
	 * Invoca, se possibile, il metodo chiudi dell'oggetto in argomento.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void chiudi(Oggetto a) throws IllegalOperationException
	{
		if (a instanceof Apertura)
			((Apertura) a).chiudi();

		else
			throw new IllegalOperationException("Non posso chiudere "+a.toString());
	}

	/**
	 * Invoca, se possibile, il metodo leggi dell'oggetto in argomento.
	 * @param a
	 * @throws IllegalOperationException
	 */
	public void leggi(Oggetto a) throws IllegalOperationException
	{
		if (a instanceof ReadObj)
			((ReadObj) a).usa();

		else
			throw new IllegalOperationException("Non posso leggere "+a.toString());
	}

	/**
	 * Ritorna la locazione corrente del personaggio giocante.
	 * @return
	 */
	public Locazione getLocCurr()	{return loc;}

	public String toString()	{return name;}
}