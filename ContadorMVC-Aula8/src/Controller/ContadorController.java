package Controller;

import model.Contador;
import view.ContadorView;

public class ContadorController {
    // referencia para o objeto model
    private Contador model1;

    // referencia para o obejto view
    private ContadorView view1;


    public ContadorController(Contador model, ContadorView view) {
        this.model1 = model; // armazena o model
        this.view1 = view; // armazena o view

        // adiciona umlistetenr (ouvinte) ao botao da view para incrementar o contador
        // quando o botao for clicado:
        this.view1.addIncrementarListener( e ->{
            model1.incrementar(); // atualizar o estado no model (incrementa o contador)
            view1.setValor(model1.getValor()); // atualiza o valor exibido na view
        });

        this.view1.addDecrementarListener(e -> {
            model1.decrementar();
            view1.setValor(model1.getValor());
        });
        this.view1.ReiniciarListener(e -> {
            model1.reiniciar();
            view1.setValor(model1.getValor());
        });
        this.view1.addMais10Listener(e -> {
            model1.Mais10();
            view1.setValor(model1.getValor());
        });

        // inicializando a view com valor atual do model (0)
        view.setValor(model1.getValor());


    }
    // Método para mostrar a interface grafica ( a view ) na tela
    public void iniciarCont(){view1.setVisible(true);}
}
