package model;

public class Contador {
    private int valor;
    //construtor da classe contador

    public void Contador(){this.valor=1000;}
    public int getValor(){ return valor;}
    public void incrementar() {this.valor ++;}
    public void decrementar() {this.valor--;}
    public void reiniciar(){this.valor = 0;}
    public void Mais10(){this.valor = 0;}

}
