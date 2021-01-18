package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService{

    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;

    @Autowired
    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository){
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerByPetId(Long petId){
        return petRepository.getOne(petId).getOwner();
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAvailableEmployeesBySkills(DayOfWeek date, Set<EmployeeSkill> skills){
        List<Employee> availableEmployees = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(date);

        for(Employee employee : employees){
            if(employee.getSkills().containsAll(skills)){
                availableEmployees.add(employee);
            }
        }

        return availableEmployees;
    }

}
