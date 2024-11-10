package ee.ivkhkdev.handlers;

import ee.ivkhkdev.models.Manufacturer;
import ee.ivkhkdev.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserHandler implements Handler<User> {
    private static final String FILE_NAME = "users.dat";
    @Override
    public List<User> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных о пользователях.");
        }
    }
}
