import java.util.ArrayList;
import java.util.List;

public class Conta {

    private int numero;
    private double saldo;
    private double limite;
    private List<Double> extrato;

    public Conta(int numero, double saldo) {
        this.numero = numero;
        this.saldo = saldo;
        this.limite = 100;
        this.extrato = new ArrayList<>();
    }

    public boolean sacar(double valor) {
        if (valor < 0 || valor > saldo + limite || extrato.size() >= 10) {
            return false;
        }

        saldo -= valor;

        if (saldo < 0) {
            limite += saldo;
            saldo = 0;
        }

        extrato.add(-valor);
        return true;
    }

    public boolean depositar(double valor) {
        if (valor < 0 || extrato.size() >= 10) {
            return false;
        }

        if (limite < 100) {
            double recuperacao = Math.min(valor, 100 - limite);
            limite += recuperacao;
            valor -= recuperacao;
        }

        saldo += valor;
        extrato.add(valor);

        return true;
    }

    public boolean transferir(Conta destino, double valor) {
        if (valor < 0 || valor > saldo + limite || extrato.size() >= 10) {
            return false;
        }

        saldo -= valor;

        if (saldo < 0) {
            limite += saldo;
            saldo = 0;
        }

        extrato.add(-valor);
        destino.depositar(valor);

        return true;
    }

    public double[] verExtrato() {
        double[] resultado = new double[extrato.size()];

        for (int i = 0; i < extrato.size(); i++) {
            resultado[i] = extrato.get(i);
        }

        return resultado;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero=" + numero +
                ", saldo=" + saldo +
                ", limite=" + limite +
                '}';
    }
}
