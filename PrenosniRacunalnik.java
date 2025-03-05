/**
 * Razred za PrenosniRacunalnik
 * 
 * @author Kristjan Mestnik
 * @version Vaja 28
 */
public class PrenosniRacunalnik extends Racunalnik {
    private int baterija;

    public PrenosniRacunalnik(String procesor, int ram, int disk, int baterija) {
        super(procesor, ram, disk);
        this.baterija = baterija;
    }

    public void napolniBaterijo(int odstotek) {
        if (odstotek > 0 && odstotek <= 100) {
            this.baterija = odstotek;
            System.out.println("Baterija je napolnjena na: " + odstotek + "%");
        } else {
            System.out.println("Neveljavna vrednost za polnjenje baterije.");
        }
    }
	/**
	 * Getter metoda za baterijo
	 * @return Stanje baterije v odstotkih
	 */
	public int getBaterija() {
		return baterija;
	}
}