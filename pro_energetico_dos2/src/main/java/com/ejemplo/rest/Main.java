package com.ejemplo.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8099/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // Crear directorio data si no existe
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        // create a resource config that scans for JAX-RS resources and providers
        // in com.ejemplo.rest package
        final ResourceConfig rc = new ResourceConfig().packages("com.ejemplo.rest");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        // Test
        performanceSortingTest();
        performanceSearchTest();

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s\nVisit %sproyecto to test the API\nHit enter to stop it...",
                BASE_URI, BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
    
    //Practica 3 Probando Tiempos de ejecucion al ordenar datos aleatorios////////////////////////////////

    public static void performanceSortingTest() {
        int[] arraySizes = {10000, 20000, 25000};
        int warmupRuns = 5;
        int measuredRuns = 10;

        for (int size : arraySizes) {
            System.out.println("\n===== Prueba de rendimiento para el volumen del array: " + size + " =====");

            // Fase de calentamiento
            System.out.println("Calentando...");
            for (int i = 0; i < warmupRuns; i++) {
                LinkedList<Integer> warmupList = generateRandomList(size);
                try {
                    warmupList.quickSort("value", true);
                    warmupList.mergeSort("value", true);
                    warmupList.shellSort("value", true);
                } catch (Exception e) {
                    System.out.println("Error de calentamiento: " + e.getMessage());
                }
            }

            List<Double> quickSortTimes = new ArrayList<>();
            List<Double> mergeSortTimes = new ArrayList<>();
            List<Double> shellSortTimes = new ArrayList<>();

            for (int run = 1; run <= measuredRuns; run++) {
                System.out.println("\nPrueba " + run + ":");

                LinkedList<Integer> baseList = generateRandomList(size);

                // QuickSort
                LinkedList<Integer> quickSortList = cloneList(baseList);
                long quickSortStart = System.nanoTime();
                try {
                    quickSortList.quickSort("value", true);
                } catch (Exception e) {
                    System.out.println("QuickSort Error: " + e.getMessage());
                    continue;
                }
                long quickSortEnd = System.nanoTime();
                double quickSortTime = (quickSortEnd - quickSortStart) / 1e9;
                quickSortTimes.add(quickSortTime);
                System.out.printf("QuickSort Tiempo: %.6f segundos%n", quickSortTime);

                // MergeSort 
                LinkedList<Integer> mergeSortList = cloneList(baseList);
                long mergeSortStart = System.nanoTime();
                try {
                    mergeSortList.mergeSort("value", true);
                } catch (Exception e) {
                    System.out.println("MergeSort Error: " + e.getMessage());
                    continue;
                }
                long mergeSortEnd = System.nanoTime();
                double mergeSortTime = (mergeSortEnd - mergeSortStart) / 1e9;
                mergeSortTimes.add(mergeSortTime);
                System.out.printf("MergeSort Tiempo: %.6f segundos%n", mergeSortTime);

                // ShellSort 
                LinkedList<Integer> shellSortList = cloneList(baseList);
                long shellSortStart = System.nanoTime();
                try {
                    shellSortList.shellSort("value", true);
                } catch (Exception e) {
                    System.out.println("ShellSort Error: " + e.getMessage());
                    continue;
                }
                long shellSortEnd = System.nanoTime();
                double shellSortTime = (shellSortEnd - shellSortStart) / 1e9;
                shellSortTimes.add(shellSortTime);
                System.out.printf("ShellSort Tiempo: %.6f segundos%n", shellSortTime);
            }

            printSortingStatistics("QuickSort", quickSortTimes);
            printSortingStatistics("MergeSort", mergeSortTimes);
            printSortingStatistics("ShellSort", shellSortTimes);
        }
    }

    private static void printSortingStatistics(String sortName, List<Double> times) {
        if (times.isEmpty()) {
            System.out.println(sortName + ": No hay medidas válidas");
            return;
        }

        double avgTime = times.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double minTime = times.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        double maxTime = times.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        double standardDeviation = calculateStandardDeviation(times);

        System.out.println("\n" + sortName + " Estadisticas:");
        System.out.printf("Tiempo promedio: %.6f segundos%n", avgTime);
        System.out.printf("Tiempo minimo: %.6f segundos%n", minTime);
        System.out.printf("Tiempo maximo: %.6f segundos%n", maxTime);
        System.out.printf("Desviacion estandar: %.6f segundos%n", standardDeviation);
    }

    private static double calculateStandardDeviation(List<Double> times) {
        double mean = times.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = times.stream()
                .mapToDouble(time -> Math.pow(time - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    private static LinkedList<Integer> generateRandomList(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        Random random = new Random();
        
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(1000000)); 
        }
        
        return list;
    }

    private static LinkedList<Integer> cloneList(LinkedList<Integer> originalList) {
        LinkedList<Integer> clonedList = new LinkedList<>();
        Integer[] array = originalList.toArray();

        for (Integer item : array) {
            clonedList.add(item);
        }

        return clonedList;
    }
    
    // Practica 3 Probando Tiempos de ejecucion al buscar datos aleatorios ////////////////////////////////

    public static void performanceSearchTest() throws Exception {
        int[] arraySizes = { 10000, 20000, 25000 };
        int warmupRuns = 5;
        int measuredRuns = 10;

        for (int size : arraySizes) {
            System.out.println(
                    "\n===== Prueba de rendimiento para la busqueda en el volumen del array: " + size + " =====");

            // Fase de calentamiento
            System.out.println("Calentando...");
            for (int i = 0; i < warmupRuns; i++) {
                LinkedList<Integer> warmupList = generateRandomList(size);
                try {
                    performLinearBinarySearch(warmupList, warmupList.get(size / 2)); 
                    performBinarySearch(warmupList, warmupList.get(size / 2)); 
                } catch (Exception e) {
                    System.out.println("Error de calentamiento: " + e.getMessage());
                }
            }

            List<Double> linearBinarySearchTimes = new ArrayList<>();
            List<Double> binarySearchTimes = new ArrayList<>();

            for (int run = 1; run <= measuredRuns; run++) {
                System.out.println("\nPrueba " + run + ":");

                LinkedList<Integer> baseList = generateRandomList(size);
                int target = baseList.get(size / 2); 

                // Búsqueda Lineal Binaria
                LinkedList<Integer> linearBinarySearchList = cloneList(baseList);
                long linearStart = System.nanoTime();
                try {
                    performLinearBinarySearch(linearBinarySearchList, target);
                } catch (Exception e) {
                    System.out.println("Error en busqueda lineal binaria: " + e.getMessage());
                    continue;
                }
                long linearEnd = System.nanoTime();
                double linearTime = (linearEnd - linearStart) / 1e9;
                linearBinarySearchTimes.add(linearTime);
                System.out.printf("Tiempo Busqueda Lineal Binaria: %.6f segundos%n", linearTime);

                // Búsqueda Binaria
                LinkedList<Integer> binarySearchList = cloneList(baseList);
                binarySearchList.quickSort("value", true); 
                long binaryStart = System.nanoTime();
                try {
                    performBinarySearch(binarySearchList, target);
                } catch (Exception e) {
                    System.out.println("Error en busqueda binaria: " + e.getMessage());
                    continue;
                }
                long binaryEnd = System.nanoTime();
                double binaryTime = (binaryEnd - binaryStart) / 1e9;
                binarySearchTimes.add(binaryTime);
                System.out.printf("Tiempo Busqueda Binaria: %.6f segundos%n", binaryTime);
            }

            printSortingStatistics("Busqueda Lineal Binaria", linearBinarySearchTimes);
            printSortingStatistics("Busqueda Binaria", binarySearchTimes);
        }
    }

    private static boolean performLinearBinarySearch(LinkedList<Integer> list, int target) throws IndexOutOfBoundsException, ListEmptyException {
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i) == target) {
                return true;
            }
        }
        return false;
    }

    private static boolean performBinarySearch(LinkedList<Integer> list, int target) throws IndexOutOfBoundsException, ListEmptyException {
        int left = 0;
        int right = list.getSize() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = list.get(mid);

            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

}
