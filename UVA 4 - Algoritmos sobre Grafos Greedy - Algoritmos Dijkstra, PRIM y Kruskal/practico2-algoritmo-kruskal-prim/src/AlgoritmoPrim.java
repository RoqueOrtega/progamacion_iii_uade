import java.util.*;

public class AlgoritmoPrim {

    static class AristaAdj {
        int destino, peso;

        public AristaAdj(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    static class ElementoHeap implements Comparable<ElementoHeap> {
        int origen, destino, peso;

        public ElementoHeap(int origen, int destino, int peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public int compareTo(ElementoHeap otro) {
            return Integer.compare(this.peso, otro.peso);
        }
    }

    public static void resolverPrim(int numVertices, List<List<AristaAdj>> grafo, int nodoInicial, String[] nombresNodos) {
        boolean[] visitado = new boolean[numVertices];
        PriorityQueue<ElementoHeap> pq = new PriorityQueue<>();

        // Marcar nodo inicial y agregar sus aristas adyacentes
        visitado[nodoInicial] = true;
        for (AristaAdj adj : grafo.get(nodoInicial)) {
            pq.add(new ElementoHeap(nodoInicial, adj.destino, adj.peso));
        }

        List<ElementoHeap> arm = new ArrayList<>();
        int costoTotal = 0;

        while (!pq.isEmpty() && arm.size() < numVertices - 1) {
            ElementoHeap actual = pq.poll();

            if (visitado[actual.destino]) continue;

            visitado[actual.destino] = true;
            arm.add(actual);
            costoTotal += actual.peso;

            for (AristaAdj adj : grafo.get(actual.destino)) {
                if (!visitado[adj.destino]) {
                    pq.add(new ElementoHeap(actual.destino, adj.destino, adj.peso));
                }
            }
        }

        System.out.println("\n=== ÁRBOLES DE RECUBRIMIENTO MÍNIMO (PRIM) ===");
        for (ElementoHeap e : arm) {
            System.out.printf("Arista (%s, %s) -> Peso: %d%n",
                    nombresNodos[e.origen], nombresNodos[e.destino], e.peso);
        }
        System.out.println("Costo Total Mínimo: " + costoTotal);
    }
}