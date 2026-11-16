public class RoboOfensivo extends Robo {
    public RoboOfensivo (int codigo, String nome, int ataque, int defesa) throws Exception {
        super(codigo, nome, ataque, defesa);
        this.definirTipo("Ofensivo");
    }

     @Override 
    public int calcularAtaque(){
        return getAtaque() + 5;
    }

    @Override 
    public String bonusAtaque(){
        return " Bonus de ataque + 5";
    }
    
}
