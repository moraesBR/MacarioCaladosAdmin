package senac.macariocalcadosadmin.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Upload implements Parcelable {
    private String nome;
    private Uri url;

    public Upload() {
    }

    public Upload(String nome) {
        this.nome = nome;
    }

    public Upload(Uri url) {
        this.url = url;
    }

    public Upload(String nome, Uri url) {
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

    protected Upload(Parcel in) {
        this.nome = in.readString();
        this.url = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Upload> CREATOR = new Creator<Upload>() {
        @Override
        public Upload createFromParcel(Parcel source) {
            return new Upload(source);
        }

        @Override
        public Upload[] newArray(int size) {
            return new Upload[size];
        }
    };
}
