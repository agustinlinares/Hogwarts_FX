package DAO;

import data.DBConnection;
import model.Wand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WandDAO {
    private final String INSERT_SQL = "INSERT INTO wand (wood, core, length)  VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE wand SET wood = ?, core = ?, length = ? WHERE id = ?";
    private final String DELETE_SQL = "DELETE FROM wand WHERE id = ?";
    private final String GET_ALL_SQL = "SELECT * FROM wand ";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM wand WHERE id = ?";


    //CRUD  - CREATE | READ | UPDATE | DELETE
    public Integer insert(Wand wand)throws SQLException {
        Integer indexWand=0;
        try(Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wand.getWood());
            ps.setString(2, wand.getCore());
            ps.setDouble(3, wand.getLength());
            ps.executeUpdate();
            ResultSet id = ps.getGeneratedKeys();
            while (id.next()) {
                indexWand = id.getInt(1);
            }
        }
        return indexWand;
    }

    public List<Wand> getAll() throws SQLException{
        List<Wand> list = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();){
            PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new Wand(
                        (rs.getInt("id")),
                        (rs.getString("wood")),
                        (rs.getString("core")),
                        rs.getDouble(("length"))
                ));
            }//endwhile
        }
        return list;
    }

    public void update(Wand wand) throws SQLException {
        try(Connection conn = DBConnection.getConnection();){
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
            ps.setString(1, wand.getWood());
            ps.setString(2, wand.getCore());
            ps.setDouble(3, wand.getLength());
            ps.setInt(4, wand.getId());
            ps.executeUpdate();
        }
    }

    public void update(Wand wand, Connection connection) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)){
            ps.setString(1, wand.getWood());
            ps.setString(2, wand.getCore());
            ps.setDouble(3, wand.getLength());
            ps.setInt(4, wand.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection conn = DBConnection.getConnection();){
            PreparedStatement ps = conn.prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Wand findById(int id) throws SQLException {
        try(Connection conn = DBConnection.getConnection();){
            PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Wand(rs.getInt("id"), rs.getString("wood"), rs.getString("core"), rs.getDouble("length"));
            }
            return null;
        }
    }

}