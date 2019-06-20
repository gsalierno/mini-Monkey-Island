package com.avventuragrafica.motoreGrafico;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.avventuragrafica.Oggetti.Oggetto;

/**
 * Definisce il tipo OggettoGrafico esteso da JButton.
 * @author Giulio Salierno
 *
 */
public class OggettoGrafico extends JButton implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private Oggetto oggetto ;		//oggetto di cui fare il wrap
	
	
	public OggettoGrafico(Oggetto oggetto)
	{
		
		super(oggetto.toString());//utilizziamo il costruttore della superclasse creando un jbutton con il nome e la descrizione dell'oggetto
		this.oggetto = oggetto;
		this.setIcon(new ImageIcon(ClassLoader.getSystemResource("com.avventuragrafica.images/"+oggetto.getNome()+".jpg")));// utilizziamo  il nome dell'oggetto in modo da associarne l'immagine appropriata
		this.setForeground(Color.BLACK);
		this.setPreferredSize(new Dimension(115,133));
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(TOP);
		this.setVerticalTextPosition(BOTTOM);
		this.setHorizontalTextPosition(CENTER);
		addMouseListener(this);
		
	}
	


	@Override
	public  void mouseClicked(MouseEvent arg0)
	{
		
		MainGui.addObjToProcess(oggetto);

		
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
