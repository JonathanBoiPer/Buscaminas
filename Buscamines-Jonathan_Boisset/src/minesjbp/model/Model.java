package minesjbp.model;

import minesjbp.controlador.Controlador;
import minesjbp.vista.Vista;

import javax.swing.*;

/**
 * Classe amb funcions per configurar i verificar el joc.
 */
public class Model {

    static int files;
    static int columnes;
    static int bombes;
    static boolean finalitzat;
    static char [][] ocult;
    static char [][] visible;

    /**
     * Funcio per iniciar el joc amb les preferences de l'usuari.
     * @param f tipus enter que es refereix a les files que tindra la taula.
     * @param c tipus enter que es refereix a les columnes que tindra la taula.
     * @param b tipus enter que es refereix a les bombes que tindra la taula.
     */
    public static void inizialitzarJoc(int f, int c,int b) {
        files = f + 2;
        columnes = c + 2;
        bombes = b;
        ocult = new char [files][columnes];
        visible = new char [files][columnes];
        finalitzat = false;

        inizialitzarCamp(ocult, ' ');
        inizialitzarCamp(visible, '\n');
        posarBombes();
        comptarBombes();
    }

    /**
     * Funcio per verificar si la fila i columna de l'usuari son correctes dintre del rang de la taula.
     * @param fila tipus enter que es refereix a la fila que ha demanat l'usuari.
     * @param columna tipus enter que es refereix a la columna que ha demanat l'usuari.
     * @return tipus boolea retorna verdader o fals en funcio de la condicio.
     */
    public static boolean verificarFC(int fila, int columna) {

        if (fila >= files -1 || fila < 1 || columna >= columnes - 1 || columna < 1) {
            //System.out.println("Error. La columna i/o la fila es incorrecte.");
            return false;
        }
        else return true;
    }

    /**
     * Funcio per crear els taulells
     * @param camp char bidimensional per guardar el taulell.
     * @param c char per iniciar els camps
     */
    static void inizialitzarCamp(char[][] camp, char c) {
        for (int i = 1; i < files -1; i++) {
            for (int j = 1; j < columnes -1; j++) {
                camp[i][j] = c;
            }
        }
    }

    /**
     * Funcio per posar bombes i comprovar que s'han posat correctament.
     */
    static void posarBombes(){
        for (int i = 0; i < bombes; i++) {
            int fila = (int)Math.floor(Math.random()*(files-2)+1);
            int columna = (int)Math.floor(Math.random()*(columnes-2)+1);
            if (ocult[fila][columna] != 'B') {
                ocult[fila][columna] = 'B';
            } else bombes++;
        }
    }

    /**
     * Funcio per comptar bombes.
     */
    static void comptarBombes(){
        for (int i = 1; i < files -1; i++) {
            for (int j = 1; j < columnes -1; j++) {
                int numBombes = 0;
                if (ocult[i][j] == ' ') {
                    if (ocult[i - 1][j - 1] == 'B') ++numBombes;
                    if (ocult[i - 1][j] == 'B') ++numBombes;
                    if (ocult[i - 1][j + 1] == 'B') ++numBombes;
                    if (ocult[i][j - 1] == 'B') ++numBombes;
                    if (ocult[i][j + 1] == 'B') ++numBombes;
                    if (ocult[i + 1][j - 1] == 'B') ++numBombes;
                    if (ocult[i + 1][j] == 'B') ++numBombes;
                    if (ocult[i + 1][j + 1] == 'B') ++numBombes;
                    if (numBombes > 0) ocult[i][j] = (char) ((char) numBombes + '0');
                }
            }
        }
    }

