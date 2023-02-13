package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.EmployeeData;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static il.cshaifasweng.OCSFMediatorExample.server.App.SafeStartTransaction;


public class Employees {

    public HashMap<Integer, Employee> employees;

    Session session = App.session;

    public Employees() {
        employees = new HashMap<Integer, Employee>();
    }

    public void generateEmployees() {
        SafeStartTransaction();

        Employee or = new Employee(308283886, "or", "meir", "308283886", "ormeir93@gmail.com", "parking lot manager", "parking lot 5");
        Employee ohad = new Employee(308283881, "ohad", "orbach", "308283881", "ohadorbach92@gmail.com", "parking lot worker", "parking lot 5");
        Employee amit = new Employee(318883584, "amit", "barak", "318883584", "amitbarak1997@gmail.com", "company manager", "");
        Employee tomer = new Employee(312160062, "tomer", "bar-orin", "312160062", "tomer6464@gmail.com", "costumer service", "");
        Employee liam = new Employee(308283884, "liam", "gavrieli", "308283884", "liamgabriely@gmail.com", "parking lot worker", "parking lot 5");

        App.session.save(ohad);
        App.session.save(or);
        App.session.save(amit);
        App.session.save(tomer);
        App.session.save(liam);
        App.session.flush();
        App.SafeCommit();
    }

    public void pullEmployeesFromDB() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        query.from(Employee.class);
        List<Employee> data = session.createQuery(query).getResultList();
        employees.clear();
        for (Employee employee : data) {
            employees.put(employee.getId(), employee);
        }
    }

    public void getEmployees() {
        pullEmployeesFromDB();
    }

    public EmployeeData employeeLoginCheck(String id, String password) {
        System.out.format(" inside employeeLoginCheck() with id: " + id + " and password: " + password + "\n");
        Integer employeeId = Integer.valueOf(id);
        String search_employee = "FROM Employee employee WHERE employee.id = :id";
        Query query = session.createQuery(search_employee);
        query.setParameter("id", employeeId);
        List<Employee> employeesResult = query.getResultList();


        if (employeesResult.isEmpty() || !employeesResult.get(0).getPassword().equals(password) || employeesResult.get(0).isLogin()) {

            System.out.format("Login failed, employeesResult empty \n");
            return new EmployeeData();
        }

        Employee employee = employeesResult.get(0);
        employee.setLogin(true);
        EmployeeData empData = new EmployeeData(employee.getId(), employee.getPrivateName(), employee.getSureName(), employee.getPassword()
                , employee.getEmail(), employee.getJob(), employee.getBranch());
        System.out.format("Login Success \n");

        return empData;
    }

    public Employee getRandomCS() {
        List<Employee> csEmployees = new ArrayList<>();
        for (Employee e : employees.values()) {
            if (e.getJob().equals("costumer service")) {
                csEmployees.add(e);
            }
        }
        if (csEmployees.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(csEmployees.size());
        return csEmployees.get(randomIndex);
    }

    public void logoutEmployee(String id) {
        employees.get(Integer.valueOf(id)).setLogin(false);
    }

    public Employee findEmployeeById(int id) {
        return employees.get(id);
    }

}
