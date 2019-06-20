package com.avventuragrafica.motoreGrafico;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JPanel;
import com.avventuragrafica.IllegalOperationException;
import com.avventuragrafica.Locazione;
import com.avventuragrafica.Motore;
import com.avventuragrafica.Personaggio;
import com.avventuragrafica.Serializza;
import com.avventuragrafica.pgGiocante;
import com.avventuragrafica.Oggetti.Oggetto;
/**
 * Classe delegata di istanziare il frame principale nella quale saranno contenute tutte le componenti grafiche del gioco
 * @author Giulio Salierno
 */
public class MainGui
{
	private JFrame frame; // frame principale
	private JTextPane textArea; // textArea che stampa la locazione corrente;
    private BuildPanel buildPanel; // oggetto ausiliario che ci permette di  istanziare panel a seconda del numero di oggetti da visualizzare
    private Wrap wrapper; // oggetto per wrapper da oggetti di tipo "primitivo" a oggetti di tipo grafico
    private Serializza serial; // oggetto per serializzare la partita corrente
    private pgGiocante pg; // salviamo un riferimento al personaggio giocante
    private static Personaggio pgToProcess; // riferimento al personaggio selezionato dall'utente
	private static Oggetto oggToProcess; // riferimento all'oggetto selezionato dall'utente
	private static Oggetto oggToProcessTemp; // riferimento temporaneo nel caso venga invocato un metodo UsaCon che richiede l'interazione tra più oggetti
	private DisplayFrame displayFrame; // oggetto che ci permette di istanziare frame ausiliari

	/* DEFINIZIONE DEI PANNELLI DELLA GUI */

	private JPanel panel0; // pannello che conterrà gli oggetti presenti all'interno dell'inventario del personaggio giocante
	private JPanel panel1; // pannello che conterrà gli oggetti dell'inventario  della locazione corrente
	private JPanel panel2; // pannello che conterrà la lista dei personaggi all'interno della locazione corrente
    private JPanel panel3;// pannello che conterrà gli oggetti del personaggio non giocante selezionato
							

	/**
	 * Richiama un metodo privato della classe per istanziare l'ambiente grafico 
	 */
	public MainGui(){initialize();}

	/**
	 * Inizializza il contenuto del frame principale
	 */

