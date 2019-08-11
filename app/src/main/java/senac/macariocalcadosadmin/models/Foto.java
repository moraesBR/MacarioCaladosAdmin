package senac.macariocalcadosadmin.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Foto implements Parcelable {
    private String nome;
    private String url;

    public Foto() {
    }

    public Foto(String nome, String url) {
        if(nome.trim().equals("")){
            nome = "Sem nome";
        }
        this.nome = nome;
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nome);
        dest.writeString(this.url);
    }

    protected Foto(Parcel in) {
        this.nome = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Foto> CREATOR = new Creator<Foto>() {
        @Override
        public Foto createFromParcel(Parcel source) {
            return new Foto(source);
        }

        @Override
        public Foto[] newArray(int size) {
            return new Foto[size];
        }
    };
}
