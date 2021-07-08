package com.example.webclinet;

import com.example.webclinet.entity.Employee;
import com.example.webclinet.service.RequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WebClinetApplicationTests {

	private static String baseUrl = "http://localhost:8081/employeeservice";
	private WebClient webClient = WebClient.create(baseUrl);

	RequestService requestService = new RequestService(webClient);

	@Test
	void getAllEmployees(){
		List<Employee> employeeList = requestService.getAllEmployees();
		System.out.println("employeeList: " + employeeList);
		System.out.println("total employees: "+ employeeList.size());
		assertTrue(employeeList.size()>0);
	}

	@Test
	void getEmployeeById(){
		Employee employee = requestService.getEmployeeById(1);
		System.out.println(employee);
		assertEquals("Chris",employee.getFirstName());
	}

	@Test
	void getEmployeeByIdNotFound(){
		Assertions.assertThrows(WebClientResponseException.class, ()->requestService.getEmployeeById(10));
	}

	@Test
	void getEmployeeByName(){
		List<Employee> employeeNameList = requestService.getEmployeeByName("Chris");
		System.out.println(employeeNameList);
		assertTrue(employeeNameList.size()>0);
		Employee employeeName = employeeNameList.get(0);
		assertEquals("Chris",employeeName.getFirstName());
	}

	@Test
	void employeeNameListNotFound() {
		String name = "ra";
		Assertions.assertThrows(WebClientResponseException.class, ()-> requestService.getEmployeeByName(name));
	}

	@Test
	void addNewEmployee(){
		Employee employee = new Employee(5,25,"MH","Rajon","Male","Architect");
		Employee employee1 = requestService.addNewEmployee(employee);
		System.out.println("employee: " + employee1);
		assertTrue(employee1.getId() != null);
	}

	@Test
	void updateEmployee(){
		Employee employee = new Employee(null,null,"Rafsan",null,null, null);
		Employee employee1 = requestService.updateEmployee(2, employee);
		System.out.println(employee1);
		assertEquals("Rafsan",employee1.getFirstName());
	}

	@Test
	void deleteEmployee(){
		//do not run this test. data loss will occur.
		String response = requestService.deleteEmployee(7);
		String responseTxt = "Employee deleted successfully";
		assertEquals(responseTxt,response);
	}
	
}
