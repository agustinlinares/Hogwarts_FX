package model;

public class DetailHouse {
    private int id;
    private int houseId;
    private String history;
    private String traits;
    private String emblemUrl;

    public DetailHouse(int id, int houseId, String history, String traits, String emblemUrl) {
        this.id = id;
        this.houseId = houseId;
        this.history = history;
        this.traits = traits;
        this.emblemUrl = emblemUrl;
    }
    //constructor para crear nuevos si fuera necesario ene l futuro
    public DetailHouse(int houseId, String history, String traits, String emblemUrl) {
        this.houseId = houseId;
        this.history = history;
        this.traits = traits;
        this.emblemUrl = emblemUrl;
    }
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHouseId() { return houseId; }
    public void setHouseId(int houseId) { this.houseId = houseId; }

    public String getHistory() { return history; }
    public void setHistory(String history) { this.history = history; }

    public String getTraits() { return traits; }
    public void setTraits(String traits) { this.traits = traits; }

    public String getEmblemUrl() { return emblemUrl; }
    public void setEmblemUrl(String emblemUrl) { this.emblemUrl = emblemUrl; }
}