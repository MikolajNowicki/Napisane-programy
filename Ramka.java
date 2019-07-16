import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lowagie.text.DocumentException;

public class Ramka extends JFrame implements ActionListener {

	private ArrayList<Dokument> dokumenty;
	private ArrayList<JTextField> polaTekstoweTytuly;
	private ArrayList<JTextField> polaTekstoweTresci;
	private JPanel mojPanel;
	private ArrayList<JButton> przyciski;
	private JButton przyciskDodaj;
	private JButton przycisk3;
	private JButton przycisk4;

	public Ramka(ArrayList<Dokument> lista) {
		this.dokumenty = lista;
		this.mojPanel = new JPanel();
		add(mojPanel);
		wypelnijPanel();

	}

	public void wypelnijPanel() {
		mojPanel.removeAll();
		GridLayout layout = new GridLayout(dokumenty.size() + 1, 3);
		mojPanel.setLayout(layout);
		polaTekstoweTytuly = new ArrayList<JTextField>();
		polaTekstoweTresci = new ArrayList<JTextField>();
		przyciski = new ArrayList<JButton>();

		for (int i = 1; i <= dokumenty.size(); i++) {
			JTextField pole = new JTextField();
			mojPanel.add(pole);
			pole.setText(dokumenty.get(i - 1).getTytul());
			polaTekstoweTytuly.add(pole);

			JTextField pole2 = new JTextField();
			mojPanel.add(pole2);
			pole2.setText(dokumenty.get(i - 1).getTresc());
			polaTekstoweTresci.add(pole2);

			JButton przycisk = new JButton("usuń dokument");
			mojPanel.add(przycisk);
			przycisk.addActionListener(this);
			przyciski.add(przycisk);
		}
		przyciskDodaj = new JButton("dodaj");
		mojPanel.add(przyciskDodaj);
		przyciski.add(przyciskDodaj);
		przyciskDodaj.addActionListener(this);

		przycisk3 = new JButton("zapisz");
		mojPanel.add(przycisk3);
		przyciski.add(przycisk3);
		przycisk3.addActionListener(this);
		
		przycisk4 = new JButton("odczytaj");
		mojPanel.add(przycisk4);
		przyciski.add(przycisk4);
		przycisk4.addActionListener(this);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object zrodlo = arg0.getSource();
		for (int i = 1; i <= przyciski.size(); i++) {
			
			if (zrodlo == przyciski.get(przyciski.size() - 1)) {
				String tytul = JOptionPane.showInputDialog(null, "Nazwa pliku tekstowego", "Ścieżka",
						JOptionPane.OK_CANCEL_OPTION);
				try {
					if (tytul==null){break;}
					load(this.dokumenty, tytul);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			
			if (zrodlo == przyciski.get(przyciski.size() - 2)) {// niezależnie
																// od liczby
																// dokumentów
																// zawsze
																// przycisk do
																// zapisywania
																// jest ostatni
				int sciezka =JOptionPane.showConfirmDialog(null, "Czy chcesz zapisać", "Zapis", JOptionPane.OK_CANCEL_OPTION);
				if (sciezka == 1||sciezka == 2) {
					break;
				}
				try {
					save(this.dokumenty);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			if (zrodlo == przyciski.get(przyciski.size() - 3)) {// niezależnie
																// od liczby
																// dokumentów
																// zawsze
																// przycisk do
																// dodawania
																// jest
																// przedostatni
				String tytul = JOptionPane.showInputDialog(null, "Nazwa dokumentu", "Nowy dokument",
						JOptionPane.OK_CANCEL_OPTION);
				if (tytul == null) {
					break;
				}
				String tresc = JOptionPane.showInputDialog(null, "Treść dokumentu", "Nowy dokument",
						JOptionPane.OK_CANCEL_OPTION);
				if (tresc == null) {
					break;
				}
				Dokument dokument = new Dokument(tytul, tresc);
				dokumenty.add(dokument);
				break;
			}
			if (zrodlo == przyciski.get(i - 1) && i < przyciski.size()) {
				dokumenty.remove(i - 1);
				break;

			}
		}
		wypelnijPanel();

	}

	private static void save(ArrayList <Dokument> lista) throws IOException{
		File file = new File("dokumenty.txt");
		FileWriter fwriter = new FileWriter(file);
		BufferedWriter bwriter = new BufferedWriter(fwriter);
		for (int i=0; i<lista.size(); i++)
		{
			bwriter.write("Dokument\n");
			bwriter.write(lista.get(i).getTytul()+"\n");
			bwriter.write(lista.get(i).getTresc()+"\n");
		}
		bwriter.close();
		System.out.println("Documents have been saved to the file");
	}
	public void load(ArrayList <Dokument> lista, String sciezkaDoPliku) throws IOException{
		
		File file = new File("dokumenty.txt");
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		lista.clear();
		String line;
		while ((line = breader.readLine()) != null) {
			if (line.equals("Dokument")) {
				String tytul = breader.readLine();
				String tresc = breader.readLine();
				Dokument mojDokumentTekstowy = new Dokument(tytul, tresc);
				lista.add(mojDokumentTekstowy);
			}
		}
		breader.close();
		System.out.println("Documents have been loaded from the file");
	}
}

