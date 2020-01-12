import java.util.Scanner;

public class Menu {
    private int escolha;
    private Scanner in;
    private CadastroVagoes cv;
    private CadastroLocomotivas cl;
    private CadastroComposicoes cc;
    
    public Menu() {
        escolha = 0;
        in = new Scanner(System.in);
        cv = new CadastroVagoes();
        cl = new CadastroLocomotivas();
        cc = new CadastroComposicoes();
        cv.carrega();
        cl.carrega();
        cc.carrega();
    }

    public boolean imprimeMenu() {
        boolean res;
        switch (escolha){
            case 0: 
                res = imprimeMenuPrincipal();
                break;
            case 1: 
                res = imprimeMenuCriarComposicao();
                break;
            case 2: 
                res = imprimeMenuEditarComposicao();
                break;
            case 3: 
                res = imprimeComposicoes();
                break;
            case 4: 
                res = imprimeMenuDesfazComposicao();
                break;
            default:
                res = encerraPrograma();
                break;
        }
        return res;
    }

    private boolean imprimeComposicoes() {
        for(int i=0;i<cc.getQtdade();i++){
            Composicao trem = cc.getPorPosicao(i);
            System.out.println("Composicao: "+trem.getIdentificador());
            for(int j = 0; j<trem.getQtdadeLocomotivas(); j++){
                System.out.println(trem.getLocomotiva(j));
            }
            for(int j = 0; j<trem.getQtdadeVagoes(); j++){
                System.out.println(trem.getVagao(j));
            }
        }
        escolha = 0;
        return true;
    }

    private void imprimeLocomotivas() {
        System.out.println("Locomotivas:");
        for(int i=0;i<cl.qtdade(); i++){
            System.out.println(cl.getPorPosicao(i));
        }
    }

    private void imprimeVagoes() {
        System.out.println("Vagoes:");
        for(int i=0;i<cv.qtdade(); i++){
            System.out.println(cv.getPorPosicao(i));
        }
    }

    private boolean imprimeMenuPrincipal() {
        System.out.println("------------------------------");
        System.out.println("1. Criar uma nova composicao");
        System.out.println("2. Editar uma composicao");
        System.out.println("3. Listar todas as composicoes");
        System.out.println("4. Desfazer uma composicao");
        System.out.println("5. Fim");
        System.out.println("------------------------------");
        if (in.hasNextInt()) {
            escolha = in.nextInt();
        } else {
            while(!in.hasNextInt()) {
                System.out.println("Escolha invalida, tente novamente:");
            }
            escolha = in.nextInt();
        }

        if (escolha > 5 || escolha < 1) {
            while(escolha > 5 || escolha < 1) {
                System.out.println("Escolha invalida, tente novamente:");
                if (in.hasNextInt()) {
                    escolha = in.nextInt();
                }
            }
        }

        return true;
    }

    private int validaIDComp() {
        int id;

        System.out.println("Insira o ID da composicao que deseja criar:");
        if (in.hasNextInt()) {
            id = in.nextInt();
        } else {
            while(!in.hasNextInt()) {
                System.out.println("ID invalido, tente novamente.");
                System.out.println("Insira um numero inteiro valido:");
            }
            id = in.nextInt();
        }

        if (cc.getPorIdentificador(id) != null) {
            while (cc.getPorIdentificador(id) != null) {
                System.out.println("Composicao ja existente.");
                System.out.println("Insira um ID de composicao válido:");
                if (in.hasNextInt()) {
                    id = in.nextInt();
                } else {
                    while(!in.hasNextInt()) {
                        System.out.println("ID invalido, tente novamente.");
                        System.out.println("Insira um numero inteiro valido:");
                    }
                    id = in.nextInt();
                }
            }
        }
        return id;
    }

    private int validaIDLocomotiva() {
        int id;
        Locomotiva l;
        System.out.println("Insira o ID da locomotiva que deseja usar:");
        if (in.hasNextInt()) {
            id = in.nextInt();
        } else {
            do {
                System.out.println("ID invalido, tente novamente.");
                System.out.println("Insira um numero inteiro valido:");
                id = in.nextInt();
            } while(!in.hasNextInt());
        }

        if (cl.getPorId(id) == null) {
            while (cl.getPorId(id) == null) {
                System.out.println("ID inexistente, tente novamente.");
                System.out.println("Insira um numero inteiro valido:");
                id = in.nextInt();
            }
        }

        l = cl.getPorId(id);

        while (l.getComposicao() != -1) {
            System.out.println("Locomotiva ja utilizada");
            System.out.println("Insira um ID de locomotiva valido:");
            if (in.hasNextInt()) {
                id = in.nextInt();
            } else {
                while(!in.hasNextInt()) {
                    System.out.println("ID invalido, tente novamente.");
                    System.out.println("Insira um numero inteiro valido:");
                }
                id = in.nextInt();
            }
            l = cl.getPorId(id);
        }

        return id;
    }

