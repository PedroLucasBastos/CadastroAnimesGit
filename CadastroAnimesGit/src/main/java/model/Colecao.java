
package model;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;


public class Colecao {
    private int id;
    private String nome;
    private String episodiosTotais;
    private String episodiosAssistidos;
    private String status;
    private String foto = null;
    private CheckBox check;
    
    public  Colecao() {
    }
    public Colecao(int id, String nome, String episodiosTotais, String episodiosAssistidos, String status,String foto){
        this.id = id;
        this.nome = nome;
        this.episodiosTotais = episodiosTotais;
        this.episodiosAssistidos = episodiosAssistidos;
        this.status = status;
        this.foto = foto;
    }
    /*
    public Colecao(int id, String nome, String episodiosTotais, String episodiosAssistidos, String status) {
        this.id = id;
        this.nome = nome;
        this.episodiosTotais = episodiosTotais;
        this.episodiosAssistidos = episodiosAssistidos;
        this.status = status;
    }
    */
    public Colecao(String status) {
        this.status = status;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEpisodiosTotais() {
        return episodiosTotais;
    }

    public void setEpisodiosTotais(String episodiosTotais) {
        this.episodiosTotais = episodiosTotais;
    }

    public String getEpisodiosAssistidos() {
        return episodiosAssistidos;
    }

    public void setEpisodiosAssistidos(String episodiosAssistidos) {
        this.episodiosAssistidos = episodiosAssistidos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override 
    public String toString() {
        return id + " | " + nome + " | " + episodiosAssistidos + " | " + episodiosTotais + " | " + status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


}
