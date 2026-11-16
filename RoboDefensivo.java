public class RoboDefensivo extends Robo {
    public RoboDefensivo (int codigo, String nome, int ataque, int defesa) throws Exception {
        super(codigo, nome, ataque, defesa);
        this.definirTipo("Defensivo");
    }
    
    @Override
    public int reduzirDano (int dano) {

        dano = dano -3;

        if(dano < 2){
            dano = 2;
        }
        return dano;
    }

    @Override 
    public String bonusDefesa() {
        return " bonus defesa + 3";
    }
    
}