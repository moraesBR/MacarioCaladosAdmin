package senac.macariocalcadosadmin.models;

import android.net.Uri;
import android.os.Parcel;

public class SelecaoUpload extends Upload {
    private boolean selecionada;

    public SelecaoUpload() {
        super();
        this.selecionada = false;
    }

    public SelecaoUpload(String nome) {
        super(nome);
        this.selecionada = false;
    }

    public SelecaoUpload(Uri url) {
        super(url);
        this.selecionada = false;
    }

    public SelecaoUpload(String nome, Uri url) {
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

    protected SelecaoUpload(Parcel in) {
        super(in);
        this.selecionada = in.readByte() != 0;
    }

    public static final Creator<SelecaoUpload> CREATOR = new Creator<SelecaoUpload>() {
        @Override
        public SelecaoUpload createFromParcel(Parcel source) {
            return new SelecaoUpload(source);
        }

        @Override
        public SelecaoUpload[] newArray(int size) {
            return new SelecaoUpload[size];
        }
    };
}
