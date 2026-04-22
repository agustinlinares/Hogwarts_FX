package DAO;

import data.DBConnection;
import model.House;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HouseDAO {
    private final String INSERT_SQL = "INSERT INTO house (name, founder)  VALUES (?,?)";
    private final String UPDATE_SQL = "UPDATE house SET name = ?, founder = ? WHERE id = ?";
    private final String DELETE_SQL = "DELETE FROM house WHERE id = ?";
    private final String GET_ALL_SQL = "SELECT * FROM house ";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM house WHERE id = ?";

    //CRUD  - CREATE | READ | UPDATE | DELETE

    public void insert(House house)throws SQLException {
        try(Connection conn = DBConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
            ps.setString(1, house.getName());
            ps.setString(2, house.getFounder());
            ps.executeUpdate();

        }
    }

    public List<House> getAll() throws SQLException{
        List<House> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new House((rs.getInt("id")), (rs.getString("name")), (rs.getString("founder"))));
            }//endwhile
        }
        return list;
    }

    public void update(House house) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
            ps.setString(1, house.getName());
            ps.setString(2, house.getFounder());
            ps.setInt(3, house.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public House findById(int id) throws SQLException {
        try(Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new House(rs.getInt("id"), (rs.getString("name")), (rs.getString("founder")));
            }
            return null;
        }
    }



}