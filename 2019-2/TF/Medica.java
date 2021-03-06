public class Medica extends Personagem{

    private Personagem alvo;

    public Medica(int linInicial,int colInicial){
        super(10,"Medica",linInicial,colInicial);
    }

    private Personagem defineAlvo(){
        System.out.println("Procurando alvo");
        for(int l=0;l<Jogo.NLIN;l++){
            for(int c=0;c<Jogo.NCOL;c++){
                Personagem p = Jogo.getInstance().getCelula(l, c).getPersonagem();
                if (p != null && p instanceof Bobao && p.infectado()){
                    alvo = p;
                    System.out.println("Alvo definido: "+alvo.getImage());
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public void infecta(){
        if (this.infectado()){
            return;
        }
        super.infecta();
        this.setImage("Medica Infectada");
        this.getCelula().setImageFromPersonagem();   
    }

    @Override
    public void cura(){
        if(this.infectado()){
            super.cura();
            this.setImage("Medica");
            this.getCelula().setImageFromPersonagem();
        }   
    }

    @Override
    public void morre() {
        super.morre();
        this.setImage("Medica Morta");
        this.getCelula().setImageFromPersonagem();
    }

    @Override
    public void atualizaPosicao() {
        if(this.estaVivo()){
            if (alvo == null || !alvo.infectado()){
                alvo = defineAlvo();
                return;
            }
    
            // Pega posicao atual do ZumbiEsperto
            int oldLin = this.getCelula().getLinha();
            int oldCol = this.getCelula().getColuna();
    
            // Pega a posicao do alvo
            int linAlvo = alvo.getCelula().getLinha();
            int colAlvo = alvo.getCelula().getColuna();
    
            // Calcula o deslocamento
            int lin = oldLin;
            int col = oldCol;
            if (lin < linAlvo) lin++;
            if (lin > linAlvo) lin--;
            if (col < colAlvo) col++;
            if (col > colAlvo) col--;
    
            // Verifica se não saiu dos limites do tabuleiro
            if (lin < 0) lin = 0;
            if (lin >= Jogo.NLIN) lin = Jogo.NLIN-1;
            if (col < 0) col = 0;
            if (col >= Jogo.NCOL) col = Jogo.NCOL-1;
    
            // Verifica se não quer ir para uma celula ocupada
            if (Jogo.getInstance().getCelula(lin, col).getPersonagem() != null){
                return;
            }else{
                // Limpa celula atual
                Jogo.getInstance().getCelula(oldLin, oldCol).setPersonagem(null);
                // Coloca personagem na nova posição
                Jogo.getInstance().getCelula(lin, col).setPersonagem(this);
            }
        }
    }

    @Override
    public void influenciaVizinhos() {
        if(this.estaVivo()){
            int lin = this.getCelula().getLinha();
            int col = this.getCelula().getColuna();
            for(int l=lin-1;l<=lin+1;l++){
                for(int c=col-1;c<=col+1;c++){
                    // Se a posição é dentro do tabuleiro
                    if (l>=0 && l<Jogo.NLIN && c>=0 && c<Jogo.NCOL){
                        // Se não é a propria celula
                        if (!( lin == l && col == c)){
                            // Recupera o personagem da célula vizinha
                            Personagem p = Jogo.getInstance().getCelula(l,c).getPersonagem();
                            // Se não for nulo e estiver infectado, cura
                            if (p != null && p.infectado() && p.estaVivo()){
                                p.cura();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void verificaEstado() {
        // Se esta infectado perde energia a cada passo
        if (this.infectado()) {
            diminuiEnergia(2);
            // Se não tem mais energia morre
            if (this.getEnergia() == 0) {
                this.morre();
            }
        }
    }
}