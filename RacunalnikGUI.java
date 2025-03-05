// Uvozimo razred za delo z vhodno-izhodnimi operacijami ter pripomočke
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Razred za prikaz delovanja grafičnega uporabniškega vmesnika za računalnike
 * Razširja razred z delo z okni
 *
 * @author Kristjan Mestnik
 * @version Vaja 36
 */
public class RacunalnikGUI extends JFrame implements ActionListener {
    // Deklariramo zasebne lastnosti GUI
    private JPanel povrsina;
    private JPanel dodajPanel;
    private JPanel upravljajPanel;
    
    // Komponente za dodajanje računalnika
    private JComboBox<String> tipComboBox;
    private JTextField procesorTextField;
    private JTextField ramTextField;
    private JTextField diskTextField;
    private JTextField dodatnoTextField;
    private JLabel dodatnoLabel;
    private JButton dodajButton;
    
    // Komponente za upravljanje računalnika
    private JTable tabela;
    private JButton vklopiButton;
    private JButton izklopiButton;
    private JButton nadgradiRamButton;
    private JButton nadgradiGrafikoButton;
    private JButton napolniBaterijoButton;
    private JButton priklopiNaElektrikoButton;
    private JButton odklopiZElektrikeButton;
    
    private RacunalnikTableModel modelTabele;
    
    /**
     * Javna statična metoda programa, Zažene se vedno ob zagonu
     * @param args Seznam argumentov iz ukazne vrstice
     */
    public static void main(String[] args) {
        // Izpišemo začetek
        System.out.println("Zaganjam GUI za računalnike...");
        
        // Ustvarimo objekt razreda RacunalnikGUI
        RacunalnikGUI gui = new RacunalnikGUI();
    }
    
