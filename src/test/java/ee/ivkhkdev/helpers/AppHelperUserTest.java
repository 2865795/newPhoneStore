package ee.ivkhkdev.helpers;

import ee.ivkhkdev.handlers.UserHandler;
import ee.ivkhkdev.input.Input;
import ee.ivkhkdev.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppHelperUserTest {

    @Mock
    private Input mockInput;

    @Mock
    private UserHandler mockUserHandler;

    @InjectMocks
    private AppHelperUser appHelperUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Arrange
        when(mockInput.nextLine()).thenReturn("John", "Doe", "25", "john.doe@example.com", "123456789");
        when(mockUserHandler.load()).thenReturn(Arrays.asList());

        // Act
        appHelperUser.add();

        // Assert
        List<User> users = appHelperUser.getUsers();
        assert(users.get(1).getName().equals("John"));
        assert(users.get(1).getSurname().equals("Doe"));
        verify(mockUserHandler, times(1)).save(anyList());
    }

    @Test
    void testDisplayAllUsers() {
        User user1 = new User("John", "Doe", 25, "john.doe@example.com", "123456789");
        User user2 = new User("Jane", "Doe", 30, "jane.doe@example.com", "987654321");
        when(mockUserHandler.load()).thenReturn(Arrays.asList(user1, user2));

        // Act
        appHelperUser.displayAll();

        // Assert
        // Проверим, что вывод на экран был произведен для двух пользователей
        verify(mockUserHandler, times(1)).load();
    }

    @Test
    void testDeleteUser() {
        User user1 = new User("John", "Doe", 25, "john.doe@example.com", "123456789");
        List<User> users = new ArrayList<>();
        users.add(user1);
        when(mockUserHandler.load()).thenReturn(users);
        when(mockInput.nextLine()).thenReturn("1"); // Пользователь с ID 1

        // Act
        appHelperUser.delete(); // Пользователь с ID 1

        // Assert
        List<User> updatedUsers = appHelperUser.getUsers();
        assert(updatedUsers.isEmpty());
        verify(mockUserHandler, times(1)).save(anyList());
    }

    @Test
    void testEditUser() {
        User user1 = new User("John", "Doe", 25, "john.doe@example.com", "123456789");
        List<User> users = new ArrayList<>();
        users.add(user1);
        when(mockUserHandler.load()).thenReturn(users);
        when(mockInput.nextLine()).thenReturn("1", "Jonathan", "Dorian", "30", "jonathan.dorian@example.com", "987654321");

        // Act
        appHelperUser.edit();

        // Assert
        User editedUser = users.get(0);
        assert(editedUser.getName().equals("Jonathan"));
        assert(editedUser.getSurname().equals("Dorian"));
        assert(editedUser.getAge() == 30);
        assert(editedUser.getEmail().equals("jonathan.dorian@example.com"));
        assert(editedUser.getPhone().equals("987654321"));

        verify(mockUserHandler, times(1)).save(anyList());
    }
}