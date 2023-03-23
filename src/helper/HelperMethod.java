package helper;

public class HelperMethod {
    public static void clearScreen() {
        //Очистка консоли
       /* System.out.print("\033[H\033[2J");
        System.out.flush();*/
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Ошибка при очистке консоли: " + e);
        }
    }
}