    /**
     * Konstruktor, ki postavi osnovne lastnosti okna
     */
    public RacunalnikGUI() {
        // Pokličemo konstruktor nadrazreda
        super("Seznam računalnikov");
        
        // Nastavimo obnašanje križca [x] tako, da konča aplikacijo
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Nastavimo velikost okna
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        // Inicializiramo elemente GUI
        dodajPanel = new JPanel();
        dodajPanel.setBorder(BorderFactory.createTitledBorder("Dodaj nov računalnik"));
        
        upravljajPanel = new JPanel();
        upravljajPanel.setBorder(BorderFactory.createTitledBorder("Upravljaj izbrani računalnik"));
        
        // === Nastavi dodajPanel ===
        dodajPanel.setLayout(new GridLayout(6, 2, 10, 10));
        
        // Dropdown za izbiro tipa računalnika
        String[] tipi = {"Osnovni", "Namizni", "Prenosni"};
        tipComboBox = new JComboBox<>(tipi);
        tipComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tip = (String) tipComboBox.getSelectedItem();
                if (tip.equals("Namizni")) {
                    dodatnoLabel.setText("Grafična kartica:");
                    dodatnoTextField.setEnabled(true);
                } else if (tip.equals("Prenosni")) {
                    dodatnoLabel.setText("Baterija (%):");
                    dodatnoTextField.setEnabled(true);
                } else {
                    dodatnoLabel.setText("Dodatno:");
                    dodatnoTextField.setEnabled(false);
                }
            }
        });
        
        // Vnosna polja za lastnosti računalnika
        procesorTextField = new JTextField(20);
        ramTextField = new JTextField(5);
        diskTextField = new JTextField(5);
        dodatnoTextField = new JTextField(20);
        
        // Labele
        JLabel tipLabel = new JLabel("Tip računalnika:");
        JLabel procesorLabel = new JLabel("Procesor:");
        JLabel ramLabel = new JLabel("RAM (GB):");
        JLabel diskLabel = new JLabel("Disk (GB):");
        dodatnoLabel = new JLabel("Dodatno:");
        
        // Dodamo elemente na dodajPanel
        dodajPanel.add(tipLabel);
        dodajPanel.add(tipComboBox);
        
        dodajPanel.add(procesorLabel);
        dodajPanel.add(procesorTextField);
        
        dodajPanel.add(ramLabel);
        dodajPanel.add(ramTextField);
        
        dodajPanel.add(diskLabel);
        dodajPanel.add(diskTextField);
        
        dodajPanel.add(dodatnoLabel);
        dodajPanel.add(dodatnoTextField);
        
        // Gumb za dodajanje računalnika
        dodajButton = new JButton("Dodaj računalnik");
        dodajButton.addActionListener(this);
        
        dodajPanel.add(new JLabel(""));
        dodajPanel.add(dodajButton);
        
        // === Nastavi upravljajPanel ===
        upravljajPanel.setLayout(new GridLayout(4, 2, 10, 10));
        
        // Gumbi za upravljanje
        vklopiButton = new JButton("Vklopi");
        izklopiButton = new JButton("Izklopi");
        nadgradiRamButton = new JButton("Nadgradi RAM");
        nadgradiGrafikoButton = new JButton("Nadgradi grafiko");
        napolniBaterijoButton = new JButton("Napolni baterijo");
        priklopiNaElektrikoButton = new JButton("Priklopi na elektriko");
        odklopiZElektrikeButton = new JButton("Odklopi z elektrike");
        
        // Dodamo poslušalce dogodkov za gumbe
        vklopiButton.addActionListener(this);
        izklopiButton.addActionListener(this);
        nadgradiRamButton.addActionListener(this);
        nadgradiGrafikoButton.addActionListener(this);
        napolniBaterijoButton.addActionListener(this);
        priklopiNaElektrikoButton.addActionListener(this);
        odklopiZElektrikeButton.addActionListener(this);
        
        upravljajPanel.add(vklopiButton);
        upravljajPanel.add(izklopiButton);
        upravljajPanel.add(nadgradiRamButton);
        upravljajPanel.add(nadgradiGrafikoButton);
        upravljajPanel.add(napolniBaterijoButton);
        upravljajPanel.add(new JLabel(""));
        upravljajPanel.add(priklopiNaElektrikoButton);
        upravljajPanel.add(odklopiZElektrikeButton);
        
        // Onemogočimo gumbe za upravljanje (dokler ne izberemo računalnika)
        enableManagementButtons(false);
        
        // === Tabla z računalniki ===
        // Inicializiramo model tabele
        modelTabele = new RacunalnikTableModel();
        
        // Tabelo postavimo na površino
        tabela = new JTable(modelTabele);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(950, 400));
        
        // Dodamo poslušalca za izbiro vrstice
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tabela.getSelectedRow();
                if (selectedRow != -1) {
                    updateButtonsForSelectedRow(selectedRow);
                }
            }
        });
        
        // === Glavni panel ===
        povrsina = new JPanel();
        povrsina.setLayout(new BorderLayout(10, 10));
        povrsina.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel upperPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        upperPanel.add(dodajPanel);
        upperPanel.add(upravljajPanel);
        
        povrsina.add(upperPanel, BorderLayout.NORTH);
        povrsina.add(scrollPane, BorderLayout.CENTER);
        
        // Površino dodamo na okno
        add(povrsina);
        
        // Prikažemo okno
        setVisible(true);
    }
    
    /**
     * Omogoči ali onemogoči gumbe za upravljanje računalnika
     * @param enable true - omogoči gumbe, false - onemogoči gumbe
     */
    private void enableManagementButtons(boolean enable) {
        vklopiButton.setEnabled(enable);
        izklopiButton.setEnabled(enable);
        nadgradiRamButton.setEnabled(enable);
        nadgradiGrafikoButton.setEnabled(false);
        napolniBaterijoButton.setEnabled(false);
        priklopiNaElektrikoButton.setEnabled(false);
        odklopiZElektrikeButton.setEnabled(false);
    }
    
    /**
     * Posodobi stanje gumbov glede na izbrani računalnik
     * @param selectedRow Indeks izbrane vrstice v tabeli
     */
    private void updateButtonsForSelectedRow(int selectedRow) {
        String tip = modelTabele.getTipAt(selectedRow);
        Racunalnik racunalnik = modelTabele.getRacunalnikAt(selectedRow);
        
        // Omogočimo osnovne gumbe za vse tipe računalnikov
        enableManagementButtons(true);
        
        // Specifični gumbi glede na tip računalnika
        if (tip.equals("Namizni")) {
            nadgradiGrafikoButton.setEnabled(true);
            napolniBaterijoButton.setEnabled(false);
            priklopiNaElektrikoButton.setEnabled(true);
            odklopiZElektrikeButton.setEnabled(true);
        } else if (tip.equals("Prenosni")) {
            nadgradiGrafikoButton.setEnabled(false);
            napolniBaterijoButton.setEnabled(true);
            priklopiNaElektrikoButton.setEnabled(false);
            odklopiZElektrikeButton.setEnabled(false);
        } else {
            nadgradiGrafikoButton.setEnabled(false);
            napolniBaterijoButton.setEnabled(false);
            priklopiNaElektrikoButton.setEnabled(false);
            odklopiZElektrikeButton.setEnabled(false);
        }
        
        // Gumbi za vklop/izklop glede na trenutno stanje
        vklopiButton.setEnabled(!racunalnik.isVklopljen());
        izklopiButton.setEnabled(racunalnik.isVklopljen());
    }
    
    /**
     * Metoda, ki jo predpisuje vmesnik ActionListener
     * Se kliče vedno, ko je pritisnjen gumb
     * @param e Dogodek ob kliku
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // Dodajanje novega računalnika
        if (source == dodajButton) {
            addNewComputer();
        } 
        // Upravljanje obstoječega računalnika
        else {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                Racunalnik izbranRacunalnik = modelTabele.getRacunalnikAt(selectedRow);
                String tip = modelTabele.getTipAt(selectedRow);
                
                if (source == vklopiButton) {
                    izbranRacunalnik.vklopi();
                } else if (source == izklopiButton) {
                    izbranRacunalnik.izklopi();
                } else if (source == nadgradiRamButton) {
                    String input = JOptionPane.showInputDialog(this, "Vnesite količino RAM-a za nadgradnjo (GB):", "Nadgradnja RAM-a", JOptionPane.QUESTION_MESSAGE);
                    if (input != null && !input.isEmpty()) {
                        try {
                            int dodatek = Integer.parseInt(input);
                            izbranRacunalnik.nadgradiRam(dodatek);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Napaka: Količina RAM-a mora biti celo število.", "Napaka pri vnosu", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (source == nadgradiGrafikoButton && tip.equals("Namizni") && izbranRacunalnik instanceof NamizniRacunalnik) {
                    NamizniRacunalnik namizni = (NamizniRacunalnik) izbranRacunalnik;
                    String novaGrafika = JOptionPane.showInputDialog(this, "Vnesite novo grafično kartico:", "Nadgradnja grafične kartice", JOptionPane.QUESTION_MESSAGE);
                    if (novaGrafika != null && !novaGrafika.isEmpty()) {
                        namizni.nadgradiGrafiko(novaGrafika);
                    }
                } else if (source == napolniBaterijoButton && tip.equals("Prenosni") && izbranRacunalnik instanceof PrenosniRacunalnik) {
                    PrenosniRacunalnik prenosni = (PrenosniRacunalnik) izbranRacunalnik;
                    String input = JOptionPane.showInputDialog(this, "Vnesite odstotek napolnjenosti baterije (%):", "Polnjenje baterije", JOptionPane.QUESTION_MESSAGE);
                    if (input != null && !input.isEmpty()) {
                        try {
                            int odstotek = Integer.parseInt(input);
                            prenosni.napolniBaterijo(odstotek);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Napaka: Odstotek mora biti celo število.", "Napaka pri vnosu", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (source == priklopiNaElektrikoButton && tip.equals("Namizni") && izbranRacunalnik instanceof ElektricniPriklop) {
                    ElektricniPriklop priklop = (ElektricniPriklop) izbranRacunalnik;
                    priklop.priklopiNaElektriko();
                } else if (source == odklopiZElektrikeButton && tip.equals("Namizni") && izbranRacunalnik instanceof ElektricniPriklop) {
                    ElektricniPriklop priklop = (ElektricniPriklop) izbranRacunalnik;
                    priklop.odklopiZElektrike();
                }
                
                // Posodobimo prikaz v tabeli
                modelTabele.updateRacunalnikAt(selectedRow);
                
                // Posodobimo stanje gumbov za vklop/izklop
                updateButtonsForSelectedRow(selectedRow);
            }
        }
    }
    
    /**
     * Metoda za dodajanje novega računalnika
     */
    private void addNewComputer() {
        // Izpišemo podatke iz vnosnih polj
        System.out.println("Dodajam računalnik...");
        String tip = (String) tipComboBox.getSelectedItem();
        String procesor = procesorTextField.getText();
        
        // Preverjamo, če so vnosna polja za RAM in disk pravilno izpolnjena
        try {
            int ram = Integer.parseInt(ramTextField.getText());
            int disk = Integer.parseInt(diskTextField.getText());
            
            switch (tip) {
                case "Osnovni":
                    // Ustvarimo osnovni računalnik
                    Racunalnik osnovni = new Racunalnik(procesor, ram, disk);
                    modelTabele.addRacunalnik(osnovni, procesor, ram, disk);
                    System.out.println("Dodan osnovni računalnik: " + procesor);
                    break;
                    
                case "Namizni":
                    // Pridobimo grafično kartico
                    String graficna = dodatnoTextField.getText();
                    
                    // Ustvarimo namizni računalnik
                    NamizniRacunalnik namizni = new NamizniRacunalnik(procesor, ram, disk, graficna);
                    modelTabele.addNamizniRacunalnik(namizni, procesor, ram, disk, graficna);
                    System.out.println("Dodan namizni računalnik: " + procesor + " z grafično: " + graficna);
                    break;
                    
                case "Prenosni":
                    // Preverjamo, če je vnosno polje za baterijo pravilno izpolnjeno
                    try {
                        int baterija = Integer.parseInt(dodatnoTextField.getText());
                        
                        // Ustvarimo prenosni računalnik
                        PrenosniRacunalnik prenosni = new PrenosniRacunalnik(procesor, ram, disk, baterija);
                        modelTabele.addPrenosniRacunalnik(prenosni, procesor, ram, disk, baterija);
                        System.out.println("Dodan prenosni računalnik: " + procesor + " z baterijo: " + baterija + "%");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Napaka: Vrednost baterije mora biti število.", "Napaka pri vnosu", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
            }
            
            // Počistimo vnosna polja
            procesorTextField.setText("");
            ramTextField.setText("");
            diskTextField.setText("");
            dodatnoTextField.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Napaka: RAM in Disk morata biti celi števili.", "Napaka pri vnosu", JOptionPane.ERROR_MESSAGE);
        }
    }
}