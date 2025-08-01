import Controller.ContadorController;
import model.Contador;
import view.ContadorView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Cria uma instância do model ( a lógica do contador)
        Contador model = new Contador();
        // Cria instância da View ( a interface gráfica)
        ContadorView view = new ContadorView();

        // Cria o Controller, que faz a ligação entre o model e View
        ContadorController controller = new ContadorController(model,view);

        // Inicia a interface gráfica
        controller.iniciarCont();
    }
}