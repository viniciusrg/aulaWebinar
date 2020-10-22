package webinar.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import webinar.model.Formacao;
import webinar.model.Instrutor;

public class InstrutorDao implements Dao<Instrutor> {

	private EntityManager em;
	
	public InstrutorDao() {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
	}
	
	@Override
	public Instrutor get(int id) {
		return em.find(Instrutor.class, id);
	}

	@Override
	public List<Instrutor> getAll() {
		//TypedQuery
		return em.createQuery("FROM Instrutor", Instrutor.class).getResultList();
	}
	
	public List<Instrutor> getAllByFormacao(Formacao pformacao){
		Query q = em.createQuery("SELECT i FROM Instrutor i WHERE i.formacao = : pformacao");
		q.setParameter("pformacao", pformacao);
		return q.getResultList();
	}

	@Override
	public void save(Instrutor t) {
		executeInsideTransaction(em->em.persist(t));
	}

	@Override
	public void update(Instrutor t) {
		executeInsideTransaction(em->em.merge(t));
	}

	@Override
	public void delete(Instrutor t) {
		executeInsideTransaction(em->em.remove(t));
	}
	
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			action.accept(em);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		}
	}
	
}
