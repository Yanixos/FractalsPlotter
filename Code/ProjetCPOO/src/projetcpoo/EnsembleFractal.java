package projetcpoo;

/**
 * Classe pour représenter un ensemble fractal
 */
public abstract class EnsembleFractal {
    
    private final FonctionRecursive f;
    
    /**
    * Constructeur d'un ensemble fractal
    * @param f : la fonction à associer pour le calcule
    */
    public EnsembleFractal(FonctionRecursive f){
        this.f = f;
    }
    
    /**
    * Méthode qui calcule l'indice de divergence de la suite
    * @param x0 : la valeur initiale de la suite
    * @param c  : la constante de la suite
    * @param maxIter : le nombre maximal des itérations
    * @return : l'indice de divergence
    */
    protected int indiceDeDivergence(Complexe x0, Complexe c, int maxIter){
        int ite = 0;
        Complexe xn = x0;
        while(++ite < maxIter && !diverge(xn)){
            xn = prochainTerme(xn, c);
        }
        return ite;
    }
    
    /**
    * Méthode qui calcule l'indice de divergence de la suite pour un pixel donné
    * @param coordEcran : les coordonnées du pixel sur l'écran
    * @param maxIter : le nombre maximal des itérations
    * @return : l'indice de divergence
    */
    public int indiceDeDivergence(Complexe coordEcran, int maxIter){
        return indiceDeDivergence(premierTerme(coordEcran), constanteC(coordEcran),
                maxIter);
    }
    
    /**
    * Méthode qui retourne le premier terme x0, étant donné les coordonnées d'un pixel
    * @param coordEcran : les coordonnées du pixel sur l'écran
    * @return : le premier terme de la suite
    */
    public abstract Complexe premierTerme(Complexe coordEcran);
    
    /**
    * Méthode qui retourne la constante c, étant donné les coordonnées d'un pixel
    * @param coordEcran : les coordonnées du pixel sur l'écran
    * @return : la constante c de la suite
    */
    public abstract Complexe constanteC(Complexe coordEcran);
    
    /**
    * Méthode qui calcule le terme X(n+1) à partir de X(n) et C
    * @param xn : le terme précédent de la suite
    * @param c : la constante C de la suite
    * @return : le prochain terme de la suite
    */
    public final Complexe prochainTerme(Complexe xn, Complexe c){
        return f.apply(xn, c);
    }
    
    /**
    * Méthode qui vérifie si la suite diverge pour un terme donné
    * @param xn : le terme courant de la suite
    * @return : la divergence pour le terme donné
    */
    public boolean diverge(Complexe xn){
        return xn.modulus() > 2;
    }
}
