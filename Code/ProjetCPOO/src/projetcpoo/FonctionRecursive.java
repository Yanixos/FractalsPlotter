package projetcpoo;

import java.util.function.BiFunction;

/**
 * Interface fonctionnelle pour représenter une fonction de la forme
 * f(X(n), C) = X(n+1), associée à un ensemble fractal
 */
public interface FonctionRecursive extends BiFunction<Complexe, Complexe, Complexe> {
    //(x(n), c) -> x(n+1)
}