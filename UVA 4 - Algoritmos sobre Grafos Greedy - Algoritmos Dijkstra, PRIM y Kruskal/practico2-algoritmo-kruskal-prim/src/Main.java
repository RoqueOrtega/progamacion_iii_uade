import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] nombresNodos = {"A", "B", "C", "D", "E"};
        int numVertices = 5;

        // --- 1. EJECUCIÓN KRUSKAL ---
        List<AlgoritmoKruskal.Arista> aristasKruskal = new ArrayList<>();
        aristasKruskal.add(new AlgoritmoKruskal.Arista(0, 1, 4)); // (A, B)
        aristasKruskal.add(new AlgoritmoKruskal.Arista(0, 2, 2)); // (A, C)
        aristasKruskal.add(new AlgoritmoKruskal.Arista(1, 2, 1)); // (B, C)
        aristasKruskal.add(new AlgoritmoKruskal.Arista(1, 3, 5)); // (B, D)
        aristasKruskal.add(new AlgoritmoKruskal.Arista(2, 3, 8)); // (C, D)
        aristasKruskal.add(new AlgoritmoKruskal.Arista(2, 4, 10));// (C, E)
        aristasKruskal.add(new AlgoritmoKruskal.Arista(3, 4, 2)); // (D, E)

        AlgoritmoKruskal.resolverKruskal(numVertices, aristasKruskal, nombresNodos);

        // --- 2. EJECUCIÓN PRIM ---
        List<List<AlgoritmoPrim.AristaAdj>> grafoPrim = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            grafoPrim.add(new ArrayList<>());
        }

        agregarAristaGrafo(grafoPrim, 0, 1, 4); // (A, B)
        agregarAristaGrafo(grafoPrim, 0, 2, 2); // (A, C)
        agregarAristaGrafo(grafoPrim, 1, 2, 1); // (B, C)
        agregarAristaGrafo(grafoPrim, 1, 3, 5); // (B, D)
        agregarAristaGrafo(grafoPrim, 2, 3, 8); // (C, D)
        agregarAristaGrafo(grafoPrim, 2, 4, 10);// (C, E)
        agregarAristaGrafo(grafoPrim, 3, 4, 2); // (D, E)

        AlgoritmoPrim.resolverPrim(numVertices, grafoPrim, 0, nombresNodos);
    }

    private static void agregarAristaGrafo(List<List<AlgoritmoPrim.AristaAdj>> g, int u, int v, int peso) {
        g.get(u).add(new AlgoritmoPrim.AristaAdj(v, peso));
        g.get(v).add(new AlgoritmoPrim.AristaAdj(u, peso));
    }
}
