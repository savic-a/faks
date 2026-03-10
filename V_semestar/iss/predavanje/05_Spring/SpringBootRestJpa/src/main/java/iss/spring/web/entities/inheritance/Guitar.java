package iss.spring.web.entities.inheritance;

import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("Gitara")
public class Guitar extends Instrument {
  private static final long serialVersionUID = 5211507212346301633L;
  
  private int noOfStrings;

  public Guitar() {
    
  }
  
  public Guitar(String name, int strings) {
    setName(name);
    noOfStrings = strings;
  }

  public int getNoOfStrings() {
    return noOfStrings;
  }

  public void setNoOfStrings(int noOfStrings) {
    this.noOfStrings = noOfStrings;
  }

  public String toString() {
    return "Gitara, id: " + getId() + ", name: " + getName() + ", broj zica: " + noOfStrings;
  }
}
