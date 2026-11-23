


    // Armazena as credenciais do usuário.
    String usuario;
    String senha;

    // Recebe e inicializa o nome de usuário e a senha.
    public Usuario(String nome, String senhaDigitada) {
        usuario = nome;
        senha = senhaDigitada;
    }

    // Compara os dados informados com as credenciais cadastradas.
    public boolean verificarLogin(String nome, String senhaDigitada) {

        if (usuario.equals(nome) && senha.equals(senhaDigitada)) {
            return true;
        } else {
            return false;
        }
    }
}
