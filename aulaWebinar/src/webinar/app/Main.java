package webinar.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import webinar.dao.InstrutorDao;
import webinar.dao.JPAUtil;
import webinar.model.Aluno;
import webinar.model.Endereco;
import webinar.model.Formacao;
import webinar.model.InscricaoSeminario;
import webinar.model.Instrutor;
import webinar.model.Seminario;

public class Main {

	public static void main(String[] args) {

		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		
		Aluno aluno = new Aluno();
		aluno.setNome("Vinicius");
		aluno.setEmail("vinicius@gmail");
		aluno.setMatricula("200555181");

		Endereco end1 = new Endereco();
		end1.setRua("Rua casa");
		end1.setCidade("Alfenas");
		
		aluno.setEndereco(end1);
		
		
		Instrutor inst = new Instrutor("Mauricio", new Endereco("Rua 123", "Alfenas"), "gmail", 10000, Formacao.JEDI);
		Instrutor inst2 = new Instrutor("Mauricio2", new Endereco("Rua 1234", "Alfenas"), "gmail", 10000, Formacao.DOUTORADO);
		
		List<Instrutor> instrutores = new ArrayList();
		instrutores.add(inst);
		instrutores.add(inst2);
		
		Seminario sem = new Seminario("Join", 101, 50.00, instrutores);
		
		
		
		em.getTransaction().begin();
		
		em.persist(aluno);
		
		
		em.persist(inst);
		em.persist(inst2);
		em.persist(sem);
		

		
		//CONSULTA
		/*Seminario semicon = em.find(Seminario.class, 1);
		Aluno alunocon = em.find(Aluno.class, 1);
		
		InscricaoSeminario inscricao = new InscricaoSeminario(alunocon, semicon);
		em.persist(inscricao);*/

		//Seminario semicon = em.find(Seminario.class, 1);
		
		List<Instrutor> instrutors = new InstrutorDao().getAllByFormacao(Formacao.JEDI);
		for(Instrutor instrutor : instrutors) {
			System.out.println(instrutor.getNome());
		}
		
		em.getTransaction().commit();
		em.close();
		
		JPAUtil.shutdown();
		

	}

}
