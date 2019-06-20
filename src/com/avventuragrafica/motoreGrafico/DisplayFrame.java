package com.avventuragrafica.motoreGrafico;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.avventuragrafica.Motore;
import com.avventuragrafica.pgGiocante;


/**
 *Classe delegata della visualizzazione dei frame Ausiliari che non vengono visualizzati nel frame principali e per questo ne sono indipendenti vengono azionati solo su scelta dell'utente
 *@author Giulio Salierno
 */

public class DisplayFrame 
{

	private MainGui gui; // riferimento al frame principale nel quale operare
	private BuildPanel buildPanel;
	private Wrap wrapper;
	private pgGiocante pg; // manteniamo un riferimento al pgGiocante

	public DisplayFrame(MainGui frame) 
	{
		this.gui = frame;
		buildPanel = new BuildPanel();
		wrapper = new Wrap(gui);
	}

	/**
	 *Istanzia un internal frame per la selezione di un oggetto con cui interagire nel caso UsaCon
	 */

	public void displayUsaConFrame()
	{
		final JFrame frame = new JFrame();
		pg = Motore.getPg(); // salviamo un riferimento al pgGiocante
		frame.setSize(new Dimension(644, 345));
		frame.setMinimumSize(new Dimension(644, 345));
		frame.setMaximumSize(new Dimension(644, 345));
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanel panel = buildPanel.buildPanel();
		JScrollPane scrollPanel = new JScrollPane(panel);
		scrollPanel.setBackground(Color.BLACK);
		scrollPanel.setBorder(new TitledBorder(new LineBorder(new Color(255,255, 255)), " Scegli Oggetto Con Cui Interagire: ",TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255,255, 255)));
		scrollPanel.setVisible(true);
		scrollPanel.setBounds(6, 6, 632, 245);
		frame.getContentPane().add(scrollPanel);
		buildPanel.addObjectPanel(panel,wrapper.wrap(pg.getInventario()),wrapper.wrap(pg.getLocCurr().getInventario())); // Costruzione del pannello con gli oggetti presenti nell'inventario del personaggio giocante e gli oggetti presenti all'interno della locazione
		panel.revalidate();

