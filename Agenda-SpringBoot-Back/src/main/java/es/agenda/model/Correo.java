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
@Table(name = "correos")
@AllArgsConstructor
public class Correo {
 
	public Correo() {	
	}
	
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter
	@Column
	private String correo;
	
	@Getter
	@Setter
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="contacto_id")
	private Contacto contacto;
	
	public String getCorreo() {
		
		if(correo == null || "null".equals(correo)) {
			correo = "";
		}
		
		return correo;
	}
}
