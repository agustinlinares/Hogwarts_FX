package model;

public class House {

    private Integer id;
    private String name;
    private String founder;

    //constructores
    public House(){}
    public House(Integer id, String name, String founder) {
        this.id = id;
        this.name = name;
        this.founder = founder;
    }
    public House(String name, String founder) {
        this.name = name;
        this.founder = founder;
    }

    //getter y setter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFounder() {
        return founder;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setName(String name) {
        this.name = name;
    }

    //toString
    public String toString() {
        return "House[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", founder='" + founder + '\'' +
                ']';
    }
}