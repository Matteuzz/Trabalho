import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Veiculo {
    private String placa;
    private LocalDateTime horaEntrada;
    private String tipo; // CARRO ou MOTO

    public Veiculo(String placa, LocalDateTime horaEntrada, String tipo) {
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.tipo = tipo.toUpperCase();
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Placa: " + placa +
                " | Tipo: " + tipo +
                " | Entrada: " + horaEntrada.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}