	private void initialize()
	{
		buildPanel = new BuildPanel(); 
		wrapper = new Wrap(this); // istanzia l'oggetto per wrappare da oggetto  semplice a oggettoGrafico tenendo un  riferimento alla maingui nella quale operare
		displayFrame = new DisplayFrame(this);
		serial = new Serializza(); // istanzia l'oggetto per permettere di  effetturare serializzazione e deserializzazione degli oggetti per salvataggio dello stream su file
									
		// Istanzia il frame principale
		frame = new JFrame("Graphical Adventure Game");
		frame.setSize(new Dimension(1280, 720));
		frame.setMinimumSize(new Dimension(1280, 720));
		frame.setMaximumSize(new Dimension(1280, 720));
		frame.getContentPane().setLayout(null);
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setBackground(Color.BLACK);
		
		// /Instanzia i Jbutton

		JButton btnNewButton = new JButton("Usa");
		btnNewButton.setBounds(192, 570, 93, 22);
		btnNewButton.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto!");

				else // prova ad usare l'oggetto con usa semplice senza  argomenti e poi con usa con personaggio
						
				{
					if (pg.usa(oggToProcess)) 
					{
						repaintAll(); // se l'azione ha avuto successo invoca il metodo repaintAll() per il refresh dei component
						
						return;
						
					} else // se entrambi i tentativi precedenti hanno esito negativo, istanzia il frame ausiliario per il metodo usacon dopo aver memorizzato l'oggetto da usare in una riferimento temporaneo dato che OggToProcess sara' sovrascritta		
					{

						Motore.write("L'oggetto: "+ oggToProcess.toString() + " non puo' essere utilizzato singolarmente, selezionare un altro oggetto con il quale utilizzarlo!");
						oggToProcessTemp = oggToProcess; 
						displayFrame.displayUsaConFrame(); // istanziamo il frame che chiamera il metodo usa con del pg giocante con argomenti appropriati a seconda della selezione dell'utente

					}

				}

			}

		});

		JButton btnNewButton_1 = new JButton("Esamina");
		btnNewButton_1.setBounds(6, 570, 93, 22);
		btnNewButton_1.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		btnNewButton_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da esaminare!");

				else
					pg.esamina(oggToProcess); // compie l'azione esamina tramite
												// pgGiocante

				oggToProcess = null;
			}
		});

		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Cerca");
		btnNewButton_2.addActionListener(new ActionListener() 
		{

			public void actionPerformed(ActionEvent e) 
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto in cui cercare!");

				else
				{
					try 
					{
						pg.cerca(oggToProcess);
						repaintAll(); // refresh dei componenti grafici poiche' potrebbero contenere nuovi oggetti
					
					} catch (IllegalOperationException e1) {}
				}

				oggToProcess = null;
			}
		});
		btnNewButton_2.setBounds(99, 570, 93, 22);
		btnNewButton_2.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_4 = new JButton("Chiudi");
		btnNewButton_4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da chiudere!");
				else
				{
					try 
					{
						pg.chiudi(oggToProcess);
					} catch (IllegalOperationException e1) {}
				}

				oggToProcess = null;
			}
		});
		btnNewButton_4.setBounds(6, 595, 93, 22);
		btnNewButton_4.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnNewButton_4);

		JButton btnSping = new JButton("Spingi");
		btnSping.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da spingere!");
				else
				{
					try
					{
						pg.spingi(oggToProcess);
					} catch (IllegalOperationException e1) {}
				}

				oggToProcess = null;
			}
		});
		btnSping.setBounds(99, 595, 93, 22);
		btnSping.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnSping);

		JButton btnNewButton_8 = new JButton("Parla\n");
		btnNewButton_8.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (pgToProcess == null)
					Motore.write("Selezionare prima un personaggio con cui parlare!");
				else
					try 
					{
						pg.parlaCon(pgToProcess);
					} catch (IllegalOperationException e) {}

				pgToProcess = null;

			}
		});
		btnNewButton_8.setBounds(192, 595, 93, 22);
		btnNewButton_8.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnNewButton_8);

		JButton btnNewButton_7 = new JButton("Entra\n");
		btnNewButton_7.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto in cui entrare!");

				else 
				{
					try 
					{
						pg.entra(oggToProcess);
						repaintAll();
					} catch (IllegalOperationException e1) {}

				}
				oggToProcess = null;
			}
		});
		btnNewButton_7.setBounds(192, 645, 93, 22);
		btnNewButton_7.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnNewButton_7);

		JButton btnNewButton_3 = new JButton("Apri");
		btnNewButton_3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da aprire!");
				else
					try
					{
						pg.apri(oggToProcess);
					} catch (IllegalOperationException e1) {}

				oggToProcess = null;

			}
		});
		btnNewButton_3.setBounds(99, 620, 93, 22);
		btnNewButton_3.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnNewButton_3);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_6 = new JButton("Leggi\n");
		btnNewButton_6.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da leggere!");
				else
				{
					try 
					{
						pg.leggi(oggToProcess);
					} catch (IllegalOperationException e) {}

				}

				oggToProcess = null;
			}
		});
		btnNewButton_6.setBounds(192, 620, 93, 22);
		btnNewButton_6.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnNewButton_6);

		JButton btnNewButton_5 = new JButton("Tira");
		btnNewButton_5.setBounds(6, 645, 93, 22);
		btnNewButton_5.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnNewButton_5);
		btnNewButton_5.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da tirare!");
				else
				{
					try 
					{
						pg.tira(oggToProcess);
					} catch (IllegalOperationException e1){}
				}
				oggToProcess = null;

			}
		});

		JButton btnDai = new JButton("Dai");
		btnDai.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da dare!");
				else
					displayFrame.displayDaiFrame();

			}
		});
		btnDai.setBounds(6, 670, 93, 22);
		btnDai.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnDai);

		JButton btnDomanda = new JButton("Domanda");
		btnDomanda.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (pgToProcess == null)
					Motore.write("Selezionare prima un personaggi a cui porre una domanda!");
				else
				{
					displayFrame.displayDomandaFrame();
				}
			}
		});
		btnDomanda.setBounds(6, 620, 93, 22);
		btnDomanda.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnDomanda);

		JButton btnAccendi = new JButton("Accendi");
		btnAccendi.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da accendere!");
				else
					try
				    {
						pg.accendi(oggToProcess);
					} catch (IllegalOperationException e) {}

				oggToProcess = null;
			}
		});
		btnAccendi.setBounds(192, 670, 93, 22);
		btnAccendi.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnAccendi);

		JButton btnSpegni = new JButton("Spegni");
		btnSpegni.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da spegnere!");
				else
					try
					{
						pg.spegni(oggToProcess);
					} catch (IllegalOperationException e){}

				oggToProcess = null;
			}
		});
		btnSpegni.setBounds(99, 670, 93, 22);
		btnSpegni.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnSpegni);

		JButton btnPrendi = new JButton("Prendi");
		btnPrendi.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if (oggToProcess == null)
					Motore.write("Selezionare prima un oggetto da prendere!");

				else
				{
					try 
					{

						pg.prendi(oggToProcess);

					} catch (IllegalOperationException e1) {}

					repaintAll();
				}
				oggToProcess = null;
			}
		});
		btnPrendi.setBounds(99, 645, 93, 22);
		btnPrendi.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		frame.getContentPane().add(btnPrendi);

		// JTextPane che stampa la locazione corrente
		textArea = new JTextPane();
		textArea.setBounds(10, 25, 1260, 40);
		textArea.setAlignmentY(Component.CENTER_ALIGNMENT);
		textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Modern No. 20", Font.BOLD | Font.ITALIC, 25));
		textArea.setBackground(Color.BLACK);
		textArea.setEditable(false);
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		frame.getContentPane().add(textArea);

																		/* MENU BAR DEL GIOCO */

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1280, 22);
		menuBar.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem nuovaPartita = new JMenuItem("Nuova Partita");
		nuovaPartita.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				JFileChooser fileChooser = new JFileChooser(); // istanziamo il JFileChooser
				fileChooser.setDialogTitle("Nuova Partita");
				int response = fileChooser.showOpenDialog(frame); // passiamo come reference il frame nel quale visualizzare il fileChooser

				if (response == JFileChooser.APPROVE_OPTION)
				{
					File f = fileChooser.getSelectedFile();

					Motore.caricaFile(f); // invochiamo il metodo carica file del motore per il caricamento tramite parsing
					Motore.saveObject(); // notifichiamo al motore che è stato eseguito il parser da file e salviamo i riferimenti degli oggetti appena istanziati
					pg = (pgGiocante) Motore.getPg(); // salviamo un riferimento al personaggio giocante
					repaintAll(); // invochiamo per ridisegnare tutti i panelche adesso conterranno degli oggetti

				}
			}
		});
		mnFile.add(nuovaPartita); // aggiungiamo l'item al menufile

		JMenuItem salvaPartita = new JMenuItem("Salva Partita");

		salvaPartita.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)

			{

				JFileChooser fileChooser = new JFileChooser(); // istanziamo il JFileChooser
				fileChooser.setDialogTitle("Salva Partita");
				FileNameExtensionFilter datFilter = new FileNameExtensionFilter("Estensione .dat", "dat"); // aggiungiamo l'unica estensione selezionabile
				fileChooser.addChoosableFileFilter(datFilter);
				int response = fileChooser.showSaveDialog(frame); // passiamo come reference il frame nel quale visualizzare il fileChooser

				if (response == JFileChooser.APPROVE_OPTION) 
				{
					File f = fileChooser.getSelectedFile(); // salviamo il riferimento al file selezionato dall'utente nel quale salvare lo stream di byte ottenuti serializzando gli oggetti
					try
					{
						serial.serializza(f);
						Motore.write("Salvataggio della partita,avvenuto con successo!!!");
					} catch (IOException e1)
					{
						e1.printStackTrace();
					} catch (IllegalOperationException e2) {}

				}
			}
		});

		mnFile.add(salvaPartita);

		/* Carica Partita */

		JMenuItem caricaPartita = new JMenuItem("Carica Partita");

		caricaPartita.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)

			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Carica Partita");
				FileNameExtensionFilter datFilter = new FileNameExtensionFilter("Estensione .dat", "dat"); // aggiungiamo l'unica estensione selezionabile
				fileChooser.addChoosableFileFilter(datFilter);

				int response = fileChooser.showOpenDialog(frame);

				if (response == JFileChooser.APPROVE_OPTION)
				{
					File f = fileChooser.getSelectedFile();
					try
					{
						serial.deserializza(f); // deserializziamo il file

						/*
						 * Se la deserializzazione è avvenuta con successo
						 * facciamo caricare al motore l'arrayList personaggi e
						 * alla Maingui le componenti grafiche
						 */

						Motore.loadObj(serial.getPersonaggi(),serial.getLocazione()); // invochiamo il metodo  loadObj per far  caricare al motore gli oggetti appena deserializzati da file 

						pg = (pgGiocante) Motore.getPg(); // salviamo un riferimento al personaggio giocante

						repaintAll();

					} catch (FileNotFoundException e1)
					{
						e1.printStackTrace();

					} catch (IOException e2) 
					{
						e2.printStackTrace();
					} catch (ClassNotFoundException e3)
					{
						e3.printStackTrace();
					}

				}

			}
		});

		mnFile.add(caricaPartita);

		// Istanzia la console
		JConsole console_1 = new JConsole();
		console_1.setBounds(291, 570, 983, 122);
		console_1.setBackground(Color.BLACK);
		console_1.setForeground(Color.LIGHT_GRAY);
		console_1.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		console_1.setEditable(false);
		console_1.setLineWrap(true);

		// Aggiungiamo la console allo ScrollPane che conterra' la console
		JScrollPane scrollPane = new JScrollPane(console_1); // associamo la console alla scrollPane
		scrollPane.setForeground(Color.WHITE);
		scrollPane.setBorder(new TitledBorder(new LineBorder(new Color(255,255, 255), 2, true), "Console", TitledBorder.LEADING,TitledBorder.TOP, null, new Color(255, 255, 255)));
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setVisible(true);
		scrollPane.setBounds(291, 570, 983, 122);
		frame.getContentPane().add(scrollPane);

		// Collegamento del System.out alla JConsole

		System.setOut(console_1.getPrintStream());
		System.setErr(console_1.getPrintStream());

		// Instanziamo il panel inizialmente vuoto
		// opportuni INVENTARIO PG GIOCANTE

		panel0 = buildPanel.buildPanel(); // istanziamo un pannello inizialmente vuoto tramite il metodo buildpanel

		// JScrollPane che conterrˆ il panel che conterra' gli oggetti del personaggio giocante
		JScrollPane scrollPanel = new JScrollPane(panel0);
		scrollPanel.setBackground(Color.BLACK);
		scrollPanel.setBorder(new TitledBorder(new LineBorder(new Color(255,255, 255)), " Inventario Personaggio Giocante ",TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255,255, 255)));
		scrollPanel.setVisible(true);
		scrollPanel.setBounds(642, 66, 632, 245);

		frame.getContentPane().add(scrollPanel);

		// Jpanel che conterra' l'inventario degli oggetti della locazione
		
		panel1 = buildPanel.buildPanel();
		JScrollPane panel_1 = new JScrollPane(panel1);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(255, 255,
				255)), " Inventario Locazione ", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel_1.setVisible(true);
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(6, 66, 633, 245);

		frame.getContentPane().add(panel_1);

		// Jpanel che conterra' la lista dei personaggi nella locazione corrente

		panel2 = buildPanel.buildPanel();

		JScrollPane panel_2 = new JScrollPane(panel2);
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(255, 255,255)), " Lista Personaggi ", TitledBorder.CENTER,TitledBorder.TOP, null, Color.WHITE));
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(6, 314, 633, 244);
		frame.getContentPane().add(panel_2);

		// JscrollPane che conterra' l'inventario del pg non giocante selezionato
		

		panel3 = buildPanel.buildPanel(); 
		JScrollPane panel_3 = new JScrollPane(panel3);
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(255, 255,255)), " Inventario Personaggio Selezionato ",TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(642, 314, 632, 244);

		frame.setBounds(100, 100, 1280, 720); // setta le dimensioni predefinite
												// del jframe
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.getContentPane().add(panel_3);

	}

	//METODI AUSILIARI PER PERMETTERE DI INTERFACCIARSI CON LE ALTRE COMPONENTI GRAFICHE DEL MOTORE

	/**
	 * metodo che setta la locazione corrente nella textArea del motore grafico
	 * e controlla se si è giunti alla locazione finale del gioco in tal caso
	 * lancia un frame ausiliario che indica la fine della partita
	 */
	public void setIntestazione(String str1, String str2)
	{
		
		textArea.setText("Personaggio Giocante: " + str1+ "	-	Locazione Corrente: " + str2);		

		if (str2.equals("Fine")) // controlliamo se si è giunti alla locazione finale
		{
			displayFrame.displayFineFrame(); //istanziamo il frame di fine del gioco
		}
	}

	/**
	 * tramite questo metodo salviamo un riferimento all'oggetto selezionato
	 * dall'utente per compierne l'azione adeguata
	 */
	public static void addObjToProcess(Oggetto ogg) 
	{
		oggToProcess = ogg;
	}

	/**
	 * Metodo che ha come valore di ritorno l'oggetto appena selezionato
	 * dall'utente, utile per la classe DisplayFrame per poter compiere l'azione
	 * adeguata
	 * 
	 */
	public static Oggetto getObjToProcess()
	{
		return oggToProcess;
	}

	/**
	 *  Tramite questo metodo salviamo un riferimento al personaggio selezionato dall'utente									
	 * @param pg
	 */
	public static void addpgToprocess(Personaggio pg) 

	{
		pgToProcess = pg;
	}

	/**
	 * Metodo che ha come valore di ritorno il personaggio appena selezionato
	 * dall'utente, utile per la classe DisplayFrame per poter compiere l'azione
	 * adeguata
	 * 
	 */
	public static Personaggio getPgToProcess()
	{
		return pgToProcess;
	}

	/**
	 * Metodo che ha come valore di ritorno l'oggetto temporaneo cliccato
	 * precedentemente dall'utente
	 */
	public static Oggetto getObjTmpToProcess()
	{
		return oggToProcessTemp;
	}

						/* METODI PER IL REFRESH DEI COMPONENT DELLA GUI */

	/**
	 * metodo per il refresh del pannello contenente l'inventario del
	 * personaggio giocante
	 * 
	 */

	public void displayPanel0() 
	{
		panel0.removeAll();
		panel0.repaint(); // ridisegnamo il component
		buildPanel.addObjectPanel(panel0, wrapper.wrap(pg.getInventario()));
		panel0.revalidate();

	}

	/**
	 * metodo per il refresh del pannello contenente l'inventario della
	 * locazione
	 * 
	 * @param loc
	 */
	private void displayPanel1(Locazione loc) // refresh dell'inventario della locazione
	{
		panel1.removeAll();
		panel1.repaint(); // ridisegnamo il component
		buildPanel.addObjectPanel(panel1, wrapper.wrap(loc.getInventario())); // ricostruisce il panello
		panel1.revalidate();

	}

	/**
	 * metodo per il refresh del pannello contenente la lista dei personaggi
	 * presenti all'interno della locazione
	 * @param loc
	 */
	private void displayPanel2(Locazione loc)// refresh della lista dei
											// personaggi presenti all'interno
											// della locazione corrente
	{
		panel2.removeAll(); // rimuoviamo il pannello all'interno del container
		panel2.repaint(); // ridisegnamo il component
		buildPanel.addPgPanel(panel2, wrapper.wrapPg(loc.getPersonaggi()));
		panel2.revalidate();

	}

	/**
	 * metodo per il refresh del pannello dell'inventario del pg non giocante selezionato
	 * @param personaggio
	 */
	public void displayPanel3(Personaggio personaggio) // Se invocato esegue il repaint dell inventario del pgNonGiocanteSelezionato
	{

		panel3.removeAll(); // / rimuoviamo gli oggetti precedentemente contenuti
		panel3.repaint(); // ridisegnamo il component
		buildPanel.addObjectPanel(panel3,wrapper.wrap(personaggio.getInventario()));
		panel3.revalidate();
	}
	
	/**
	 * Metodo che ha lo scopo di ridisegnare tutti i panel dell'interfaccia grafica
	 */
	private void repaintAll() // metodo che se invocato ridisegna tutti panel dell'interfaccia grafica
	{
		setIntestazione(pg.getNome(), Motore.getCurrentLocation().toString()); // setta la locazione corrente e il nome del personaggio giocante
																				
		displayPanel0(); // refresh dell'inventario del personaggio giocante
		displayPanel1(Motore.getCurrentLocation()); // refresh del pannello
		displayPanel2(Motore.getCurrentLocation());

		panel3.removeAll(); // pannello 3 deve solamente essere svuotato
		panel3.revalidate();

	}

	/**
	 * se invocato provoca la chiusura del frame principale
	 */
	public void closeGame()
	{
		frame.dispose();
	}

}