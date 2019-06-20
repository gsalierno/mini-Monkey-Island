package com.avventuragrafica;

import java.io.Serializable;
import java.util.ArrayList;

import com.avventuragrafica.Oggetti.Oggetto;

/**
 *  Racchiude un insieme di metodi utili per memorizzare / rimuovere eventi all'interno della memoria dei personaggi presenti all'interno di una locazione.
 * @author Giulio Salierno
 *
 */
public class MemorizzaEventi implements Serializable
{
	private static final long serialVersionUID = -2584502389518007996L;

	/**
	 * Salva nella memoria eventi di ogni personaggio gli oggetti e gli altri personaggi che si trovano all'interno della sua stessa locazione.
	 * @param locazioni
	 */
	public void aggiornaEventoSiTrovaPossiede(ArrayList<Locazione> locazioni)
	{

		/* ### REGISTRA NELLA MEMORIA EVENTI DI OGNI PERSONAGGIO QUALI ALTRI PERSONAGI SI TROVANO ALL'INTERNO DELLA LOCAZIONE ### */

		for (Locazione loc : locazioni)
		{
			for (Personaggio pg : loc.getPersonaggi())
			{
				Evento event = new Evento(pg, Const.SI_TROVA_IN, loc);

				for (Personaggio pg1 : loc.getPersonaggi())
					pg1.regEvent(event);
			}
		}

		/* ### REGISTRA NELLA MEMORIA EVENTI DI OGNI PERSONAGGIO QUALI ALTRI OGGETTI SI TROVANO ALL'INTERNO DELLA LOCAZIONE ### */

		for (Locazione loc : locazioni)
		{
			ArrayList<Oggetto> inventario = loc.getInventario();
			
			for (Oggetto obj : inventario)
			{
				Evento event = new Evento(obj, Const.SI_TROVA_IN, loc);

				for (Personaggio pg1 : loc.getPersonaggi())
					pg1.regEvent(event);
			}
		}

		/* ### REGISTRA NELLA MEMORIA EVENTI DI OGNI PERSONAGGIO QUALI OGGETTI SONO POSSEDUTI DAGLI ALTRI PERSONAGGI PRESENTI ALL'INTERNO DELLA LOCAZIONE ### */

		for (Locazione loc : locazioni)
		{
			for (Personaggio pg : loc.getPersonaggi())
			{
				for (Oggetto ogg : pg.getInventario())
				{
					for (Personaggio pg1 : loc.getPersonaggi())
					{
						Evento evento = new Evento(pg, Const.POSSIEDE, ogg);
						pg1.regEvent(evento);
					}
				}
			}
		}
	}

	/**
	 * Aggiunge l'evento passato in argomento nella memoria eventi di tutti i personaggi presenti nell'ArrayList passato in argomento.
	 * @param e
	 * @param pg
	 */
	public void aggiornaEvento(Evento e, ArrayList<Personaggio> pg)
	{
		for (Personaggio personaggio : pg)
			personaggio.regEvent(e);
	}

	/**
	 * Elimina l'evento passato in argomento dalla memoria eventi di tutti i personaggi presenti nell'ArrayList passato in argomento.
	 * @param e
	 * @param pg
	 */
	public void rimuoviEvento(Evento e, ArrayList<Personaggio> pg)
	{
		for (Personaggio personaggio : pg)
			personaggio.remEvent(e);
	}
}