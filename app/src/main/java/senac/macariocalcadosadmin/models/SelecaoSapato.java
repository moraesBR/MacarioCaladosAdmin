package senac.macariocalcadosadmin.models;

import android.os.Parcel;

public class SelecaoSapato extends Sapato {
    private boolean selecionado;

    public SelecaoSapato(String nome, String tipo, String modelo, String genero, String idade, String tamanho) {
        super(nome, tipo, modelo, genero, idade, tamanho);
        this.selecionado = false;
    }

    public SelecaoSapato(String nome, String tipo, String modelo, String genero, String idade) {
        super(nome, tipo, modelo, genero, idade);
        this.selecionado = false;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado() {
        this.selecionado = this.selecionado ? false: true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.selecionado ? (byte) 1 : (byte) 0);
    }

    protected SelecaoSapato(Parcel in) {
        super(in);
        this.selecionado = in.readByte() != 0;
    }

    public static final Creator<SelecaoSapato> CREATOR = new Creator<SelecaoSapato>() {
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
