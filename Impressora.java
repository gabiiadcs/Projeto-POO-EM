
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class Impressora {

    // Armazena o objeto responsável pela entrada de dados do console.
    private final EntradaConsole input;

    // Controlam o estado e armazenam os dados de configuração da impressora.
    private boolean conexaoAberta;
    private boolean configurada;
    private int tipo;
    private String modelo;
    private String conexao;
    private int parametro;

    // Recebe a entrada do console utilizada pelos métodos da impressora.
    public Impressora(EntradaConsole input) {
        this.input = input;
    }

    // Solicita e armazena as configurações necessárias para conectar a impressora.
    void configurarImpressora() {

        System.out.println("\n===== CONFIGURAR IMPRESSORA =====");
        System.out.println("Tipo de conexao:");
        System.out.println("1 - USB");
        System.out.println("2 - Serial");
        System.out.println("3 - Rede");
        System.out.println("4 - Bluetooth");
        System.out.print("Escolha uma opcao: ");

        int novoTipo = input.lerInteiro();

        System.out.println("\nModelos disponiveis: i8 ou i9");
        System.out.print("Modelo: ");
        String novoModelo = input.lerLinha();

        System.out.println("\nConexao (USB, COM2 ou IP): ");
        System.out.print("Digite a conexao: ");
        String novaConexao = input.lerLinha();

        System.out.println("\nParametro USB - 0, Serial - velocidade e Rede - porta");
        System.out.print("Digite o parametro: ");
        int novoParametro = input.lerInteiro();

        tipo = novoTipo;
        modelo = novoModelo;
        conexao = novaConexao;
        parametro = novoParametro;

        configurada = true;

        System.out.println("\nConfiguracao salva com sucesso!");
    }

    // Utiliza as configurações armazenadas para estabelecer a comunicação com a impressora.
    void abrirConexao() {

        if (!configurada) {
            System.out.println("Configure a impressora antes de abrir a conexao.");
            return;
        }

        if (!conexaoAberta) {

            try {
                int ret = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(
                        tipo, modelo, conexao, parametro
                );

                if (ret == 0) {
                    conexaoAberta = true;
                    System.out.println("Comunicou com sucesso!");
                } else {
                    System.out.println("Erro ao abrir conexao. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Nao foi possivel carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Ja existe uma comunicacao em aberto.");
        }
    }

    // Envia os dados do código de barras para impressão.
    void impressaoCodigoBarras() {

        if (conexaoAberta) {

            try {
                int ret = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(
                        8, "{A012345678912", 100, 2, 3
                );

                if (ret == 0) {
                    System.out.println("Codigo de barras impresso com sucesso!");
                    avancarPapel();
                    cortarPapel();
                } else {
                    System.out.println("Erro ao imprimir codigo de barras. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Nao foi possivel carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Nao existe uma comunicacao em aberto.");
        }
    }

    // Encerra a comunicação com a impressora.
    void fecharConexao() {

        if (conexaoAberta) {

            try {
                int ret = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();

                if (ret == 0) {
                    conexaoAberta = false;
                    System.out.println("Conexao fechada com sucesso!");
                } else {
                    System.out.println("Erro ao fechar conexao. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Nao foi possivel carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Nao existe uma comunicacao em aberto.");
        }
    }

    // Recebe um texto do usuário e envia para a impressora.
    void imprimirTexto() {

        if (conexaoAberta) {

            System.out.println("Digite o texto que deseja imprimir:");
            String dados = input.lerLinha();

            int posicao = 1;
            int estilo = 0;
            int tamanho = 5;

            try {
                int ret = ImpressoraDLL.INSTANCE.ImpressaoTexto(
                        dados, posicao, estilo, tamanho
                );

                if (ret >= 0) {
                    System.out.println("Texto impresso com sucesso!");
                    avancarPapel();
                    cortarPapel();
                } else {
                    System.out.println("Erro ao imprimir texto. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Nao foi possivel carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Conexao fechada. Abra a conexao antes.");
        }
    }

    // Recebe os dados e solicita a impressão de um QR Code.
    void imprimirQRCode() {

        if (conexaoAberta) {

            System.out.println("Digite os dados do QR Code:");
            String dados = input.lerLinha();

            int tamanho = 4;
            int nivelCorrecao = 2;

            try {
                int ret = ImpressoraDLL.INSTANCE.ImpressaoQRCode(
                        dados, tamanho, nivelCorrecao
                );

                if (ret == 0) {
                    System.out.println("QR Code impresso com sucesso!");
                    avancarPapel();
                    cortarPapel();
                } else {
                    System.out.println("Erro ao imprimir QR Code. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Erro ao carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Conexao fechada. Abra a conexao antes.");
        }
    }

    // Verifica a existência do arquivo SAT e envia seu caminho para a DLL.
    void imprimirXMLSAT() {

        if (conexaoAberta) {

            // Caminho fixo do arquivo XML SAT.
            String caminho = "D:\\TB\\Projeto POO EM\\Projeto POO EM\\XMLSAT.xml";
            Path arquivo = Paths.get(caminho);

            System.out.println("Imprimindo XML SAT...");

            // Verifica se o arquivo existe antes de solicitar a impressão.
            if (!Files.isRegularFile(arquivo)) {
                System.out.println("Arquivo XML nao encontrado: " + caminho);
                return;
            }

            try {
                int ret = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(
                        "path=" + arquivo.toAbsolutePath(), 0
                );

                if (ret == 0) {
                    System.out.println("XML SAT impresso com sucesso!");
                    avancarPapel();
                    cortarPapel();
                } else {
                    System.out.println("Erro ao imprimir XML SAT. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Erro ao carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Conexao fechada. Abra a conexao antes.");
        }
    }

    // Lê o XML de cancelamento, extrai a assinatura do QR Code e envia para impressão.
    void imprimirXMLCancelamento() {

        if (conexaoAberta) {

            // Caminho fixo do arquivo XML de cancelamento.
            String caminho = "D:\\TB\\Projeto POO EM\\Projeto POO EM\\CANC_SAT.xml";
            Path arquivo = Paths.get(caminho);

            System.out.println("Imprimindo XML de cancelamento...");

            if (!Files.isRegularFile(arquivo)) {
                System.out.println("Arquivo XML nao encontrado: " + caminho);
                return;
            }

            try {
                // Lê o conteúdo do arquivo utilizando a codificação UTF-8.
                String xml = new String(
                        Files.readAllBytes(arquivo),
                        StandardCharsets.UTF_8
                );

                // Define as tags utilizadas para localizar a assinatura no XML.
                String inicioTag = "<assinaturaQRCODE>";
                String fimTag = "</assinaturaQRCODE>";

                int inicio = xml.indexOf(inicioTag);
                int fim = xml.indexOf(fimTag);

                // Verifica se as duas tags foram encontradas corretamente.
                if (inicio == -1 || fim == -1 || fim < inicio) {
                    System.out.println("Assinatura QR Code nao encontrada no XML.");
                    return;
                }

                // Extrai o conteúdo localizado entre as tags.
                String assinatura = xml.substring(
                        inicio + inicioTag.length(), fim
                ).trim();

                int ret = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(
                        xml, assinatura, 0
                );

                if (ret == 0) {
                    System.out.println("XML de cancelamento impresso com sucesso!");
                    avancarPapel();
                    cortarPapel();
                } else {
                    System.out.println("Erro ao imprimir cancelamento. Retorno: " + ret);
                }

            } catch (IOException e) {
                // Trata possíveis erros durante a leitura do arquivo.
                System.out.println("Erro ao ler o arquivo XML: " + e.getMessage());

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Erro ao carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Conexao fechada. Abra a conexao antes.");
        }
    }

    // Aciona a abertura da gaveta utilizando parâmetros fixos.
    void abrirGaveta() {

        if (conexaoAberta) {

            int pino = 1;
            int tempoInicio = 5;
            int tempoFim = 5;

            try {
                int ret = ImpressoraDLL.INSTANCE.AbreGaveta(
                        pino, tempoInicio, tempoFim
                );

                if (ret == 0) {
                    System.out.println("Gaveta aberta com sucesso!");
                } else {
                    System.out.println("Erro ao abrir gaveta. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Erro ao carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Conexao fechada. Abra a conexao antes.");
        }
    }

    // Executa o sinal sonoro com quantidade e tempos previamente definidos.
    void sinalSonoro() {

        if (conexaoAberta) {

            int quantidade = 3;
            int tempoInicio = 50;
            int tempoFim = 5;

            try {
                int ret = ImpressoraDLL.INSTANCE.SinalSonoro(
                        quantidade, tempoInicio, tempoFim
                );

                if (ret == 0) {
                    System.out.println("Sinal sonoro executado com sucesso!");
                } else {
                    System.out.println("Erro no sinal sonoro. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Erro ao carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Conexao fechada. Abra a conexao antes.");
        }
    }

    // Avança o papel utilizando uma quantidade fixa de linhas.
    void avancarPapel() {

        if (conexaoAberta) {

            int linhas = 5;

            try {
                int ret = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);

                if (ret == 0) {
                    System.out.println("Papel avancado com sucesso!");
                } else {
                    System.out.println("Erro ao avancar papel. Retorno: " + ret);
                }

            } catch (UnsatisfiedLinkError e) {
                System.out.println("Erro ao carregar a DLL: " + e.getMessage());
            }

        } else {
            System.out.println("Conexao fechada. Abra a conexao antes.");
        }
    }

    // Executa o corte do papel após a impressão.
    void cortarPapel() {

        if (!conexaoAberta) {
            System.out.println("Conexao fechada. Nao foi possivel cortar o papel.");
            return;
        }

        try {
            int ret = ImpressoraDLL.INSTANCE.Corte(3);

            if (ret == 0) {
                System.out.println("Papel cortado com sucesso!");
            } else {
                System.out.println("Erro ao cortar papel. Retorno: " + ret);
            }

        } catch (UnsatisfiedLinkError e) {
            System.out.println("Erro ao carregar a DLL: " + e.getMessage());
        }
    }
}
