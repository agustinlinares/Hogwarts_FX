package DAO;

import data.DBConnection;
import model.Wizard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WizardDAO {
    private final String INSERT_SQL = "INSERT INTO wizard (id,name, age, house_id, wand_id) VALUES (?,?,?,?,?)";
    private final String UPDATE_SQL = "update wizard set name=?, age=?, house_id=?, wand_id=? where id=?";
    private final String DELETE_SQL = "DELETE FROM wizard WHERE id=?";
    private final String GET_ALL_SQL = "SELECT * FROM wizard";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM wizard WHERE id=?";



    //CRUD  - CREATE | READ | UPDATE | DELETE
    public void create(Wizard wizard)throws SQLException {
        try(Connection con = DBConnection.getConnection();){
            PreparedStatement ps = con.prepareStatement(INSERT_SQL);
            ps.setInt(1, wizard.getId());
            ps.setString(2, wizard.getName());
            ps.setInt(3, wizard.getAge());
            ps.setInt(4, wizard.getHouseId());
            if (wizard.getWandId() == null || wizard.getWandId() == 0) {
                ps.setNull(5, java.sql.Types.INTEGER); // Esto mete un NULL en la base de datos
            } else {
                ps.setInt(5, wizard.getWandId());   // Esto mete el ID si existe
            }
            ps.executeUpdate();
        }
    }

    public List<Wizard> getAll() throws SQLException{
        List<Wizard> list = new ArrayList<>();
        try(Connection con = DBConnection.getConnection();){
            PreparedStatement ps = con.prepareStatement(GET_ALL_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(
                        new Wizard(
                                (rs.getInt("id")),
                                (rs.getString("name")),
                                (rs.getInt("age")),
                                (rs.getInt("house_id")),
                                (rs.getInt("wand_id"))
                        ));
            }//endwhile
        }
        return list;
    }

    public Wizard findById(Integer id) throws SQLException{
        Wizard wizard = null;
        try(Connection con = DBConnection.getConnection();){
            PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                wizard=new Wizard(
                        (rs.getInt("id")),
                        (rs.getString("name")),
                        (rs.getInt("age")),
                        (rs.getInt("house_id")),
                        (rs.getInt("wand_id"))
                );
            }
            return wizard;
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection con = DBConnection.getConnection();){
            PreparedStatement ps = con.prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void update(Wizard wizard) throws SQLException {
        try(Connection con = DBConnection.getConnection();){
            PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1, wizard.getName());
            ps.setInt(2, wizard.getAge());
            ps.setInt(3, wizard.getHouseId());
            if (wizard.getWandId() == null || wizard.getWandId() == 0) {
                ps.setNull(4, java.sql.Types.INTEGER); // Esto mete un NULL en la base de datos
            } else {
                ps.setInt(4, wizard.getWandId());      // Esto mete el ID si existe
            }
            ps.setInt(5, wizard.getId());
            ps.executeUpdate();
        }
    }

    public void update(Wizard wizard, Connection connection) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)){
            ps.setString(1, wizard.getName());
            ps.setInt(2, wizard.getAge());
            ps.setInt(3, wizard.getHouseId());
            if (wizard.getWandId() == null || wizard.getWandId() == 0) {
                ps.setNull(4, java.sql.Types.INTEGER); // Esto mete un NULL en la base de datos
            } else {
                ps.setInt(4, wizard.getWandId());      // Esto mete el ID si existe
            }
            ps.setInt(5, wizard.getId());
            ps.executeUpdate();
        }
    }

}