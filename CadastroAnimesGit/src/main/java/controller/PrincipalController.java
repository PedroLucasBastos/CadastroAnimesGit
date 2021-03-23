/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Colecao;
import model.dao.ColecaoDaoJDBC;
import model.dao.DaoFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TableView<Colecao> tblColecao;
    @FXML
    private TableColumn<Colecao, String> tblColNome;
    @FXML
    private TableColumn<Colecao, String > tblColEpisodiosAssistidos;
    @FXML
    private TableColumn<Colecao, String> tblcolEpisodiosTotais;
    @FXML
    private TableColumn<Colecao, String> tblColStatus;
    
    private List<Colecao>listaColecao;
    private ObservableList<Colecao> observableListColecao; 
    private CheckBox select;
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnEstatistica;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarColecao("");
    }    

    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
        App.setRoot("Cadastro");
        //App.setRoot("Perfil");
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) throws IOException, Exception {
        Colecao condicao = tblColecao.selectionModelProperty().getValue().getSelectedItem(); 
        if (condicao == null)
        {
            //Função da msg de erro
            msgErroSelecionar();
        } else
        {
            
            ColecaoDaoJDBC dao = DaoFactory.novaColecaoDao();
            Colecao colecaoSelecionado = dao.pesquisarPorId(tblColecao.selectionModelProperty().getValue().getSelectedItem().getId());
            //System.out.println("\n\n\n\n colecaoSelecionado: "+colecaoSelecionado.getFoto()+"\n\n\n\n");
            CadastroController.setColecaoSelecionado(colecaoSelecionado);
            App.setRoot("Cadastro");
        }
    }
//  #2A2E37 cinza, #1b1d22 cinza escuro
    @FXML
    private void btnExcluirOnAction(ActionEvent event) throws Exception {
        Colecao colecaoSelecionado = tblColecao.selectionModelProperty().getValue().getSelectedItem();
       // Irá verificar se coelcaoSelecionado está vazio
        if (colecaoSelecionado==null)
        {
            //Função da msg de erro
            msgErroSelecionar();
        } else
        {
            ColecaoDaoJDBC dao = DaoFactory.novaColecaoDao();
        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Aviso");
            alert.setContentText("Confirma a exclusão de " + colecaoSelecionado.getNome() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                String menssagem = "";
                try{
                    dao.excluir(colecaoSelecionado);
                }catch (Exception e){
                    menssagem = "Ocorreu um erro" + e.getMessage();
                    Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                    alertErro.setTitle("Aviso");
                    alertErro.setContentText(menssagem);
                    alertErro.showAndWait();
                }
            }
        }
        
        carregarColecao("");
    }

    @FXML
    private void btnPesquisarOnAction(ActionEvent event) {
        carregarColecao(txtPesquisar.getText());
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        carregarColecao("");
        txtPesquisar.clear();
    }
    // msg de erro para quando não selecionar nenhum anime
    private void msgErroSelecionar(){
         Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
         dialogoErro.setTitle("ERROR !!!!!!!!!1");
         dialogoErro.setHeaderText("NENHUM ANIME SELECIONADO");
         dialogoErro.setContentText("SELECIONE UM ANIME PARA COMPLETAR A AÇÃO");
         dialogoErro.showAndWait();
    }
    
    // atualizar a tabela de animes
    public void carregarColecao(String param){
        tblColNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColEpisodiosAssistidos.setCellValueFactory(new PropertyValueFactory<>("episodiosAssistidos"));
        tblcolEpisodiosTotais.setCellValueFactory(new PropertyValueFactory<>("episodiosTotais"));
        tblColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        try{
            ColecaoDaoJDBC dao = DaoFactory.novaColecaoDao();
            listaColecao = dao.listar(param);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        observableListColecao = FXCollections.observableArrayList(listaColecao);
        tblColecao.setItems(observableListColecao);
    }
    @FXML
    private void btnPerfilOnAction(ActionEvent event) throws IOException, Exception { 
        if (tblColecao.selectionModelProperty().getValue().getSelectedItem()==null)
        {
            //Função da msg de erro
            msgErroSelecionar();
        } else
        {
            ColecaoDaoJDBC dao = DaoFactory.novaColecaoDao();
            Colecao colecaoSelecionadoPerfil = dao.pesquisarPorId(tblColecao.selectionModelProperty().getValue().getSelectedItem().getId());
       
            PerfilController.setColecaoSelecionadoPerfil(colecaoSelecionadoPerfil);
            App.setRoot("Perfil");
        }
    }

    @FXML
    private void btnEstatisticaOnAction(ActionEvent event) throws IOException {
        App.setRoot("Estatistica");
    }
}
