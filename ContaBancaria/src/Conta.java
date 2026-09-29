public class Conta {
    private int numero;
    private double saldo;
    private double limite;
    //    private double extrato[];
//    private ArrayList<Double> extrato;
    private double[] extrato;
    private int operacoesRealizadas;

    public Conta(int numero, double saldoinicial) {
        this.numero = numero;
        this.saldo = saldoinicial;
        this.limite = 100.0;
        this.extrato = new double[10];
//        this.extrato = new ArrayList<>()
    }

    public double getSaldo() {
        return saldo;
    }

    boolean sacar(double valor) {
        if (valor < 0) {
            return false;
        }
        else if (valor > saldo + limite) {
            return false;
        }
        else {
            this.saldo = this.saldo - valor;
            for (int i = 0; i < 10; i++){
                if (extrato[i] == 0){
                    extrato[i] = - valor;
                    break;
                }
            }
            return true;
        }
    }

    boolean depositar (double valor){
        if (valor < 0){
            return false;
        }
        else {
            this.saldo = this.saldo + valor;
            for (int i = 0; i < 10; i++){
                if (extrato[i] == 0){
                    extrato[i] = + valor;
                    break;
                }
            }
            return true;
        }
    }
    boolean transferir (Conta destino, double valor){
        if (valor < 0 || valor > saldo){
            return false;
        }
        else {
            this.saldo = this.saldo - valor;
            destino.depositar(valor);

            for (int i = 0; i < 10; i++){
                if (extrato[i] == 0){
                    extrato[i] = - valor;
                    break;
                }
            }
            return true;
        }
    }

    double[] verExtrato() {
        return extrato;
    }

    public String toString(){
        return "Conta{numero=" + numero + ", saldo=" + saldo + ", limite=" + limite + "}";
    }

}

