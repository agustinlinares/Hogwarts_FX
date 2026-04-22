package model;

public class Wand {
    private Integer id;
    private String wood,core;
    private double length;

    //cosntructor
    public Wand(Integer id,String wood, String core, double length) {
        this.id = id;
        this.wood = wood;
        this.core = core;
        this.length = length;
    }

    public Wand(String wood, String core, double length) {
        this.wood = wood;
        this.core = core;
        this.length = length;
    }

    public Wand() {
    }

    // getter y setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWood() {
        return wood;
    }

    public void setWood(String wood) {
        this.wood = wood;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }


    public String toString() {
        return "Wand[" +
                "id=" + id +
                ", wood='" + wood + '\'' +
                ", core='" + core + '\'' +
                ", length=" + length +
                ']';
    }
}