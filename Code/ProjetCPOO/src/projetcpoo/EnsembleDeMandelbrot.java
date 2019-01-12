package projetcpoo;

/**
 * Classe pour représenter les ensembles de Mandelbrot
 */
 
public class EnsembleDeMandelbrot extends EnsembleFractal {

    public EnsembleDeMandelbrot(FonctionRecursive f) {
        super(f);
    }

    @Override
    public Complexe premierTerme(Complexe coordEcran) {
        return Complexe.zero();
    }

    @Override
    public Complexe constanteC(Complexe coordEcran) {
        return coordEcran;
    }
}
