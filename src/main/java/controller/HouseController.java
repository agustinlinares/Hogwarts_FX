package controller;

import model.House;
import DAO.HouseDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HouseController {
    private HouseDAO houseDAO;

    public HouseController() {
        houseDAO = new HouseDAO();
    }

    public void insert(String name, String founder){
        try{
            House house=new House(name,founder);
            houseDAO.insert(house);
            System.out.println("☑️ Casa agregada con exito");
        }catch(SQLException e){
            System.out.println("❌ Error al agregar casa : " + e.getMessage());
        }
    }

    public void update(int id,String name, String founder){
        try{
            House house=new House(name,founder);
            house.setId(id);
            houseDAO.update(house);
            System.out.println("☑️  Casa actualizada con exito ");
        }catch (SQLException e){
            System.out.println("❌ Error al actualizar casa "+e.getMessage());
        }

    }

    public void delete(int id){
        try{
            houseDAO.delete(id);
            System.out.println("☑️ Casa eliminada con exito");
        }catch (SQLException e){
            System.out.println("❌ Error al borrar la casa "+e.getMessage());
        }
    }

    public List<House> getAll(){
        List<House> houses = new ArrayList<>();;
        try {
            houses = houseDAO.getAll();
        }catch (SQLException e){
            System.out.println("❌ Error al obtener casas"+e.getMessage());
        }
        return  houses;
    }

}