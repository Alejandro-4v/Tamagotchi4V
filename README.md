# Tamagotchi Concurrente en Java

## Descripción del Proyecto

Este proyecto es una simulación del clásico juego Tamagotchi, implementado en Java y ejecutado en la consola. La característica principal es el uso de concurrencia: cada mascota Tamagotchi opera en su propio hilo de ejecución (`Thread`), lo que permite gestionar múltiples mascotas de forma simultánea e independiente.

## Funcionamiento

El programa permite al usuario crear uno o más Tamagotchis, asignando un nombre único a cada uno. Una vez creados, sus ciclos de vida comienzan a correr en paralelo.

### Ciclo de Vida y Estados

Cada Tamagotchi tiene un ciclo de vida y puede morir por dos causas:
1.  **Vejez**: Muere automáticamente tras 5 minutos de vida.
2.  **Higiene**: Muere si no se le asea en un periodo de 3.3 minutos. El programa emite un aviso cuando el riesgo es inminente.

Las mascotas transitan por varios estados: `VIVO`, `COMIENDO`, `JUGANDO`, `ASEANDO` y `MUERTO`. El estado `VIVO` es el estado por defecto, desde el cual se puede interactuar con la mascota.

### Interacción del Usuario

El cuidador interactúa con las mascotas a través de un menú en la consola que se bloquea mientras un Tamagotchi está en medio de un juego. Las acciones disponibles son:

*   **Alimentar**: La mascota pasa al estado `COMIENDO` por un tiempo aleatorio.
*   **Asear**: La mascota se limpia, reiniciando su contador de higiene para evitar que muera por suciedad.
*   **Jugar**: Inicia un minijuego de sumas matemáticas. Solo una mascota puede jugar a la vez, bloqueando la acción para las demás.
*   **Matar**: Permite al usuario terminar con la vida de una mascota.
*   **Ver Estado**: Muestra el estado actual de todos los Tamagotchis (vivos, comiendo, muertos, etc.).

El juego termina cuando todos los Tamagotchis han muerto o el usuario decide salir.

---

[!Ask DeepWiki](https://deepwiki.com/Alejandro-4v/Tamagotchi4V)