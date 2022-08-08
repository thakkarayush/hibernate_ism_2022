package com;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.bean.RoleBean;

/**
 * Hello world!
 *
 */
public class App {
	// use to create session factory from give configuration
	Configuration cfg = null;

	// use to create session
	SessionFactory factory = null;

	App() {
		cfg = new Configuration().configure("hibernate-config.xml");
		factory = cfg.buildSessionFactory();
	}
	
	void addRole() {
		System.out.println("Add role");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Role Name:");
		String roleName = s.next();
		
		RoleBean roleBean = new RoleBean();
		roleBean.setRoleName(roleName);
		
		Session session = factory.openSession();// every time open
		Transaction tx = session.beginTransaction();
		try {
			session.save(roleBean);// insert query
			tx.commit();// db save
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();// undo
		}
		session.close();
	}
	
	void getAllRoles() {
		System.out.println("Display data");
		
		Session session = factory.openSession();
		
		Query query = session.createQuery("from RoleBean");// RoleBean - class

//		Query query = session.createQuery("select * from role");// sql will not work -- need to pass hql 

		List<RoleBean> roles = query.getResultList();

		for (RoleBean r : roles) {
			System.out.println(r.getRoleId() + "   " + r.getRoleName());
		}
		session.close();
		
	}
	
	void getRoleById() {
		System.out.println("Display day by id");
		Scanner scr = new Scanner(System.in);
		System.out.println("Enter roleId");
		int roleId = scr.nextInt();

		Session session = factory.openSession();

		RoleBean role = session.get(RoleBean.class, roleId);

		if (role == null) {
			System.out.println("Invalid id please try again!!!");
		} else {
			System.out.println("=====Role Detail=====");
			System.out.println(role.getRoleId());
			System.out.println(role.getRoleName());
		}
		session.close();

	}

	void deleteRoleById() {
		System.out.println("Delete data by id");
		Scanner scr = new Scanner(System.in);
		System.out.println("Enter roleId");
		int roleId = scr.nextInt();

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			RoleBean role = session.get(RoleBean.class, roleId);
			session.delete(role);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}

	}

	public static void main(String[] args) {
		int choice = 0;
		
		App app = new App();
		
		while(true) {
			System.out.println("\n0 for exit\n1 for add\n2 for list all roles\n3 for view\n4 for delete role");
			System.out.println("Enter choice....");

			Scanner scr = new Scanner(System.in);

			choice = scr.nextInt();

			switch (choice) {
			case 0:
				System.exit(0);
			case 1:
				app.addRole();
				break;
			case 2:
				app.getAllRoles();
				break;
			case 3:
				app.getRoleById();
				break;
			case 4:
				app.deleteRoleById();
				break;
			}// switch
		}
		

		

	}
}