public class Main {

    public static void main(String[] args) {
        Conta minhaConta = new Conta(1001, 2000);
        System.out.println(minhaConta); //Conta{numero=1001, saldo=2000.0, limite=100.0}

        Conta destino = new Conta(10, 0);

        minhaConta.sacar(200);
        System.out.println(minhaConta); //Conta{numero=1001, saldo=1800.0, limite=100.0}

        if(!minhaConta.sacar(10000)){
            System.out.println("Saldo insuficiente"); //Saldo insuficiente
        }

        minhaConta.depositar(500);
        System.out.println(minhaConta); //Conta{numero=1001, saldo=2300.0, limite=100.0}

        minhaConta.transferir(destino, 400);
        System.out.println(minhaConta); //Conta{numero=1001, saldo=1900.0, limite=100.0}

        if(!minhaConta.transferir(destino, 4000000)) {
            System.out.println("Saldo insuficiente"); //Saldo insuficiente
        }

        minhaConta.sacar(1950);
        System.out.println(minhaConta); //Conta{numero=1001, saldo=0.0, limite=50.0}

        minhaConta.depositar(50);
        System.out.println(minhaConta); //Conta{numero=1001, saldo=0.0, limite=100.0}

        double[] extrato = minhaConta.verExtrato();
        for(int i = 0; i < extrato.length; i++) {
            System.out.println(extrato[i]); // -200.0 500.0 -400.0 -1950.0 50.0
        }
    }

}