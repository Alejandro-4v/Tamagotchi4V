package org.cuatrovientos.psp.tamago;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import static org.cuatrovientos.psp.tamago.Tamagotchi.tamagotchiJugando;

public class Cuidador {

    static HashMap<String, Tamagotchi> tamagos = new HashMap<>();
    static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        int cuantosTamagotchis = getIntInput("¿Cuántos tamagotchis tendrás? -> ", false);

        for (int i = 0; i < cuantosTamagotchis; i++) {
            String nombre = getStringInput("¿Cómo se llamará el tamagotchi " + (i + 1) + "? -> ", false);
            while (tamagos.containsKey(nombre)) {
                System.out.println("Ya existe un tamagotchi con ese nombre");
                nombre = getStringInput("¿Cómo se llamará el tamagotchi " + (i + 1) + "? -> ", false);
            }
            tamagos.put(nombre.toLowerCase(), new Tamagotchi(nombre));
        }

        for (Tamagotchi tamago : tamagos.values()) {
            Thread tamagoThread = new Thread(tamago);
            tamagoThread.start();
        }

        while (algunTamagotchiVivo()) {

            if (!tamagotchiJugando) {
                int accion = getIntInput(getMenu(), false, new ValidIntValues(1, 6));

                if (accion == 6) {
                    break;
                }

                ArrayList<String> nombresTamagosOciosos = getNombresTamagotchisOciosos();

                switch (accion) {
                    case 1:
                        if (nombresTamagosOciosos.isEmpty()) {
                            System.out.println("Todavía no hay Tamagotchis ociosos, están todos ocupados (o muertos)");
                            break;
                        }

                        String tamagoParaAlimentar = getStringInput("¿A qué Tamagotchi alimentarás?", true, nombresTamagosOciosos);

                        tamagos.get(tamagoParaAlimentar).comerManzana();
                        break;

                    case 2:
                        if (nombresTamagosOciosos.isEmpty()) {
                            System.out.println("Todavía no hay Tamagotchis ociosos, están todos ocupados (o muertos)");
                            break;
                        }

                        String tamagoParaAsear = getStringInput("¿A qué Tamagotchi vas a asear?", true, nombresTamagosOciosos);

                        tamagos.get(tamagoParaAsear).asear();
                        break;

                    case 3:

                        if (nombresTamagosOciosos.isEmpty()) {
                            System.out.println("Todavía no hay Tamagotchis ociosos, están todos ocupados (o muertos)");
                            break;
                        }

                        String tamagoParaJugar = getStringInput("¿Con qué Tamagotchi vas a jugar?", true, nombresTamagosOciosos);

                        tamagos.get(tamagoParaJugar).jugar();
                        break;

                    case 4:

                        if (nombresTamagosOciosos.isEmpty()) {
                            System.out.println("Todavía no hay Tamagotchis ociosos, están todos ocupados (o muertos)");
                            break;
                        }

                        String tamagoParaMatar = getStringInput("¿A qué Tamagotchi quieres matar (con cariño)?", true, nombresTamagosOciosos);

                        tamagos.get(tamagoParaMatar).kill();
                        break;

                    case 5:

                        for (Tamagotchi tamago : tamagos.values()) {
                            System.out.println("Estado de " + tamago.getNombre() + ": " + tamago.getEstado());
                        }
                        break;

                    default:
                        throw new RuntimeException("Unexpected 'accion' value: " + accion);
                }
            }

        }

        System.exit(0);
    }

    protected static int getIntInput(String msg, boolean newline) {
        while (true) {
            try {
                System.out.print(msg);
                int input = Integer.parseInt(SC.nextLine() + (newline ? "\n" : ""));
                if (input <= 0) {
                    throw new RuntimeException();
                }
                return input;
            } catch (Exception e) {
                System.out.println("Valor no válido");
            }
        }
    }

    protected static int getIntInput(String msg, boolean newline, ValidIntValues validValues) {
        int input;
        do {
            input = getIntInput(msg, newline);
        } while (!validValues.contains(input));
        return input;
    }

    protected static String getStringInput(String msg, boolean newline) {
        String input;
        do {
            System.out.print(msg + (newline ? "\n" : ""));
            input = SC.nextLine();
        } while (input.isEmpty());
        return input;
    }

    protected static String getStringInput(String msg, boolean newline, ArrayList<String> tamagosValidos) {
        String input;
        do {
            listTamagotchis(tamagosValidos);
            System.out.print(msg + (newline ? "\n" : ""));
            input = SC.nextLine();
        } while (!tamagosValidos.contains(input.toLowerCase()));
        return input.toLowerCase();
    }

    protected static boolean algunTamagotchiVivo() {
        for (Tamagotchi tamago : tamagos.values()) {
            if (tamago.getEstado() != Tamagotchi.Estado.MUERTO) {
                return true;
            }
        }
        return false;
    }

    private static String getMenu() {
        return "1 -> Alimentar a un Tamagotchi\n" +
                "2 -> Asear a un Tamagotchi\n" +
                "3 -> Jugar con un Tamagotchi\n" +
                "4 -> Matar a un Tamagotchi\n" +
                "5 -> Ver estado de mis Tamagotchis\n" +
                "6 -> Salir\n" +
                "¿Qué quieres hacer? -> ";
    }

    private static ArrayList<String> getNombresTamagotchisOciosos() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Tamagotchi tamago : tamagos.values()) {
            if (tamago.getEstado() == Tamagotchi.Estado.VIVO) {
                nombres.add(tamago.getNombre().toLowerCase());
            }
        }
        return nombres;
    }

    private static void listTamagotchis(ArrayList<String> nombres) {
        for (String s : nombres) {
            System.out.println(StringUtils.capitalize(s));
        }
    }
}
