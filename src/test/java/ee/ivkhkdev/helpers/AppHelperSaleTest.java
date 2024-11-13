package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.PhoneHandler;
import ee.ivkhkdev.handlers.SaleHandler;
import ee.ivkhkdev.handlers.UserHandler;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Manufacturer;
import ee.ivkhkdev.models.Phone;
import ee.ivkhkdev.models.Sale;
import ee.ivkhkdev.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class AppHelperSaleTest {

    @Mock
    private Input mockInput;

    @Mock
    private SaleHandler mockSaleHandler;

    @Mock
    private PhoneHandler mockPhoneHandler;

    @Mock
    private UserHandler mockUserHandler;

    @InjectMocks
    private AppHelperSale appHelperSale;

    @Mock
    private AppHelperPhone mockAppHelperPhone;

    @Mock
    private AppHelperUser mockAppHelperUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add() {
        User user = new User("John", "Doe", 25, "john.doe@example.com", "123456789");
        List<User> users = new ArrayList<>();
        users.add(user);
        Phone phone = new Phone(new Manufacturer("Apple", "USA"), "iPhone", 2022, "black", 999, 10);
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);

        when(mockAppHelperUser.getUsers()).thenReturn(users);
        when(mockAppHelperPhone.getPhones()).thenReturn(phones);
        when(mockAppHelperPhone.sellPhone(1, 2)).thenReturn(true);
        when(mockAppHelperPhone.getPhoneById(1)).thenReturn(phone);
        when(mockInput.nextInt()).thenReturn(1);
        when(mockInput.nextLine()).thenReturn("").thenReturn("1").thenReturn("2");
        appHelperSale.add();

        verify(mockSaleHandler, times(1)).save(anyList());
        verify(mockAppHelperPhone, times(1)).sellPhone(1, 2);
        verify(mockAppHelperPhone, times(1)).getPhoneById(1);
    }

    @Test
    void displayAll() {
        Manufacturer manufacturer1 = new Manufacturer("Apple", "USA");
        Sale sale = new Sale(new User("John", "Doe", 25, "john.doe@example.com", "123456789"), new Phone(manufacturer1, "iPhone", 2022, "black", 999, 10));
        when(mockSaleHandler.load()).thenReturn(Arrays.asList(sale));
        appHelperSale.displayAll();
        verify(mockSaleHandler, times(1)).load();
    }

    @Test
    void delete() {

    }

    @Test
    void edit() {
    }
}