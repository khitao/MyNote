import java.util.InputMismatchException;
import java.util.Scanner;

import static helper.HelperMethod.clearScreen;
import static helper.HelperVariables.redColor;
import static helper.HelperVariables.resetColor;

public class MyNotesApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyNotes myNotes = new MyNotes();
        MyNotes.creatingNoteFileWithFirstNote();


        int choice;

        boolean flag = true;

        clearScreen();

        while (flag) {
            myNotes.readOutNoteAndAddToArray();
            System.out.println("Меню:");
            System.out.println("1. Добавить заметку");
            System.out.println("2. Редактировать заметку");
            System.out.println("3. Удалить заметку");
            System.out.println("0. Закрыть приложение");

            System.out.print("Выберите пункт меню: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        clearScreen();
                        myNotes.addNote();
                        clearScreen();
                        break;
                    case 2:
                        clearScreen();
                        myNotes.changeNote();
                        if (MyNotes.notes.size() != 0)
                            clearScreen();
                        break;
                    case 3:
                        clearScreen();
                        myNotes.deleteNote();
                        if (MyNotes.notes.size() != 0)
                            clearScreen();
                        break;
                    case 0:
                        clearScreen();
                        System.out.println("\nСеанс окончен.\n");
                        flag = false;
                        break;
                    default:
                        clearScreen();
                        System.out.println(redColor + "\nВведенное вами число не соответствует ни одному пункту меню. Попробуйте еще раз.\n" + resetColor);
                        break;
                }
            } catch (InputMismatchException e) {
                clearScreen();
                System.out.println(redColor + "\nОшибка ввода: вы вводите не число.\n" + resetColor);
                scanner.nextLine(); // очистка буфера ввода
            }
        }
    }
}
