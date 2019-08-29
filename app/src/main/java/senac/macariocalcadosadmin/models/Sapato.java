package senac.macariocalcadosadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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
    private double antigoValor;
    private List<Foto> fotos;
    private int quantidade;
    private boolean promocao;
    private String key;

    public Sapato() {
    }
    /*
    public Sapato(String nome, String tipo, String modelo, String genero, String idade, String tamanho) {
        if (nome.isEmpty() || modelo.isEmpty())
            throw new IllegalArgumentException();

        this.genero = Genero.valueOf(genero);
        this.modelo = modelo;
        this.tipo = Tipo.valueOf(tipo);
        this.idade = Idade.valueOf(idade);
        if (this.genero.equals(Genero.FEMININO)) {
            this.tamanho = Tamanho.valueOf(TamFeminino.valueOf(tamanho).toString());
        }
        if (this.genero.equals(Genero.MASCULINO)) {
            this.tamanho = Tamanho.valueOf(TamMasculino.valueOf(tamanho).toString());
        }
        this.nome = nome;
        this.valor = 0.0;
        this.quantidade = 0;
        this.promocao = false;
        this.codigo = MD5.md5(this.nome
                + this.tipo.toString()
                + this.modelo
                + this.genero.toString()
                + this.idade.toString());
    }
    */

    public Sapato(String nome, String tipo, String modelo, String genero, String idade) {

        if (nome.isEmpty() || modelo.isEmpty())
            throw new IllegalArgumentException("erro");

        this.genero = Genero.valueOf(genero);
        this.modelo = modelo;
        this.tipo = Tipo.valueOf(tipo);
        this.idade = Idade.valueOf(idade);
        this.nome = nome;
        this.valor = 0.0;
        this.quantidade = 0;
        this.promocao = false;
        this.codigo = MD5.md5(this.nome
                + this.tipo.toString()
                + this.modelo
                + this.genero.toString()
                + this.idade.toString());
    }

    public void setCodigo(){
        this.codigo = MD5.md5(this.nome
                + this.tipo.toString()
                + this.modelo
                + this.genero.toString()
                + this.idade.toString());
    }

    public String getKey() {
        return key;
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

    public double getAntigoValor() {
        return antigoValor;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setIdade(Idade idade) {
        this.idade = idade;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isPromocao() {
        return promocao;
    }

    public void setQuantidade(int quantidade) {
        try {
            if (quantidade < 0) throw new IllegalArgumentException();
            this.quantidade = quantidade;
        } catch (IllegalArgumentException ex) {
            this.quantidade = 0;
        }
    }

    public void setAntigoValor(double antigoValor) {
        this.antigoValor = antigoValor;
    }

    public boolean setValor(double valor) {
        try {
            if (valor < 0.0) throw new IllegalArgumentException();
            if(valor != this.valor) {
                this.promocao = valor < this.valor ? true : false;
                this.antigoValor = this.valor;
                this.valor = valor;
                return true;
            }
            return false;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public static void copiar(Sapato orig, Sapato dest){
        dest.codigo = String.copyValueOf(orig.codigo.toCharArray());
        dest.nome = String.copyValueOf(orig.nome.toCharArray());
        dest.modelo = String.copyValueOf(orig.modelo.toCharArray());
        dest.genero = Genero.valueOf(orig.genero.toString());
        dest.tipo = Tipo.valueOf(orig.tipo.toString());
        dest.idade = Idade.valueOf(orig.idade.toString());
        //dest.tamanho = Tamanho.valueOf(orig.tamanho.toString());
        dest.valor = orig.valor;
        dest.antigoValor = orig.antigoValor;
        dest.fotos = new ArrayList<>(orig.fotos);
        dest.quantidade = orig.quantidade;
        dest.promocao = orig.promocao;
        dest.key = orig.key;
    }

    public void addFoto(Foto foto) {
        this.fotos.add(foto);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codigo);
        dest.writeString(this.nome);
        dest.writeString(this.modelo);
        dest.writeInt(this.genero == null ? -1 : this.genero.ordinal());
        dest.writeInt(this.idade == null ? -1 : this.idade.ordinal());
        dest.writeInt(this.tipo == null ? -1 : this.tipo.ordinal());
        dest.writeInt(this.tamanho == null ? -1 : this.tamanho.ordinal());
        dest.writeDouble(this.valor);
        dest.writeDouble(this.antigoValor);
        dest.writeTypedList(this.fotos);
        dest.writeInt(this.quantidade);
        dest.writeByte(this.promocao ? (byte) 1 : (byte) 0);
        dest.writeString(this.key);
    }

    protected Sapato(Parcel in) {
        this.codigo = in.readString();
        this.nome = in.readString();
        this.modelo = in.readString();
        int tmpGenero = in.readInt();
        this.genero = tmpGenero == -1 ? null : Genero.values()[tmpGenero];
        int tmpIdade = in.readInt();
        this.idade = tmpIdade == -1 ? null : Idade.values()[tmpIdade];
        int tmpTipo = in.readInt();
        this.tipo = tmpTipo == -1 ? null : Tipo.values()[tmpTipo];
        int tmpTamanho = in.readInt();
        this.tamanho = tmpTamanho == -1 ? null : Tamanho.values()[tmpTamanho];
        this.valor = in.readDouble();
        this.antigoValor = in.readDouble();
        this.fotos = in.createTypedArrayList(Foto.CREATOR);
        this.quantidade = in.readInt();
        this.promocao = in.readByte() != 0;
        this.key = in.readString();
    }

    public static final Creator<Sapato> CREATOR = new Creator<Sapato>() {
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

