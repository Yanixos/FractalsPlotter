package projetcpoo;

/**
 * Classe pour représenter l'ensemble de Julia
 */
public class EnsembleDeJulia extends EnsembleFractal {

    private Complexe c;

    /**
     * Constructeur d'un ensemble de Julia
     * @param f : la fonction à associer pour le calcule
     * @param c : la constante à utiliser pendant le calcule
     */    
    public EnsembleDeJulia(FonctionRecursive f, Complexe c) {
        super(f);
        this.c = c;
    }

    @Override
    public Complexe premierTerme(Complexe coordEcran) {
        return coordEcran;
    }

    @Override
    public Complexe constanteC(Complexe coordEcran) {
        return c;
    }
}
