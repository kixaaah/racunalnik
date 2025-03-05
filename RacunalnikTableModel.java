import javax.swing.table.*;
import java.util.ArrayList;

/**
 * Razred za delo s tabelo računalnikov
 * Razširja privzeti razred za tabele
 *
 * @author Kristjan Mestnik
 * @version Vaja 36*
 */
public class RacunalnikTableModel extends DefaultTableModel {
    
    // Seznam, ki hrani reference na dejanske objekte
    private ArrayList<Racunalnik> seznamRacunalnikov;
    // Seznam, ki hrani tipe računalnikov (osnovni, namizni, prenosni)
    private ArrayList<String> tipiRacunalnikov;
    
    /**
     * Konstruktor, ki ustvari tabelo računalnikov
     */
    public RacunalnikTableModel() {
        // Pokličemo konstruktor nadrazreda
        super();
        
        // Inicializiramo sezname
        seznamRacunalnikov = new ArrayList<>();
        tipiRacunalnikov = new ArrayList<>();
        
        // Dodamo stolpce v tabelo
        addColumn("Tip");
        addColumn("Procesor");
        addColumn("RAM (GB)");
        addColumn("Disk (GB)");
        addColumn("Dodatne informacije");
        addColumn("Stanje");
    }
    
    /**
     * Vrne referenco na objekt računalnika na določenem indeksu
     * @param index Indeks računalnika v seznamu
     * @return Referenca na objekt razreda Racunalnik
     */
    public Racunalnik getRacunalnikAt(int index) {
        if (index >= 0 && index < seznamRacunalnikov.size()) {
            return seznamRacunalnikov.get(index);
        }
        return null;
    }
    
    /**
     * Vrne tip računalnika na določenem indeksu
     * @param index Indeks računalnika v seznamu
     * @return Niz, ki predstavlja tip računalnika ("Osnovni", "Namizni", "Prenosni")
     */
    public String getTipAt(int index) {
        if (index >= 0 && index < tipiRacunalnikov.size()) {
            return tipiRacunalnikov.get(index);
        }
        return "";
    }
    
    /**
     * Javna metoda, ki doda namizni računalnik v tabelo
     * @param r Objekt razreda NamizniRacunalnik, ki ga dodamo v tabelo
     */
    public void addNamizniRacunalnik(NamizniRacunalnik r, String procesor, int ram, int disk, String graficna) {
        // Dodamo računalnik v seznam
        seznamRacunalnikov.add(r);
        tipiRacunalnikov.add("Namizni");
        
        // Ustvarimo seznam objektov nizov ki predstavljajo vrstico tabele
        Object[] vrstica = new Object[]{
            "Namizni", 
            procesor, 
            Integer.toString(ram), 
            Integer.toString(disk), 
            "Grafična: " + graficna, 
            "Izklopljen"
        };
        
        // vrstico ustavimo v tabelo
        addRow(vrstica);
    }
    
    /**
     * Javna metoda, ki doda prenosni računalnik v tabelo
     * @param r Objekt razreda PrenosniRacunalnik, ki ga dodamo v tabelo
     */
    public void addPrenosniRacunalnik(PrenosniRacunalnik r, String procesor, int ram, int disk, int baterija) {
        // Dodamo računalnik v seznam
        seznamRacunalnikov.add(r);
        tipiRacunalnikov.add("Prenosni");
        
        // Ustvarimo seznam objektov nizov ki predstavljajo vrstico tabele
        Object[] vrstica = new Object[]{
            "Prenosni", 
            procesor, 
            Integer.toString(ram), 
            Integer.toString(disk), 
            "Baterija: " + baterija + "%", 
            "Izklopljen"
        };
        
        // vrstico ustavimo v tabelo
        addRow(vrstica);
    }
    
    /**
     * Javna metoda, ki doda osnovni računalnik v tabelo
     * @param r Objekt razreda Racunalnik, ki ga dodamo v tabelo
     */
    public void addRacunalnik(Racunalnik r, String procesor, int ram, int disk) {
        // Dodamo računalnik v seznam
        seznamRacunalnikov.add(r);
        tipiRacunalnikov.add("Osnovni");
        
        // Ustvarimo seznam objektov nizov ki predstavljajo vrstico tabele
        Object[] vrstica = new Object[]{
            "Osnovni", 
            procesor, 
            Integer.toString(ram), 
            Integer.toString(disk), 
            "/", 
            "Izklopljen"
        };
        
        // vrstico ustavimo v tabelo
        addRow(vrstica);
    }
    
    /**
     * Posodobi prikaz računalnika na določenem indeksu
     * @param index Indeks računalnika v seznamu
     */
    public void updateRacunalnikAt(int index) {
        if (index >= 0 && index < seznamRacunalnikov.size()) {
            Racunalnik r = seznamRacunalnikov.get(index);
            
            // Posodobimo RAM v tabeli (predpostavljamo, da je to tretji stolpec - indeks 2)
            setValueAt(r.getRAM() + "", index, 2); // +""  za pretvorbo v String
            
            // Posodobimo prikaz stanja (vklopljen/izklopljen)
            setValueAt(r.isVklopljen() ? "Vklopljen" : "Izklopljen", index, 5);
            
            // Posebne posodobitve glede na tip računalnika
            String tip = tipiRacunalnikov.get(index);
            if (tip.equals("Namizni") && r instanceof NamizniRacunalnik) {
                NamizniRacunalnik nr = (NamizniRacunalnik) r;
                setValueAt("Grafična: " + nr.getGraficnaKartica(), index, 4);
            } else if (tip.equals("Prenosni") && r instanceof PrenosniRacunalnik) {
                PrenosniRacunalnik pr = (PrenosniRacunalnik) r;
                setValueAt("Baterija: " + pr.getBaterija() + "%", index, 4);
            }
        }
    }
}