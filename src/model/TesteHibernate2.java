/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;


/**
 *
 * @author taniabasso
 */

public class TesteHibernate2 {
    private static Session session = HibernateUtil.getSessionFactory().openSession();
    
    public static List<Professor> getProfessors() {
        Query qProfessor = session.createQuery("from Professor");
        return (List<Professor>) qProfessor.list();
    }
    
    public static List<Aluno> getAlunos() {
        Query qAluno = session.createQuery("from Aluno");
        return (List<Aluno>) qAluno.list();
    }
    
    public static <T> List<T> from(Class<T> classe, String hql) {
        String hqlQ = "from " + classe.getCanonicalName();
        if (!hql.trim().isEmpty())
            hqlQ = hqlQ + " " + hql;
        Query q = session.createQuery(hqlQ);
        return (List<T>) q.list();
    }
    
    public static <T> List<T> from(Class<T> classe) {
        return from(classe, "");
    }

    public static void main(String[] args) {        
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
        
        System.out.println("\n\n\n\nProfessores:");
        for (Professor professor : from(Professor.class)) {
            System.out.println(professor.getNome());
        }
        
        System.out.println("\n\n\n\n" + "Alunos:");
        for (Aluno aluno : from(Aluno.class)) {
            System.out.println(aluno.getNome() + " | " + aluno.getRa());
        }
        
        System.out.println("\n\n\n\n" + "Alunos com RA maior que 2000");
        for (Aluno aluno : from(Aluno.class, "where RA > 2000")) {
            System.out.println(aluno.toString());
        }
        
        System.out.println("\n\n\n\nAlunos cujo nome começa com M");
        for (Aluno aluno : from(Aluno.class, "where nome LIKE 'm    %'")) {
            System.out.println(aluno.toString());
        }
       
        System.out.println("\n\n\n\n");
        session.close();
        HibernateUtil.shutdown();
    }
}
