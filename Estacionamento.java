import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Estacionamento {

    private final int CAPACIDADE;
    private ArrayList<Veiculo> veiculosEstacionados = new ArrayList<>();
    private Queue<Veiculo> filaEspera = new LinkedList<>();

    private double faturamentoTotal = 0;

    // Tarifas
    private final double CARRO_INICIAL = 12, CARRO_ADICIONAL = 8;
    private final double MOTO_INICIAL = 8,  MOTO_ADICIONAL = 5;

    public Estacionamento(int capacidade) {
        this.CAPACIDADE = capacidade;
    }

    // ----- MÉTODOS DE CONSULTA -----

    public int getVagasOcupadas() {
        return veiculosEstacionados.size();
    }

    public int getVagasLivres() {
        return CAPACIDADE - getVagasOcupadas();
    }

    public void mostrarVagas() {
        System.out.println("--- Vagas ---");
        System.out.println("Capacidade Total: " + CAPACIDADE);
        System.out.println("Ocupadas: " + getVagasOcupadas());
        System.out.println("Disponiveis: " + getVagasLivres());
        System.out.println("-----------------");
    }

    public Optional<Veiculo> pesquisarVeiculo(String placa) {
        return veiculosEstacionados.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    // ----- LISTAR VEÍCULOS -----

    public void listarVeiculos() {
        if (veiculosEstacionados.isEmpty()) {
            System.out.println("️ Estacionamento vazio. Nenhum veiculo presente.");
            return;
        }

        System.out.println("--- Veiculos Estacionados (" + getVagasOcupadas() + " Total) ---");
        for (Veiculo v : veiculosEstacionados) {
            System.out.println("- " + v);
        }
        System.out.println("------------------------------------");
    }

    // ----- REGISTRAR ENTRADA -----

    public boolean registrarEntrada(String placa, LocalDateTime horaEntrada, String tipo) {

        // Verifica duplicidade
        if (pesquisarVeiculo(placa).isPresent()) {
            System.out.println(" O veiculo com placa " + placa + " ja esta estacionado.");
            return false;
        }

        Veiculo novo = new Veiculo(placa, horaEntrada, tipo);

        // Se houver vaga → entra
        if (getVagasLivres() > 0) {
            veiculosEstacionados.add(novo);
            System.out.println(" Entrada registrada! " + novo);
            return true;
        }

        // Se não houver vaga → vai para fila
        filaEspera.add(novo);
        System.out.println("️ Estacionamento cheio. Veiculo enviado para fila de espera.");
        System.out.println("Posicao na fila: " + filaEspera.size());
        return false;
    }

    // ----- REGISTRAR SAÍDA -----

    public double registrarSaida(String placa, LocalDateTime horaSaida) {
        Optional<Veiculo> opt = pesquisarVeiculo(placa);

        if (opt.isEmpty()) {
            System.out.println(" Veiculo não encontrado.");
            return 0;
        }

        Veiculo v = opt.get();
        veiculosEstacionados.remove(v);

        long horas = Duration.between(v.getHoraEntrada(), horaSaida).toHours();
        if (horas == 0) horas = 1; // 1h mínima

        double total = calcularValor(v.getTipo(), horas);
        faturamentoTotal += total;

        System.out.println(" Veiculo " + v.getPlaca() + " saiu.");
        System.out.println("Tipo: " + v.getTipo());
        System.out.println("Horas cobradas: " + horas);
        System.out.printf("Total a pagar: R$ %.2f\n", total);

        // Liberou vaga → chama o próximo da fila automaticamente
        if (!filaEspera.isEmpty()) {
            Veiculo daFila = filaEspera.poll();
            veiculosEstacionados.add(daFila);
            System.out.println(" Fila liberada! Veiculo " + daFila.getPlaca() + " entrou automaticamente.");
        }

        return total;
    }

    // ----- CÁLCULO DAS TARIFAS -----

    private double calcularValor(String tipo, long horas) {
        if (tipo.equalsIgnoreCase("CARRO")) {
            return CARRO_INICIAL + (Math.max(0, horas - 1) * CARRO_ADICIONAL);
        } else {
            return MOTO_INICIAL + (Math.max(0, horas - 1) * MOTO_ADICIONAL);
        }
    }

    // ----- RELATÓRIO DE FATURAMENTO -----

    public void relatorioFaturamento() {
        System.out.println("===== RELATORIO DE FATURAMENTO =====");
        System.out.printf("Total arrecadado ate o momento: R$ %.2f\n", faturamentoTotal);
    }
}
