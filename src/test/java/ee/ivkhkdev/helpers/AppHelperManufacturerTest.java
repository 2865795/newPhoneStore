package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.ManufacturerHandler;
import ee.ivkhkdev.handlers.UserHandler;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Manufacturer;
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

class AppHelperManufacturerTest {

    @Mock
    private Input mockInput;

    @Mock
    private ManufacturerHandler mockManufacturerHandler;

    @InjectMocks
    private AppHelperManufacturer appHelperManufacturer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add() {
        when(mockInput.nextLine()).thenReturn("Apple", "USA");
        when(mockManufacturerHandler.load()).thenReturn(Arrays.asList());
        appHelperManufacturer.add();
        List<Manufacturer> manufacturers = appHelperManufacturer.getManufacturers();
        assert manufacturers.get(0).getName().equals("Apple");
        assert manufacturers.get(0).getCountry().equals("USA");
        verify(mockManufacturerHandler, times(1)).save(anyList());
    }

    @Test
    void displayAll() {
        Manufacturer manufacturer1 = new Manufacturer("Apple", "USA");
        Manufacturer manufacturer2 = new Manufacturer("Samsung", "Korea");
        when(mockManufacturerHandler.load()).thenReturn(Arrays.asList(manufacturer1, manufacturer2));
        appHelperManufacturer.displayAll();
        verify(mockManufacturerHandler, times(1)).load();
    }

    @Test
    void delete() {
        Manufacturer manufacturer1 = new Manufacturer("Apple", "USA");
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturer1);
        when(mockManufacturerHandler.load()).thenReturn(manufacturers);
        when(mockInput.nextLine()).thenReturn("1");
        appHelperManufacturer.delete();
        List<Manufacturer> updatedManufacturers = appHelperManufacturer.getManufacturers();
        assert(updatedManufacturers.isEmpty());
        verify(mockManufacturerHandler, times(1)).save(anyList());
    }

    @Test
    void edit() {
        Manufacturer manufacturer1 = new Manufacturer("Apple", "USA");
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturer1);
        when(mockManufacturerHandler.load()).thenReturn(manufacturers);
        when(mockInput.nextLine()).thenReturn("1", "Samsung", "Korea");
        appHelperManufacturer.edit();
        Manufacturer editedManufacturer = manufacturers.get(0);
        assert editedManufacturer.getName().equals("Samsung");
        assert editedManufacturer.getCountry().equals("Korea");
        verify(mockManufacturerHandler, times(1)).save(anyList());
    }
}