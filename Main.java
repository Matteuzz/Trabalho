import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Estacionamento estacionamento = new Estacionamento(3); // capacidade exemplo

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Registrar entrada");
            System.out.println("2. Registrar saida");
            System.out.println("3. Listar veiculos");
            System.out.println("4. Mostrar vagas");
            System.out.println("5. Faturamento");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            int op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1 -> handleEntrada(sc, estacionamento);
                case 2 -> handleSaida(sc, estacionamento);
                case 3 -> estacionamento.listarVeiculos();
                case 4 -> estacionamento.mostrarVagas();
                case 5 -> estacionamento.relatorioFaturamento();
                case 0 -> { return; }
            }
        }
    }

    private static void handleEntrada(Scanner sc, Estacionamento estacionamento) {
        System.out.print("Digite a placa: ");
        String placa = sc.nextLine().toUpperCase();

        System.out.println("Tipo de veiculo:");
        System.out.println("1. Carro");
        System.out.println("2. Moto");
        System.out.print("Escolha: ");
        String tipo = sc.nextLine().equals("1") ? "CARRO" : "MOTO";

        LocalDateTime horaEntrada = null;

        System.out.println("1. Usar hora atual");
        System.out.println("2. Informar manualmente");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(sc.nextLine());

        if (op == 1) {
            horaEntrada = LocalDateTime.now();
        } else {
            System.out.print("Digite (dd/MM/yyyy HH:mm): ");
            String texto = sc.nextLine();
            horaEntrada = LocalDateTime.parse(texto, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }

        estacionamento.registrarEntrada(placa, horaEntrada, tipo);
    }

    private static void handleSaida(Scanner sc, Estacionamento estacionamento) {
        System.out.print("Digite a placa do veiculo: ");
        String placa = sc.nextLine();

        LocalDateTime horaSaida = LocalDateTime.now();
        estacionamento.registrarSaida(placa, horaSaida);
    }
}
