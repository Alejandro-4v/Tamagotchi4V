package org.cuatrovientos.psp.tamago;

import java.util.Random;
import java.util.Date;

public class Tamagotchi implements Runnable {

    // Enum para controlar estados
    private enum Estado {
        VIVO, ASEANDO, JUGANDO, COMIENDO, MUERTO
    }

    // Estáticas con distintos usos
    private static final Random RANDOM = new Random(); // Random para lo que tarda en comer
    private static final Date DATE; // Fecha actual que se irá actualizando para medir tiempos en consola

    // Variables generales
    private String nombre;

    private int tiempoParaComer;

    private Estado estado;

    // Variables para medir tiempos, estados y saber si mostrar o no mostrar ciertos
    // mensajes
    private long nacimiento;
    private long ultimoAseo;

    private boolean avisadoRiesgoDeMuerte = false;

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

            if (estado != Estado.ASEANDO) {
                if (getTiempoDesdeUltimoAseo() >= 100000 && !avisadoRiesgoDeMuerte) {
                    System.out.println("El tamagotchi " + this.nombre + " está a punto de morir por cochino");
                    avisadoRiesgoDeMuerte = true;
                } else if (getTiempoDesdeUltimoAseo() >= 200000) {
                    stinkyDeath();
                    break;
                }
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    // Funciones para obtener tiempos concurridos para cada caso
    private int getTiempoDeVida() {
        return (int) (System.currentTimeMillis() - this.nacimiento);
    }

    private int getTiempoDesdeUltimoAseo() {
        return (int) (System.currentTimeMillis() - this.ultimoAseo);

    }

    // Función para matar o morir
    public void kill() {
        System.out.println("El tamagotchi " + this.nombre + " ha muerto");
        estado = Estado.MUERTO;
    }

    // Función para morir por sucio
    public void stinkyDeath() {
        System.out.println("El tamagotchi " + this.nombre + " ha muerto por cochino");]
        estado = Estado.MUERTO;
    }

}