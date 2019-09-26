/*
 * Copyright (c) 2019 Diego Urrutia-Astorga http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package cl.ucn.disc.hpc.primes;

import org.junit.Test;

/**
 * Class to test the Primes.
 *
 * @author Diego Urrutia-Astorga.
 * @version 0.0.1
 */
public final class TestParallelPrimes {

    /**
     * Testing de la funcion isPrime
     */
    @Test
    public void testPrime() {

        try {
            ParallelPrimes.isPrime(-1);
            ParallelPrimes.isPrime(0);
            throw new RuntimeException("No se cayo con numeros negativos!!");
        } catch (IllegalArgumentException ex) {
            //
        }

        if (ParallelPrimes.isPrime(1)) {
            throw new RuntimeException("Error: 1 es primo ?!?");
        }

        if (!ParallelPrimes.isPrime(2)) {
            throw new RuntimeException("Error: 2 no es primo ?!?");
        }

        if (!ParallelPrimes.isPrime(17)) {
            throw new RuntimeException("Error: 17 no es primo ?!?");
        }

        if (ParallelPrimes.isPrime(200)) {
            throw new RuntimeException("Error: 200 es primo ?!?");
        }

    }

}
