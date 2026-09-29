public class Mapa {
    private final char [][] layout;
    private final int largura;
    private final int altura;

    public Mapa (int largura, int altura){
        this.altura = altura;
        this.largura = largura;
        this.layout = new char [largura][altura];
        inicializarMapa();
    }

    private void inicializarMapa (){
        for (int i = 0; i < largura; i++ ){
        for (int j = 0; j < altura; j++){
        layout[i][j] = 'w';
            }
        }
    }

    public void exibirMapa () {
        for (int i = 0; i < largura; i++){
            for (int j = 0; j < altura; j++){
                System.out.print (layout [i][j] + " ");
            }
            System.out.println ();
        }
    }

    public boolean posicaoValida (int x, int y){
        if (y >= 0 && y <= altura && x > 0 && x <= largura){
            return true;
        }
        else {
            return false;
        }
    }

}
