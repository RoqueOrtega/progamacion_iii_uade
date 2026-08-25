import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CambioGreedy {

    /**
     * Clase auxiliar para almacenar el resultado del algoritmo.
     */
    public static class ResultadoCambio {
        private final int totalMonedas;
        private final Map<Integer, Integer> desglose;

        public ResultadoCambio(int totalMonedas, Map<Integer, Integer> desglose) {
            this.totalMonedas = totalMonedas;
            this.desglose = desglose;
        }

        public int getTotalMonedas() {
            return totalMonedas;
        }

        public Map<Integer, Integer> getDesglose() {
            return desglose;
        }

        @Override
        public String toString() {
            return "Total de Monedas: " + totalMonedas + " | Desglose: " + desglose;
        }
    }

    /**
     * Implementación del Algoritmo Voraz (Greedy) para el Problema del Cambio.
     *
     * @param monto Monto exacto a devolver.
     * @param denominaciones Arreglo con las monedas disponibles.
     * @return Objeto ResultadoCambio con la cantidad de monedas y su desglose.
     */
    public static ResultadoCambio calcularCambioGreedy(int monto, Integer[] denominaciones) {
        // Aseguramos que las denominaciones estén ordenadas de mayor a menor
        Arrays.sort(denominaciones, Collections.reverseOrder());

        Map<Integer, Integer> desglose = new LinkedHashMap<>();
        int totalMonedas = 0;
        int montoRestante = monto;

        for (int moneda : denominaciones) {
            if (moneda <= montoRestante) {
                int cantidad = montoRestante / moneda; // Operación DIV (División entera)

                desglose.put(moneda, cantidad);
                totalMonedas += cantidad;

                montoRestante %= moneda; // Operación MOD (Residuo)
            }

            if (montoRestante == 0) {
                break; // Se completó el cambio exacto
            }
        }

        // Si no se pudo dar el cambio exacto con las denominaciones disponibles
        if (montoRestante > 0) {
            System.out.println("ADVERTENCIA: No fue posible entregar el cambio exacto con el sistema monetario actual.");
        }

        return new ResultadoCambio(totalMonedas, desglose);
    }

    public static void main(String[] args) {
        System.out.println("=== PROGRAMACIÓN III: CASOS DE PRUEBA ALGORITMO GREEDY ===\n");

        // ------------------------------------------------------------------------
        // CASO DE PRUEBA 1: Sistema Monetario A (Canónico)
        // ------------------------------------------------------------------------
        Integer[] sistemaA = {100, 50, 20, 10, 5, 2, 1};
        //Integer[] sistemaA = {240, 60, 24, 12, 6, 3, 2, 1};
        //Integer[] sistemaA = {240, 60, 30, 24, 12, 6, 3, 2, 1};
        int montoA = 288;

        System.out.println("1. SISTEMA A (Moneda Actual - Canónico)");
        System.out.println("   Denominaciones: " + Arrays.toString(sistemaA));
        System.out.println("   Monto a devolver: $" + montoA);

        ResultadoCambio resA = calcularCambioGreedy(montoA, sistemaA);
        System.out.println("   Resultado Greedy: " + resA);
        System.out.println("   -> Resultado Óptimo Garantizado.\n");

        // ------------------------------------------------------------------------
        // CASO DE PRUEBA 2: Sistema Monetario B (No Canónico - Falla de Optimalidad)
        // ------------------------------------------------------------------------
        Integer[] sistemaB = {4, 3, 1};
        int montoB = 6;

        System.out.println("2. SISTEMA B (Moneda Especial - No Canónico)");
        System.out.println("   Denominaciones: " + Arrays.toString(sistemaB));
        System.out.println("   Monto a devolver: $" + montoB);

        ResultadoCambio resB = calcularCambioGreedy(montoB, sistemaB);
        System.out.println("   Resultado Greedy: " + resB);
        System.out.println("   -> Solución Óptima Real: 2 monedas de $3 (3 + 3 = 6)");
        System.out.println("   -> Demostración: El algoritmo Greedy entrega 3 monedas (4 + 1 + 1), por lo cual NO alcanza el óptimo global.");
    }
}