package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.UserHandler;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Manufacturer;
import ee.ivkhkdev.models.User;
import java.util.List;

public class AppHelperUser implements AppHelper {
    private List<User> users;
    private UserHandler userHandler;
    private Input input;

    public AppHelperUser() {
        this.input = new ConsoleInput();
        this.userHandler = new UserHandler();
        this.users = userHandler.load();
    }

    @Override
    public void add() {
        String name = getString("Введите имя покупателя: ");
        String surname = getString("Введите фамилию покупателя: ");
        int age = getInt("Введите возраст покупателя: ");
        String email = getString("Введите email покупателя: ");
        String phone = getString("Введите телефон покупателя: ");

        User user = new User(name, surname, age, email, phone);
        users.add(user);
        userHandler.save(users);
        System.out.println("Покутель добавлен: " + user);
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

    @Override
    public void displayAll() {
        users = userHandler.load();
        System.out.println("Список покупателей:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + 1 + ". " + users.get(i));
        }
        System.out.println("==== Конец списка ====");
    }

    @Override
    public void delete() {
        displayAll();
        int userId = getInt("Введите ID покупателя для удаления: ");
        if (userId > 0 && userId <= users.size()) {
            users.remove(userId - 1);
            userHandler.save(users);
            System.out.println("Покупатель удален.");
        } else {
            System.out.println("покупатель с указанным ID не найден.");
        }
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void edit() {
        displayAll();

        int userId = getInt("Введите ID покупателя для редактирования: ");
        if (userId > 0 && userId <= users.size()) {
            User user = users.get(userId - 1);

            String name = getString("Введите новое имя (текущее: " + user.getName() + "): ");
            if (!name.isEmpty()) user.setName(name);
            String surname = getString("Введите новую фамилию (текущее: " + user.getSurname() + "): ");
            if (!surname.isEmpty()) user.setSurname(surname);
            int age = getInt("Введите новый возраст (текущий: " + user.getAge() + "): ");
            if (age > 0) user.setAge(age);
            String email = getString("Введите новый email (текущий: " + user.getEmail() + "): ");
            if (!email.isEmpty()) user.setEmail(email);
            String phone = getString("Введите новый телефон (текущий: " + user.getPhone() + "): ");
            if (!phone.isEmpty()) user.setPhone(phone);
            userHandler.save(users);
            System.out.println("Данные покупателя обновлены: " + user);
        } else {
            System.out.println("Покупатель с указанным ID не найден.");
        }
    }
}
