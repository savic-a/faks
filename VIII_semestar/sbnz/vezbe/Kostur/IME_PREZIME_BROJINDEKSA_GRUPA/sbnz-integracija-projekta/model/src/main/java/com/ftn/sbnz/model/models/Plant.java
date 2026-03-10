package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class Plant {
     @Position(0)
    private String name;

    @Position(1)
    private String plant;

    public Plant(String name, String plant) {
        this.name = name;
        this.plant = plant;
    }

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String Plant) {
        this.plant = Plant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Plant plant1 = (Plant) o;

        if (name != null ? !name.equals(plant1.name) : plant1.name != null) { return false; }
        if (plant != null ? !plant.equals(plant1.plant) : plant1.plant != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (plant != null ? plant.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Plant{" +
               "name='" + name + '\'' +
               ", plant='" + plant + '\'' +
               '}';
    }
}
