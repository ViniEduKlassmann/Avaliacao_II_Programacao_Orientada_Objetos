import java.util.ArrayList;
import java.util.Scanner;

public abstract class Robo {
    private int codigo;
    private String tipo;
    private String nome;
    private  int ataque;
    private int defesa;
    private int energia;
    private int vitorias;
    private int derrotas;
    private ArrayList<Combate> combates;
    private int pontos;

    private static ArrayList<Robo> robos = new ArrayList<Robo>();

    public int getCodigo(){
        return codigo;
    }

    public String getNome(){
        return nome;
    }

    public int getAtaque(){
        return ataque;
    }

    public int getDefesa(){
        return defesa;
    }

    public int getEnergia(){
        return energia;
    }

    public int getVitorias(){
        return vitorias;
    }

    public int getDerrotas(){
        return derrotas;
    }

    public int getPontos(){
        return pontos;
    }
    public String getTipo(){
        return this.tipo;
    }

    //Aqui vai receber o dano para alterar a energia do robo
    public void receberDano(int dano){
       
        this.energia -=dano;

        if(this.energia < 0){
            this.energia = 0;
        }   
    }

    public Robo(int codigo, String nome, int ataque, int defesa) throws Exception {
        if (codigo <= 0) {
            throw new Exception("Código deve ser positivo");
        }

        for (Robo r : robos) {
            if (r.codigo == codigo) {
                throw new Exception("Código já existe, digite outro");
            }
        }

        if (nome.trim().equals("")) {
            throw new Exception("Nome não pode ser vazio");
        }

        // o ataque deverá estar entre 10 e 30;
        if (ataque < 10 || ataque > 30) {
            throw new Exception("Ataque deve ser entre 10 e 30");
        }
        //  e a defesa, entre 0 e 20
        if (defesa < 0 || defesa > 20) {
            throw new Exception("Defesa deve ser entre 0 e 20");
        }

        this.codigo = codigo;
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.energia = 100;
        this.vitorias = 0;
        this.derrotas = 0;
        this.pontos = 0;
        this.combates = new ArrayList<>();

        robos.add(this);
    }

    public boolean estaDisponivel() {
        return this.energia > 30;
    }

    public int calcularAtaque(){
        return this.ataque;
    }
    
    public int reduzirDano(int dano){
        return dano;
    }
    public String bonusAtaque(){
        return "";
    }

    public String bonusDefesa(){
        return "";
    }

    public void recuperarEnergia(int quantidade) throws Exception{

        if(quantidade <=0 || quantidade % 10 != 0){
            throw new Exception("Energia deve ser positiva e multipla de 10");
        }

        int quantidadedePontos = quantidade / 10;

        if(quantidadedePontos > this.pontos){
            throw new Exception("Pontos insulficientes");
        }

        if (this.energia + quantidade > 100) {
            throw new Exception("Energia não pode ultrpassar de 100");
        }

        this.energia += quantidade;
        //Para descontar 1 ponto quando recuperar energia
        this.pontos -= quantidadedePontos;
    }

    public void regitrarVitoria(){
        this.pontos ++;
        this.pontos += 3;
    }

    public void regitrarDerrota(){
        this.derrotas ++;
    }

    public void regitrarEmpate(){
        this.pontos ++;
    }

    public static ArrayList<Robo> listarRobos(){
        return new ArrayList<>(robos);
    }

    protected void definirTipo(String tipo){
        this.tipo = tipo;
    }

    // Para remover robos
    public static void removerRobo(Robo robo) throws Exception{
        if(!robos.contains(robo)){
            throw new Exception("Robo não encontrado");
        }
        if(!robo.combates.isEmpty()){
            throw new Exception(robo.nome + "Este robo já participou de combates");
        }

        robos.remove(robo);
    }

    public void registrarCombate(Robo adversario){
        this.combates.add(new Combate(adversario));
    }


    public String imprimirRobo() {
        
        return "Codigo: " + getCodigo() + " | Tipo: " + getTipo() + " | Nome: " + getNome() + " | Energia: " + getEnergia()
            + " | Ataque: " + getAtaque() + bonusAtaque() +" | Defesa: " + getDefesa() + bonusDefesa() + " | Pontos: " + getPontos()
            + " | Vitorias: " + getVitorias() + " | Derrotas: " + getDerrotas() 
            + " | Disponível: " + (this.estaDisponivel() ? "Sim" : "Em recuperação");
    }

    public static Robo buscarRobo(Scanner s) throws Exception {
        try {
            System.out.println("Digite o código que deseja buscar");
            int codigo = s.nextInt();

            for (Robo robo : Robo.robos) {
                if (codigo == robo.codigo) {
                    return robo;
                }
            }
            throw new Exception("Robo " + codigo + " não encontrado");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int aproveitamento() {
        //  O aproveitamento será calculado dividindo as vitórias pelo total de combates realizados e multiplicando por 100.
        return this.combates.size() != 0 
            ? (this.vitorias / this.combates.size()) * 100
            : 0;
    }

    public static ArrayList<Robo> ordenarRobos() {
        ArrayList<Robo> ordenado = Robo.robos;

        int n = ordenado.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ordenado.get(j).pontos == ordenado.get(j + 1).pontos) {
                    // nesta sequência: maior número de vitórias, maior energia restante e menor código
                    if (ordenado.get(j).vitorias == ordenado.get(j + 1).vitorias) {
                        if (ordenado.get(j).energia == ordenado.get(j + 1).energia) {
                            if (ordenado.get(j).codigo == ordenado.get(j + 1).codigo) {
                                // no-op
                            }
                            if (ordenado.get(j).codigo < ordenado.get(j + 1).codigo) {
                                Robo temp = ordenado.get(j);
                                ordenado.set(j, ordenado.get(j + 1));
                                ordenado.set(j + 1, temp);
                            }
                        } else {
                            if (ordenado.get(j).energia < ordenado.get(j + 1).energia) {
                                Robo temp = ordenado.get(j);
                                ordenado.set(j, ordenado.get(j + 1));
                                ordenado.set(j + 1, temp);
                            }
                        }
                    } else {
                        if (ordenado.get(j).vitorias < ordenado.get(j + 1).vitorias) {
                            Robo temp = ordenado.get(j);
                            ordenado.set(j, ordenado.get(j + 1));
                            ordenado.set(j + 1, temp);
                        }
                    }
                } else if (ordenado.get(j).pontos < ordenado.get(j + 1).pontos) {
                    Robo temp = ordenado.get(j);
                    ordenado.set(j, ordenado.get(j + 1));
                    ordenado.set(j + 1, temp);
                }
            }
        }

        return ordenado;
    }
}