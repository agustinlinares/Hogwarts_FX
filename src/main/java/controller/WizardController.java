package controller;

import model.Wizard;
import DAO.WizardDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WizardController {

    private WizardDAO wizardDAO;

    public WizardController(){
        wizardDAO= new WizardDAO();
    }

    public void insert(String name,Integer age,Integer house_id, Integer wand_id){
        try{
            Wizard wizard=new Wizard(name,age,house_id,wand_id);
            wizardDAO.create(wizard);
            System.out.println("☑️ Mago agregado con exito");
        }catch(SQLException e){
            System.out.println("❌ Error al agregar mago : " + e.getMessage());
        }
    }

    public void update(Integer id,String name,Integer age,Integer house_id, Integer wand_id){
        try{
            Wizard wizard= wizardDAO.findById(id);
            wizard.setName(name);
            wizard.setAge(age);
            wizard.setHouseId(house_id);
            wizard.setWandId(wand_id);
            wizardDAO.update(wizard);
            System.out.println("☑️  Mago actualizado con exito ");
        }catch (SQLException e){
            System.out.println("❌ Error al actualizar mago "+e.getMessage());
        }

    }

    public void delete(Integer id){
        try{
            wizardDAO.delete(id);
            System.out.println("☑️ Mago eliminado con exito");
        }catch (SQLException e){
            System.out.println("❌ Error al borrar el mago "+e.getMessage());
        }
    }

    public List<Wizard> getAll(){
        List<Wizard> wizards = new ArrayList<>();;
        try {
            wizards  = wizardDAO.getAll();
        }catch (SQLException e){
            System.out.println("❌ Error al obtener magos "+e.getMessage());
        }
        return  wizards;
    }

}