/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.dao.ConnFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class EstatisticaController implements Initializable {

    @FXML
    private Button btnVoltar;
    
    private Connection conn;
    private Label lblCompleto;
    private Label lblAssistindo;
    private Label lblPlanejando;
    @FXML
    private PieChart pctEstatistica;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            grafico();
        } catch (SQLException ex) {
            Logger.getLogger(EstatisticaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnVoltarOnAction(ActionEvent event) throws IOException {
        App.setRoot("Principal");
    }
    public void grafico() throws SQLException{
        int t, tc = 0,ta = 0,tp = 0;
        conn = ConnFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) as Total FROM colecao");
        ResultSet total = ps.executeQuery();
        if(total.next()){
            t=total.getInt("Total");
        }
        
        PreparedStatement tComp = conn.prepareStatement("SELECT COUNT(*) as TotalC FROM colecao WHERE status = 'Completo'");
        ResultSet totalC = tComp.executeQuery();
        if(totalC.next()){
            tc = totalC.getInt("TotalC");
            //lblCompleto.setText(Integer.toString(tc));
            System.out.println("Completos:" + tc );
        }
        
        PreparedStatement tAssis = conn.prepareStatement("SELECT COUNT(*) as TotalA FROM colecao WHERE status = 'Assistindo'");
        ResultSet totalA = tAssis.executeQuery();
        if(totalA.next()){
            ta = totalA.getInt("TotalA");
            //lblAssistindo.setText(Integer.toString(ta));
            System.out.println("Completos:" + ta );
        }
        
        PreparedStatement tPlan = conn.prepareStatement("SELECT COUNT(*) as TotalP FROM colecao WHERE status = 'Planejando'");
        ResultSet totalP = tPlan.executeQuery();
        if(totalP.next()){
            tp = totalP.getInt("TotalP");
            //lblPlanejando.setText(Integer.toString(tp));
            System.out.println("Completos:" + tp );
        }
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Completo", tc),
                new PieChart.Data("Assistindo", ta),
                new PieChart.Data("Planejando", tp)
        );
        pctEstatistica.setData(pieChartData);
    }
}