    private int validaIDVagao() {
        int id;
        Vagao v;
        System.out.println("Insira o ID do vagao que deseja usar:");
        imprimeVagoes();
        if (in.hasNextInt()) {
            id = in.nextInt();
        } else {
            do {
                System.out.println("ID invalido, tente novamente.");
                System.out.println("Insira um numero inteiro valido:");
                id = in.nextInt();
            } while(!in.hasNextInt());
        }

        while (cv.getPorId(id) == null) {
            System.out.println("ID inexistente, tente novamente.");
            System.out.println("Insira um numero inteiro valido:");
            if (in.hasNextInt()) {
                id = in.nextInt();
            }
        }

        v = cv.getPorId(id);

        while (v.getComposicao() != -1){
            System.out.println("Vagao ja utilizado");
            System.out.println("Insira um ID de vagao valido:");
            if (in.hasNextInt()) {
                id = in.nextInt();
            } else {
                while(!in.hasNextInt()) {
                    System.out.println("ID invalido, tente novamente.");
                    System.out.println("Insira um numero inteiro valido:");
                    id = in.nextInt();
                }
            }
            v = cv.getPorId(id);
        }

        return id;
    }

    private boolean imprimeMenuCriarComposicao() {
        int idComp, idLoc;

        idComp = validaIDComp();

        imprimeLocomotivas();
        idLoc = validaIDLocomotiva();        
        Locomotiva l = cl.getPorId(idLoc);
        Composicao t = new Composicao(idComp);
        t.engataLocomotiva(l);
        l.setComposicao(t);
        cc.cadastra(t);
        System.out.println("Composicao criada");
        escolha = 0;
        return true;
    }

    private void insereLocomotivaNaComposicao(Composicao t) {
        int idLoc;
        if (t.getQtdadeVagoes() == 0) {
            imprimeLocomotivas();
            idLoc = validaIDLocomotiva();
            Locomotiva l = cl.getPorId(idLoc);
            t.engataLocomotiva(l);
            l.setComposicao(t);
        } else {
            System.out.println("Voce ja engatou vagoes na composicao");
        }
    }

    private void insereVagaoNaComposicao(Composicao t) {
        int idVag;
        if (t.getQtdadeLocomotivas() > 0) {
            imprimeVagoes();
            idVag = validaIDVagao();
            Vagao v = cv.getPorId(idVag);
            t.engataVagao(v);
            v.setComposicao(t);
        } else {
            System.out.println("Voce precisa engatar uma locomotiva antes");
        }
    }

    private void removeUltimoElemento(Composicao t) {
        if (t.getQtdadeVagoes() > 0) {
            t.desengataVagao();
        } else if (t.getQtdadeLocomotivas() > 0) {
            t.desengataLocomotiva();
        } else {
            System.out.println("Sua composicao esta vazia");
        }
    }

    private void imprimeLocomotivasLivres() {
        System.out.println("Locomotivas livres:");
        for(int i=0;i<cl.qtdade(); i++){
            Locomotiva l = cl.getPorPosicao(i);
            if (l.getComposicao() != -1) {
                System.out.println(l);
            }
        }
    }

    private void imprimeVagoesLivres() {
        System.out.println("Vagoes livres:");
        for(int i=0;i<cv.qtdade(); i++){
            Vagao v = cv.getPorPosicao(i);
            if (v.getComposicao() == -1) {
                System.out.println(v);
            }
        }
    }

    private boolean imprimeMenuEditarComposicao() {
        int idComp, op;
        if (cc.getQtdade() > 0) {
            imprimeComposicoes();
            System.out.println("Insira o ID da composicao que deseja editar:");
            if (in.hasNextInt()) {
                idComp = in.nextInt();
            } else {
                while(!in.hasNextInt()) {
                    System.out.println("ID invalido, tente novamente.");
                    System.out.println("Insira um numero inteiro valido:");
                }
                idComp = in.nextInt();
            }

            Composicao t = cc.getPorIdentificador(idComp);
            System.out.println("Composicao: " + idComp);
            do {
                System.out.println("1. Inserir locomotiva");
                System.out.println("2. Inserir vagao");
                System.out.println("3. Remover ultimo elemento");
                System.out.println("4. Listar locomotivas livres");
                System.out.println("5. Listar vagoes livres");
                System.out.println("6. Encerrar edicao");
                if (in.hasNextInt()) {
                    op = in.nextInt();
                } else {
                    do {
                        System.out.println("Escolha invalida, tente novamente:");
                        op = in.nextInt();
                    } while(!in.hasNextInt());
                }

                while (op < 1 || op > 6) {
                    System.out.println("Escolha invalida, tente novamente:");
                    op = in.nextInt();
                }

                switch (op) {
                    case 1: 
                        insereLocomotivaNaComposicao(t); 
                        break;
                    case 2: 
                        insereVagaoNaComposicao(t); 
                        break;
                    case 3: 
                        removeUltimoElemento(t); 
                        break;
                    case 4: 
                        imprimeLocomotivasLivres(); 
                        break;
                    case 5: 
                        imprimeVagoesLivres(); 
                        break;
                    default: 
                        break;
                }
            } while (op != 6);
        } else {
            System.out.println("Voce nao tem composicoes para editar");
        }
        escolha = 0;
        return true;
    }

    private boolean imprimeMenuDesfazComposicao() {
        int idComp;
        if (cc.getQtdade() > 0) {
            System.out.println("Escolha o ID de uma composição para desfazer:");
            idComp = validaIDComp();
            Composicao trem = cc.getPorIdentificador(idComp);
            for (int i = 0; i < trem.getQtdadeVagoes(); i++) {
                trem.desengataVagao();
            }
            for (int i = 0; i < trem.getQtdadeLocomotivas(); i++) {
                trem.desengataLocomotiva();
            }
            cc.removePorId(idComp);
        } else {
            System.out.println("Voce nao tem composicoes para editar");
        }
        escolha = 0;
        return true;
    }

    private boolean encerraPrograma() {
        cc.persiste();
        cv.persiste();
        cl.persiste();
        return false;
    }
}