package senac.macariocalcadosadmin.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class Sapato implements Parcelable {
    private String codigo;
    private String nome;
    private String modelo;
    private Genero genero;
    private Idade idade;
    private Tipo tipo;
    private Tamanho tamanho;
    private double valor;
    private List<Foto> fotos;
    private int quantidade;
    private boolean promocao;

    public Sapato(String nome, String tipo, String genero, String idade, String tamanho) {
        try{
            if (nome.isEmpty())
                throw new IllegalArgumentException();

            this.genero = Genero.valueOf(genero);

            this.tipo = Tipo.valueOf(tipo);

            this.idade = Idade.valueOf(idade);

            if(genero.equals(Genero.FEMININO)){
                this.tamanho = Tamanho.valueOf(TamFeminino.valueOf(tamanho).toString());
            }
            if(genero.equals(Genero.MASCULINO)){
                this.tamanho = Tamanho.valueOf(TamMasculino.valueOf(tamanho).toString());
            }

            this.nome = nome;

            this.codigo = MD5.md5(this.nome
                            +this.tipo.toString()
                            +this.genero.toString()
                            +this.idade.toString()
                            +this.tamanho.toString());

            this.valor = 0.0;
            this.quantidade = 0;
            this.promocao = false;
        }
        catch (IllegalArgumentException ex){
            Log.e("SAPATO",ex.toString());
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getModelo() {
        return modelo;
    }

    public Genero getGenero() {
        return genero;
    }

    public Idade getIdade() {
        return idade;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public double getValor() {
        return valor;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isPromocao() {
        return promocao;
    }

    public void setQuantidade(int quantidade) {
        try{
            if(quantidade < 0) throw new IllegalArgumentException();
            this.quantidade = quantidade;
        }
        catch (IllegalArgumentException ex){
            this.quantidade = 0;
        }
    }

    public boolean setValor(double valor) {
        try{
            if (valor < 0.0) throw new IllegalArgumentException();
            this.promocao = valor < this.valor? true: false;
            this.valor = valor;
            return true;
        }
        catch (IllegalArgumentException ex){
            this.valor = 0.0;
            return false;
        }
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codigo);
        dest.writeString(this.nome);
        dest.writeInt(this.genero == null ? -1 : this.genero.ordinal());
        dest.writeInt(this.idade == null ? -1 : this.idade.ordinal());
        dest.writeInt(this.tipo == null ? -1 : this.tipo.ordinal());
        dest.writeInt(this.tamanho == null ? -1 : this.tamanho.ordinal());
        dest.writeDouble(this.valor);
        dest.writeTypedList(this.fotos);
        dest.writeInt(this.quantidade);
        dest.writeByte(this.promocao ? (byte) 1 : (byte) 0);
    }

    protected Sapato(Parcel in) {
        this.codigo = in.readString();
        this.nome = in.readString();
        int tmpGenero = in.readInt();
        this.genero = tmpGenero == -1 ? null : Genero.values()[tmpGenero];
        int tmpIdade = in.readInt();
        this.idade = tmpIdade == -1 ? null : Idade.values()[tmpIdade];
        int tmpTipo = in.readInt();
        this.tipo = tmpTipo == -1 ? null : Tipo.values()[tmpTipo];
        int tmpTamanho = in.readInt();
        this.tamanho = tmpTamanho == -1 ? null : Tamanho.values()[tmpTamanho];
        this.valor = in.readDouble();
        this.fotos = in.createTypedArrayList(Foto.CREATOR);
        this.quantidade = in.readInt();
        this.promocao = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Sapato> CREATOR = new Parcelable.Creator<Sapato>() {
        @Override
        public Sapato createFromParcel(Parcel source) {
            return new Sapato(source);
        }

        @Override
        public Sapato[] newArray(int size) {
            return new Sapato[size];
        }
    };
}

