package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class User {
    @Position(0)
    private String potomak;

    @Position(1)
    private String predak;

    public User(String potomak, String predak) {
        this.potomak = potomak;
        this.predak = predak;
    }

    public String getPotomak() {
        return potomak;
    }

    public void setPotomak(String potomak) {
        this.potomak = potomak;
    }

    public String getPredak() {
        return predak;
    }

    public void setPredak(String predak) {
        this.predak = predak;
    }

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) { return true; }
    //     if (o == null || getClass() != o.getClass()) { return false; }

    //     User predak1 = (User) o;

    //     if (potomak != null ? !potomak.equals(predak1.potomak) : predak1.potomak != null) { return false; }
    //     if (predak != null ? !predak.equals(predak1.predak) : predak1.predak != null) { return false; }

    //     return true;
    // }

    // @Override
    // public int hashCode() {
    //     int result = potomak != null ? potomak.hashCode() : 0;
    //     result = 31 * result + (predak != null ? predak.hashCode() : 0);
    //     return result;
    // }

    // @Override
    // public String toString() {
    //     return "predak{" +
    //            "potomak='" + potomak + '\'' +
    //            ", predak='" + predak + '\'' +
    //            '}';
    // }
}
