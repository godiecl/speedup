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

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The Parallel Primes to show the speedup.
 *
 * @author Diego Urrutia-Astorga.
 * @version 0.0.1
 */
public final class ParallelPrimes {

    /**
     * The max prime to find.
     */
    private static final long MAX = 5000;

    /**
     * Function to test primality.
     *
     * @param n to prime test.
     * @return true is n is prime.
     */
    public static boolean isPrime(final long n) {

        // Can't process negative numbers.
        if (n <= 0) {
            throw new IllegalArgumentException("Error in n: Can't process negative numbers");
        }

        // One isn't prime!
        if (n == 1) {
            return false;
        }

        // Testing from 2 to n-1
        for (long i = 2; i < n; i++) {

            // If module == 0 -> not prime!
            if (n % i == 0) {
                return false;
            }
        }

        return true;

    }

    /**
     * Testing the primes with threads.
     *
     * @param threads to use.
     * @throws InterruptedException
     */
    private static void runWithConcurrentThreads(int threads) throws InterruptedException {

        // The Chronos
        final StopWatch stopWatch = StopWatch.createStarted();

        // The Executor to control the threads.
        final ExecutorService executor = Executors.newFixedThreadPool(threads);

        // Submit the tasks (from 2 to MAX)
        for (long i = 2; i <= MAX; i++) {
            executor.execute(new Task(i));
        }

        // Shutdown the executor (don't receive more tasks).
        executor.shutdown();

        // Await for the tasks to end.
        executor.awaitTermination(1, TimeUnit.DAYS);

        System.out.println("Threads: " + threads + " Primes: " + Task.getPrimes());
        System.out.println("Threads: " + threads + " Time: " + stopWatch.toString());

        // Reset!
        Task.resetPrimes();

    }

    /**
     * The concurrent task!
     */
    private static class Task implements Runnable {

        /**
         * The Main Counter
         */
        private static final AtomicLong aLong = new AtomicLong();

        /**
         * The Number to test
         */
        private final long number;

        /**
         * The Constructor
         *
         * @param number to test
         */
        public Task(long number) {
            this.number = number;
        }

        /**
         * @return numbers of primes.
         */
        public static long getPrimes() {
            return aLong.get();
        }

        /**
         * Set to 0 the counter.
         */
        public static void resetPrimes() {
            aLong.set(0);
        }

        /**
         * Run forest.
         */
        @Override
        public void run() {

            if (ParallelPrimes.isPrime(this.number)) {
                aLong.getAndIncrement();
            }

        }

    }

    /**
     * The Main!
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        for (int i = 1; i <= 8; i++) {
            System.out.println("Testing with " + i + " threads..");
            runWithConcurrentThreads(i);
        }

    }


}
