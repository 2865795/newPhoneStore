package ee.ivkhkdev.helpers;

import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Manufacturer;
import ee.ivkhkdev.models.Phone;
import ee.ivkhkdev.handlers.PhoneHandler;

import java.util.List;

public class AppHelperPhone implements AppHelper {
    private List<Phone> phones;
    private PhoneHandler phoneHandler;
    private Input input;
    private AppHelperManufacturer manufacturerHelper;

    public AppHelperPhone() {
        this.input = new ConsoleInput();
        this.phoneHandler = new PhoneHandler();
        this.phones = phoneHandler.load();
        this.manufacturerHelper = new AppHelperManufacturer();
    }

    @Override
    public void add() {
        manufacturerHelper.displayAll();
        System.out.print("Производитель есть в списке? (1 - да, 0 - нет): ");
        int response = input.nextInt();
        input.nextLine();  // Очищаем буфер после nextInt()
        if (response != 1) {
            System.out.println("Возврат в главное меню.");
            return;
        }

        // Если выбран "1" (производитель есть в списке), то показываем список и просим выбрать
        System.out.print("Введите номер производителя из списка: ");
        int manufacturerId = input.nextInt();
        input.nextLine();  // Очищаем буфер после nextInt()
        if (manufacturerId < 1 || manufacturerId > manufacturerHelper.getManufacturers().size()) {
            System.out.println("Неверный номер производителя.");
            return;
        }

        // Получаем производителя по ID
        Manufacturer manufacturer = manufacturerHelper.getManufacturers().get(manufacturerId - 1);

        // Далее запрашиваем данные для телефона
        String name;
        // Запрашиваем название телефона и проверяем его корректность
        while (true) {
            System.out.print("Введите название телефона: ");
            name = input.nextLine().trim();  // Вводим название телефона, убираем лишние пробелы
            if (!name.isEmpty()) {
                break;  // Выход из цикла, если название корректное
            } else {
                System.out.println("Ошибка! Название телефона не может быть пустым. Попробуйте снова.");
            }
        }

        // Запрос на год выпуска, цвет и прочее
        int year = getInt("Введите год выпуска: ");
        String color = getString("Введите цвет: ");
        double price = getDouble("Введите цену: ");
        int quantity = getInt("Введите количество: ");

        // Создаем новый телефон и сохраняем его
        Phone phone = new Phone(manufacturer, name, year, color, price, quantity);
        phones.add(phone);
        phoneHandler.save(phones);
        System.out.println("Телефон добавлен: " + phone);
    }

    public void replenishStock() {
        displayAll();
        int phoneId = getInt("Введите ID телефона для пополнения: ");
        int amount = getInt("Введите количество для добавления: ");

        Phone phone = getPhoneById(phoneId);
        if (phone != null) {
            phone.setQuantity(phone.getQuantity() + amount);
            phoneHandler.save(phones);
            System.out.println("Количество успешно обновлено.");
        } else {
            System.out.println("Телефон с указанным ID не найден.");
        }
    }

    boolean sellPhone(int phoneId, int quantity) {
        Phone phone = getPhoneById(phoneId);
        if (phone != null && phone.getQuantity() >= quantity) {
            phone.setQuantity(phone.getQuantity() - quantity);
            phoneHandler.save(phones);
            System.out.println("Телефон продан.");
            displayAll();
            return true;
        } else {
            System.out.println("Ошибка при продаже: проверьте ID телефона и доступное количество.");
            return false;
        }
    }

    @Override
    public void displayAll() {
        System.out.println("Список телефонов:");
        for (Phone phone : phones) {
            System.out.println(phone);
        }
        System.out.println("==== Конец списка ====");
    }

    @Override
    public void delete() {
        displayAll();
        int phoneId = getInt("Введите ID телефона для удаления: ");
        Phone phone = getPhoneById(phoneId);
        if (phone != null) {
            phones.remove(phone);
            phoneHandler.save(phones);
            System.out.println("Телефон удален.");
        } else {
            System.out.println("Телефон с указанным ID не найден.");
        }
    }

    @Override
    public void edit() {
        displayAll();

        int phoneId = getInt("Введите ID телефона для редактирования: ");
        if (phoneId > 0 && phoneId <= phones.size()) {
            Phone phone = phones.get(phoneId - 1);

            String name = getString("Введите новое имя (текущее: " + phone.getName() + "): ");
            if (!name.isEmpty()) phone.setName(name);
            int year = getInt("Введите новый год (текущий: " + phone.getYear() + "): ");
            if (year > 0) phone.setYear(year);
            String color = getString("Введите новый цвет (текущий: " + phone.getColor() + "): ");
            if (!color.isEmpty()) phone.setColor(color);
            double price = getDouble("Введите новую цену (текущая: " + phone.getPrice() + "): ");
            if (price > 0) phone.setPrice(price);
            phoneHandler.save(phones);
            System.out.println("Данные телефона обновлены: " + phone);
        } else {
            System.out.println("Телефон с указанным ID не найден.");
        }
    }

    public Phone getPhoneById(int id) {
        return phones.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
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

    private double getDouble(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ошибка ввода. Попробуйте снова: ");
            }
        }
    }
}
