package iss.spring.web.entities.relations.onetoone;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "insurance_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "insurance")
@Table(name = "policies")
public class InsurancePolicy implements Serializable {

	private static final long serialVersionUID = -5332694115568043817L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "insurance_id_generator")
	private int id;
	private String policyName;

	public InsurancePolicy() {

	}

	public InsurancePolicy(String policyName) {
		this.policyName = policyName;
		System.out.println("Created an Insurance policy with name: " + policyName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	@Override
	public String toString() {
		return "(" + getId() + ", PolicyName: " + getPolicyName() + ")";
	}
}
