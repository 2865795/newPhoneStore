package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.UserHandler;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Manufacturer;
import ee.ivkhkdev.models.User;

import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;

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

    @Override
    public void displayAll() {
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

    @Override
    public void edit() {

    }
}
