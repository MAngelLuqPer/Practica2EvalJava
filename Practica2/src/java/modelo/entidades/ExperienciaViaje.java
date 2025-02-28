/*
 * Entidad ExperienciaViaje.
 */
package modelo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jose
 */
@Entity
public class ExperienciaViaje implements Serializable, Comparable<ExperienciaViaje> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 30, nullable = false)
    private String titulo;
    @Column(length = 400, nullable = false)
    private String descripcion;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @OneToMany
    private List<Actividad> actividades;
    @ManyToOne
    private Usuario usuario;
    
    public ExperienciaViaje() {
        actividades = new ArrayList();
    }
    
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(ExperienciaViaje o) {
        return this.fechaInicio.compareTo(o.fechaInicio);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExperienciaViaje)) {
            return false;
        }
        ExperienciaViaje other = (ExperienciaViaje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return titulo;
    }
    
}
