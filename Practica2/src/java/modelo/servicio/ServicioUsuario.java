/*
 * ServicioUsuario.
 */
package modelo.servicio;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entidades.ExperienciaViaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.entidades.Usuario;
import modelo.servicio.exceptions.NonexistentEntityException;

/**
 *
 * @author jose
 */
public class ServicioUsuario implements Serializable {

    public ServicioUsuario(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getExperiencias() == null) {
            usuario.setExperiencias(new ArrayList<ExperienciaViaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ExperienciaViaje> attachedExperiencias = new ArrayList<ExperienciaViaje>();
            for (ExperienciaViaje experienciasExperienciaViajeToAttach : usuario.getExperiencias()) {
                experienciasExperienciaViajeToAttach = em.getReference(experienciasExperienciaViajeToAttach.getClass(), experienciasExperienciaViajeToAttach.getId());
                attachedExperiencias.add(experienciasExperienciaViajeToAttach);
            }
            usuario.setExperiencias(attachedExperiencias);
            em.persist(usuario);
            for (ExperienciaViaje experienciasExperienciaViaje : usuario.getExperiencias()) {
                Usuario oldUsuarioOfExperienciasExperienciaViaje = experienciasExperienciaViaje.getUsuario();
                experienciasExperienciaViaje.setUsuario(usuario);
                experienciasExperienciaViaje = em.merge(experienciasExperienciaViaje);
                if (oldUsuarioOfExperienciasExperienciaViaje != null) {
                    oldUsuarioOfExperienciasExperienciaViaje.getExperiencias().remove(experienciasExperienciaViaje);
                    oldUsuarioOfExperienciasExperienciaViaje = em.merge(oldUsuarioOfExperienciasExperienciaViaje);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            List<ExperienciaViaje> experienciasOld = persistentUsuario.getExperiencias();
            List<ExperienciaViaje> experienciasNew = usuario.getExperiencias();
            List<ExperienciaViaje> attachedExperienciasNew = new ArrayList<ExperienciaViaje>();
            for (ExperienciaViaje experienciasNewExperienciaViajeToAttach : experienciasNew) {
                experienciasNewExperienciaViajeToAttach = em.getReference(experienciasNewExperienciaViajeToAttach.getClass(), experienciasNewExperienciaViajeToAttach.getId());
                attachedExperienciasNew.add(experienciasNewExperienciaViajeToAttach);
            }
            experienciasNew = attachedExperienciasNew;
            usuario.setExperiencias(experienciasNew);
            usuario = em.merge(usuario);
            for (ExperienciaViaje experienciasOldExperienciaViaje : experienciasOld) {
                if (!experienciasNew.contains(experienciasOldExperienciaViaje)) {
                    experienciasOldExperienciaViaje.setUsuario(null);
                    experienciasOldExperienciaViaje = em.merge(experienciasOldExperienciaViaje);
                }
            }
            for (ExperienciaViaje experienciasNewExperienciaViaje : experienciasNew) {
                if (!experienciasOld.contains(experienciasNewExperienciaViaje)) {
                    Usuario oldUsuarioOfExperienciasNewExperienciaViaje = experienciasNewExperienciaViaje.getUsuario();
                    experienciasNewExperienciaViaje.setUsuario(usuario);
                    experienciasNewExperienciaViaje = em.merge(experienciasNewExperienciaViaje);
                    if (oldUsuarioOfExperienciasNewExperienciaViaje != null && !oldUsuarioOfExperienciasNewExperienciaViaje.equals(usuario)) {
                        oldUsuarioOfExperienciasNewExperienciaViaje.getExperiencias().remove(experienciasNewExperienciaViaje);
                        oldUsuarioOfExperienciasNewExperienciaViaje = em.merge(oldUsuarioOfExperienciasNewExperienciaViaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<ExperienciaViaje> experiencias = usuario.getExperiencias();
            for (ExperienciaViaje experienciasExperienciaViaje : experiencias) {
                experienciasExperienciaViaje.setUsuario(null);
                experienciasExperienciaViaje = em.merge(experienciasExperienciaViaje);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }
    
    public Usuario validarUsuario(String email, String pwd) {
        List<Usuario> usuariosExistentes = findUsuarioEntities();
        for (Usuario usu : usuariosExistentes){
            if (usu.getEmail().equals(email) && usu.getPassword().equals(pwd)) {
                return usu;
            }
        }
        return null;
    }
//Devuelve true si el usuario existe y false si no existe, desde su email.
    public boolean buscarUsuarioPorEmail(String email) {
        List<Usuario> usuarioExistentes = findUsuarioEntities();
        for (Usuario usu : usuarioExistentes) {
            if (usu.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    
    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
