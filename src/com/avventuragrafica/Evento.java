package com.avventuragrafica;

import java.io.Serializable;

import com.avventuragrafica.Oggetti.Oggetto;

/**
 * Classe delegata di definire il tipo Evento per la memorizzazione delle azioni nelle memorie dei personaggi
 * @author Giulio Salierno
 *
 */

public class Evento implements Serializable
{
	private static final long serialVersionUID = 8557682465127670448L;
	protected Personaggio pg1;
	protected Personaggio pg2;
	protected Const event;
	protected Oggetto ogg;
	protected Locazione loc;
	protected Evento evento1;

	/**
	 * Genera un evento del tipo Personaggio POSSIEDE Oggetto.
	 * @param pg1
	 * @param b
	 * @param c
	 */
	public Evento(Personaggio pg1, Const b, Oggetto c)
	{
		this.pg1 = pg1;
		this.event = b;
		this.ogg = c;
	}

	/**
	 * Genera un evento del tipo Oggetto SI_TROVA_IN Locazione.
	 * @param pg1
	 * @param b
	 * @param c
	 */
	public Evento(Oggetto pg1, Const b, Locazione c)
	{
		this.ogg = pg1;
		this.event = b;
		this.loc = c;
	}

	/**
	 * Genera un evento del tipo Personaggio SI_TROVA_IN Locazione.
	 * @param pg1
	 * @param b
	 * @param c
	 */
	public Evento(Personaggio pg1, Const b, Locazione c)
	{
		this.pg1 = pg1;
		this.event = b;
		this.loc = c;

	}

	/**
	 * Genera un evento del tipo Personaggio DA Oggetto Personaggio.
	 * @param pg1
	 * @param b
	 * @param ogg
	 * @param pg2
	 */
	public Evento(Personaggio pg1, Const b, Oggetto ogg, Personaggio pg2)
	{
		this.pg1 = pg1;
		this.pg2 = pg2;
		this.event = b;
		this.ogg = ogg;
	}

	/**
	 * Genera un evento del tipo Personaggio PARLA_CON Personaggio.
	 * @param pg1
	 * @param b
	 * @param pg2
	 */
	public Evento(Personaggio pg1, Const b, Personaggio pg2)
	{
		this.pg1 = pg1;
		this.event = b;
		this.pg2 = pg2;
	}

	/**
	 * Genera un evento del tipo Personaggio DOMANDA Evento Personaggio.
	 * @param pg1
	 * @param domanda
	 * @param e
	 * @param pg2
	 */
	public Evento(Personaggio pg1, Const domanda, Evento e, Personaggio pg2)
	{
		this.pg1 = pg1;
		this.event = domanda;
		this.pg2 = pg2;
		this.evento1 = e;
	}

	public Evento(Const domanda)
	{
		this.event = domanda;
	}

	/**
	 * Override del metodo equals per poter confrontare due oggetti di tipo
	 * evento
	 */
	@Override
	public boolean equals(Object obj)
	{
		Evento e = null;
		if (obj instanceof Evento)	// ### CONTROLLA SE L'OBJECT PASSATO IN INPUT SIA O MENO UN EVENTO ###
		{
			e = (Evento) obj;
			if (e.getConstant().equals(this.getConstant()))	// ### VERIFICA SE LE COSTANTI DEI DUE EVENTI SONO UGUALI ###
			{
				
				/* ### IN BASE ALLA COSTANTE CHE CARATTERIZZA L'EVENTO ESAMINATO CONTROLLA LE OPPORTUNE RIMANENTI UGUAGLIANZE ### */
				
				switch(e.getConstant())
				{
				case POSSIEDE:
				{
					if (this.getPersonaggio().equals(e.getPersonaggio()) && this.getOggetto().equals(e.getOggetto()))
						return true;

					else
						return false;
				}

				case SI_TROVA_IN:
				{
					
					/* ### CONTROLLA SE IL PRIMO PARAMETRO DELL'EVENTO E' UN PERSONAGGIO O UN OGGETTO PER VERIFICARE SOLO LE OPPORTUNE UGUAGLIANZE ### */
					
					if (this.getPersonaggio() == null)	
					{
						if (this.getOggetto().equals(e.getOggetto()) && this.getLocazione().equals(e.getLocazione()))
							return true;

						else
							return false;
					}
					else
					{
						if (this.getPersonaggio().equals(e.getPersonaggio()) && this.getLocazione().equals(e.getLocazione()))
							return true;

						else
							return false;
					}

				}
				
				default:
					return false;
				}
			} else
				return false;
		} else
			return false;
	}

	/**
	 * Ritorna la costante che caratterizza un evento.
	 * @return
	 */
	public Const getConstant()	{return event;} 

	/**
	 * Ritorna uno dei personaggi che può caratterizzare un evento. (Personaggio che si trova in una locazione, che da' un oggetto ad un altro personaggio, che avvia un dialogo, che pone una domanda...)
	 * @return
	 */
	public Personaggio getPersonaggio()	{return pg1;}

	/**
	 * Restituisce la locazione che può caratterizzare un evento. (Locazione in cui si trova un personaggio, in cui si trova un oggetto...)
	 * @return
	 */
	public Locazione getLocazione() {return loc;}

	/**
	 * Restituisce l'oggetto che peuò caratterizzare un evento. (Oggetto posseduto da un personaggio, che si trova in una locazione, che viene fato da un personaggio ad un altro...)
	 * @return
	 */
	public Oggetto getOggetto()	{return ogg;}
}