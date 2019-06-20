package com.avventuragrafica.Oggetti;

import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Interfacce.Usabile;


public class Grog extends SimpleObj
{
	private static final long serialVersionUID = -347844507468394837L;

	public Grog(String ID, String name, String description)	{super(ID, name, description);}

	/**
	 * Cancella interamente la memoria eventi del personaggio giocante.
	 */
	public boolean usa(pgGiocante pg)
	{
		Motore.write ("Il Grog è un miscuglio segreto che contiene una o più di queste sostanze: Cherosene, glicol propilene, dolcificanti artificiali, acido solforico, rum, acetone, colorante rosso n.2, detriti, grasso per motore, acido per la batteria e/o peperone. CIN! CIN!");
		pg.perdiMemoria();
		
		return true;
	}

	/**
	 * Metodo non utilizzabile.
	 */
	@Override
	public boolean usa()	{return false;}

	/**
	 * Metodo non utilizzabile
	 */
	@Override
	public void usa(Usabile ogg)	{}
}