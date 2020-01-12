public abstract class ElementoDeComposicao{
    public int identificador;
    public int composicao;

    public int getIdentificador();
	public int getComposicao();
	public void setComposicao(Composicao composicao);
	public String toLineFile();
	public String toString();
}