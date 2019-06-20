package com.avventuragrafica.motoreGrafico;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Classe delegata di istanziare JPanel da rappresentare nel frame principale
 * @author Giulio Salierno
 *
 */

public class BuildPanel 
{
	private JPanel panel;
	
	
	// metodo che ha come valore di ritorno un Panel inizialmente vuoto
	
	public JPanel buildPanel() 
	{ 
		
		/* ISTANZIA IL JPANEL */
		
		 panel = new JPanel();
		 panel.setBackground(Color.BLACK);
		 panel.setPreferredSize(new Dimension(632,0));
		 
		 return panel;
		
	}
	
	
	
	
	/* AGGIUNGE GLI OGGETTI AL PANNELLO */
	
	
	public void addObjectPanel(JPanel panel,ArrayList<OggettoGrafico> oggetti) // aggiunge oggetti al panel in modo dinamico
	{

		 Integer numrighe = 0 ; 
		 Integer panelSize = 0;
		 
		 Integer div = oggetti.size() / 5 ; // 5 numero di colonne
		 
		 Integer resto = oggetti.size() % 5 ;
		 
		 if(resto == 0) numrighe = div;
		 
		 else numrighe = div + 1 ;
		 
		 panelSize = numrighe * 138; // 140 altezza di ogni riga
		 
		 panel.setPreferredSize(new Dimension(632,panelSize));
		
		
		for(OggettoGrafico oggetto : oggetti)
			panel.add(oggetto);
		
		
	}
	
	//Overloading
	public void addObjectPanel(JPanel panel, ArrayList<OggettoGrafico> oggetti1, ArrayList<OggettoGrafico> oggetti2) // aggiunge oggetti al panel in modo dinamico (Overloading per il Frame ausiliario necessario al metodo UsaCon)
	{

		 Integer numrighe = 0 ; 
		 Integer panelSize = 0;
		 
		 Integer div = (oggetti1.size()+oggetti2.size()) / 5 ; // 5 numero di colonne
		 
		 Integer resto = (oggetti1.size()+oggetti2.size()) % 5 ;
		 
		 if(resto == 0) numrighe = div;
		 
		 else numrighe = div + 1 ;
		 
		 panelSize = numrighe * 138; // 140 altezza di ogni riga
		 
		 panel.setPreferredSize(new Dimension(632,panelSize));
		
		
		for(OggettoGrafico oggetto : oggetti1)
			panel.add(oggetto);
			
		
		for(OggettoGrafico oggetto : oggetti2)
			panel.add(oggetto);
		
	}
	
	
	/*  AGGIUNGE I PERSONAGGI AL PANNELLO */
	
	public void addPgPanel(JPanel panel,ArrayList<PgGrafico> personaggi) // aggiunge oggetti al panel in modo dinamico
	{

		 Integer numrighe = 0 ; 
		 Integer panelSize = 0;
		 
		 Integer div = personaggi.size() / 5 ; // 5 numero di colonne
		 
		 Integer resto = personaggi.size() % 5 ;
		 
		 if(resto == 0) numrighe = div;
		 
		 else numrighe = div + 1 ;
		 
		 panelSize = numrighe * 138; // 140 altezza di ogni riga
		 
		 panel.setPreferredSize(new Dimension(632,panelSize));
		
		
		for(PgGrafico pg : personaggi)
			panel.add(pg);
		
		
	}
	
	

	
	
	
	
}
