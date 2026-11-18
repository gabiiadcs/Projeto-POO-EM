Projeto Elgin – Sistema de Impressão

1. Sobre o projeto
O projeto foi desenvolvido em Java e tem como objetivo controlar uma impressora Elgin, permitindo configurar a conexão e realizar diferentes tipos de impressão.

2. Tecnologias utilizadas
- Java
- Biblioteca JNA
- DLL da Elgin
- Java Scanner

 3. Estrutura do projeto
- Main: inicia o programa e realiza o login.
- Usuario: verifica o usuário e a senha.
- EntradaConsole: faz a leitura dos dados.
- Menu: apresenta as opções do sistema.
- Impressora: contém as funções da impressora.
- ImpressoraDLL: faz a comunicação com a DLL da Elgin.

4. Como executar
1. Instalar o Java JDK.
2. Abrir o projeto em uma IDE.
3. Adicionar a biblioteca JNA.
4. Verificar se as DLLs estão na pasta correta.
5. Executar a classe Main.

 5. Login de teste
- Usuário: `admin`
- Senha: `1234`

O sistema permite até três tentativas de login.

6. Funcionalidades do menu
- Configurar e conectar a impressora.
- Imprimir textos, QR Codes e códigos de barras.
- Imprimir XML SAT e cancelamento SAT.
- Abrir gaveta.
- Executar sinal sonoro.
- Avançar e cortar papel.
- Fechar conexão.

7. Testes realizados
Foram previstas verificações de login, conexão, existência dos arquivos XML e funcionamento das operações. Também foram tratados possíveis erros de comunicação com a DLL.

 8. Autores
- Enzo Lima
- Gabriela Alves
- Sara Vitor Dias
