package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.SaleHandler;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.Phone;
import ee.ivkhkdev.models.Sale;
import ee.ivkhkdev.models.User;

import java.util.List;

public class AppHelperSale implements AppHelper {
    private List<Sale> sales;
    private SaleHandler saleHandler;
    private Input input;
    private AppHelperPhone appHelperPhone;
    private AppHelperUser appHelperUser;

    public AppHelperSale() {
        this.input = new ConsoleInput();
        this.saleHandler = new SaleHandler();
        this.sales = saleHandler.load();
        this.appHelperPhone = new AppHelperPhone();
        this.appHelperUser = new AppHelperUser();
    }

    @Override
    public void add() {
        appHelperUser.displayAll();
        int userId = 0;
        while (true) {
            System.out.print("Выберите id покупателя из списка: ");
            userId = input.nextInt();
            input.nextLine();
            if (userId < 1 || userId > appHelperUser.getUsers().size()) {
                System.out.println("Неверный id покупателя.");
            } else {
                break;
            }
        }
        User user = appHelperUser.getUsers().get(userId - 1);
        appHelperPhone.displayAll();
        int phoneId = getInt("Введите ID телефона для продажи: ");
        int quantity = getInt("Введите количество для продажи: ");
        if (appHelperPhone.sellPhone(phoneId, quantity)) {
            Phone phone = appHelperPhone.getPhoneById(phoneId);
            phone.setQuantity(quantity);
            Sale sale = new Sale(user, phone);
            sales.add(sale);
            saleHandler.save(sales);
        }
    }

    @Override
    public void displayAll() {
        sales = saleHandler.load();
        System.out.println("Список продаж:");
        for (Sale sale : sales) {
            System.out.println(sale);
        }
        System.out.println("==== Конец списка ====");
    }

    @Override
    public void delete() {

    }

    @Override
    public void edit() {

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
}
