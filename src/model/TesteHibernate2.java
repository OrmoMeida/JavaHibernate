/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.hibernate.Session;
import util.HibernateUtil;


/**
 *
 * @author taniabasso
 */
public class TesteHibernate2 {
    public static void main(String[] args) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        
        session.save(new Aluno(1234, "Maria"));
        session.save(new Aluno(4567,"Jose"));
        session.save(new Professor(3344, "Cecília", "cecilia123@email.com", 1500.50));
        session.save(new Professor(3562, "Mário", "m_azevedo@email.com", 2030.50));
        
        try {
            session.save(new Professor(3344, "Everaldo", "eve.silva@email.com", 1050.3));
        } catch (Exception ex) {
            System.out.println("\n\n\n\n" + ex.getMessage() + " : " + ex.getClass().getCanonicalName() + "\n\n\n\n");
        }

        session.save(new Disciplina(2, "Culinária", 400));
        session.save(new Disciplina(8, "Processos industriais", 600));
        
        session.getTransaction().commit();
       
        session.close();
        HibernateUtil.shutdown();
    }
}
