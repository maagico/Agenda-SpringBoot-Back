package es.agenda.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telefonos")
@AllArgsConstructor
public class Telefono {

	public Telefono() {
	}
	
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter
	@Column
	private String numero;
	
	@Getter
	@Setter
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="contacto_id")
	private Contacto contacto;
	
	public String getNumero() {
		
		if(numero == null || "null".equals(numero)) {
			numero = "";
		}
		
		return numero;
	}
}
