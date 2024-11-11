package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.ManufacturerHandler;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add() {
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
        Manufacturer manufacturer1 = new Manufacturer("Apple", "USA");
        Sale sale = new Sale(new User("John", "Doe", 25, "john.doe@example.com", "123456789"), new Phone(manufacturer1, "iPhone", 2022, "black", 999, 10));
        List<Sale> sales = new ArrayList<>();
        sales.add(sale);
        when(mockSaleHandler.load()).thenReturn(sales);
        when(mockInput.nextLine()).thenReturn("0");
        appHelperPhone.delete();
        List<Phone> updatedPhones = appHelperPhone.getPhones();
        assert(updatedPhones.isEmpty());
        verify(mockPhoneHandler, times(1)).save(anyList());
    }

    @Test
    void edit() {
    }
}