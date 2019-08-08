package senac.macariocalcadosadmin.models;

import android.net.Uri;
import android.os.Parcel;

public class SelecaoFoto extends Foto {
    private boolean selecionada;

    public SelecaoFoto() {
        super();
        this.selecionada = false;
    }

    public SelecaoFoto(String nome) {
        super(nome);
        this.selecionada = false;
    }

    public SelecaoFoto(Uri url) {
        super(url);
        this.selecionada = false;
    }

    public SelecaoFoto(String nome, Uri url) {
        super(nome, url);
        this.selecionada = false;
    }

    public boolean isSelecionada() {
        return this.selecionada;
    }

    public void setSelecionada() {
        this.selecionada = this.selecionada ? false: true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.selecionada ? (byte) 1 : (byte) 0);
    }

    protected SelecaoFoto(Parcel in) {
        super(in);
        this.selecionada = in.readByte() != 0;
    }

    public static final Creator<SelecaoFoto> CREATOR = new Creator<SelecaoFoto>() {
        @Override
        public SelecaoFoto createFromParcel(Parcel source) {
            return new SelecaoFoto(source);
        }

        @Override
        public SelecaoFoto[] newArray(int size) {
            return new SelecaoFoto[size];
        }
    };
}
