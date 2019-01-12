package projetcpoo;

import java.util.function.Function;
import javafx.scene.paint.Color;

/**
 * Interface fonctionnelle pour représenter une fonction de la forme
 * f(nombre d'itérations) = couleur
 */
public interface PaletteDeCouleurs extends Function<Integer, Color> {
    
    final static PaletteDeCouleurs PALETTE_1 = 
            ((indice) -> {
                return Color.hsb(90 + 180 * ((double)indice/257),
                                (double)(indice)/257,
                                (double)(256 - indice)/257, 1);
            });
    
    final static PaletteDeCouleurs PALETTE_2 = 
            ((indice) -> {
                return Color.hsb(285 - 270 * ((double)indice/257),
                                1d,
                                1d, 1);
            });
    
    final static PaletteDeCouleurs PALETTE_3 = 
            ((indice) -> {
                return Color.hsb(150 + 30 * ((double)indice/257),
                                (double)(256 - indice)/257,
                                (double)(indice)/257, 1);
            });
}
