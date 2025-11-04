package org.cuatrovientos.psp.tamago;

import java.util.Random;
import java.util.Date;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class Tamagotchi implements Runnable {

    // Variable que será modificada para gestionar los juegos concurrentes
    public static boolean tamagotchiJugando = false;

    // Enum para controlar estados
    private enum Estado {
        VIVO, ASEANDO, JUGANDO, COMIENDO, MUERTO
    }

    // Estáticas con distintos usos
    private static final Random RANDOM = new Random(); // Random para lo que tarda en comer
    private static Date date; // Fecha actual que se irá actualizando para medir tiempos en consola
    private static final Scanner SC = new Scanner(System.in); // Scanner para el juego

    // Variables generales
    private String nombre;

    private int tiempoParaComer;

    private Estado estado;

    // Variables para medir tiempos, estados y saber si mostrar o no mostrar ciertos
    // mensajes
    private long nacimiento;
    private long ultimoAseo;
    private long inicioComida;

    private boolean avisadoRiesgoDeMuerte = false;

    // Variables para el juego
    private int numeroParaElCuidador;

    public Tamagotchi(String nombre) {
        this.nombre = nombre;
        this.tiempoParaComer = RANDOM.nextInt(5);
        this.nacimiento = this.ultimoAseo = System.currentTimeMillis();
        this.estado = Estado.VIVO;
    }

    @Override
    public void run() {
        while (estado != Estado.MUERTO) {

            if (estado == Estado.VIVO && getTiempoDeVida() >= 300000) {
                kill();
                break;
            }
            if (estado == Estado.VIVO) {
                long tiempoUltimoAseo = getTiempoDesdeUltimoAseo();
                if (tiempoUltimoAseo >= 100000 && tiempoUltimoAseo < 200000 && !avisadoRiesgoDeMuerte) {
                    System.out.println("El tamagotchi " + this.nombre + " está a punto de morir por cochino");
                    avisadoRiesgoDeMuerte = true;
                } else if (getTiempoDesdeUltimoAseo() >= 200000) {
                    stinkyDeath();
                    break;
                }
            }

            if (estado == Estado.COMIENDO) {
                if (getTiempoDesdeInicioComida() >= tiempoParaComer * 1000) {
                    date = new Date();
                    System.out
                            .println("El tamagotchi " + this.nombre + " ha terminado de comer a las " + date.getHours()
                                    + ":" + date.getMinutes() + ":" + date.getSeconds());
                    estado = Estado.VIVO;
                }
            }

            if (estado == Estado.JUGANDO) {
                int num1 = RANDOM.nextInt(10);
                int num2 = RANDOM.nextInt(10);
                while (num1 + num2 > 10) {
                    num1 = RANDOM.nextInt(10);
                    num2 = RANDOM.nextInt(10);
                }
                System.out.println("La suma entre " + num1 + " y " + num2 + " es...");
                int respuesta = SC.nextInt();
                while (respuesta != num1 + num2) {
                    System.out.println("La suma entre " + num1 + " y " + num2 + " es...");
                    respuesta = SC.nextInt();
                }
                System.out.println("Correcto!");
                tamagotchiJugando = false;
                estado = Estado.VIVO;
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    // Función para empezar el aseo (y modificar el estado, la lógica está en el
    // run)
    public void asear() {
        if (estado == Estado.ASEANDO) {
            System.out.println("El tamagotchi " + this.nombre + " ya se está aseando...");
        } else if (estado != Estado.VIVO) {
            System.out.println("El tamagotchi " + this.nombre + " está ocupado, todavía no puede bañarse...");
        } else {
            System.out.println("El tamagotchi " + this.nombre + " está aseándose...");
            estado = Estado.ASEANDO;
        }
    }

    // Función para empezar a comer (y modificar el estado, la lógica está en el
    // run)
    public void comerManzana() {
        if (estado == Estado.COMIENDO) {
            System.out.println("El tamagotchi " + this.nombre + " ya está comiendo...");
        } else if (estado != Estado.VIVO) {
            System.out.println("El tamagotchi " + this.nombre + " está ocupado, todavía no puede comer...");
        } else {
            date = new Date();
            System.out.println("El tamagotchi " + this.nombre + " ha empezado a comer a las " + date.getHours() + ":"
                    + date.getMinutes() + ":" + date.getSeconds());
            estado = Estado.COMIENDO;
            inicioComida = System.currentTimeMillis();
        }
    }

    // Función para empezar a jugar (y modificar el estado, la lógica está en el
    // run)
    public void jugar() {
        if (estado == Estado.JUGANDO) {
            System.out.println("El tamagotchi " + this.nombre + " ya está jugando...");
        } else if (estado != Estado.VIVO) {
            System.out.println("El tamagotchi " + this.nombre + " está ocupado, todavía no puede jugar...");
        } else {
            if (!tamagotchiJugando) {
                tamagotchiJugando = true;
                System.out.println("El tamagotchi " + this.nombre + " empieza a jugar");
                estado = Estado.JUGANDO;
            } else {
                System.out.println("Ya hay un tamagotchi jugando...");
            }
        }
    }

    // Funciones para obtener tiempos concurridos para cada caso
    private int getTiempoDeVida() {
        return (int) (System.currentTimeMillis() - this.nacimiento);
    }

    private int getTiempoDesdeUltimoAseo() {
        return (int) (System.currentTimeMillis() - this.ultimoAseo);
    }

    private int getTiempoDesdeInicioComida() {
        return (int) (System.currentTimeMillis() - this.inicioComida);
    }

    // Función para matar o morir
    public void kill() {
        System.out.println("El tamagotchi " + this.nombre + " ha muerto");
        estado = Estado.MUERTO;
    }

    // Función para morir por sucio
    private void stinkyDeath() {
        System.out.println("El tamagotchi " + this.nombre + " ha muerto por cochino");
        estado = Estado.MUERTO;
    }

}