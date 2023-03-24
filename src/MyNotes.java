import java.io.*;
import java.util.ArrayList;
import java.util.List;


import static helper.HelperMethod.clearScreen;
import static helper.HelperVariables.*;

public class MyNotes {
    private static final String NOTES_FILE = "notes.txt";

    private static BufferedReader reader = null;
    private static BufferedWriter writer = null;

    protected static final List<String> notes = new ArrayList<>();


    protected static void creatingNoteFileWithFirstNote() {
        // Создание файла заметок с первой заметкой (по умолчанию), если файл не существует

        String DEFAULT_NOTE = "Первая заметка с текстом.";

        try {

            reader = new BufferedReader(new FileReader(NOTES_FILE));


        } catch (IOException e) {
            try {
                writer = new BufferedWriter(new FileWriter(NOTES_FILE));
                writer.write(DEFAULT_NOTE);
                writer.newLine();
                writer.flush();
                notes.add(DEFAULT_NOTE);
            } catch (IOException ex) {
                System.err.println("Ошибка создания файла заметок: " + ex.getMessage());
            } finally {
                try {
                    if (writer != null)
                        writer.close();

                } catch (IOException ex) {
                    System.err.println("Ошибка закрытия файла заметок: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                System.err.println("Ошибка закрытия файла заметок: " + e.getMessage());
            }
        }
    }


    protected void readOutNoteAndAddToArray() {
        // Чтение заметок из файла, вывод их на экран и добавление в массив
        try {

            reader = new BufferedReader(new FileReader(NOTES_FILE));
            notes.clear();

            String line;
            int count = 0;
            boolean isEmpty = true;

            System.out.println("Ваши заметки:");
            while ((line = reader.readLine()) != null) {
                count++;
                notes.add(line);
                System.out.printf("Заметка №%d: %s\n", count, line);
                isEmpty = false;
            }

            if (isEmpty)  // Проверяем, пуст ли файл
                System.out.println(cyanColor + "На данный момент у вас отсутствуют заметки." + resetColor);


        } catch (IOException e) {
            System.err.println("Ошибка чтения файла заметок: " + e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                System.err.println("Ошибка закрытия файла заметок: " + e.getMessage());
            }
        }
    }

    protected void writeChangesToFile(BufferedWriter writer) throws IOException {
        // Запись изменений в файл
        for (String note : notes) {
            writer.write(note);
            writer.newLine();
        }
        writer.flush();
    }


    protected void deleteNote() {
        // Удаление заметок и запись их в файл
        try {
            readOutNoteAndAddToArray();

            writer = new BufferedWriter(new FileWriter(NOTES_FILE));

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));


            while (true) {

                if (notes.size() == 0) {
                    clearScreen();
                    System.out.println(redColor + "\nДля того чтобы удалять заметки, необходимо сначала добавить их.\n" + resetColor);
                    break;
                }

                String input;

                if (notes.size() != 1) {
                    System.out.print("Введите номер заметки для удаления (для возвращения в Меню нажмите Enter):");
                    input = consoleReader.readLine();
                } else {
                    input = "1";
                }

                if (input.isEmpty())
                    break;


                int noteIndex;

                try {
                    noteIndex = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(redColor + "Ошибка: введите целое число" + resetColor);
                    continue;
                }

                if (noteIndex <= 0 || noteIndex > notes.size()) {
                    System.out.println(redColor + "Ошибка: заметки под таким номером не существует." + resetColor);
                    continue;
                }

                while (true) {
                    System.out.print(lavenderColor + "Внимание: Вы уверены, что хотите удалить заметку? Для подтверждения удаления \n" +
                            "нажмите Enter (это действие нельзя будет отменить). Чтобы отменить удаление,\nнажмите Пробел, а затем Enter" + resetColor);
                    input = consoleReader.readLine();


                    if (input.equals(" "))
                        break;


                    if (!input.isEmpty()) {
                        System.out.println(redColor + "Некорректный ввод" + resetColor);
                        continue;
                    } else {
                        clearScreen();
                    }

                    notes.remove(noteIndex - 1);
                    break;
                }

                break;
            }

            writeChangesToFile(writer);

        } catch (IOException e) {
            System.err.println("Ошибка записи файла заметок: " + e.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();

            } catch (IOException e) {
                System.err.println("Ошибка закрытия файла заметок: " + e.getMessage());
            }
        }
    }

    protected void addNote() {
        // Редактирование заметок и запись их в файл
        try {
            readOutNoteAndAddToArray();

            writer = new BufferedWriter(new FileWriter(NOTES_FILE));

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));


            System.out.print("Добавьте новую заметку (для возвращения в Меню нажмите Enter):");

            String input = consoleReader.readLine();

            if (!input.isEmpty()) {
                writer.write(input);
                writer.newLine();
            }

            writeChangesToFile(writer);

        } catch (IOException e) {
            System.err.println("Ошибка записи файла заметок: " + e.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();

            } catch (IOException e) {
                System.err.println("Ошибка закрытия файла заметок: " + e.getMessage());
            }
        }
    }

    protected void changeNote() {
        // Редактирование заметок и запись их в файл
        try {
            readOutNoteAndAddToArray();

            writer = new BufferedWriter(new FileWriter(NOTES_FILE));

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));


            while (true) {

                if (notes.size() == 0) {
                    clearScreen();
                    System.out.println(redColor + "\nДля того чтобы редактировать заметки, необходимо сначала добавить их.\n" + resetColor);
                    break;
                }

                String input;

                if (notes.size() != 1) {
                    System.out.print("Введите номер заметки для редактирования (для возвращения в Меню нажмите Enter):");
                    input = consoleReader.readLine();
                } else {
                    input = "1";
                }

                int noteIndex;

                if (input.isEmpty())
                    break;


                try {
                    noteIndex = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(redColor + "Ошибка: введите целое положительное число" + resetColor);
                    continue;
                }

                if (noteIndex <= 0 || noteIndex > notes.size()) {
                    System.out.println(redColor + "Ошибка: заметки под таким номером не существует." + resetColor);
                } else {
                    String oldNote = notes.get(noteIndex - 1);
                    System.out.print("Введите новый текст заметки или нажмите Enter, чтобы оставить без изменений:");
                    String newNote = consoleReader.readLine();

                    if (newNote.trim().isEmpty())// Если пользователь ничего не ввел, то заметка остается без изменений
                        newNote = oldNote;

                    notes.set(noteIndex - 1, newNote);
                    break;
                }
            }

            writeChangesToFile(writer);

        } catch (IOException e) {
            System.err.println("Ошибка записи файла заметок: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Ошибка закрытия файла заметок: " + e.getMessage());
            }
        }
    }


}