    /**
     * Funcio per destapar una casella de la taula.
     * @param f tipus enter que es refereix a la fila que ha demanat l'usuari.
     * @param c tipus enter que es refereix a la columna que ha demanat l'usuari.
     */
    public static void trepitjar(int f, int c) {
        if (visible [f][c] == ocult [f][c]){
            System.out.println("Aquesta casella ja s'ha trepitjat.");
        } else if(ocult [f][c] == 'B'){
            visible [f][c] = ocult [f][c];
            Vista.mostrarCamp(visible);
            finalitzat = true;
            System.out.println("\nHas trepitjat una bomba. Game Over ;(\n");
            Model.mostrarSolucio();
        } else {
            trepitjarRecursivament(f, c);
            Vista.mostrarCamp(visible);
            if (jocGuanyat   ()) {
                finalitzat = true;
                JOptionPane.showInternalMessageDialog(null,"Felicitats, has guanyat el joc!!","BuscaMines",JOptionPane.PLAIN_MESSAGE);
                int n = JOptionPane.showConfirmDialog(
                        null,
                        "Vols tornar a jugar?",
                        null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (n == 0){
                    Controlador.joc.setVisible(false);
                    Controlador.iniciar(8,8,10);
                }
                else System.exit(0);
            }
        }
    }

    /**
     * Funcio per substituir a la funcio recursiva en cas de que no funcioni.
     * @param f enter referent a fila.
     * @param c enter referent a columna.
     */
    static void trepitjarVoltant(int f, int c){

        if (visible [f][c] == ocult [f][c]) {
            return;
        } else visible [f][c] = ocult [f][c];

        if (visible[f][c] == '\n') {
            visible [f -1][c -1] = ocult [f -1][c -1];
            visible [f -1][c] = ocult [f -1][c];
            visible [f -1][c +1] = ocult [f -1][c +1];
            visible [f][c -1] = ocult [f][c -1];
            visible [f][c +1] = ocult [f][c +1];
            visible [f +1][c -1] = ocult [f +1][c -1];
            visible [f +1][c] = ocult [f +1][c];
            visible [f +1][c +1] = ocult [f +1][c +1];
        }
    }

    /**
     * Funcio per destapar les caselles buides recursivament
     * @param f enter referent a fila.
     * @param c enter referent a columna.
     */
    static void trepitjarRecursivament(int f, int c) {
        if (!verificarFC(f, c)) return;
        if (visible [f][c] == ocult [f][c]) {
            return;
        } else visible [f][c] = ocult [f][c];
        Vista.caselles[f][c].setEnabled(false);

        if (visible[f][c] == ' ') {
            trepitjarRecursivament(f -1, c -1);
            trepitjarRecursivament(f -1, c);
            trepitjarRecursivament(f -1, c +1);
            trepitjarRecursivament(f, c -1);
            trepitjarRecursivament(f, c +1);
            trepitjarRecursivament(f +1, c -1);
            trepitjarRecursivament(f +1, c);
            trepitjarRecursivament(f +1, c +1);
        }
    }

    /**
     * Funcio per posar i treure una bandera en una casella.
     * @param f tipus enter que es refereix a la fila que ha demanat l'usuari.
     * @param c tipus enter que es refereix a la columna que ha demanat l'usuari.
     */
    public static void bandera(int f, int c) {
        if (visible [f][c] == ocult [f][c]){
            System.out.println("Aquesta casella ja s'ha trepitjat.");
        } else if(visible [f][c] == 'P'){
            visible [f][c] = '\n';
            Vista.mostrarCamp(visible);
        } else {
            visible [f][c] = 'P';
            Vista.mostrarCamp(visible);
        }
        if (jocGuanyat()) {
            finalitzat = true;
            JOptionPane.showInternalMessageDialog(null,"Felicitats, has guanyat el joc!!","BuscaMines",JOptionPane.PLAIN_MESSAGE);
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Vols tornar a jugar?",
                    null,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (n == 0){
                Controlador.joc.setVisible(false);
                Controlador.iniciar(8,8,10);
            }
            else System.exit(0);
        }
    }

    /**
     * Per comprovar si el joc esta finalitzat o no.
     * @return tipus boolea en funcio de si es true o false la variable que es comprova.
     */
    public static boolean jocAcabat() {
        return finalitzat;
    }

    /**
     * Funcio semafor per comprovar si l'usuari a guanyat el joc.
     * @return boolean referent a la comprovacio.
     */
    static boolean jocGuanyat() {
        boolean resultat, definitiu = true;
        for (int i = 1; i < files -1; i++) {
            for (int j = 1; j < columnes -1; j++) {
                if(ocult [i][j] == visible [i][j] || ocult [i][j] == 'B' && visible[i][j] == 'P') resultat = true;
                else resultat = false;
                if (resultat == true && definitiu == true) definitiu = true;
                else definitiu = false;
            }
        }
        return definitiu;
    }

    /**
     * Funcio per mostrar la solucio i per demanar al jugador si vol tornar a jugar.
     */
    static void mostrarSolucio(){
        for (int i = 1; i < files -1; i++) {
            for (int j = 1; j < columnes -1; j++) {
                if (visible[i][j] == 'P' && ocult[i][j] != 'B') {
                    visible[i][j] = '*';
                } else {
                    visible[i][j] = ocult[i][j];
                }
            }
        }
        Vista.mostrarCamp(visible);
        int n = JOptionPane.showConfirmDialog(
                null,
                "Has trepitjat una bomba ;(.\nVols tornar a jugar?",
                null,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (n == 0){
            Controlador.joc.setVisible(false);
            Controlador.iniciar(8,8,10);
        }
        else System.exit(0);
    }
}
