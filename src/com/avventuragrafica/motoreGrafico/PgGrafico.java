package com.avventuragrafica.motoreGrafico;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import com.avventuragrafica.Personaggio;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Oggetti.Oggetto;


/**
 * Definisce il tipo PgGrafico
 */
public class PgGrafico extends JButton implements MouseListener
{
	
	private static final long serialVersionUID = 1L;
	private Personaggio pg ; // riferimento al personaggio di cui fare il wrap
	private MainGui frame; // riferimento al frame principale
	
	public PgGrafico(MainGui frame,Personaggio pg)
	{
		super(pg.getNome());//utilizziamo il costruttore della superclasse creando un jbutton con il nome del personaggio
		this.pg = pg;
		this.frame = frame;
		this.setIcon(new ImageIcon(ClassLoader.getSystemResource("com.avventuragrafica.images/Anonymous.jpg"))) ; // utilizziamo  il nome dell'oggetto in modo da associarne l'immagine appropriata
		this.setForeground(Color.BLACK);
		this.setPreferredSize(new Dimension(115,133));
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(TOP);
		this.setVerticalTextPosition(BOTTOM);
		this.setHorizontalTextPosition(CENTER);
		
		if(pg instanceof pgGiocante)
			this.setVisible(false);
		
		addMouseListener(this);
		
	}
	
	
	public ArrayList<Oggetto> inventario() { return pg.getInventario();} // ha come valore di ritorno il tipo primitivo del suo inventario



	@Override
	public void mouseClicked(MouseEvent arg0)
	{

		MainGui.addpgToprocess(pg);
		frame.displayPanel3(pg); // visualizza l'inventario del personaggio non giocante selezionato

		
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
