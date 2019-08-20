package senac.macariocalcadosadmin.models;

public enum Campo {
    GENERO("genero"), IDADE("idade"), TIPO("tipo"), VALOR("valor"), PROMOCAO("promocao");

    private final String campo;

    private Campo(String campo) {
        this.campo = campo;
    }

    @Override
    public String toString() {
        return campo;
    }
}
