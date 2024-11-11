package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.ManufacturerHandler;
import ee.ivkhkdev.handlers.PhoneHandler;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Manufacturer;
import ee.ivkhkdev.models.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class AppHelperPhoneTest {
    @Mock
    private Input mockInput;

    @Mock
    private PhoneHandler mockPhoneHandler;

    @Mock
    private ManufacturerHandler mockManufacturerHandler;

    @InjectMocks
    private AppHelperPhone appHelperPhone;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add() {
        Manufacturer manufacturer1 = new Manufacturer("Apple", "USA");
        when(mockManufacturerHandler.load()).thenReturn(Arrays.asList(manufacturer1));
        when(mockInput.nextInt()).thenReturn(1, 1);
        when(mockInput.nextLine()).thenReturn( "1","iPhone", "2022", "black", "999", "10");
        appHelperPhone.add();
        List<Phone> phones = appHelperPhone.getPhones();
        assert phones.get(2).getName().equals("iPhone");
        verify(mockPhoneHandler, times(1)).save(anyList());
    }

    @Test
    void displayAll() {
        Manufacturer manufacturer1 = new Manufacturer("Apple", "USA");
        Manufacturer manufacturer2 = new Manufacturer("Samsung", "Korea");
        Phone phone1 = new Phone(manufacturer1, "iPhone", 2022, "black", 999, 10);
        Phone phone2 = new Phone(manufacturer2, "Gelaxy", 2022, "black", 999, 10);
        when(mockPhoneHandler.load()).thenReturn(Arrays.asList(phone1, phone2));
        appHelperPhone.displayAll();
        verify(mockPhoneHandler, times(1)).load();
    }

    @Test
    void delete() {
        Manufacturer manufacturer = new Manufacturer("Apple", "USA");
        Phone phone1 = new Phone(manufacturer, "iPhone", 2022, "black", 999, 10);
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        when(mockPhoneHandler.load()).thenReturn(phones);
        when(mockInput.nextLine()).thenReturn("0");
        appHelperPhone.delete();
        List<Phone> updatedPhones = appHelperPhone.getPhones();
        assert(updatedPhones.isEmpty());
        verify(mockPhoneHandler, times(1)).save(anyList());
    }

    @Test
    void edit() {
        Manufacturer manufacturer = new Manufacturer("Apple", "USA");
        Phone phone1 = new Phone(manufacturer, "iPhone", 2022, "black", 999, 10);
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        when(mockPhoneHandler.load()).thenReturn(phones);
        when(mockInput.nextLine()).thenReturn("1", "iPhone", "2015", "red", "299");
        appHelperPhone.edit();
        Phone updatedPhone = phones.get(0);
        assert(updatedPhone.getName().equals("iPhone"));
        assert(updatedPhone.getColor().equals("red"));
        verify(mockPhoneHandler, times(1)).save(anyList());
    }
}