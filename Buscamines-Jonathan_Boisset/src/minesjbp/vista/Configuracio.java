package minesjbp.vista;

import minesjbp.controlador.Controlador;

import javax.swing.*;
import java.awt.event.*;

/**
 * Classe per controlar el boto de configuracio.
 */
public class Configuracio extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner files;
    private JSpinner columnes;
    private JSpinner bombes;
    public int[] result = new int[3];

    /**
     * Funcio per iniciar la interficie en cas de que l'usuari presioni el boto
     */
    public Configuracio() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Funcio per guardar i passar els parametres que ha introduit el client en cas de que li doni a OK.
     */
    private void onOK() {
        result[0] = (int) files.getValue();
        result[1] = (int) columnes.getValue();
        result[2] = (int) bombes.getValue();
        Controlador.joc.setVisible(false);
        Controlador.iniciar(result[0], result[1], result[2]);
        Controlador.conf.setVisible(false);
    }

    /**
     * Funcio per quan l'usuari li dona a cancelar
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Funcio per setejar els minims i maxims de cada parametre.
     */
    private void createUIComponents() {
        files = new JSpinner(new SpinnerNumberModel(8,8,18,1));
        columnes = new JSpinner(new SpinnerNumberModel(8,8,30,1));
        bombes = new JSpinner(new SpinnerNumberModel(10,1,99,1));
    }
}
