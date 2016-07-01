package ar.edu.grupoesfera.cursospring.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.grupoesfera.cursospring.modelo.Farmacia;

@Repository
@Transactional
public class FarmaciaDao {
	
	@Inject
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Farmacia> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(Farmacia.class).list();
	}
	
	public Farmacia getById(Long id) {
		return sessionFactory.getCurrentSession().get(Farmacia.class, id);
	}
	
	public Farmacia save(Farmacia farmacia) {
		sessionFactory.getCurrentSession().save(farmacia);
		return farmacia;
	}
	
	public void delete(Farmacia farmacia) {
		sessionFactory.getCurrentSession().delete(farmacia);
	}

}
