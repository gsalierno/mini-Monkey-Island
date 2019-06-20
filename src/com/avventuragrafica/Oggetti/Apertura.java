package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Locazione;
import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Usabile;

public abstract class Apertura extends Oggetto implements Usabile

{
	private static final long serialVersionUID = -8187305378009997848L;

	private boolean lock = true;	// Boolean di controllo che stabilisce se l'apertura sia bloccata o meno da una chiave. (True = APERTO / False = CHIUSO)
	private boolean stato = false;	// Boolean di controllo che stabilisce se l'apertura è chiusa o aperta. (True = APERTO / False = CHIUSO)

	protected Locazione loc1;
	protected Locazione loc2;

	public Apertura(String ID, String nome, String descrizione, Locazione loc1, Locazione loc2)
	{
		super(ID, nome, descrizione);
		this.loc1 = loc1;
		this.loc2 = loc2;
	}

	/**
	 * Ritorna l'ID dell'apertura.
	 */
	public String getId()	{return ID;}

	/**
	 * Ritorna la descrizione dell'apertura.
	 * @return
	 */
	public String getDescr()	{return description;}

	abstract public void entra(pgGiocante pg);

	/**
	 * Restituisce un messaggio generico circa l'apertura esaminata.
	 */
	public void esamina()	{Motore.write(description + " chissa' dove conduce...");}

	/**
	 * Stampa un messaggio nella console di gioco che indica che non è possibile utilizzare l'azione USA su nessun tipo di apertura.
	 */
	public boolean usa()
	{
		Motore.write("Non so come usare "+this.getDescr()+"! Piuttosto potrei provare ad aprirla...");
		return true;
	}

	/**
	 * Metodo non utilizzabile.
	 */
	public boolean usa(pgGiocante pg)	{return false;}

	/**
	 * Metodo non utilizzabile.
	 */
	public void usa(Usabile ogg)	{}

	/**
	 * Controlla se l'apertura è bloccata da una chiave o è stata già aperta quindi, se possibile, la apre. 
	 */
	public void apri()
	{
		if (lock)	// ### CONTROLLA SE L'APERTURA E' STATA BLOCCATA IN FASE DI PARSING PERCHE' CORRISPONDENTE AD UN KEYOBJ ###
		{
			if (stato)
				Motore.write(this.toString()+" e' gia' aperta!");
			else
			{
				stato = true;
				Motore.write(this.toString()+" e' adesso aperta!");
			}
		}
		else
			Motore.write(this.getDescr()+" sembrerebbe essere chiusa a chiave...");
	}

	/**
	 * Controlla se l'apertura è bloccata da una chiave o è stata già chiusa quindi, se possibile, la chiude.
	 */
	public void chiudi()
	{
		if (lock)	// ### CONTROLLA SE L'APERTURA E' STATA BLOCCATA IN FASE DI PARSING PERCHE' CORRISPONDENTE AD UN KEYOBJ ###
		{
			if (stato)
			{
				stato = false;
				Motore.write(this.toString()+" e' adesso chiusa!");
			}
			else
				Motore.write(this.toString()+" sembrerebbe essere gia' chiusa!");
		}
		else
			Motore.write(this.toString()+" sembrebbbe essere chiusa a chiave...");
	}

	/**
	 * Blocca l'apertura (invocato in fase di parsing quando vengono istanziati i KeyObj per bloccare le aperture corrispondenti).
	 */
	public void lock()	{lock = false;}

	/**
	 * Sblocca l'apertura.
	 */
	public void unLock() {lock = true;}

	/**
	 * Ritorna la locazione d'arrivo dell'apertura.
	 * @return
	 */
	public Locazione locArrivo()	{return loc2;}
	
	/**
	 * Ritorna la locazione di partenza dell'apertura.
	 * @return
	 */
	public Locazione locPartenza()	{return loc1;}

	/**
	 * Ritorno lo stato dell'apertura (APERTO / CHIUSO).
	 * @return
	 */
	public boolean getStato()	{return stato;}
}