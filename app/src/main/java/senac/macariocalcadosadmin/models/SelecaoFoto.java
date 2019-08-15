package senac.macariocalcadosadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SelecaoFoto implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.selecionada ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.foto, flags);
    }

    protected SelecaoFoto(Parcel in) {
        this.selecionada = in.readByte() != 0;
        this.foto = in.readParcelable(Foto.class.getClassLoader());
    }

    public static final Parcelable.Creator<SelecaoFoto> CREATOR = new Parcelable.Creator<SelecaoFoto>() {
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
