package vereadores.eleicao;

public enum Genero {
    MASCULINO(2, "Masculino"),
    FEMININO(4, "Feminino");

    private final int codigo;
    private final String descricao;
    
    Genero(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
    public static Genero fromCodigo(int codigo) {
        for (Genero g : Genero.values()) {
            if (g.getCodigo() == codigo) {
                return g;
            }
        }

        throw new IllegalArgumentException("Código de gênero inválido ou desconhecido no CSV: " + codigo);
    }
}
