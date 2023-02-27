package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.ZooKeeper;

public class ZooKeeperHelper {
	
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("zoo");

	public void insertZooKeeper(ZooKeeper s) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<ZooKeeper> showAllZooKeepers(){
		EntityManager em = emfactory.createEntityManager();
		List<ZooKeeper> allKeepers = em.createQuery("SELECT s from ZooKeeper s").getResultList();
		return allKeepers;
	}
	
	public ZooKeeper findZooKeeper(String nameToLookUp) {

		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ZooKeeper> typedQuery = em.createQuery("select sh from ZooKeeper sh where sh.keeperName = :selectedName",ZooKeeper.class);
		typedQuery.setParameter("selectedName", nameToLookUp);
		typedQuery.setMaxResults(1);
		ZooKeeper foundKeeper;
		try {
			foundKeeper = typedQuery.getSingleResult();
		} catch (NoResultException ex) {
			foundKeeper = new ZooKeeper(nameToLookUp);
		}
		em.close();

		return foundKeeper;
	}

}
