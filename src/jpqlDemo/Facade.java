/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpqlDemo;

import Entity.Student;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Andreas
 */
public class Facade {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqlDemoPU");
    EntityManager em = emf.createEntityManager();
    
    public List<Student> findAllStudents(){
       Query query = em.createNamedQuery("Student.findAll");
       List<Student> allStudents = query.getResultList();
       return allStudents;
    }
    
    public List<Student> findFirstNameJan(){
        Query query = em.createNamedQuery("Student.findByFirstname");
        query.setParameter("firstname", "jan");
        List<Student> studentsNamedJan = query.getResultList();
        return studentsNamedJan;
    }
    
        public List<Student> findLastNameOlsen(){
        Query query = em.createNamedQuery("Student.findByLastname");
        query.setParameter("lastname", "Olsen");
        List<Student> studentsNamedOlsen = query.getResultList();
        return studentsNamedOlsen;
    }
        
        public Integer totalStudyPointsForAStudent(int id){
        Query query = em.createNamedQuery("Studypoint.findStudentScore");
        query.setParameter("id", id);
        List<Integer> scores = query.getResultList();
        Integer totalScore = 0;
        for (Integer score : scores) {
                totalScore = totalScore + score;
        }
        return totalScore;
        }   
        
        public Integer totalStudypoints(){
            Query query = em.createNamedQuery("Studypoint.findTotalScore");
            List<Integer> scores = query.getResultList();
            Integer totalScore = 0;
            for (Integer score : scores) {
                totalScore = totalScore + score;
            }
            return totalScore;
        }
        
        public Student findStudentsWithHighestStudypoints(){
            Student s = null;
        Query query = em.createNamedQuery("Student.findAll");
        List<Student> allStudents;
        allStudents = query.getResultList();

        Map<Integer, Integer> studyPoints = new HashMap();

        for (int i = 1; i < allStudents.size() + 1; i++) {
            Integer totalScore = 0;

            totalScore = totalStudyPointsForAStudent(i);
            studyPoints.put(i, totalScore);
        }
        int maxValueInMap = (Collections.max(studyPoints.values()));  // This will return max value in the Hashmap
        for (Entry<Integer, Integer> entry : studyPoints.entrySet()) {  // Itrate through hashmap
            if (entry.getValue() == maxValueInMap) {
                s = em.find(Student.class, entry.getKey());
            }
        }
        return s;
        }
        
        public Student findStudentsWithLowestStudypoints(){
            Student s = null;
            Query query = em.createNamedQuery("Student.findAll");
            List<Student> allStudents;
            allStudents = query.getResultList();
            Map<Integer, Integer> studyPoints = new HashMap();
            for (int i = 1; i < allStudents.size() + 1; i++) {
                Integer totalScore = 0;
                totalScore = totalStudyPointsForAStudent(i);
                studyPoints.put(i, totalScore);
            }
            int minValueInMap = (Collections.min(studyPoints.values()));  // This will return max value in the Hashmap
            for (Entry<Integer, Integer> entry : studyPoints.entrySet()) {  // Itrate through hashmap
                if (entry.getValue() == minValueInMap) {
                    s = em.find(Student.class, entry.getKey());
                }
            }
            return s;
            }   
        
        public Student createStudent(String firstname, String lastname){
            Student s = new Student(firstname, lastname);
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            return s;
        }
        
        public void addStudyPoint(String description, int maxVal, int score, int id) {
        Student s = em.find(Student.class, id);
        s.addStudyPoint(description, maxVal, score);
        em.getTransaction().begin();
        em.merge(s);
        em.getTransaction().commit();
    }
}
