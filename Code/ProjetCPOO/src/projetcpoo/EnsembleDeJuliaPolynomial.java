package projetcpoo;

/**
 * Classe pour représenter l'ensemble de Julia qui a une fonction polynomiale
 */
public class EnsembleDeJuliaPolynomial extends EnsembleDeJulia {
    
    private final int degree;

    /**
     * Constructeur d'un ensemble de Julia polynomial
     * @param degree : le degré du polynome
     * @param c : la constante de la fonction
     */
    public EnsembleDeJuliaPolynomial(int degree, Complexe c) {
        super(((x0, c2) -> {
                Complexe xn = x0;
                for(int i = 1;i < degree;i++) {
                    xn = xn.times(x0);
                }
                return c2.plus(xn);
            }),
            c);
        this.degree = degree;
    }
}
