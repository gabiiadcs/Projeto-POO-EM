
import java.util.NoSuchElementException;

public class Menu {

    private final EntradaConsole input;
    private final Impressora impressora;

    // Inicializa a entrada do console e o objeto da impressora.
    public Menu(EntradaConsole input) {
        this.input = input;
        this.impressora = new Impressora(input);
    }

    // Exibe as opções disponíveis para controlar a impressora Elgin.
    void menuImpressora() {

        System.out.println("\n===== MENU ELGIN =====");
        System.out.println("1 - Configurar Conexao");
        System.out.println("2 - Abrir Conexao");
        System.out.println("3 - Fechar Conexao");
        System.out.println("4 - Imprimir Texto");
        System.out.println("5 - Imprimir QR Code");
        System.out.println("6 - Imprimir Codigo de Barras");
        System.out.println("7 - Imprimir XML SAT");
        System.out.println("8 - Imprimir XML Cancelamento SAT");
        System.out.println("9 - Abrir Gaveta");
        System.out.println("10 - Sinal Sonoro");
        System.out.println("0 - Sair");
    }

    // Realiza a leitura da opção escolhida pelo usuário.
    private int lerOpcaoMenu() {

        System.out.print("\nDigite a opcao desejada: ");

        return input.lerInteiro();
    }

    // Controla a navegação do menu e executa a operação selecionada.
    void menu() {

        try {

            int opc;

            do {

                menuImpressora();

                opc = lerOpcaoMenu();

                // Direciona a execução para o método correspondente da impressora.
                switch (opc) {

                    case 1:
                        impressora.configurarImpressora();
                        break;

                    case 2:
                        impressora.abrirConexao();
                        break;

                    case 3:
                        impressora.fecharConexao();
                        break;

                    case 4:
                        impressora.imprimirTexto();
                        break;

                    case 5:
                        impressora.imprimirQRCode();
                        break;

                    case 6:
                        impressora.impressaoCodigoBarras();
                        break;

                    case 7:
                        impressora.imprimirXMLSAT();
                        break;

                    case 8:
                        impressora.imprimirXMLCancelamento();
                        break;

                    case 9:
                        impressora.abrirGaveta();
                        break;

                    case 10:
                        impressora.sinalSonoro();
                        break;

                    case 0:
                        System.out.println("\nSaindo do sistema...");
                        break;

                    default:
                        System.out.println("\nOpcao invalida!");
                }

            } while (opc != 0); // Mantém o menu ativo até o usuário escolher sair.

        } catch (NoSuchElementException e) {

            // Trata a situação em que não há mais dados disponíveis na entrada.
            System.out.println("\nEntrada nao informada. Encerrando sistema.");
        }
    }
}
