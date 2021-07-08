package com.example.webclinet.service;

import com.example.webclinet.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.example.webclinet.entity.EmployeeConstants.*;

@Slf4j
public class RequestService {

    private WebClient webClient;

    public RequestService(WebClient webClient) {

        this.webClient = webClient;
    }

    //http://localhost:8081/employeeservice/v1/allEmployees
    public List<Employee> getAllEmployees(){
        return webClient.get().uri(GET_ALL_EMPLOYEES_V1)
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();
    }

    public Employee getEmployeeById(int employeeId){
        try{
            return webClient.get().uri(GET_EMPLOYEE_BY_ID_V1,employeeId)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        }catch (WebClientResponseException e){
            log.error("Error Response Code :{} body: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error("WebClientResponseException in getEmployeeById ", e);
            throw e;
        }catch (Exception e){
            log.error("Exception in getEmployeeById", e);
            throw e;
        }

    }

    public List<Employee> getEmployeeByName(String employeeName){

        String uri = UriComponentsBuilder.fromUriString(GET_EMPLOYEE_BY_NAME_V1)
                .queryParam("employee_name",employeeName)
                .build().toString();

        try {
            return webClient.get().uri(uri)
                    .retrieve()
                    .bodyToFlux(Employee.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e){
            log.error("Error Response Code :{} body :{}",e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error("WebClientResponseException ",e);
            throw e;
        } catch (Exception e){
            log.error("Exception in getEmployeeByName ", e);
            throw e;
        }
    }

    public Employee addNewEmployee(Employee employee){
        return webClient.post().uri(ADD_EMPLOYEE_V1)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
    }

    public Employee updateEmployee(int empId, Employee employee){
        return webClient.put().uri(GET_EMPLOYEE_BY_ID_V1,empId)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
    }

    public String deleteEmployee(int empId){
        return webClient.delete().uri(GET_EMPLOYEE_BY_ID_V1, empId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
