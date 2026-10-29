import java.util.Scanner;

public class JuegoFosforosMisere {

    private static final int MIN_RETIRO = 1;
    private static final int MAX_RETIRO = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Cantidad inicial de fósforos según la consigna de la imagen
        int fosforosTotales = 41;

        System.out.println("=== JUEGO DE LOS FÓSFOROS (Versión: Pierde el que retira el último) ===");
        System.out.println("Cantidad inicial de fósforos: " + fosforosTotales + "[cite: 2]");
        System.out.println("Regla: Cada jugador retira 1, 2 o 3 fósforos por turno[cite: 2].");
        System.out.println("¡Atención! El jugador que retire el último fósforo pierde la partida[cite: 2].\n");

        System.out.println("¿Quién querés que comience?");
        System.out.println("1. Empieza el Jugador (Humano)");
        System.out.println("2. Empieza la Computadora (IA)");
        System.out.print("Elige una opción (1 o 2): ");
        int opcion = scanner.nextInt();

        boolean turnoHumano = (opcion == 1);

        // Bucle principal del juego
        while (fosforosTotales > 0) {
            imprimirPila(fosforosTotales);

            if (turnoHumano) {
                // Turno del Jugador Humano
                int retiro = obtenerJugadaHumana(scanner, fosforosTotales);
                fosforosTotales -= retiro;
                System.out.println("Retiraste " + retiro + " fósforo(s).\n");

                if (fosforosTotales == 0) {
                    System.out.println("¡Has retirado el último fósforo! Has perdido la partida.");
                    break;
                }
            } else {
                // Turno de la Computadora (Estrategia óptima)
                System.out.println("Turno de la Computadora...");
                int retiro = calcularJugadaIA(fosforosTotales);
                fosforosTotales -= retiro;
                System.out.println("La computadora retiró " + retiro + " fósforo(s).\n");

                if (fosforosTotales == 0) {
                    System.out.println("¡La computadora ha retirado el último fósforo! La computadora pierde, ¡has ganado!");
                    break;
                }
            }

            // Alternar turnos
            turnoHumano = !turnoHumano;
        }

        scanner.close();
    }

    /**
     * Valida y obtiene la cantidad de fósforos que desea retirar el humano.
     */
    private static int obtenerJugadaHumana(Scanner scanner, int fosforosRestantes) {
        int retiro;
        while (true) {
            int maxValido = Math.min(MAX_RETIRO, fosforosRestantes);
            System.out.print("¿Cuántos fósforos deseas retirar (1 a " + maxValido + ")? ");
            retiro = scanner.nextInt();

            if (retiro >= MIN_RETIRO && retiro <= maxValido) {
                break;
            }
            System.out.println("Jugada inválida. Debes retirar entre " + MIN_RETIRO + " y " + maxValido + " fósforo(s).");
        }
        return retiro;
    }

    /**
     * Calcula la jugada óptima de la IA para la versión donde pierde el último.
     * La estrategia ganadora consiste en dejar al oponente con un número de fósforos
     * congruente a 1 módulo 4 (es decir, 1, 5, 9, 13, etc.).
     */
    private static int calcularJugadaIA(int fosforosRestantes) {
        if (fosforosRestantes == 1) {
            return 1; // Si queda 1, la IA está obligada a tomarlo y perder.
        }

        int residuo = (fosforosRestantes - 1) % (MAX_RETIRO + 1);

        if (residuo == 0) {
            // Posición perdedora teórica actual: la IA toma 1 por defecto para estirar la partida.
            return 1;
        } else {
            // Deja al rival en una posición segura de residuo 1 (módulo 4).
            return residuo;
        }
    }

    /**
     * Muestra el estado actual de los fósforos.
     */
    private static void imprimirPila(int cantidad) {
        System.out.println("--------------------------------------------------");
        System.out.println("Fósforos restantes en la pila: " + cantidad);
        if (cantidad <= 30) {
            System.out.print("Pila: ");
            for (int i = 0; i < cantidad; i++) {
                System.out.print("| ");
            }
            System.out.println();
        } else {
            System.out.println("Pila de fósforos [|||| ... " + cantidad + " unidades restantes]");
        }
        System.out.println("--------------------------------------------------");
    }
}
