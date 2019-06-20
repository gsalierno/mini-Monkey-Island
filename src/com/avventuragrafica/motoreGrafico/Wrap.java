package com.avventuragrafica.motoreGrafico;

import java.util.ArrayList;

import com.avventuragrafica.Personaggio;
import com.avventuragrafica.Oggetti.Oggetto;


														
/**
 * Classe delegata di eseguire il wrap da oggetto a oggetto grafico e da personaggio a personaggio grafico 
 */

public class Wrap 
{
private ArrayList<OggettoGrafico> oggettiGrafici;
private ArrayList<PgGrafico> personaggiGrafici;	
private MainGui frame; // riferimento alla mainGui nella quale operare utile per poi richiamare il metodo appropriato del frame nel pgGiocante


		public Wrap(MainGui frame)
		{
			this.frame = frame;
	
		}
	
	
	/**
	 * Esegue il wrap di un arrayList di Oggetti e ha come valore di ritorno un ArrayList di OggettiGrafici.
	 * @param oggetti
	 * @return
	 */
	public   ArrayList<OggettoGrafico> wrap(ArrayList<Oggetto> oggetti)
	{
		oggettiGrafici = new ArrayList<OggettoGrafico>();
		
		for(Oggetto ogg : oggetti)
		{
			
			OggettoGrafico oggetto = new OggettoGrafico(ogg);
			
			oggettiGrafici.add(oggetto);
		
	}
		
	
		
	return oggettiGrafici;

}
	
	
	/**
	 * Esegue il wrap di un arrayList di Personaggi e ha come valore di ritorno un ArrayList di pgGrafici.
	 * @param personaggi
	 * @return
	 */
	public ArrayList<PgGrafico> wrapPg(ArrayList<Personaggio> personaggi)
	{
		personaggiGrafici = new ArrayList<PgGrafico>();
		
		for(Personaggio pg : personaggi)
		{
			PgGrafico personaggio = new PgGrafico(frame,pg);
			
			personaggiGrafici.add(personaggio);
		}
		
		return personaggiGrafici;
		
	}
	
}
