/*
 * ServicioExperienciaViaje.
 */
package modelo.servicio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.exceptions.NonexistentEntityException;

/**
 *
 * @author jose
 */
public class ServicioExperienciaViaje implements Serializable {

    public ServicioExperienciaViaje(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ExperienciaViaje experienciaViaje) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = experienciaViaje.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                experienciaViaje.setUsuario(usuario);
            }
            em.persist(experienciaViaje);
            if (usuario != null) {
                usuario.getExperiencias().add(experienciaViaje);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ExperienciaViaje experienciaViaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ExperienciaViaje persistentExperienciaViaje = em.find(ExperienciaViaje.class, experienciaViaje.getId());
            Usuario usuarioOld = persistentExperienciaViaje.getUsuario();
            Usuario usuarioNew = experienciaViaje.getUsuario();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                experienciaViaje.setUsuario(usuarioNew);
            }
            experienciaViaje = em.merge(experienciaViaje);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getExperiencias().remove(experienciaViaje);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getExperiencias().add(experienciaViaje);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = experienciaViaje.getId();
                if (findExperienciaViaje(id) == null) {
                    throw new NonexistentEntityException("The experienciaViaje with id " + id + " no longer exists.");
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
            ExperienciaViaje experienciaViaje;
            try {
                experienciaViaje = em.getReference(ExperienciaViaje.class, id);
                experienciaViaje.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The experienciaViaje with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = experienciaViaje.getUsuario();
            if (usuario != null) {
                usuario.getExperiencias().remove(experienciaViaje);
                usuario = em.merge(usuario);
            }
            em.remove(experienciaViaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ExperienciaViaje> findExperienciaViajeEntities() {
        return findExperienciaViajeEntities(true, -1, -1);
    }

    public List<ExperienciaViaje> findExperienciaViajeEntities(int maxResults, int firstResult) {
        return findExperienciaViajeEntities(false, maxResults, firstResult);
    }

    private List<ExperienciaViaje> findExperienciaViajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ExperienciaViaje.class));
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

    public ExperienciaViaje findExperienciaViaje(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ExperienciaViaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getExperienciaViajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ExperienciaViaje> rt = cq.from(ExperienciaViaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    //Servicio sencillo encargado de hacer una peticion SQL para buscar entre dos fechas
    public List<ExperienciaViaje> findExperienciasByFecha(Date fechaInicio, Date fechaFin) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM ExperienciaViaje e WHERE e.fechaInicio BETWEEN :inicio AND :fin", ExperienciaViaje.class)
                    .setParameter("inicio", fechaInicio)
                    .setParameter("fin", fechaFin)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
