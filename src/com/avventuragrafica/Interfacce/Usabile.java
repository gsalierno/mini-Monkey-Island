package com.avventuragrafica.Interfacce;

import com.avventuragrafica.pgGiocante;

public interface Usabile
{
	boolean usa();	
	boolean usa(pgGiocante pg);
	void usa(Usabile  ogg);
}