		JButton exitButton = new JButton("Usa");
		exitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (MainGui.getObjToProcess().equals(
						MainGui.getObjTmpToProcess().getId()))
				{
					Motore.write("Selezionare un oggetto con cui usare "+ MainGui.getObjToProcess().toString() + " prima di premere il tasto Usa!");

				} else {

					pg.usaCon(MainGui.getObjTmpToProcess(),
							MainGui.getObjToProcess());

					frame.dispose(); // provoca la chiusura del frame
				}

			}
		});

		exitButton.setBounds(278, 267, 93, 22);
		exitButton.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		exitButton.setMaximumSize(new Dimension(93, 22));
		frame.getContentPane().add(exitButton); // aggiungiamo il bottone alla scrollPane da modificare
	}

	/**
	 * Visualizza un frame ausiliario nel quale visualizza i possibili oggetti
	 * da dare al pgSelezionato
	 */

	public void displayDaiFrame() 
	{
		pg = Motore.getPg(); // salviamo un riferimento al pgGiocante

		
		final JFrame frame = new JFrame();
		frame.setSize(new Dimension(644, 345));
		frame.setMinimumSize(new Dimension(644, 345));
		frame.setMaximumSize(new Dimension(644, 345));
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel panel = buildPanel.buildPanel();
		JScrollPane scrollPanel = new JScrollPane(panel);
		scrollPanel.setBackground(Color.BLACK);
		scrollPanel.setBorder(new TitledBorder(new LineBorder(new Color(255,255, 255)), " Scegli Oggetto Con Cui Interagire: ",TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255,255, 255)));
		scrollPanel.setVisible(true);
		scrollPanel.setBounds(6, 6, 632, 245);
		frame.getContentPane().add(scrollPanel);
		buildPanel.addPgPanel(panel,wrapper.wrapPg(pg.getLocCurr().getPersonaggi()));
		panel.revalidate();

		JButton exitButton = new JButton("Dai");
		exitButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if (MainGui.getPgToProcess() == null)
				{
					Motore.write("Selezionare un personaggio a cui dare "+ MainGui.getObjToProcess().toString()+ " prima di premere il tasto Dai!");

				} else 
				{

					pg.dai(MainGui.getObjToProcess(),MainGui.getPgToProcess()); //il personaggio compie l'azione dai con gli oggetti selezionati OGGETTO > PERSONAGGIO
					
					frame.dispose(); // provoca la chiusura del frame

					/// refresh del pannello contenente l'inventario delpersonaggio non-Giocante con cui si è interagito
					gui.displayPanel3(MainGui.getPgToProcess()); 
					gui.displayPanel0(); // refresh del pannello contenente l'inventario del personaggio giocante

				}

			}
		});

		exitButton.setBounds(278, 267, 93, 22);
		exitButton.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		exitButton.setMaximumSize(new Dimension(93, 22));
		frame.getContentPane().add(exitButton);

	}

	/**
	 * Visualizza un frame ausiliario nel quale visualizza le domande da porre
	 * al pgNonGiocante selezionato
	 */
	public void displayDomandaFrame()
	{
		pg = Motore.getPg(); // salviamo un riferimento al pgGiocante
		
		final JFrame frame = new JFrame();
		frame.setSize(new Dimension(668, 130));
		frame.setMinimumSize(new Dimension(656, 130));
		frame.setMaximumSize(new Dimension(656, 130));
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBounds(3, 3, 656, 89);
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), " Scegli Domanda Da Porre a "+MainGui.getPgToProcess().toString(),TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255,255, 255)));
		frame.getContentPane().add(panel);

		JButton domanda_1 = new JButton("Chiedi se possiede oggetti utili");
		domanda_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{	
				
				pg.domanda(MainGui.getPgToProcess(), 1); // invochiamo il metodo domanda con argomento il pgNonGiocante selezionato e la domanda
				frame.dispose();
				gui.displayPanel0(); // refresh del pannello
				gui.displayPanel3(MainGui.getPgToProcess()); // refresh del pannello
				MainGui.addpgToprocess(null); // riponiamo a null il riferimento al pgSelezionato
			}
		});
		domanda_1.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		domanda_1.setBounds(9, 26, 632, 22);
		domanda_1.setPreferredSize(new Dimension(632, 22));
		panel.add(domanda_1);

		JButton domanda_2 = new JButton("Chiedi se altri personaggi in questa locazione possiedono oggetti utili");
		domanda_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Motore.getPg().domanda(MainGui.getPgToProcess(),2);
				frame.dispose();
				MainGui.addpgToprocess(null); // riponiamo a null il riferimento al pgSelezionato

			}
		});

		domanda_2.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		domanda_2.setBounds(9, 54, 632, 22);
		domanda_2.setPreferredSize(new Dimension(632, 22));
		panel.add(domanda_2);

	}
	
	/**
	 * Metodo che istanzia un frame ausiliario per indicare la fine del gioco 
	 */
	public void displayFineFrame()
	{
		final JFrame frame = new JFrame();
		frame.setSize(new Dimension(460, 130));
		frame.setMinimumSize(new Dimension(668, 130));
		frame.setMaximumSize(new Dimension(668, 130));
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		JTextArea text = new JTextArea();
		text.setBounds(110, 6, 656, 40);
		text.setForeground(Color.WHITE);
		text.setBackground(Color.BLACK);
		text.setEditable(false);
		text.setFont(new Font("Modern No. 20", Font.BOLD | Font.ITALIC, 25));
		text.setText("Complimenti! Hai raggiunto la fine del gioco!");
		frame.getContentPane().add(text);
		
		JButton btnContinua = new JButton("Continua");
		btnContinua.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				frame.dispose();
			}
		});
		btnContinua.setBounds(231, 52, 93, 22);
		btnContinua.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnContinua);
		
		JButton btnFine = new JButton("Esci");
		btnFine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				frame.dispose();
				gui.closeGame();
			}
		});
		btnFine.setBounds(344, 52, 93, 22);
		btnFine.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnFine);
		
		
		
	}

}
