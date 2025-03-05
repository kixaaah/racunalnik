/**
 * Glavni program za testiranje razredov Racunalnik, NamizniRacunalnik in PrenosniRacunalnik
 * 
 * @author Kristjan Mestnik
 * @version Vaja 34
 */
public class TestRacunalnik {

    public static void main(String[] args) {
        
        // Ustvarimo tri objekte razreda Racunalnik
        Racunalnik racunalnik1 = new Racunalnik("Intel i5", 8, 256);
        Racunalnik racunalnik2 = new Racunalnik("AMD Ryzen 7", 16, 512);
        Racunalnik racunalnik3 = new Racunalnik("Intel i9", 32, 1024);
		

        // Preizkusimo metode na prvem računalniku
        System.out.println("\n--- Testiranje prvega računalnika ---");
        racunalnik1.vklopi();
        racunalnik1.nadgradiRam(4);
        racunalnik1.izklopi();

        // Preizkusimo metode na drugem računalniku
        System.out.println("\n--- Testiranje drugega računalnika ---");
        racunalnik2.vklopi();
        racunalnik2.nadgradiRam(8);
        racunalnik2.izklopi();

        // Preizkusimo metode na tretjem računalniku
        System.out.println("\n--- Testiranje tretjega računalnika ---");
        racunalnik3.vklopi();
        racunalnik3.nadgradiRam(16);
        racunalnik3.izklopi();
		
		// Ustvarimo objekt razreda NamizniRacunalnik
		// Priklop in odklop namiznega računalnika na elektriko preko interface vmesnika ElektricniPriklop
		System.out.println("\n--- Ustvarjamo objekt razreda NamizniRacunalnik ---");
        NamizniRacunalnik namizni = new NamizniRacunalnik("Intel i7", 16, 512, "NVIDIA RTX 3060");
        if (namizni instanceof ElektricniPriklop) {
            ElektricniPriklop namizniPriklop = (ElektricniPriklop) namizni;
            namizniPriklop.priklopiNaElektriko();   
        namizni.vklopi();
        namizni.nadgradiRam(8);
        namizni.nadgradiGrafiko("NVIDIA RTX 4090");
        namizni.izklopi();
		namizniPriklop.odklopiZElektrike();
        }

        // Ustvarimo objekt razreda PrenosniRacunalnik
		System.out.println("\n--- Ustvarjamo objekt razreda PrenosniRacunalnik ---");
        PrenosniRacunalnik prenosni = new PrenosniRacunalnik("AMD Ryzen 9", 32, 1024, 50);
        prenosni.vklopi();
        prenosni.nadgradiRam(16);
        prenosni.napolniBaterijo(100);
        prenosni.izklopi();
    }
}