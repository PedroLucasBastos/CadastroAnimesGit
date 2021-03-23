package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Colecao;

public class ColecaoDaoJDBC implements InterfaceDao<Colecao>{

    private Connection conn;
    
    public ColecaoDaoJDBC() throws Exception{
        this.conn = ConnFactory.getConnection();
    }
    
    @Override
    public void incluir(Colecao entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Colecao (nome,episodiosTotais,episodiosAssistidos,status,foto) VALUES(?,?,?,?,?)");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEpisodiosTotais());
        ps.setString(3, entidade.getEpisodiosAssistidos());
        ps.setString(4, entidade.getStatus());
        ps.setString(5, entidade.getFoto());
        ps.execute();
    }

    @Override
    public void editar(Colecao entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("UPDATE Colecao SET nome = ?,episodiosTotais = ?,episodiosAssistidos = ?,status = ?, foto = ? WHERE id = ?");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEpisodiosTotais() );
        ps.setString(3, entidade.getEpisodiosAssistidos());
        ps.setString(4, entidade.getStatus());
        ps.setString(5,entidade.getFoto());
        ps.setInt(6, entidade.getId());
        ps.execute();
    }

    @Override
    public void excluir(Colecao entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Colecao WHERE id = ?");
        ps.setInt(1, entidade.getId());
        ps.execute();
    }

    @Override
    public Colecao pesquisarPorId(int id) throws Exception {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Colecao WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        List<Colecao> lista = new ArrayList();
        Colecao c = null;
        if (rs.next()) {
            c = new Colecao();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setEpisodiosTotais(rs.getString("episodiosTotais"));
            c.setEpisodiosAssistidos(rs.getString("episodiosAssistidos"));
            c.setStatus(rs.getString("status"));
            c.setFoto(rs.getString("foto"));
        }
        return c;
    }

    @Override
    public List<Colecao> listar(String param) throws Exception {
        PreparedStatement ps = null;
        if (param == ""){
            ps = conn.prepareStatement("SELECT * FROM Colecao");
        }else{
            ps = conn.prepareStatement("SELECT * FROM Colecao WHERE nome like '%"+ param + "%'");   
        }
        ResultSet rs = ps.executeQuery();
        List<Colecao> lista = new ArrayList();
        while (rs.next()) {
            Colecao c = new Colecao();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setEpisodiosTotais(rs.getString("episodiosTotais"));
            c.setEpisodiosAssistidos(rs.getString("episodiosAssistidos"));
            c.setStatus(rs.getString("status"));
            //c.setFoto(rs.getString("foto"));
            lista.add(c);
        }
        return lista;
    }
    
}
