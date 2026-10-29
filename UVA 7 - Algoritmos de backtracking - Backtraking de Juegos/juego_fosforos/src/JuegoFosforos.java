import java.util.Scanner;

public class JuegoFosforos {

    // Regla: Se pueden retirar de 1 a 3 fósforos por turno
    private static final int MIN_RETIRO = 1;
    private static final int MAX_RETIRO = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== BIENVENIDO AL JUEGO DE LOS FÓSFOROS ===");
        System.out.print("Ingresa la cantidad inicial de fósforos en la pila (ej. 13 o 21): ");
        int fosforosTotales = scanner.nextInt();

        if (fosforosTotales < 1) {
            System.out.println("La cantidad inicial debe ser mayor a 0.");
            scanner.close();
            return;
        }

        System.out.println("\n¿Quién querés que comience?");
        System.out.println("1. Empieza el Jugador (Humano)");
        System.out.println("2. Empieza la Computadora (IA)");
        System.out.print("Elige una opción (1 o 2): ");
        int opcion = scanner.nextInt();

        boolean turnoHumano = (opcion == 1);

        // Bucle principal del juego
        while (fosforosTotales > 0) {
            imprimirPila(fosforosTotales);

            if (turnoHumano) {
                // Turno del Jugador
                int retiro = obtenerJugadaHumana(scanner, fosforosTotales);
                fosforosTotales -= retiro;
                System.out.println("Retiraste " + retiro + " fósforo(s).\n");

                if (fosforosTotales == 0) {
                    System.out.println("¡Felicitaciones! Retiraste el último fósforo. ¡Has ganado!");
                    break;
                }
            } else {
                // Turno de la Computadora (Estrategia matemática / Óptima)
                System.out.println("Turno de la Computadora...");
                int retiro = calcularJugadaIA(fosforosTotales);
                fosforosTotales -= retiro;
                System.out.println("La computadora retiró " + retiro + " fósforo(s).\n");

                if (fosforosTotales == 0) {
                    System.out.println("¡La computadora ha retirado el último fósforo! Gana la computadora.");
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
            System.out.print("¿Cuántos fósforos deseas retirar (1 a " + Math.min(MAX_RETIRO, fosforosRestantes) + ")? ");
            retiro = scanner.nextInt();

            int maxValido = Math.min(MAX_RETIRO, fosforosRestantes);
            if (retiro >= MIN_RETIRO && retiro <= maxValido) {
                break;
            }
            System.out.println("Jugada inválida. Debes retirar entre " + MIN_RETIRO + " y " + maxValido + " fósforo(s).");
        }
        return retiro;
    }

    /**
     * Calcula la jugada óptima de la IA basada en la aritmética modular (Múltiplos de 4).
     * Si la IA deja al rival con un múltiplo de 4, asegura la victoria si juega perfecto.
     */
    private static int calcularJugadaIA(int fosforosRestantes) {
        int residuo = fosforosRestantes % (MAX_RETIRO + 1);

        // Si el residuo es 0, la IA está en una posición "perdedora" matemática temporal.
        // Retira 1 por defecto esperando un error del humano.
        if (residuo == 0) {
            return 1;
        } else {
            // Deja al rival con un múltiplo de (MAX_RETIRO + 1)
            return residuo;
        }
    }

    /**
     * Dibuja de manera gráfica los fósforos restantes en la consola.
     */
    private static void imprimirPila(int cantidad) {
        System.out.println("--------------------------------------------------");
        System.out.println("Fósforos restantes en la pila: " + cantidad);
        System.out.print("Pila: ");
        for (int i = 0; i < cantidad; i++) {
            System.out.print("| ");
        }
        System.out.println("\n--------------------------------------------------");
    }
}
