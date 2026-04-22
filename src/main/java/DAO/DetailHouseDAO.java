package DAO;

import data.DBConnection;
import model.DetailHouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailHouseDAO {

    private final String SEARCH_SQL = "SELECT * FROM detail_house WHERE wand_id=?";
    private final String UPDATE_SQL = "UPDATE detail_house SET emblemUrl=? WHERE wand_id=?";
    private final String INSERT_SQL = "INSERT INTO detail_house(house_id, history, traits, emblemUrl) VALUES (?,?,?,?)";
    private final String DELETE_SQL = "DELETE FROM detail_house WHERE wand_id=?";


    public List<DetailHouse> search() throws SQLException {
        try(Connection conn = DBConnection.getConnection()) {
            List<DetailHouse> list = new ArrayList<>();
            PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                list.add(new DetailHouse(
                        rs.getInt("id"),
                        rs.getInt("house_id"),
                        rs.getString("history"),
                        rs.getString("traits"),
                        rs.getString("emblemUrl")
                ));
            }
            return list;
        }
    }

    public void update(DetailHouse detailHouse) throws SQLException {
        //solo permito la actualizacion de los emblemas por si no funcionara la url original
        try(Connection conn = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1, detailHouse.getEmblemUrl());
            preparedStatement.setString(2, detailHouse.getId()+"");
            preparedStatement.executeUpdate();
        }
    }

    public void insert(DetailHouse detailHouse) throws SQLException {
        try(Connection conn = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL);
            preparedStatement.setInt(1, detailHouse.getHouseId());
            preparedStatement.setString(2, detailHouse.getHistory());
            preparedStatement.setString(3, detailHouse.getTraits());
            preparedStatement.setString(4, detailHouse.getEmblemUrl());
        }
    }

    public void delete(DetailHouse detailHouse) throws SQLException {
        try(Connection conn = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, detailHouse.getHouseId());
        }
    }

}