package org.example.View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserListPanel extends JPanel {
    private DefaultListModel<String> userListModel;
    private JList<String> userList;
    private String currentUsername; // Хранить имя текущего пользователя

    public UserListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Users"));

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(userList);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Обновление списка пользователей
    public void updateUsers(List<String> users) {
        userListModel.clear();
        for (String user : users) {
            if(user != null) {
                userListModel.addElement(user);
            }
        }
        highlightCurrentUser();
    }

    // Установка текущего пользователя, которого нужно подсветить
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
        highlightCurrentUser(); // Подсветим сразу после установки
    }

    // Метод для подсветки текущего пользователя
    private void highlightCurrentUser() {
        if (currentUsername != null) {
            // Проходим по всем пользователям и ищем текущего
            for (int i = 0; i < userListModel.getSize(); i++) {
                String user = userListModel.getElementAt(i);
                if (user.equals(currentUsername)) {
                    userList.setSelectionBackground(new Color(180, 112, 111, 70)); // Цвет фона для выделения
                    userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Позволяем выделять одного пользователя
                    userList.setSelectedIndex(i); // Подсвечиваем текущего пользователя
                    break;
                }
            }
        }
    }
}
