package senac.macariocalcadosadmin.models;

public class SelecaoFoto {
    private boolean selecionada;
    private Foto foto;

    public SelecaoFoto(Foto foto) {
        this.foto = foto;
        this.selecionada = false;
    }

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
