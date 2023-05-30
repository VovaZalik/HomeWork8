import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] products = {"Хлеб", "Соль", "Молоко"};
        int[] prices = {30, 15, 65};
        Basket basket = null;
        Scanner s = new Scanner(System.in);
        File basketFile = new File("basket.bin");

        if (basketFile.exists()) {
            basket = Basket.loadFromBinFile(basketFile);
        } else basket = new Basket(prices,products);

        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " " + "руб/шт;");
        }

        while (true) {
            menu();
            String input = s.nextLine();
            if ("end".equals(input)) break;
            String[] parts = input.split(" ");
            int productNum = Integer.parseInt(parts[0]) - 1;
            int amount = Integer.parseInt(parts[1]);
            basket.addToCart(productNum,amount);
            basket.saveBin(basketFile);
        }
        basket.printCart();
    }

    private static void menu() {
        System.out.println("Введите номер товара и его количество!");
        System.out.println();
        System.out.println("Чтобы зaвершить набор корзины - введите end");
    }
}
