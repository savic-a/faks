package iss.spring.web.entities.inheritance;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="TYPE_OF_INSTRUMENT", discriminatorType=DiscriminatorType.STRING)
//@DiscriminatorValue("Osnovni instrument")
@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@TableGenerator(name="instrument_id_generator", table="primary_keys", pkColumnName="key_pk", pkColumnValue="instrument", valueColumnName="value_pk")

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public abstract class Instrument implements Serializable {
  private static final long serialVersionUID = -772822558816958249L;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "instrument_id_generator")
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;
  private String name;

  public Instrument() {
	  
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String toString() {
    return "Instrument, id: " + id + ", name: " + name;
  }
}
