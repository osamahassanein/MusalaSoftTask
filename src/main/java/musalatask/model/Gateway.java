package musalatask.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "devices")
public class Gateway implements Serializable {
 
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String ipv4;

	@JsonManagedReference
	// @LazyCollection(LazyCollectionOption.FALSE)
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //mappedBy = "gateway", 
	//@Fetch(value=FetchMode.SELECT)
	//@JoinColumn(name = "gateway_id", insertable = true, nullable = false, updatable = false)
	@OneToMany(mappedBy = "gateway") // , cascade = CascadeType.ALL
	private Set<Device> devices;
	
}
