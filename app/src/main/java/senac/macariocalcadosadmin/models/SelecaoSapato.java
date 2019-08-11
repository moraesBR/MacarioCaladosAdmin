package senac.macariocalcadosadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SelecaoSapato implements Parcelable {
    private Sapato sapato;
    private boolean selecionado;

    public SelecaoSapato() {
    }

    public SelecaoSapato(Sapato sapato) {
        this.sapato = sapato;
        this.selecionado = false;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado() {
        this.selecionado = this.selecionado ? false: true;
    }

    public Sapato getSapato() {
        return sapato;
    }

    public void setSapato(Sapato sapato) {
        this.sapato = sapato;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.sapato, flags);
        dest.writeByte(this.selecionado ? (byte) 1 : (byte) 0);
    }

    protected SelecaoSapato(Parcel in) {
        this.sapato = in.readParcelable(Sapato.class.getClassLoader());
        this.selecionado = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SelecaoSapato> CREATOR = new Parcelable.Creator<SelecaoSapato>() {
        @Override
        public SelecaoSapato createFromParcel(Parcel source) {
            return new SelecaoSapato(source);
        }

        @Override
        public SelecaoSapato[] newArray(int size) {
            return new SelecaoSapato[size];
        }
    };
}
