package com.decagon.pf_montecarlos.ClassModels;
import java.security.SecureRandom;
import java.util.Random;

public class ExpRandom {

    private static SecureRandom secureRandom = new SecureRandom();

    private ExpRandom() {
    }

    /**
     * Método estático para generar valores de una variable aleatoria
     * con distribución exponencial por medio del método de Transformada Inversa.
     *
     * @param lambda Parámetro de la distribución exponencial.
     * @return valor para la variable aleatoria con dist. exponencial
     */
    public static Double generadorDistribucionExponencial(double lambda) {

        double random = secureRandom.nextDouble();

        double exponencial = ((Math.log(random) / lambda) * (-1));

        return exponencial;
    }

    /**
     * Método estático para generar valores de una variable aleatoria
     * con distribución uniforme entre a y b por médio del método de Transformada
     * Inversa.
     *
     * @param a parámetro a de la distribución uniforme.
     * @param b parámetro b de la distribución uniforme.
     * @return valor para la variable aleatoria con dist. uniforme.
     * <p>
     * Caso especial: Uniforme(0:1)
     */
    public static Double generadorDistribucionUniformeAB(double a, double b) {

        double random = secureRandom.nextDouble();

        double uniformeAB = a + ((b - a) * random);

        return uniformeAB;
    }

    /**
     * Método estático para generar valores de una variable aleatoria
     * con distribución triangular por médio del método de Transformada
     * Inversa.
     *
     * @param a Parámetro a de la variable aleatoria con distribución triangular
     * @param b Parámetro b de la variable aleatoria con distribución triangular
     * @param c Parámetro c de la variable aleatoria con distribución triangular
     * @return valor para la variable aleatoria con distribución triangular
     */
    public static Double generadorDistribucionTriangular(double a, double b, double c) {

        double random = secureRandom.nextDouble();
        double x;
        double aux;

        if( random <= ((b-a)/c-a) ){

            aux = Math.sqrt(((c-a)*(b-a)*random));

            x = a + aux;
        }else{
            aux = Math.sqrt((c-a)*(c-b)*(1-random));

            x = c - aux;
        }

        return x;
    }

    /**
     * Método estático para generar valores de una variable aleatoria
     * con distribución Poisson por médio del método de Transformada
     * Inversa.
     *
     * @param lambda Parámetro Lambda de la distribución Poisson.
     * @return valor de la variable aleatoria con distribución Poisson.
     */
    public static Double generadorDistribucionPoisson(double lambda) {

        double L = Math.exp(-lambda);
        double k = 0;
        double p = 1.0;

        do {
            k++;
            p = p * secureRandom.nextDouble();
        } while (p > L);

        return (k - 1);
    }
}