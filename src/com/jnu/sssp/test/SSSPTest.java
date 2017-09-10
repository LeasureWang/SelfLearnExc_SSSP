package com.jnu.sssp.test;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.hibernate.ejb.QueryHints;
import org.junit.Test;//
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import antlr.collections.List;

import com.jnu.sssp.entity.Department;
import com.jnu.sssp.repository.DepartmentRepository;

public class SSSPTest {

	private ApplicationContext ctx = null;
	private DepartmentRepository departmentRepository;
	private EntityManagerFactory entityManagerFactory;	
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		departmentRepository = ctx.getBean(DepartmentRepository.class);
		entityManagerFactory=ctx.getBean(EntityManagerFactory.class);
	}
	
	
	
	@Test
	public void testJPASecondLevelCache(){
		String jpql="FROM Department d";
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		Query query=entityManager.createQuery(jpql);
		List departments=(List) query.setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
		entityManager.close();
		
		entityManager=entityManagerFactory.createEntityManager();
		query=entityManager.createQuery(jpql);
		departments=(List) query.setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
		entityManager.close();
	}
	
	
	@Test
	public void testRespositorySecondLevelCache() {
		List departments = (List) departmentRepository.getAll();
		departments = (List) departmentRepository.findAll();
	}

	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());

	}

}
