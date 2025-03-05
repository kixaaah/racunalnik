/**
 * Vmesnik za električni priklop naprav
 * 
 * @author Kristjan Mestnik
 * @version Vaja 34
 */
public interface ElektricniPriklop {

    /**
     * Priklopi napravo na električno omrežje.
     */
    void priklopiNaElektriko();

    /**
     * Odklopi napravo z električnega omrežja.
     */
    void odklopiZElektrike();
}