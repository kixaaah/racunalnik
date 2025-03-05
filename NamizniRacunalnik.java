/**
 * Razred za NamizniRacunalnik
 * 
 * @author Kristjan Mestnik
 * @version Vaja 36 - test popravek za GIT
 */
public class NamizniRacunalnik extends Racunalnik implements ElektricniPriklop {
    private String graficnaKartica;

    public NamizniRacunalnik(String procesor, int ram, int disk, String graficnaKartica) {
        super(procesor, ram, disk);
        this.graficnaKartica = graficnaKartica;
    }

    public void nadgradiGrafiko(String novaGrafika) {
        this.graficnaKartica = novaGrafika;
        System.out.println("Grafična kartica je nadgrajena na: " + novaGrafika);
    }
	
	public void priklopiNaElektriko() {
        System.out.println("Namizni računalnik je priklopljen v elektriko.");
    }

    @Override
    public void odklopiZElektrike() {
        System.out.println("Namizni računalnik je odklopljen iz elektrike.");
    }
	
	/**
	 * Getter metoda za grafično kartico
	 * @return Naziv grafične kartice
	 */
	public String getGraficnaKartica() {
		return graficnaKartica;
	}
}