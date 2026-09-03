import java.util.*;

public class AlgoritmoKruskal {

    // Clase para representar las aristas
    static class Arista implements Comparable<Arista> {
        int origen, destino, peso;

        public Arista(int origen, int destino, int peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public int compareTo(Arista otra) {
            return Integer.compare(this.peso, otra.peso);
        }
    }

    // Estructura de Datos para Conjuntos Disjuntos (Disjoint Set Union)
    static class DSU {
        private final int[] padre;
        private final int[] rango;

        public DSU(int n) {
            padre = new int[n];
            rango = new int[n];
            for (int i = 0; i < n; i++) {
                padre[i] = i;
                rango[i] = 0;
            }
        }

        public int buscar(int i) {
            if (padre[i] != i) {
                padre[i] = buscar(padre[i]); // Path Compression
            }
            return padre[i];
        }

        public boolean unior(int i, int j) {
            int raizI = buscar(i);
            int raizJ = buscar(j);

            if (raizI != raizJ) {
                // Union by Rank
                if (rango[raizI] < rango[raizJ]) {
                    padre[raizI] = raizJ;
                } else if (rango[raizI] > rango[raizJ]) {
                    padre[raizJ] = raizI;
                } else {
                    padre[raizJ] = raizI;
                    rango[raizI]++;
                }
                return true;
            }
            return false;
        }
    }

    public static void resolverKruskal(int numVertices, List<Arista> aristas, String[] nombresNodos) {
        Collections.sort(aristas); // Ordenamiento Greedy
        DSU dsu = new DSU(numVertices);

        List<Arista> arm = new ArrayList<>();
        int costoTotal = 0;

        for (Arista arista : aristas) {
            if (dsu.unior(arista.origen, arista.destino)) {
                arm.add(arista);
                costoTotal += arista.peso;
            }
        }

        System.out.println("=== ÁRBOLES DE RECUBRIMIENTO MÍNIMO (KRUSKAL) ===");
        for (Arista a : arm) {
            System.out.printf("Arista (%s, %s) -> Peso: %d%n",
                    nombresNodos[a.origen], nombresNodos[a.destino], a.peso);
        }
        System.out.println("Costo Total Mínimo: " + costoTotal);
    }
}
