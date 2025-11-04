package org.cuatrovientos.psp.tamago;

import java.util.Random;

public class Tamagotchi implements Runnable {

    private static final Random RANDOM = new Random();
    
    private String nombre;

    private int tiempoParaComer;
    private Estado estado;

    public long nacimiento;

    public Tamagotchi(String nombre) {
        this.nombre = nombre;
        this.tiempoParaComer = RANDOM.nextInt(5);
        this.nacimiento = System.currentTimeMillis();
        this.estado = Estado.VIVO;
    }

    @Override
    public void run() {}

    public String getNombre() {
        return nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    private enum Estado {
        VIVO, ASEANDO, JUGANDO, COMIENDO, MUERTO
    }
    
}