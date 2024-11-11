package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Manufacturer;
import ee.ivkhkdev.handlers.ManufacturerHandler;
import ee.ivkhkdev.models.User;

import java.util.List;

public class AppHelperManufacturer implements AppHelper {
    private List<Manufacturer> manufacturers;
    private ManufacturerHandler manufacturerHandler;
    private Input input;

    public AppHelperManufacturer() {
        this.input = new ConsoleInput();
        this.manufacturerHandler = new ManufacturerHandler();
        this.manufacturers = manufacturerHandler.load();
    }

    @Override
    public void add() {
        String name = getString("Введите название производителя: ");
        String country = getString("Введите страну производителя: ");

        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturers.add(manufacturer);
        manufacturerHandler.save(manufacturers);
        System.out.println("Производитель добавлен: " + manufacturer);
    }

    @Override
    public void displayAll() {
        manufacturers = manufacturerHandler.load();
        System.out.println("Список производителей:");
        for (int i = 0; i < manufacturers.size(); i++) {
            System.out.println(i + 1 + ". " + manufacturers.get(i));
        }
        System.out.println("==== Конец списка ====");
    }

    @Override
    public void delete() {
        displayAll();
        int manufacturerId = getInt("Введите ID производителя для удаления: ");
        if (manufacturerId > 0 && manufacturerId <= manufacturers.size()) {
            manufacturers.remove(manufacturerId - 1);
            manufacturerHandler.save(manufacturers);
            System.out.println("Производитель удален.");
        } else {
            System.out.println("Производитель с указанным ID не найден.");
        }
    }

    @Override
    public void edit() {
        displayAll();

        int manufacturerId = getInt("Введите ID производителя для редактирования: ");
        if (manufacturerId > 0 && manufacturerId <= manufacturers.size()) {
            Manufacturer manufacturer = manufacturers.get(manufacturerId - 1);

            String name = getString("Введите новое имя (текущее: " + manufacturer.getName() + "): ");
            if (!name.isEmpty()) manufacturer.setName(name);
            String country = getString("Введите новую страну (текущее: " + manufacturer.getCountry() + "): ");
            if (!country.isEmpty()) manufacturer.setCountry(country);
            manufacturerHandler.save(manufacturers);
            System.out.println("Данные производителя обновлены: " + manufacturer);
        } else {
            System.out.println("Производитель с указанным ID не найден.");
        }
    }

    private String getString(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    private int getInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ошибка ввода. Попробуйте снова: ");
            }
        }
    }

    public Manufacturer getManufacturerByName(String name) {
        return manufacturers.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }
}
