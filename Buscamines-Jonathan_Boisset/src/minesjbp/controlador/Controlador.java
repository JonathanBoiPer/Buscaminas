package minesjbp.controlador;

import minesjbp.model.Model;
import minesjbp.vista.Configuracio;
import minesjbp.vista.Vista;

import javax.swing.*;
import java.awt.event.*;

/**
 * Classe Controlador per gestionar el contacte del client amb el programa
 */
public class Controlador {

    public static Vista joc;
    public static Configuracio conf;

    /**
     * Funcio MouseListener per agafar la casella en la cual clica l'usuari
     */
    public static MouseListener clicCasella = new MouseAdapter() {
    public void mousePressed(MouseEvent e) {

        boolean botoDret = SwingUtilities.isRightMouseButton(e);

        if (Model.jocAcabat()) return;

        JComponent casella = (JComponent) e.getSource();

        int fila = (int) casella.getClientProperty("fila");
        int columna = (int) casella.getClientProperty("columna");

        if (!Model.verificarFC(fila, columna)) return;

        if (botoDret) {
            Model.bandera(fila, columna);
        } else {
            Model.trepitjar(fila, columna);
            casella.setEnabled(false);
        }
        }
    };

    /**
     * Funcio per escoltar els 4 botons per configurar el taulell
     */
    public static ActionListener clicBotons = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton boto = (JButton)e.getSource();
            String opcio = boto.getText();

            switch (opcio) {
                case "8x8 10":
                    joc.setVisible(false);
                    iniciar(8,8,10);
                    break;
                case "16x16 40":
                    joc.setVisible(false);
                    iniciar(16,16,40);
                    break;
                case "16x30 99":
                    joc.setVisible(false);
                    iniciar(16,30,99);
                    break;
                case "Configurar":
                    conf = new Configuracio();
                    conf.pack();
                    conf.setTitle("Configuració");
                    conf.setResizable(false);
                    conf.setLocationRelativeTo(null);
                    conf.setVisible(true);
                    break;
                default:
                    joc.setVisible(false);
                    iniciar(8,8,10);
            }
        }
    };

    /**
     * Funcio per iniciar el joc
     * @param files enter referent a les files
     * @param columnes enter referent a les columnes
     * @param bombes enter referent a les bombes
     */
    public static void iniciar(int files, int columnes, int bombes){
        joc = new Vista(files, columnes, bombes);
    }
}