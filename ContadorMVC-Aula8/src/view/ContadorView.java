package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ContadorView extends JFrame {
    private JLabel lblContador;
    private JButton btnIncrementar;
    private JButton btnDecrementar;
    private JButton btnReiniciar;
    private JButton btnMais10;

    public ContadorView() {
        setTitle(" | --- Contador de MVC --- | ");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza a janela na tela
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));


        lblContador = new JLabel("0");
        lblContador.setFont(new Font("Arialmes", Font.BOLD, 48));


        btnIncrementar = new JButton(" | --- Incrementar --- | ");
        btnIncrementar.setFont(new Font("Arialmes", Font.PLAIN, 18));

        btnDecrementar = new JButton(" | --- Decrementar --- |");
        btnDecrementar.setFont(new Font("Arialmes", Font.PLAIN, 18));

        btnReiniciar = new JButton(" | --- Reiniciar --- |");
        btnReiniciar.setFont(new Font("Arialmes",Font.PLAIN,18));

        btnMais10 = new JButton(" | --- + 10 --- |");
        btnMais10.setFont(new Font("Arialmes", Font.PLAIN,18));

        add(lblContador);
        add(btnIncrementar);
        add(btnDecrementar);
        add(btnReiniciar);
        add(btnMais10);
    }

    public void setValor(int valor) {
        lblContador.setText(String.valueOf(valor));
    }

    public void addIncrementarListener(ActionListener listener) {
        btnIncrementar.addActionListener((listener));
    }
    public void addDecrementarListener(ActionListener listener){
        btnDecrementar.addActionListener(listener);
    }
    public void ReiniciarListener(ActionListener listener){
        btnReiniciar.addActionListener(listener);
    }
    public void addMais10Listener(ActionListener listener){
        btnMais10.addActionListener(listener);
    }
}