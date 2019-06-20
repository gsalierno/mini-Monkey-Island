package com.avventuragrafica.motoreGrafico;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

/**
 * Definisce una classe Jconsole che estende JtextArea. 
 */
public class JConsole extends JTextArea 
{

private static final long serialVersionUID = 1L;




	
private PrintStream printStream;

  public JConsole()
  {
    printStream = new PrintStream(new ConsolePrintStream());
    

	 
  }

  public PrintStream getPrintStream()
  {
    return printStream;
  }

  //L' output stream definito da noi
  private class ConsolePrintStream extends ByteArrayOutputStream
  {
    public synchronized void write(byte[] b, int off, int len)
    {
      setCaretPosition(getDocument().getLength()); // sceglie di canalizzare il buffer da stampare
      String str = new String(b);
      append(str.substring(off, len));
    }
  }

}