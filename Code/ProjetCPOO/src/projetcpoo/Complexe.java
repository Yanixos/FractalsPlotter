package projetcpoo;

/**
 * Classe pour représenter un nombre complexe et ses operations
 */
public class Complexe {
    
    private double Re, Im;

    /**
     * Constructeur du nombre complexe 
     * @param Re : la partie réelle d'un nombre complexe
     * @param Im : la partie imaginaire d'un nombre complexe
     */
    public Complexe(double Re, double Im){
        this.Re = Re;
        this.Im = Im;
    }
    
     /**
     * @return : le nombre complexe null
     */
    public static Complexe zero(){
        return new Complexe(0, 0);
    }
    
    
     /**
     * @return : la partie réelle d'un nombre complexe
     */
    public double getRe() {
        return Re;
    }
    
     /**
     * @return : la partie imaginaire d'un nombre complexe
     */
    public double getIm() {
        return Im;
    }

    /**
     * Méthode qui fait l'addition entre deux nombre complexe
     * @param c : le nombre complexe à ajouter
     * @return  : la somme du nombre complexe avec le nombre complexe c
     */
    public Complexe plus(Complexe c){
        return new Complexe(Re + c.getRe(), Im + c.getIm());
    }
     /**
     * Méthode qui fait la multiplication entre deux nombre complexe
     * @param c : le nombre à multiplier par
     * @return  : la multiplication du nombre complexe par le nombre complexe c
     */
    public Complexe times(Complexe c){
        return new Complexe(Re * c.getRe() - Im * c.getIm(),
                Im * c.getRe() + Re * c.getIm());
    }
       
     /**
     * Méthode qui applique l'opération exponentiel sur le nombre complexe
     * @return : l'exponentiel du nombre complexe
     */
    public Complexe exp(){
    	return new Complexe( Math.exp(Re) * Math.cos(Im), Math.exp(Re) * Math.sin(Im) );	
    }
    
    /**
     * Méthode qui applique l'operation module sur le nombre complexe
     * @return : le module du nombre complexe
     */  
    public double modulus(){
        return Math.sqrt(Im * Im + Re * Re);
    }
}
