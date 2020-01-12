public class App{
    public static void main(String args[]){
        Menu menu = new Menu();
        boolean executa = true;

        while (executa) {
            executa = menu.imprimeMenu();
        }
        System.out.println("Fim");

    }
}