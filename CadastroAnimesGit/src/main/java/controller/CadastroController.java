
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Colecao;
import model.dao.ColecaoDaoJDBC;
import model.dao.DaoFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class CadastroController implements Initializable {
    //criando lista da combo box
    //comboBox teste para amanha
    /*private ObservableList<String> observableComboBox = FXCollections.observableArrayList("Planejando",
            "Assistindo","Completo");
    */
    public ObservableList<String> observableComboBox = FXCollections.observableArrayList("Planejando",
            "Assistindo","Completo");
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEpisodiosAssistidos;
    @FXML
    private TextField txtEpisodiosTotais;
    @FXML
    private Button btnGravar;
    @FXML
    private Button btnCancelar;
    
    private static Colecao colecaoSelecionado;
    @FXML
    private ComboBox cbbStatus;
    @FXML
    private ImageView imgFoto;
    private String caminhoFoto;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbbStatus.setValue("");
        cbbStatus.setItems(observableComboBox);
         if(colecaoSelecionado != null){
            txtNome.setText(colecaoSelecionado.getNome());
            txtEpisodiosAssistidos.setText(colecaoSelecionado.getEpisodiosAssistidos());
            txtEpisodiosTotais.setText(colecaoSelecionado.getEpisodiosTotais());
            this.caminhoFoto = colecaoSelecionado.getFoto();
            carregarFoto(this.caminhoFoto);
        }
    }    

    @FXML
    private void txtNomeOnAction(ActionEvent event) {
    }

    @FXML
    private void txtEpisodiosAssistidosOnAction(ActionEvent event) {
    }

    @FXML
    private void txtEpisodiosTotaisOnAction(ActionEvent event) {
    }

    @FXML
    private void btnGravarOnAction(ActionEvent event) throws IOException, Exception {
        //Recebenco os dados
        String statusBox = null;
        Colecao colecao = new Colecao();
            if(!isEmpty()){

                    // Chamada da função que fará o cadastro no Banco de Dados
                    cadastrarAnime(colecao,statusBox);
                    //voltando para a tela inicial
                } else
                {
                    Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                    dialogoErro.setTitle("ERROR !!!!!!!!!1");
                    dialogoErro.setHeaderText("Entrada inválida de dados");
                    dialogoErro.setContentText("Nenhum campo pode estar vazio");
                    dialogoErro.showAndWait();
                    App.setRoot("Cadastro");
                }
        
       // App.setRoot("Principal");
}

    @FXML
    private void btnCancelarOnAction(ActionEvent event) throws IOException {
        App.setRoot("Principal");
        colecaoSelecionado = null;
    }

    @FXML
    private void imgFotoMouseClicked(MouseEvent event) throws IOException {
        selecionaFoto();
        colecaoSelecionado = null;
    }
    @FXML
    private void txtEpisodiosTotaisOnKeyReleased(KeyEvent event) {
         char[] text = txtEpisodiosTotais.getText().toCharArray();
         
        if (txtEpisodiosTotais.getText().length() > 0){

            for (int i = 0; i < text.length; i++){
                if (!(text[i] >= '0' && text[i] <= '9')){
                    txtEpisodiosTotais.setText(txtEpisodiosTotais.getText().replace(String.valueOf(text[i]), ""));
                }
            }
        }
    }

    @FXML
    private void txtEpisodiosAssistidosOnKeyPressed(KeyEvent event) {
        char[] text = txtEpisodiosAssistidos.getText().toCharArray();

        if (txtEpisodiosAssistidos.getText().length() > 0){

            for (int i = 0; i < text.length; i++){
                if (!(text[i] >= '0' && text[i] <= '9')){
                    txtEpisodiosAssistidos.setText(txtEpisodiosAssistidos.getText().replace(String.valueOf(text[i]), ""));
                }
            }
        }
    }
    public void selecionaFoto()    {
            FileChooser f = new FileChooser();
            f.getExtensionFilters().add(new ExtensionFilter("Imagens","*.jpg","*.png","*.jpeg"));
            File file = f.showOpenDialog(new Stage());
            if(file != null){
                imgFoto.setImage(new Image("file:///"+file.getAbsolutePath()));
                caminhoFoto = file.getAbsolutePath();
            }


    }
    public static Colecao getColecaoSelecionado() {
        return colecaoSelecionado;
    }

    public static void setColecaoSelecionado(Colecao colecaoSelecionado) {
        CadastroController.colecaoSelecionado = colecaoSelecionado;
    }
    private boolean isEmpty()    {
        String nome = txtNome.getText();
        String epAss = txtEpisodiosAssistidos.getText();
        String epTotal = txtEpisodiosTotais.getText();
        if((nome.equals("")) ||(epAss.equals("")) || (epTotal.equals("")))
            return true;
        return false;
    }
    
    private void cadastrarAnime(Colecao colecao,String statusBox )  throws IOException, Exception    {
        int EpAssistidos = Integer.parseInt(txtEpisodiosAssistidos.getText());
        int EpTotais = Integer.parseInt(txtEpisodiosTotais.getText());
        if((EpTotais >= EpAssistidos))
            {
                colecao.setNome(txtNome.getText());
                colecao.setEpisodiosAssistidos(txtEpisodiosAssistidos.getText());
                colecao.setEpisodiosTotais(txtEpisodiosTotais.getText());
                colecao.setStatus(null);//colocando o status via codigo, por enquanto.

                statusBox = cbbStatus.getSelectionModel().getSelectedItem().toString();
                colecao.setStatus(statusBox);
                colecao.setFoto(caminhoFoto);

                //gravando os dados/verificandio se ta vindo da tela de gravaçao ou edicao
                ColecaoDaoJDBC dao = DaoFactory.novaColecaoDao();
                if(colecaoSelecionado == null){
                    dao.incluir(colecao);
                }else{
                    colecao.setId(colecaoSelecionado.getId());
                    dao.editar(colecao);
                    colecaoSelecionado = null;
                     }
                App.setRoot("Principal");
            }else
            {
                    Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                    dialogoErro.setTitle("ERROR !!!!!!!!!1");
                    dialogoErro.setHeaderText("Entrada inválida de dados");
                    dialogoErro.setContentText("Quantidade de episodios assistidos deve ser menor ou igual a episodios totais");
                    dialogoErro.showAndWait();
                    App.setRoot("Cadastro");
            }
        
}
    
    private void carregarFoto(String caminhoF){
        Image imagem = new Image("file:/"+caminhoF);
        System.out.println("\n\n\n CaminhoF: "+caminhoF+"\n\n\n");
        this.imgFoto.setImage(imagem);
        
    }
}