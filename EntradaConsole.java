import java.util.NoSuchElementException;
import java.util.Scanner;

public class EntradaConsole {

    private final Scanner scanner;

    public EntradaConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public String lerLinha() {
        if (!scanner.hasNextLine()) {
            throw new NoSuchElementException("Entrada nao informada.");
        }
        return scanner.nextLine();
    }

    public int lerInteiro() {
        while (true) {
            String valor = lerLinha();
            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException e) {
                System.out.print("Valor invalido! Digite um numero: ");
            }
        }
    }
}
