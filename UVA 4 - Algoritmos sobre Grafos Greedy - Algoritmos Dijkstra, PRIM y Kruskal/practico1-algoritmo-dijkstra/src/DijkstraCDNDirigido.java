import java.util.*;

public class DijkstraCDNDirigido {

    static class Arista {
        char destino;
        int peso;

        public Arista(char destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    static class NodoDistancia implements Comparable<NodoDistancia> {
        char nodo;
        int distancia;

        public NodoDistancia(char nodo, int distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(NodoDistancia o) {
            return Integer.compare(this.distancia, o.distancia);
        }
    }

    private final Map<Character, List<Arista>> grafo = new HashMap<>();

    // Grafo DIRIGIDO: Solo agrega la conexión origen -> destino
    public void agregarAristaDirigida(char origen, char destino, int peso) {
        grafo.computeIfAbsent(origen, k -> new ArrayList<>()).add(new Arista(destino, peso));
    }

    public void ejecutarDijkstra(char origen) {
        Map<Character, Integer> distancias = new HashMap<>();
        Map<Character, Character> predecesores = new HashMap<>();
        PriorityQueue<NodoDistancia> pq = new PriorityQueue<>();
        Set<Character> visitados = new HashSet<>();

        for (char nodo : new char[]{'A', 'B', 'C', 'D', 'E', 'F'}) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }

        distancias.put(origen, 0);
        pq.add(new NodoDistancia(origen, 0));

        while (!pq.isEmpty()) {
            NodoDistancia actual = pq.poll();
            char u = actual.nodo;

            if (visitados.contains(u)) continue;
            visitados.add(u);

            for (Arista arista : grafo.getOrDefault(u, Collections.emptyList())) {
                char v = arista.destino;
                int peso = arista.peso;

                if (!visitados.contains(v) && distancias.get(u) != Integer.MAX_VALUE
                        && distancias.get(u) + peso < distancias.get(v)) {

                    distancias.put(v, distancias.get(u) + peso);
                    predecesores.put(v, u);
                    pq.add(new NodoDistancia(v, distancias.get(v)));
                }
            }
        }

        imprimirResultados(distancias, predecesores);
    }

    private void imprimirResultados(Map<Character, Integer> distancias, Map<Character, Character> predecesores) {
        System.out.println("Resultados Correctos (Grafo Dirigido):");
        for (char nodo : distancias.keySet()) {
            System.out.printf("Hasta '%c': %2d ms | Ruta: %s%n",
                    nodo, distancias.get(nodo), obtenerRuta(nodo, predecesores));
        }
    }

    private String obtenerRuta(char destino, Map<Character, Character> predecesores) {
        List<Character> camino = new ArrayList<>();
        for (Character at = destino; at != null; at = predecesores.get(at)) {
            camino.add(at);
        }
        Collections.reverse(camino);
        return String.join(" -> ", camino.stream().map(Object::toString).toArray(String[]::new));
    }

    public static void main(String[] args) {
        DijkstraCDNDirigido cdn = new DijkstraCDNDirigido();

        // Conexiones dirigidas explícitas
        cdn.agregarAristaDirigida('A', 'B', 4);
        cdn.agregarAristaDirigida('A', 'C', 2);
        cdn.agregarAristaDirigida('B', 'C', 1);
        cdn.agregarAristaDirigida('B', 'D', 5);
        cdn.agregarAristaDirigida('C', 'D', 8);
        cdn.agregarAristaDirigida('C', 'E', 10);
        cdn.agregarAristaDirigida('D', 'E', 2);
        cdn.agregarAristaDirigida('D', 'F', 6);
        cdn.agregarAristaDirigida('E', 'F', 3);

        cdn.ejecutarDijkstra('A');
    }
}