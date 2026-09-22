public class Runner {

    public static void main(final String[] args) {

        Conta minhaConta = new Conta(1001, 2000);
        System.out.println(minhaConta);

        Conta destino = new Conta(10, 0);

        minhaConta.sacar(200);
        System.out.println(minhaConta);

        if (!minhaConta.sacar(10000)) {
            System.out.println("Saldo insuficiente");
        }

        minhaConta.depositar(500);
        System.out.println(minhaConta);

        minhaConta.transferir(destino, 400);
        System.out.println(minhaConta);

        if (!minhaConta.transferir(destino, 4000000)) {
            System.out.println("Saldo insuficiente");
        }

        minhaConta.sacar(1950);
        System.out.println(minhaConta);

        minhaConta.depositar(50);
        System.out.println(minhaConta);

        double[] extrato = minhaConta.verExtrato();

        for (int i = 0; i < extrato.length; i++) {
            System.out.println(extrato[i]);
        }
    }
}
