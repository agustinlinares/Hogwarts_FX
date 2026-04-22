package controller;

import model.Wand;
import DAO.WandDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WandController {

    private WandDAO wandDAO;

    public WandController() {
        wandDAO = new WandDAO();
    }

    public Integer insert(String wood, String core, double length){
        Integer indexWand = 0;
        try{
            Wand wand=new Wand(wood,core,length);
            indexWand = wandDAO.insert(wand);
            System.out.println("☑️ Barita agregada con exito");
        }catch(SQLException e){
            System.out.println("❌ Error al agregar Barita : " + e.getMessage());
        }
        return indexWand;
    }

    public void update(int id,String wood, String core, double length){
        try{
            Wand wand=new Wand(wood,core,length);
            wand.setId(id);
            wandDAO.update(wand);
            System.out.println("☑️  Barita actualizado con exito ");
        }catch (SQLException e){
            System.out.println("❌ Error al actualizar barita "+e.getMessage());
        }
    }

    public void delete(int id){
        try{
            wandDAO.delete(id);
            System.out.println("☑️ Barita eliminado con exito");
        }catch (SQLException e){
            System.out.println("❌ Error al borrar la Barita "+e.getMessage());
        }
    }

    //controller para JSwing

    public List<Wand> getAll(){
        List<Wand> wands = new ArrayList<>();;
        try {
            wands = wandDAO.getAll();
        }catch (SQLException e){
            System.out.println("❌ Error al obtener baritas"+e.getMessage());
        }
        return  wands;
    }


}