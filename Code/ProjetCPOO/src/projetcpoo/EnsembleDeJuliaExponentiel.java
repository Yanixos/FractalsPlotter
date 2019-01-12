package projetcpoo;

/**
 * Classe pour représenter un ensemble de Julia qui a une fonction exponentielle
 */
public class EnsembleDeJuliaExponentiel extends EnsembleDeJulia
{
    /**
     * Constructeur d'un ensemble de Julia exponentiel
     * @param c : la constante de la suite
     */
    public EnsembleDeJuliaExponentiel(Complexe c) {
        super(((x0, c2) -> {
                Complexe xn = x0;
                xn = xn.exp();
                return c2.plus(xn);
            }),
            c);
    }
    
    @Override
    public boolean diverge(Complexe xn){
        return xn.modulus() > Math.E;
    }
}
