/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Colecao;
import start.App;

/**
 * FXML Controller class
 *
 * @author natan
 */
public class PerfilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static Colecao colecaoSelecionado;
    @FXML
    private TextField txtNomePerfil;
    @FXML
    private TextField txtEpisodiosAssistidosPerfil;
    @FXML
    private TextField txtEpisodiosTotaisPerfil;
    
    private static Colecao colecaoSelecionadoPerfil;
    @FXML
    private Button btnVoltar;
    
    private String caminhoFoto;
    @FXML
    private ImageView imgFotoPerfil;
    @FXML
    private Text lblProgresso;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int epA=0,epT=0;
        if(colecaoSelecionadoPerfil != null){
            txtNomePerfil.setText(colecaoSelecionadoPerfil.getNome());
            txtEpisodiosAssistidosPerfil.setText(colecaoSelecionadoPerfil.getEpisodiosAssistidos());
            txtEpisodiosTotaisPerfil.setText(colecaoSelecionadoPerfil.getEpisodiosTotais());
            this.caminhoFoto = colecaoSelecionadoPerfil.getFoto();
            carregarFoto(this.caminhoFoto);
            epA = Integer.parseInt(colecaoSelecionadoPerfil.getEpisodiosAssistidos());
            epT = Integer.parseInt(colecaoSelecionadoPerfil.getEpisodiosTotais());
            falantes(epA,epT);
        }
    }    
    @FXML
    private void btnVoltarOnAction(ActionEvent event) throws IOException {
        App.setRoot("Principal");
    }
    private static Colecao getColecaoSelecionadoPerfil() {
        return colecaoSelecionadoPerfil;
    }

    public static void setColecaoSelecionadoPerfil(Colecao colecaoSelecionadoPerfil) {
        PerfilController.colecaoSelecionadoPerfil = colecaoSelecionadoPerfil;
    }
    
    private void carregarFoto(String caminhoF){
        Image imagem = new Image("file:/"+caminhoF);
        System.out.println("\n\n\n CaminhoF: "+caminhoF+"\n\n\n");
        this.imgFotoPerfil.setImage(imagem);
        
    }
    public void falantes (int epAssist, int epTotal)
    {
        int tf = 0;
        tf = epTotal - epAssist;
        if(tf == 0)
        {
            lblProgresso.setText("Completo");
        } else
            lblProgresso.setText("Faltam "+Integer.toString(tf)+" episodios para o fim");
    }
}
