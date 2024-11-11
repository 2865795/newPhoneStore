package ee.ivkhkdev.handlers;

import ee.ivkhkdev.models.Sale;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaleHandler implements Handler<Sale>{
    private static final String FILE_NAME = "sales.dat";

    @Override
    public List<Sale> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Sale>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<Sale> sales) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(sales);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных о продажах.");
        }
    }
}
