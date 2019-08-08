package senac.macariocalcadosadmin.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Foto implements Parcelable {
    private String nome;
    private Uri url;

    public Foto() {
    }

    public Foto(String nome) {
        this.nome = nome;
    }

    public Foto(Uri url) {
        this.url = url;
    }

    public Foto(String nome, Uri url) {
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

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nome);
        dest.writeParcelable(this.url, flags);
    }

    protected Foto(Parcel in) {
        this.nome = in.readString();
        this.url = in.readParcelable(Uri.class.getClassLoader());
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
