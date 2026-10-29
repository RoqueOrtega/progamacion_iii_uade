import java.util.Scanner;

public class TaTeTiAlfaBeta {

    private static final char JUGADOR_HUMANO = 'O'; // MIN (-10)
    private static final char JUGADOR_IA = 'X';     // MAX (+10)
    private static final char VACIO = ' ';

    public static void main(String[] args) {
        char[][] tablero = {
                {VACIO, VACIO, VACIO},
                {VACIO, VACIO, VACIO},
                {VACIO, VACIO, VACIO}
        };

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== TA-TE-TI con Poda Alfa-Beta y Profundidad ===");
        imprimirTablero(tablero);

        while (true) {
            // Turno del Jugador Humano (MIN)
            turnoHumano(tablero, scanner);
            imprimirTablero(tablero);

            if (esFinDelJuego(tablero)) break;

            // Turno de la IA (MAX) con Alfa-Beta
            System.out.println("Turno de la IA (X)...");
            int[] mejorMovimiento = encontrarMejorMovimiento(tablero);
            tablero[mejorMovimiento[0]][mejorMovimiento[1]] = JUGADOR_IA;
            imprimirTablero(tablero);

            if (esFinDelJuego(tablero)) break;
        }

        scanner.close();
    }

    /**
     * Evalúa el estado del tablero.
     * Retorna +10 si gana MAX, -10 si gana MIN, 0 si no hay victoria.
     */
    private static int evaluar(char[][] b) {
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

        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == JUGADOR_IA) return +10;
            else if (b[0][0] == JUGADOR_HUMANO) return -10;
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == JUGADOR_IA) return +10;
            else if (b[0][2] == JUGADOR_HUMANO) return -10;
        }

        return 0;
    }

    private static boolean quedanMovimientos(char[][] tablero) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) return true;
            }
        }
        return false;
    }

    /**
     * Algoritmo Min-Max optimizado con Poda Alfa-Beta y Factor de Profundidad.
     */
    private static int minMaxAlfaBeta(char[][] tablero, int profundidad, boolean esMax, int alfa, int beta) {
        int puntaje = evaluar(tablero);

        // Ajuste por profundidad:
        // - Si gana MAX rápido, el puntaje es más alto (10 - profundidad).
        // - Si gana MIN rápido, el puntaje es más bajo (-10 + profundidad).
        if (puntaje == 10) return puntaje - profundidad;
        if (puntaje == -10) return puntaje + profundidad;
        if (!quedanMovimientos(tablero)) return 0;

        // Turno de MAX (Maximizar)
        if (esMax) {
            int mejor = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == VACIO) {
                        tablero[i][j] = JUGADOR_IA;

                        mejor = Math.max(mejor, minMaxAlfaBeta(tablero, profundidad + 1, false, alfa, beta));
                        tablero[i][j] = VACIO; // Backtracking

                        alfa = Math.max(alfa, mejor);

                        // Poda Alfa-Beta
                        if (beta <= alfa) {
                            break;
                        }
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
                        tablero[i][j] = JUGADOR_HUMANO;

                        mejor = Math.min(mejor, minMaxAlfaBeta(tablero, profundidad + 1, true, alfa, beta));
                        tablero[i][j] = VACIO; // Backtracking

                        beta = Math.min(beta, mejor);

                        // Poda Alfa-Beta
                        if (beta <= alfa) {
                            break;
                        }
                    }
                }
            }
            return mejor;
        }
    }

    /**
     * Selecciona el movimiento óptimo inicializando Alfa (-infinito) y Beta (+infinito).
     */
    private static int[] encontrarMejorMovimiento(char[][] tablero) {
        int mejorValor = Integer.MIN_VALUE;
        int[] mejorMovimiento = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) {
                    tablero[i][j] = JUGADOR_IA;

                    int valorMovimiento = minMaxAlfaBeta(tablero, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    tablero[i][j] = VACIO;

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
