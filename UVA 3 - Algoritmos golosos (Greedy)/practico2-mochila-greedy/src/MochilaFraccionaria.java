import java.util.Arrays;
import java.util.Comparator;

public class MochilaFraccionaria {

    /**
     * Clase interna que representa cada objeto/mercancía disponible.
     */
    public static class Item {
        private String nombre;
        private double peso;
        private double valor;

        public Item(String nombre, double peso, double valor) {
            this.nombre = nombre;
            this.peso = peso;
            this.valor = valor;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPeso() {
            return peso;
        }

        public double getValor() {
            return valor;
        }

        /**
         * Calcula la densidad o valor relativo por unidad de peso (v_i / p_i).
         */
        public double getDensidad() {
            return valor / peso;
        }
    }

    /**
     * Resuelve el problema de la Mochila Fraccionaria utilizando la estrategia Greedy.
     * Complejidad Temporal: O(n log n) por la etapa de ordenamiento.
     *
     * @param items     Arreglo de mercancías disponibles.
     * @param capacidad Capacidad máxima de peso del camión/mochila (P).
     * @return El beneficio total máximo alcanzado.
     */
    public static double resolverMochilaFraccionaria(Item[] items, double capacidad) {
        // 1. Ordenar los ítems por densidad (v_i / p_i) de mayor a menor -> O(n log n)
        Arrays.sort(items, Comparator.comparingDouble(Item::getDensidad).reversed());

        double beneficioTotal = 0.0;
        double pesoActual = 0.0;

        System.out.println("=== PLAN DE CARGA DEVORADOR ===");

        // 2. Selección codiciosa/devoradora -> O(n)
        for (Item item : items) {
            if (pesoActual + item.getPeso() <= capacidad) {
                // Caso A: El objeto entra completo (x_i = 1.0)
                pesoActual += item.getPeso();
                beneficioTotal += item.getValor();
                System.out.printf("- %-10s | Tomado: 100.00%% | Peso: %6.2f | Valor aportado: %6.2f%n",
                        item.getNombre(), item.getPeso(), item.getValor());
            } else {
                // Caso B: El objeto entra de forma fraccionaria (x_i < 1.0)
                double remanente = capacidad - pesoActual;
                double fraccion = remanente / item.getPeso(); // x_i
                double valorFraccion = item.getValor() * fraccion;

                beneficioTotal += valorFraccion;
                pesoActual += remanente;

                System.out.printf("- %-10s | Tomado: %6.2f%% | Peso: %6.2f | Valor aportado: %6.2f%n",
                        item.getNombre(), fraccion * 100, remanente, valorFraccion);

                // La mochila o camión ha alcanzado su capacidad máxima
                break;
            }
        }

        System.out.println("----------------------------------------------------------------");
        System.out.printf(" Capacidad Usada: %.2f / %.2f%n", pesoActual, capacidad);
        System.out.printf(" Beneficio Total: $%.2f%n", beneficioTotal);
        System.out.println("================================================================\n");

        return beneficioTotal;
    }

    public static void main(String[] args) {
        // Configuración de la capacidad máxima (P)
        double capacidadCamion = 10.0;

        // Definición de mercancías de prueba
        Item[] inventario = new Item[]{
                new Item("Caja A", 7.0, 70.0), // Densidad: 10.0
                new Item("Caja B", 5.0, 45.0), // Densidad: 9.0
                new Item("Caja C", 5.0, 45.0)  // Densidad: 9.0
        };

        // Ejecución del algoritmo
        resolverMochilaFraccionaria(inventario, capacidadCamion);
    }
}
