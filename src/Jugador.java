import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN_SUPERIOR = 10;
    private final int MARGEN_IZQUIERDO = 10;
    private final int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN_IZQUIERDO + DISTANCIA * (TOTAL_CARTAS - 1);
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN_SUPERIOR);
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        String resultado = "";
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                resultado += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
            }
        }
        return resultado;
    }
        private String getNombreCarta(int valor) {
        return NombreCarta.values()[valor - 1].toString();
    }

        
public String getEscaleras() {

    String resultado = "";

    for (Pinta pinta : Pinta.values()) {

        int[] valores = new int[TOTAL_CARTAS];
        int contador = 0;

        for (Carta carta : cartas) {
            if (carta.getPinta() == pinta) {

                int valor = carta.getIndice() % 13;
                if (valor == 0) valor = 13;

                valores[contador] = valor;
                contador++;
            }
        }

        // si hay menos de 3 cartas, no puede haber escalera
        if (contador < 3) continue;

        // ordenar
        for (int i = 0; i < contador - 1; i++) {
            for (int j = i + 1; j < contador; j++) {
                if (valores[i] > valores[j]) {
                    int aux = valores[i];
                    valores[i] = valores[j];
                    valores[j] = aux;
                }
            }
        }

        int consecutivos = 1;

        for (int i = 0; i < contador - 1; i++) {

            if (valores[i] + 1 == valores[i + 1]) {
                consecutivos++;
            } else {

                if (consecutivos >= 3) {

                    int inicio = valores[i - consecutivos + 1];
                    int fin = valores[i];

                    resultado += Grupo.values()[consecutivos]
                            + " de " + pinta + " "
                            + getNombreCarta(inicio)
                            + " a "
                            + getNombreCarta(fin) + "\n";
                }

                consecutivos = 1;
            }
        }

        // verificar al final (solo si hay suficientes cartas)
        if (consecutivos >= 3 && contador >= consecutivos) {

            int inicio = valores[contador - consecutivos];
            int fin = valores[contador - 1];

            resultado += Grupo.values()[consecutivos]
                    + " de " + pinta + " "
                    + getNombreCarta(inicio)
                    + " a "
                    + getNombreCarta(fin) + "\n";
        }
    }

    if (resultado.equals("")) {
        return "No hay escaleras\n";
    }

    return resultado;
}
}
