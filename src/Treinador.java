public class Treinador {
    private String name;
    private int x;
    private int y;
    private Main.Pokemon[] pokemons;

    public Treinador(String name, int x, int y, int capacidade){
        this.name = name;
        this.x = x;
        this.y = y;
        this.pokemons = new Main.Pokemon[capacidade];
    }

    public void mover (String direcao){
        if (direcao.equals ("cima")) {
            this.y = this.y - 1;
        }
        else if (direcao.equals("baixo")){
            this.y = this.y + 1;
        }
        else if (direcao.equals("esquerda")){
            this.x = this.x - 1;
        }
        else if (direcao.equals("direita")){
            this.x = this.x + 1;
        }
        else {
            System.out.println ("Ops, acho que não tem nenhum comando.");
        }
    }
    public void listar (){
        for (int i = 0; i < pokemons.length ; i++){
            System.out.println(pokemons[i].name);
        }
    }
    public boolean capturar(Main.Pokemon novo){
        for (int i = 0; i < pokemons.length; i++){
            if (pokemons[i]== null){
                pokemons[i] = novo;
                return true;
            }
        }
        return false;

    }
}