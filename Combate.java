public class Combate {
    
    Robo adversario;

    public Combate(Robo adversario) {
        this.adversario = adversario;
    }

    public static boolean realizarAtaque(Robo atacante, Robo defende, int rodada) {
        int dano = atacante.calcularAtaque() - defende.getDefesa();
        if (dano < 5) {
            dano = 5;
        }
        if (rodada % 2 == 0) {
            dano += 5;
        }

        dano = defende.reduzirDano(dano);
        
        //Chama o método da classe robo 
        defende.receberDano(dano);

        // dados privados, tem que pegar comm getenergia da classe robo
        if (defende.getEnergia() == 0) {
            return false;
        }

        return true;
    }

    public static void realizarCombate(Robo primeiro, Robo segundo) {
        for(int i = 0; i < 5; i++) {
            System.out.println("Rodada " + (i + 1));
            // Primeiro Ataca
            if (!realizarAtaque(segundo, primeiro, i)) {
                break;
            }
            if (!realizarAtaque(primeiro, segundo, i)) {
                break;
            }
            System.out.println(primeiro.imprimirRobo());
            System.out.println(segundo.imprimirRobo());
        }

        if (primeiro.getEnergia() == segundo.getEnergia()) {
            System.out.println("Empate");
            //Registra o empate utilizando o metodo criado na classe robo
            primeiro.regitrarEmpate();
            //Registra o empate utilizando o metodo criado na classe robo
            segundo.regitrarEmpate();
        } else if (primeiro.getEnergia() > segundo.getEnergia()) {
            System.out.println(primeiro.getNome() + " venceu!");
            //Registra a vitoria utilizando o metodo criado na classe robo
            primeiro.regitrarVitoria();
            //Registra a derrota utilizando o metodo criado na classe robo
            segundo.regitrarDerrota();
        } else {
            System.out.println(segundo.getNome() + " venceu!");
            segundo.regitrarVitoria();
            //segundo.pontos += 3; Não precisa mais disso, pois o metado já faz as duas funçoes de contar vítoria e os pontos na classe
            primeiro.regitrarDerrota();
        }
    }
}
