package org.cuatrovientos.psp.tamago;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Cuidador {

    static HashMap<String, Tamagotchi> tamagos = new HashMap<>();
    static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {

        int cuantosTamagotchis = getIntInput("¿Cuántos tamagotchis tendrás? -> ", false);

        for (int i = 0; i < cuantosTamagotchis; i++) {
            String nombre = getStringInput("¿Cómo se llamará el tamagotchi " + (i + 1) + "? -> ", false);
            while (tamagos.containsKey(nombre)) {
                System.out.println("Ya existe un tamagotchi con ese nombre");
                nombre = getStringInput("¿Cómo se llamará el tamagotchi " + (i + 1) + "? -> ", false);
            }
            tamagos.put(nombre, new Tamagotchi(nombre));
        }
    }

    protected static int getIntInput(String msg, boolean newline) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(SC.nextLine() + (newline ? "\n" : ""));
            } catch (Exception e) {
                System.out.println("Valor no válido");
            }
        }
    }

    protected static int getIntInput(String msg, boolean newline, ArrayList<Integer> validValues) {
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
        } while (!input.isEmpty());
        return input;
    }

}
