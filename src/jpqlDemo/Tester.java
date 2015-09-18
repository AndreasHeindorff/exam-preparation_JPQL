/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpqlDemo;

import Entity.Student;

/**
 *
 * @author Andreas
 */
public class Tester {
    public static void main(String[] args) {
        Facade f = new Facade();
        f.findAllStudents();
        f.findFirstNameJan();
        f.findLastNameOlsen();
        f.findStudentsWithHighestStudypoints();
        f.findStudentsWithLowestStudypoints();
        f.totalStudyPointsForAStudent(1);
        f.totalStudypoints();
        
        
        
        
        
        
    }
    
    
    
}
