public class Policial extends Personagem{

    public Policial(int linInicial,int colInicial){
        super(20,"Policial",linInicial,colInicial);
    }

    @Override
    public void infecta(){
        if (this.infectado()){
            return;
        }
        super.infecta();
        this.setImage("Policial Infectado");
        this.getCelula().setImageFromPersonagem();   
    }

    @Override
    public void cura(){
        if(this.infectado()){
            super.cura();
            this.setImage("Policial");
            this.getCelula().setImageFromPersonagem();
        }   
    }

    @Override
    public void morre() {
        super.morre();
        this.setImage("Policial Morto");
        this.getCelula().setImageFromPersonagem();
    }

    @Override
    public void atualizaPosicao() {
        if(this.estaVivo()){
            int dirLin = Jogo.getInstance().aleatorio(3)-1;
            int dirCol = Jogo.getInstance().aleatorio(3)-1;
            int oldLin = this.getCelula().getLinha();
            int oldCol = this.getCelula().getColuna();
            int lin = oldLin + dirLin;
            int col = oldCol + dirCol;
            if (lin < 0) lin = 0;
            if (lin >= Jogo.NLIN) lin = Jogo.NLIN-1;
            if (col < 0) col = 0;
            if (col >= Jogo.NCOL) col = Jogo.NCOL-1;
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
        boolean atacou = false;
        if(this.estaVivo()){
            int lin = this.getCelula().getLinha();
            int col = this.getCelula().getColuna();
            for(int l=lin-2;l<=lin+2;l++){
                for(int c=col-2;c<=col+2;c++){
                    // Se a posição é dentro do tabuleiro
                    if (l>=0 && l<Jogo.NLIN && c>=0 && c<Jogo.NCOL){
                        // Se não é a propria celula
                        if (!( lin == l && col == c)){
                            // Recupera o personagem da célula vizinha
                            Personagem p = Jogo.getInstance().getCelula(l,c).getPersonagem();
                            // Se não for nulo e for zumbi, mata
                            if (p != null && p.ehZumbi() && p.estaVivo()){
                                p.diminuiEnergia(10);
                                atacou = true;
                            }
                        }
                    }
                    if(atacou) break;
                }
                if(atacou) break;
            }
        }
    }

    @Override
    public void verificaEstado() {
        // Se esta infectado perde energia a cada passo
        if (this.infectado()) {
            diminuiEnergia(1);
            // Se não tem mais energia morre
            if (this.getEnergia() == 0) {
                this.morre();
            }
        }
    }
}