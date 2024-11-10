package ee.ivkhkdev;

import ee.ivkhkdev.helpers.AppHelperPhone;
import ee.ivkhkdev.helpers.AppHelperManufacturer;
import ee.ivkhkdev.helpers.AppHelperUser;
import ee.ivkhkdev.input.ConsoleInput;
import ee.ivkhkdev.input.Input;

public class App {
    private final AppHelperUser userHelper;
    private AppHelperPhone phoneHelper;
    private AppHelperManufacturer manufacturerHelper;
    private Input input;

    public App() {
        this.phoneHelper = new AppHelperPhone();
        this.manufacturerHelper = new AppHelperManufacturer();
        this.userHelper = new AppHelperUser();
        this.input = new ConsoleInput();
    }

    public void run() {
        while (true) {
            System.out.println("\n Список доступных опций: ");
            System.out.println("1. Добавить производителя");
            System.out.println("2. Добавить телефон");
            System.out.println("3. Добавить покупателя");
            System.out.println("4. Показать список производителей");
            System.out.println("5. Показать список телефонов");
            System.out.println("6. Показать список покупателей");
            System.out.println("7. Редактировать производителя");
            System.out.println("8. Редактировать телефон");
            System.out.println("9. Редактировать покупателя");
            System.out.println("10. Удалить производителя");
            System.out.println("11. Удалить телефон");
            System.out.println("12. Удалить покупателя");
            System.out.println("13. Пополнить количество телефонов");
            System.out.println("14. Продать телефон");
            System.out.println("15. Показать продажи телефонов");
            System.out.println("0. Выйти");

            int choice = getInt("Выберите опцию: ");
            System.out.println();

            switch (choice) {
                case 1:
                    manufacturerHelper.add();
                    break;
                case 2:
                    phoneHelper.add();
                    break;
                case 3:
                    userHelper.add();
                    break;
                case 4:
                    manufacturerHelper.displayAll();
                    break;
                case 5:
                    phoneHelper.displayAll();
                    break;
                case 6:
                    userHelper.displayAll();
                    break;
                case 7:
                    manufacturerHelper.edit();
                    break;
                case 8:
                    phoneHelper.edit();
                    break;
                case 9:
                    userHelper.edit();
                    break;
                case 10:
                    manufacturerHelper.delete();
                    break;
                case 11:
                    phoneHelper.delete();
                    break;
                case 12:
                    userHelper.delete();
                    break;
                case 13:
                    phoneHelper.replenishStock();
                    break;
                case 14:
                    
                    break;
                case 15:

                    break;
                case 0:
                    phoneHelper.savePhones();
                    manufacturerHelper.saveManufacturers();
                    userHelper.saveUsers();
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
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