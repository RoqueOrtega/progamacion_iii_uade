import java.util.Scanner;

public class TaTeTiMinMax {

    // Constantes para los jugadores y estados
    private static final char JUGADOR_HUMANO = 'O'; // MIN (-1)
    private static final char JUGADOR_IA = 'X';     // MAX (+1)
    private static final char VACIO = ' ';

    public static void main(String[] args) {
        char[][] tablero = {
                {VACIO, VACIO, VACIO},
                {VACIO, VACIO, VACIO},
                {VACIO, VACIO, VACIO}
        };

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== TA-TE-TI con Algoritmo Min-Max ===");
        imprimirTablero(tablero);

        // Bucle principal del juego
        while (true) {
            // 1. Turno del Jugador Humano (MIN)
            turnoHumano(tablero, scanner);
            imprimirTablero(tablero);

            if (esFinDelJuego(tablero)) break;

            // 2. Turno de la IA (MAX) utilizando Min-Max
            System.out.println("Turno de la IA (X)...");
            int[] mejorMovimiento = encontrarMejorMovimiento(tablero);
            tablero[mejorMovimiento[0]][mejorMovimiento[1]] = JUGADOR_IA;
            imprimirTablero(tablero);

            if (esFinDelJuego(tablero)) break;
        }

        scanner.close();
    }

    /**
     * Evalúa el tablero actual para determinar si hay un ganador o un empate.
     * @return +10 si gana la IA (MAX), -10 si gana el Humano (MIN), 0 si hay empate o juego no terminado.
     */
    private static int evaluar(char[][] b) {
        // Evaluación de Filas y Columnas
        for (int i = 0; i < 3; i++) {
            if (b[i][0] == b[i][1] && b[i][1] == b[i][2]) {
                if (b[i][0] == JUGADOR_IA) return +10;
                else if (b[i][0] == JUGADOR_HUMANO) return -10;
            }
            if (b[0][i] == b[1][i] && b[1][i] == b[2][i]) {
                if (b[0][i] == JUGADOR_IA) return +10;
                else if (b[0][i] == JUGADOR_HUMANO) return -10;
            }
        }

        // Evaluación de Diagonales
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == JUGADOR_IA) return +10;
            else if (b[0][0] == JUGADOR_HUMANO) return -10;
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == JUGADOR_IA) return +10;
            else if (b[0][2] == JUGADOR_HUMANO) return -10;
        }

        return 0; // Sin ganador por el momento o empate
    }

    /**
     * Verifica si quedan casilleros vacíos en el tablero.
     */
    private static boolean quedanMovimientos(char[][] tablero) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) return true;
            }
        }
        return false;
    }

    /**
     * Implementación recursiva del algoritmo Min-Max.
     * @param tablero Estado actual del tablero.
     * @param esMax true si es turno de MAX (IA), false si es turno de MIN (Humano).
     */
    private static int minMax(char[][] tablero, boolean esMax) {
        int puntaje = evaluar(tablero);

        // Casos Base (Nodos Terminales)
        if (puntaje == 10) return puntaje;   // Gana MAX
        if (puntaje == -10) return puntaje;  // Gana MIN
        if (!quedanMovimientos(tablero)) return 0; // Empate

        // Turno de MAX (Maximizar)
        if (esMax) {
            int mejor = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) {
                        tablero[i][j] = JUGADOR_IA; // Hacer movimiento
                        mejor = Math.max(mejor, minMax(tablero, false)); // Llamada recursiva
                        tablero[i][j] = VACIO;     // Deshacer movimiento (Backtracking)
                    }
                }
            }
            return mejor;
        }
        // Turno de MIN (Minimizar)
        else {
            int mejor = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) {
                        tablero[i][j] = JUGADOR_HUMANO; // Hacer movimiento
                        mejor = Math.min(mejor, minMax(tablero, true)); // Llamada recursiva
                        tablero[i][j] = VACIO;          // Deshacer movimiento (Backtracking)
                    }
                }
            }
            return mejor;
        }
    }

    /**
     * Evalúa todos los movimientos posibles y elige el de mayor valor para la IA.
     */
    private static int[] encontrarMejorMovimiento(char[][] tablero) {
        int mejorValor = Integer.MIN_VALUE;
        int[] mejorMovimiento = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) {
                    tablero[i][j] = JUGADOR_IA; // Probar movimiento
                    int valorMovimiento = minMax(tablero, false); // Calcular valor con Min-Max
                    tablero[i][j] = VACIO;     // Deshacer (Backtracking)

                    if (valorMovimiento > mejorValor) {
                        mejorValor = valorMovimiento;
                        mejorMovimiento[0] = i;
                        mejorMovimiento[1] = j;
                    }
                }
            }
        }
        return mejorMovimiento;
    }

    // --- Métodos Auxiliares para Entrada/Salida por Consola ---

    private static void turnoHumano(char[][] tablero, Scanner scanner) {
        int fila, col;
        while (true) {
            System.out.print("Ingresa tu jugada (Fila [0-2] y Columna [0-2] separadas por espacio): ");
            fila = scanner.nextInt();
            col = scanner.nextInt();
            if (fila >= 0 && fila < 3 && col >= 0 && col < 3 && tablero[fila][col] == VACIO) {
                tablero[fila][col] = JUGADOR_HUMANO;
                break;
            }
            System.out.println("Jugada inválida o casilla ocupada. Intenta de nuevo.");
        }
    }

    private static boolean esFinDelJuego(char[][] tablero) {
        int resultado = evaluar(tablero);
        if (resultado == 10) {
            System.out.println("¡La IA (X) ha ganado!");
            return true;
        }
        if (resultado == -10) {
            System.out.println("¡Felicidades, ganaste (O)!");
            return true;
        }
        if (!quedanMovimientos(tablero)) {
            System.out.println("¡Empate!");
            return true;
        }
        return false;
    }

    private static void imprimirTablero(char[][] tablero) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tablero[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }
}
