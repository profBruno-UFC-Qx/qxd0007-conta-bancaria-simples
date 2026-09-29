/**
 * @author bruno
 *
 */
public class Conta {

    public Conta(int numero, double saldo) {
    }

    
    public int getNumero() {
        return 0;
    }
    
    public double getSaldo() {
        return 0;
    }

    
    public double getLimite() {
        return 0;
    }
    
    public boolean sacar(double valor) {
        return false;
    }

   
    public boolean depositar(double valor) {
        return true;
    }

    
    public boolean transferir(Conta destino, double valor) {
        return false;
    }


    public boolean alterarLimite(double novoLimite) {
        return false;
    }


    public double[] verExtrato() {

        return null;
    }
}