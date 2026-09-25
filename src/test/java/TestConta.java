import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestConta {

    private static final double DELTA = 0.01;

    private Conta conta;
    private Conta destino;

    @BeforeEach
    public void inicializacao() {
        conta = new Conta(1001, 2000);
        destino = new Conta(10, 0);
    }

    // ---------- Inicialização ----------

    @Test
    @DisplayName("Conta criada com número, saldo informado e limite de 100")
    public void criarConta() {
        assertEquals(1001, conta.getNumero(), "O numero da conta deve ser igual ao informado na criacao");
        assertEquals(100, conta.getLimite(), DELTA, "O limite inicial de uma conta deve ser 100");
        assertEquals(2100, conta.getSaldo(), DELTA, "getSaldo() deve retornar o saldo mais o limite");

        Conta outra = new Conta(1010, 1000);
        assertEquals(1010, outra.getNumero(), "O numero da conta deve ser igual ao informado na criacao");
        assertEquals(100, outra.getLimite(), DELTA, "O limite inicial de uma conta deve ser 100");
        assertEquals(1100, outra.getSaldo(), DELTA, "getSaldo() deve retornar o saldo mais o limite");
    }

    @Test
    @DisplayName("Conta recém-criada tem extrato vazio")
    public void extratoInicialVazio() {
        double[] extrato = conta.verExtrato();
        assertNotNull(extrato, "verExtrato() nao deve retornar null");
        assertEquals(0, extrato.length, "Uma conta nova nao deve ter operacoes no extrato");
    }

    // ---------- Saques ----------

    @Test
    @DisplayName("Não é possível sacar valor negativo")
    public void naoPodeSacarValorNegativo() {
        assertFalse(conta.sacar(-100), "Sacar valores negativos nao deve ser permitido");
        assertEquals(2100, conta.getSaldo(), DELTA, "O saldo deve permanecer inalterado");
        assertEquals(100, conta.getLimite(), DELTA, "O limite deve permanecer inalterado");
    }

    @Test
    @DisplayName("Não é possível sacar mais que o saldo disponível")
    public void naoPodeSacarMaisQueOSaldoDisponivel() {
        assertFalse(conta.sacar(5000), "Nao deve ser possivel sacar mais que saldo + limite");
        assertEquals(2100, conta.getSaldo(), DELTA, "O saldo deve permanecer inalterado");
        assertEquals(100, conta.getLimite(), DELTA, "O limite deve permanecer inalterado");
    }

    @Test
    @DisplayName("Saque sem usar o limite")
    public void podeSacarSemUsarLimite() {
        assertTrue(conta.sacar(1000), "Sacar um valor menor que o saldo deve ser permitido");
        assertEquals(1100, conta.getSaldo(), DELTA, "O saldo nao foi atualizado corretamente");
        assertEquals(100, conta.getLimite(), DELTA, "O limite nao deveria ter sido utilizado");
    }

    @Test
    @DisplayName("Saque usando parte do limite")
    public void podeSacarUsandoLimite() {
        assertTrue(conta.sacar(2050), "Sacar um valor coberto por saldo + limite deve ser permitido");
        assertEquals(50, conta.getSaldo(), DELTA, "O saldo disponivel deve ser o que restou do limite");
        assertEquals(50, conta.getLimite(), DELTA, "Os 50 que excederam o saldo devem sair do limite");
    }

    @Test
    @DisplayName("É possível sacar exatamente o saldo disponível, mas não um centavo a mais")
    public void sacarExatamenteOSaldoDisponivel() {
        Conta outra = new Conta(20, 2000);
        assertFalse(outra.sacar(2100.01), "Nao deve ser possivel sacar mais que saldo + limite");

        assertTrue(conta.sacar(2100), "Deve ser possivel sacar exatamente saldo + limite");
        assertEquals(0, conta.getSaldo(), DELTA, "Todo o saldo disponivel foi sacado");
        assertEquals(0, conta.getLimite(), DELTA, "Todo o limite foi utilizado");
    }

    @Test
    @DisplayName("Depois de usar parte do limite, só o que restou dele pode ser sacado")
    public void naoPodeSacarMaisQueOLimiteRestante() {
        assertTrue(conta.sacar(2050), "Sacar um valor coberto por saldo + limite deve ser permitido");
        assertFalse(conta.sacar(51), "Restam apenas 50 de limite");
        assertEquals(50, conta.getSaldo(), DELTA, "O saldo deve permanecer inalterado");
        assertEquals(50, conta.getLimite(), DELTA, "O limite deve permanecer inalterado");
    }

    // ---------- Depósitos ----------

    @Test
    @DisplayName("Não é possível depositar valor negativo")
    public void naoPodeDepositarValorNegativo() {
        assertFalse(conta.depositar(-100), "Depositar valores negativos nao deve ser permitido");
        assertEquals(2100, conta.getSaldo(), DELTA, "O saldo deve permanecer inalterado");
    }

    @Test
    @DisplayName("Depósito de valor positivo aumenta o saldo")
    public void podeDepositarValorPositivo() {
        assertTrue(conta.depositar(900), "Depositar valores positivos deve ser permitido");
        assertEquals(3000, conta.getSaldo(), DELTA, "O saldo nao foi atualizado corretamente");
        assertEquals(100, conta.getLimite(), DELTA, "O limite nao deveria mudar");
    }

    @Test
    @DisplayName("Depósito restaura primeiro o limite utilizado")
    public void depositoRestauraOLimite() {
        assertTrue(conta.sacar(2100), "Deve ser possivel sacar exatamente saldo + limite");

        assertTrue(conta.depositar(50), "Depositar valores positivos deve ser permitido");
        assertEquals(50, conta.getSaldo(), DELTA, "O saldo nao foi atualizado corretamente");
        assertEquals(50, conta.getLimite(), DELTA, "O deposito deve ir primeiro para o limite");

        assertTrue(conta.depositar(100), "Depositar valores positivos deve ser permitido");
        assertEquals(150, conta.getSaldo(), DELTA, "O saldo nao foi atualizado corretamente");
        assertEquals(100, conta.getLimite(), DELTA, "O limite deve voltar a 100 e o restante ir para o saldo");
    }

    // ---------- Transferências ----------

    @Test
    @DisplayName("Não é possível transferir valor negativo")
    public void naoPodeTransferirValorNegativo() {
        assertFalse(conta.transferir(destino, -50), "Transferir valores negativos nao deve ser permitido");
        assertEquals(2100, conta.getSaldo(), DELTA, "O saldo da origem deve permanecer inalterado");
        assertEquals(100, destino.getSaldo(), DELTA, "O saldo do destino deve permanecer inalterado");
    }

    @Test
    @DisplayName("Não é possível transferir mais que o saldo disponível")
    public void naoPodeTransferirMaisQueOSaldoDisponivel() {
        assertFalse(conta.transferir(destino, 3000), "Nao deve ser possivel transferir mais que saldo + limite");
        assertEquals(2100, conta.getSaldo(), DELTA, "O saldo da origem deve permanecer inalterado");
        assertEquals(100, destino.getSaldo(), DELTA, "O saldo do destino deve permanecer inalterado");
    }

    @Test
    @DisplayName("Transferência sem usar o limite")
    public void podeTransferirSemUsarLimite() {
        assertTrue(conta.transferir(destino, 1000), "Transferir um valor menor que o saldo deve ser permitido");
        assertEquals(1100, conta.getSaldo(), DELTA, "O saldo da origem nao foi atualizado corretamente");
        assertEquals(100, conta.getLimite(), DELTA, "O limite da origem nao deveria ter sido utilizado");
        assertEquals(1100, destino.getSaldo(), DELTA, "O valor transferido deve ser creditado no destino");
    }

    @Test
    @DisplayName("Transferência usando parte do limite")
    public void podeTransferirUsandoLimite() {
        assertTrue(conta.transferir(destino, 2050), "Transferir um valor coberto por saldo + limite deve ser permitido");
        assertEquals(50, conta.getSaldo(), DELTA, "O saldo disponivel da origem deve ser o que restou do limite");
        assertEquals(50, conta.getLimite(), DELTA, "Os 50 que excederam o saldo devem sair do limite da origem");
        assertEquals(2150, destino.getSaldo(), DELTA, "O valor transferido deve ser creditado no destino");
    }

    @Test
    @DisplayName("Transferência para conta com limite usado restaura primeiro o limite do destino")
    public void transferenciaRestauraOLimiteDoDestino() {
        assertTrue(destino.sacar(80), "Deve ser possivel sacar usando o limite");

        assertTrue(conta.transferir(destino, 50), "Transferencia valida deve ser permitida");
        assertEquals(70, destino.getLimite(), DELTA, "A transferencia deve ir primeiro para o limite do destino");
        assertEquals(70, destino.getSaldo(), DELTA, "O saldo do destino nao foi atualizado corretamente");

        assertTrue(conta.transferir(destino, 100), "Transferencia valida deve ser permitida");
        assertEquals(100, destino.getLimite(), DELTA, "O limite do destino deve voltar a 100");
        assertEquals(170, destino.getSaldo(), DELTA, "O restante deve ir para o saldo do destino");
    }

    // ---------- Extrato ----------

    @Test
    @DisplayName("Extrato registra apenas as operações realizadas com sucesso, em ordem")
    public void extratoDoExemplo() {
        conta.sacar(200);
        conta.sacar(10000);
        conta.depositar(500);
        conta.transferir(destino, 400);
        conta.transferir(destino, 4000000);
        conta.sacar(1950);
        conta.depositar(50);

        double[] esperado = {-200.0, 500.0, -400.0, -1950.0, 50.0};
        assertArrayEquals(esperado, conta.verExtrato(), DELTA,
                "O extrato deve conter somente as operacoes realizadas, na ordem em que ocorreram");
    }

    @Test
    @DisplayName("Operações inválidas não aparecem no extrato")
    public void operacoesInvalidasNaoEntramNoExtrato() {
        conta.sacar(-10);
        conta.depositar(-10);
        conta.transferir(destino, -10);
        conta.depositar(30);

        assertArrayEquals(new double[] {30.0}, conta.verExtrato(), DELTA,
                "Operacoes com valores negativos nao devem ser registradas");
    }

    @Test
    @DisplayName("Transferência aparece como depósito no extrato do destino")
    public void transferenciaNoExtratoDoDestino() {
        conta.transferir(destino, 400);

        assertArrayEquals(new double[] {-400.0}, conta.verExtrato(), DELTA,
                "A transferencia deve aparecer com valor negativo no extrato da origem");
        assertArrayEquals(new double[] {400.0}, destino.verExtrato(), DELTA,
                "A transferencia deve aparecer como deposito (valor positivo) no extrato do destino");
    }

    @Test
    @DisplayName("A conta aceita no máximo 10 operações")
    public void limiteDeDezOperacoes() {
        for (int i = 0; i < 10; i++) {
            assertTrue(conta.depositar(10), "As 10 primeiras operacoes devem ser permitidas");
        }

        assertFalse(conta.depositar(10), "Depois de 10 operacoes, novos depositos nao devem ser permitidos");
        assertFalse(conta.sacar(10), "Depois de 10 operacoes, novos saques nao devem ser permitidos");
        assertFalse(conta.transferir(destino, 10), "Depois de 10 operacoes, novas transferencias nao devem ser permitidas");

        assertEquals(2200, conta.getSaldo(), DELTA, "Operacoes recusadas nao devem alterar o saldo");
        assertEquals(100, destino.getSaldo(), DELTA, "Transferencia recusada nao deve alterar o destino");
        assertEquals(10, conta.verExtrato().length, "O extrato deve ter no maximo 10 operacoes");
    }

    // ---------- Representação textual ----------

    @Test
    @DisplayName("toString mostra número, saldo (sem o limite) e limite")
    public void representacaoTextual() {
        assertEquals("Conta{numero=1001, saldo=2000.0, limite=100.0}", conta.toString());

        conta.sacar(2050);
        assertEquals("Conta{numero=1001, saldo=0.0, limite=50.0}", conta.toString(),
                "O saldo exibido nao inclui o limite e nunca fica negativo");
    }
}
