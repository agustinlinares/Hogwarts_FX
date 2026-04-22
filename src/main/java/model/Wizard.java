package model;

public class Wizard {

    private Integer id;
    private String name;
    private Integer age, houseId, wandId;

    //constructores

    public Wizard(Integer id, String name, Integer age, Integer houseId, Integer wandId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.houseId = houseId;
        this.wandId = wandId;
    }

    public Wizard(String name, Integer age, Integer houseId, Integer wandId) {
        this.name = name;
        this.houseId = houseId;
        this.age = age;
        this.wandId = wandId;
    }

    public Wizard() {}

    //getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getWandId() {
        return wandId;
    }

    public void setWandId(Integer wandId) {
        this.wandId = wandId;
    }

    //toString
    public String toString() {
        return "Wizard[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", houseId=" + houseId +
                ", wandId=" + wandId +
                ']';
    }
}