package minesjbp;

import minesjbp.controlador.Controlador;
import minesjbp.vista.Vista;

import javax.swing.*;

/**
 * Classe per iniciar el joc
 */
public class Mines {

    /**
     * Funcio per iniciar el joc i mostrar un dialeg
     * @param args .
     */
    public static void main(String[] args) {
        Controlador.iniciar(8,8,10);
        JOptionPane.showInternalMessageDialog(null,"Benvingut al Joc BuscaMines.\nToca una casella per començar o selecciona un altre taulell.","BuscaMines",JOptionPane.PLAIN_MESSAGE);
    }
}
