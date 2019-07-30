package senac.macariocalcadosadmin.models;

import android.util.Log;

import java.util.List;

public class Sapato {
    private String codigo;
    private String nome;
    private Genero genero;
    private Idade idade;
    private Tipo tipo;
    private Tamanho tamanho;
    private double valor;
    private List<String> urlImagem;
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

    public List<String> getUrlImagem() {
        return urlImagem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean isPromocao() {
        return promocao;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPromocao(boolean promocao) {
        this.promocao = promocao;
    }

    public boolean setValor(double valor) {
        try{
            if (valor < 0.0) throw new IllegalArgumentException();
            this.valor = valor;
            return true;
        }
        catch (IllegalArgumentException ex){
            this.valor = 0.0;
            return false;
        }
    }

    public void setUrlImagem(List<String> urlImagem) {
        this.urlImagem = urlImagem;
    }
}

