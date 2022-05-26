package minesjbp.vista;

import minesjbp.controlador.Controlador;
import minesjbp.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Classe Vista per crear les diferents interficies
 */
public class Vista extends JFrame {
    private final JPanel panell;
    private final JPanel botons;
    static final int MIDA_CASELLA = 35;
    private static int files;
    private static int columnes;
    public static JButton[][] caselles;

    /**
     * Constructor de la classe Vista per crear la interficie grafica
     * @param f enter referent a les files
     * @param c enter referent a les columnes
     * @param b enter referent a les bombes
     */
    public Vista(int f, int c, int b) {

        Vista.files = f + 2;
        Vista.columnes = c + 2;

        caselles = new JButton[files][columnes];

        Model.inizialitzarJoc(f,c,b);

        Container contenidor = getContentPane();

        //Panell Caselles
        panell = new JPanel();
        panell.setLayout(null);
        //panell.setSize(600, 600);
        panell.setPreferredSize(new Dimension(MIDA_CASELLA * (columnes -2), MIDA_CASELLA * (files -2)));

        //Creacio de botons
        JButton jtb;
        for (int fila = 1; fila < files -1; fila++) {
            for (int columna = 1; columna < columnes -1; columna++) {
                jtb = new JButton("");
                jtb.setBounds((columna -1) * MIDA_CASELLA, (fila -1) * MIDA_CASELLA, MIDA_CASELLA, MIDA_CASELLA);
                jtb.setMargin(new Insets(0,0,0,0));
                jtb.setFocusable(false);
                jtb.putClientProperty("fila", fila);
                jtb.putClientProperty("columna", columna);
                jtb.addMouseListener(Controlador.clicCasella);
                caselles[fila][columna] = jtb;
                panell.add(jtb);
            }
        }

        // Panell inferior (botons)

        botons = new JPanel(new GridLayout(2, 2));

        //botons.setLayout(gLayout);
        JButton vuit = new JButton("8x8 10");
        JButton setze = new JButton("16x16 40");
        JButton trenta = new JButton("16x30 99");
        JButton configurar = new JButton("Configurar");

        vuit.addActionListener(Controlador.clicBotons);
        setze.addActionListener(Controlador.clicBotons);
        trenta.addActionListener(Controlador.clicBotons);
        configurar.addActionListener(Controlador.clicBotons);

        botons.add(vuit);
        botons.add(setze);
        botons.add(trenta);
        botons.add(configurar);

        contenidor.add(panell, BorderLayout.PAGE_START);
        contenidor.add(botons, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setVisible(true);
        setTitle("BuscaMines");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Funcio per imprimir en cada casella el tauler de l'usuari
     * @param camp char bidimensional referent al tauler que veu l'usuari
     */
    public static void mostrarCamp(char[][] camp) {
        for (int i = 1; i < camp.length -1; i++) {
            for (int j = 1; j < camp[i].length -1; j++) {
                caselles[i][j].setText("" + camp[i][j]);
            }
        }
    }
/*
    public static void mostrarCamp(char[][] camp) {
        char lletra = 'A';
        char numero = '1';
        int l = 0;
        int n = 0;
        for (int i = 0; i < camp.length -1; i++) {
            for (int j = 0; j < camp[i].length -1; j++) {
                if (i == 0 && j > 0) {
                    camp[i][j] = (char) (numero + n);
                    n++;
                } else if (j == 0 && i > 0) {
                    camp[i][j] = (char) (lletra + l);
                    l++;
                }
                System.out.print("  " + camp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
*/
}
