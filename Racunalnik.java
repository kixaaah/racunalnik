/**
 * Razred za model računalnika
 * 
 * @author Kristjan Mestnik
 * @version Vaja 27
 */
public class Racunalnik {

    // Lastnosti računalnika
    private String procesor;
    private int ram;
    private int disk;
    private boolean vklopljen;

    /**
     * Konstruktor za inicializacijo novega računalnika
     * Inicializira vse lastnosti
     * 
     * @param procesor Tip procesorja
     * @param ram      Količina RAM-a (v GB)
     * @param disk     Velikost diska (v GB)
     */
    public Racunalnik(String procesor, int ram, int disk) {
        this.procesor = procesor;
        this.ram = ram;
        this.disk = disk;
        this.vklopljen = false;
        System.out.println("Ustvarjen je računalnik s procesorjem " + procesor + ", " + ram + " GB RAM-a in " + disk + " GB diska.");
    }

    /**
     * Metoda za vklop računalnika
     * 
     * @return true - če je bil računalnik uspešno vklopljen, false - če je že bil vklopljen
     */
    public boolean vklopi() {
        if (vklopljen) {
            System.out.println("Računalnik je že vklopljen.");
            return false;
        } else {
            vklopljen = true;
            System.out.println("Računalnik se vklaplja ... Računalnik je vklopljen.");
            return true;
        }
    }

    /**
     * Metoda za izklop računalnika
     * 
     * @return true - če je bil računalnik uspešno izklopljen, false - če je že bil izklopljen
     */
    public boolean izklopi() {
        if (!vklopljen) {
            System.out.println("Računalnik je že izklopljen.");
            return false;
        } else {
            vklopljen = false;
            System.out.println("Računalnik se izklaplja ... Računalnik je izklopljen.");
            return true;
        }
    }

    /**
     * Metoda za nadgradnjo RAM-a
     * 
     * @param dodatek Količina RAM-a, ki ga želimo dodati (v GB)
     */
    public void nadgradiRam(int dodatek) {
        if (dodatek > 0) {
            ram += dodatek;
            System.out.println("RAM je nadgrajen za " + dodatek + " GB. Skupaj je zdaj " + ram + " GB RAM-a.");
        } else {
            System.out.println("Neveljavna vrednost za nadgradnjo RAM-a.");
        }
    }
			
		/**
		 * Getter metoda za RAM
		 * @return Količina RAM-a (v GB)
		 */
		public int getRAM() {
			return ram;
		}

		/**
		 * Getter metoda za disk
		 * @return Velikost diska (v GB)
		 */
		public int getDisk() {
			return disk;
		}

		/**
		 * Getter metoda za procesor
		 * @return Tip procesorja
		 */
		public String getProcesor() {
			return procesor;
		}

		/**
		 * Preveri, ali je računalnik vklopljen
		 * @return true - če je računalnik vklopljen, false - če je izklopljen
		 */
		public boolean isVklopljen() {
			return vklopljen;
		}
}