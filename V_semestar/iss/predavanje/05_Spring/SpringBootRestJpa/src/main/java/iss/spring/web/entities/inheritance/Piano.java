package iss.spring.web.entities.inheritance;

import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("Klavir")
public class Piano extends Instrument {

  private static final long serialVersionUID = 5104041874583613486L;
  private int noOfKeys;

  public Piano() {
    
  }
  
  public Piano(String name, int keys) {
    setName(name);
    noOfKeys = keys;
  }
  
  public int getNoOfKeys() {
    return noOfKeys;
  }

  public void setNoOfKeys(int noOfKeys) {
    this.noOfKeys = noOfKeys;
  }

  public String toString() {
    return "Klavir, id: " + getId() + ", name: " + getName() + ", broj dirki: " + noOfKeys;
  }
}
