package ru.zagbor.practice.suleimanov.task1.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepository;
import ru.zagbor.practice.suleimanov.task1.service.impl.CustomerServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

//???????
    @Test
    public void testGetAll(){
        List<Customer> expectedCustomers = List.of(new Customer());
        when(repository.getAll()).
                thenReturn(expectedCustomers);

        List<Customer> actualCustomers = service.getAll();

        verify(repository).getAll();
        Assert.assertFalse(actualCustomers.isEmpty());
        Assert.assertEquals(expectedCustomers.size(), actualCustomers.size());

    }

    @Test
    public void testGetById(){
        Long expectId = 1L;
        Optional<Customer> actualCustomer = service.getCustomerById(expectId);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(repository).getById(longArgumentCaptor.capture());
        Long actualCustomerId = longArgumentCaptor.getValue();
        Assert.assertEquals(expectId, actualCustomerId);
    }

    @Test
    public void testDeleteById() {
        Long expectId = 1L;
        service.deleteById(expectId);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(repository).deleteById(longArgumentCaptor.capture());
        Long actualCustomer = longArgumentCaptor.getValue();
        Assert.assertEquals(expectId, actualCustomer);
    }

    @Test
    public void testCreate() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setAccount(new Account());
        customer.setName("Boris");

        service.create(customer);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).create(customerArgumentCaptor.capture());
        Customer actualCustomer = customerArgumentCaptor.getValue();
        Assert.assertEquals(customer, actualCustomer);
    }

    @Test
    public void testDeleteSpecialtyCustomer(){
        AtomicLong specialtiesIdSequence = new AtomicLong(System.currentTimeMillis());

        Set<Specialty> expectedSpecialtySet = new HashSet<>();
        expectedSpecialtySet.add(Specialty.builder().id(specialtiesIdSequence.incrementAndGet()).build());
        expectedSpecialtySet.add(Specialty.builder().id(specialtiesIdSequence.incrementAndGet()).build());
        expectedSpecialtySet.add(Specialty.builder().id(specialtiesIdSequence.incrementAndGet()).build());

        Specialty specialtyForDelete =
                Specialty.builder()
                        .id(specialtiesIdSequence.incrementAndGet())
                        .build();
        HashSet<Specialty> specialties = new HashSet<>(expectedSpecialtySet);
        specialties.add(specialtyForDelete);


        Customer customer = Customer.builder().specialties(specialties).build();

        service.deleteSpecialtyCustomer(customer, specialtyForDelete);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).update(customerArgumentCaptor.capture());
        Customer actualCustomer = customerArgumentCaptor.getValue();
        Set<Specialty> actualSpecialties = actualCustomer.getSpecialties();
        Assert.assertEquals(expectedSpecialtySet.size(), actualSpecialties.size());
        Assert.assertEquals(expectedSpecialtySet, actualCustomer.getSpecialties());

    }

    @Test
    public void testChangeName() {

        Customer customer = new Customer(1L, "testName", Collections.emptySet(), new Account());
        String expectedName = "expectName";

        service.changeName(customer, expectedName);

        Assert.assertEquals(expectedName, customer.getName());

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).update(customerArgumentCaptor.capture());
        Customer actualCustomer = customerArgumentCaptor.getValue();
        Assert.assertEquals(customer.getId(), actualCustomer.getId());
        Assert.assertEquals(expectedName, actualCustomer.getName()); // <---
        Assert.assertEquals(customer.getSpecialties(), actualCustomer.getSpecialties());
        Assert.assertEquals(customer.getAccount(), actualCustomer.getAccount());
    }

    @Test
    public void testChangeAccountStatus() {
        Account account = new Account();
        account.setAccountStatus(Account.AccountStatus.DELETED);
        Customer customer = new Customer(1L, "testName", Collections.emptySet(), account);
     //   account.changeAccountStatus(customer, Account.AccountStatus.ACTIVE);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).update(customerArgumentCaptor.capture());
        Customer actualCustomer = customerArgumentCaptor.getValue();
        Assert.assertEquals(customer.getAccount().getAccountStatus(), actualCustomer.getAccount().getAccountStatus());
    }

    @Test
    public void testAddSpecialtyCustomer() {
        Customer customer = new Customer(1L, "testName", new HashSet<>(), new Account());

        when(repository.getById(anyLong()))
                .thenReturn(Optional.of(customer));

        Specialty specialty1 = new Specialty();
        specialty1.setId(1);
        specialty1.setName("Архитектор");

      //  service.addSpecialtyCustomer(customer.getId(), specialty1);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).update(customerArgumentCaptor.capture());
        Customer actualCustomer = customerArgumentCaptor.getValue();
        Assert.assertEquals(customer, actualCustomer);
    }

    @Test
    public void testIsCustomerExist()  {
        Long expectId = 1L;
        service.isCustomerExist(expectId);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(repository).isCustomerExist(longArgumentCaptor.capture());
        Long actualCustomer = longArgumentCaptor.getValue();
        Assert.assertEquals(expectId, actualCustomer);
    }
 /*   @Test
    public void perfectTestChangeName() throws IOException, SQLException {
        CustomerRepository repository = mock(CustomerRepository.class);
//        doThrow(new RuntimeException("olololo!!!!!111111"))
//                .when(repository).update(any(Customer.class));


        CustomerService service = new CustomerServiceImpl(repository);
        Customer customer = new Customer(1L, "ololo-name", Collections.emptySet(), new Account());
        String expectedName = "gsom-name";

        service.changeName(customer, expectedName);

        Assert.assertEquals(expectedName, customer.getName());

        verify(repository).update(any(Customer.class));
//        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
//        verify(repository).update(customerArgumentCaptor.capture());
//        Customer actualCustomer = customerArgumentCaptor.getValue();
//        Assert.assertEquals(customer.getId(), actualCustomer.getId());
//        Assert.assertEquals(expectedName, actualCustomer.getName()); // <---
//        Assert.assertEquals(customer.getSpecialties(), actualCustomer.getSpecialties());
//        Assert.assertEquals(customer.getAccount(), actualCustomer.getAccount());
    }*/


/*
    Customer customer = new Customer();
        customer.setId(1);
        customer.setAccount(new Account());
        customer.setName("Boris");
    Specialty specialty1 = new Specialty();
        specialty1.setId(1);
        specialty1.setName("Архитектор");
    Specialty specialty2 = new Specialty();
        specialty2.setId(2);
        specialty2.setName("Строитель");
    Set<Specialty> specialties = Set.of(specialty1, specialty2);
        customer.setSpecialties(specialties);
*/
}
