import java.util.ArrayList;

public class Composicao {
	private ArrayList<Vagao> vagoes;
	private ArrayList<Locomotiva> locomotivas;
	private int identificador;

	public Composicao(int identificador){
		this.identificador = identificador;
		vagoes = new ArrayList<>();
		locomotivas = new ArrayList<>();
	}

	public int getIdentificador() {
		return identificador;
	}

	public int getQtdadeLocomotivas() {
		return locomotivas.size();
	}

	public Locomotiva getLocomotiva(int posicao) {
		if (posicao >= 0 && posicao < locomotivas.size()) {
			return locomotivas.get(posicao);
		} else {
			return null;
		}
	}

	public int getQtdadeVagoes() {
		return vagoes.size();
	}

	public Vagao getVagao(int posicao) {
		if (posicao >= 0 && posicao < vagoes.size()) {
			return vagoes.get(posicao);
		} else {
			return null;
		}
	}

	public boolean engataLocomotiva(Locomotiva locomotiva) {
		if (vagoes.size() == 0 && locomotiva.getComposicao() == -1){
			locomotivas.add(locomotiva);
			return true;
		}
		return false;
	}

	public boolean engataVagao(Vagao vagao) {
		if (locomotivas.size() > 0) {
			int maxVagoes = 0;
			double maxPeso = 0, pesoAtual = 0;
			for (Locomotiva locomotiva : locomotivas) {
				maxVagoes += locomotiva.getQtdadeMaxVagoes();
				maxPeso += locomotiva.getPesoMaximo();
			}
	
			if (locomotivas.size() > 1) {
				maxPeso *= 0.9;
			}
	
			for (Vagao vag : vagoes) {
				pesoAtual += vag.getCapacidadeCarga();
			}
	
			if (pesoAtual + vagao.getCapacidadeCarga() <= maxPeso && vagoes.size() < maxVagoes) {
				vagoes.add(vagao);
				return true;
			}
		}
		return false;
	}

	public boolean desengataLocomotiva() {
		if(locomotivas.size() > 0) {
			if (vagoes.size() > 0) {
				return false;
			}
			Locomotiva aux = locomotivas.remove(locomotivas.size() - 1);
			aux.setComposicao(null);
			return true;
		}
		return false;
	}

	public boolean desengataVagao() {
		if (vagoes.size() > 0) {
			Vagao aux = vagoes.remove(vagoes.size() -1);
			aux.setComposicao(null);
			return true;
		}
		return false;
	}

	public String toLineFile(){
		String aux = "";
		aux += this.getIdentificador() +",";
		aux += this.getQtdadeLocomotivas()+",";
		for(int i=0;i<this.getQtdadeLocomotivas();i++){
			aux += this.getLocomotiva(i).toLineFile()+",";
		}
		aux += this.getQtdadeVagoes()+",";
		for(int i=0;i<this.getQtdadeVagoes();i++){
			aux += this.getVagao(i).toLineFile();
			if (i<this.getQtdadeVagoes()-1){
				aux += ",";
			}
		}
		return aux;
	}
}
