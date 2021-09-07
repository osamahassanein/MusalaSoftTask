package musalatask.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode 
public class Device implements Serializable {
 
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String vendor;
	private Date creationDate;
	private String status;

	// bi-directional many-to-one association to Gateway
	@JsonBackReference
	//@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false ) 
	//@JoinColumn(name = "gateway_id", insertable = true, updatable = false) // , insertable = false, updatable = false
	@ManyToOne(fetch = FetchType.EAGER) //, cascade = CascadeType.REFRESH
    @JoinColumn(name = "gateway_id", insertable = true, updatable = false)
	private Gateway gateway;

}