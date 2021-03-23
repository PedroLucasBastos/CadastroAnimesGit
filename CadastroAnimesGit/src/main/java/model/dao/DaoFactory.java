package model.dao;

public class DaoFactory {
    public static ColecaoDaoJDBC novaColecaoDao() throws Exception{
        return new ColecaoDaoJDBC();
    }
}
