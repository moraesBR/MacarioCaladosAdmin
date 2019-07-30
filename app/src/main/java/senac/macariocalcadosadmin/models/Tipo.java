package senac.macariocalcadosadmin.models;

public enum Tipo {
    ESPORTIVO("esportivo"),
    CASUAL("casual"),
    SOCIAL("social");

    private final String tipo;

    private Tipo (String tipo){
        this.tipo = tipo;
    }
}
