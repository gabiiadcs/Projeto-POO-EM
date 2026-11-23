


public class Main {

    // Realiza a leitura dos dados e verifica se a entrada está vazia.
    private static String lerLinha(EntradaConsole entrada, String campo) {

        try {
            System.out.print("Digite o " + campo + ": ");

            String valor = entrada.lerLinha();

            if (valor == null || valor.isBlank()) {
                System.out.println("\nEntrada vazia para " + campo + ". Programa encerrado.");
                return null;
            }

            return valor;

        } catch (Exception e) {
            System.out.println("\nEntrada nao informada. Programa encerrado.");
            return null;
        }
    }

    public static void main(String[] args) {

        // Inicializa a entrada do console e cria o usuário do sistema.
        try (Scanner scanner = new Scanner(System.in)) {

            EntradaConsole entrada = new EntradaConsole(scanner);
            Usuario usuario = new Usuario("admin", "1234");

            boolean logado = false;

            // Permite até três tentativas para realizar o login.
            for (int i = 1; i <= 3; i++) {

                System.out.println("\n===== LOGIN =====");

                String nome = lerLinha(entrada, "usuario");
                if (nome == null) {
                    return;
                }

                String senha = lerLinha(entrada, "senha");
                if (senha == null) {
                    return;
                }

                // Verifica se as credenciais informadas estão corretas.
                if (usuario.verificarLogin(nome, senha)) {

                    System.out.println("\nLogin realizado com sucesso!");
                    logado = true;
                    break;

                } else {

                    int restantes = 3 - i;

                    System.out.println("\nUsuario ou senha incorretos!");

                    if (restantes > 0) {
                        System.out.println("Tentativas restantes: " + restantes);
                    }
                }
            }

            // Encerra o programa caso o limite de tentativas seja atingido.
            if (!logado) {

                System.out.println("\nLimite de tentativas atingido!");
                System.out.println("Programa encerrado.");

                return;
            }

            // Após o login, libera o acesso ao menu principal.
            System.out.println("\nBem-vindo ao sistema Elgin!");

            Menu menu = new Menu(entrada);
            menu.menu();
        }
    }
}
