import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable{
    protected static final long serialVersionUID = 1L;
    protected int[] prices;
    protected String[] products;
    protected int[] counts;
    public Basket () {

    }
    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.counts = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        counts[productNum] += amount;
    }
    public void printCart() {
        int sum = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println(
                        products[i] + " " +
                                prices[i] + " руб за шт; " + " " + "Вы добавили в корзину: " +
                                counts[i] + " шт; " + "В сумме выходит: " +
                                (counts[i] * prices[i]));
                sum += (counts[i] * prices[i]);
            }
        }
        System.out.println("Итого : " + sum + " руб");
    }
    public void saveTxt(File textFile) throws FileNotFoundException {
        try(PrintWriter printWriter = new PrintWriter(textFile)){
            for (int price : prices) {
                System.out.print(price + " ");
            }
            for (String product : products) {
                System.out.print(product + " ");
            }
            for (int count : counts) {
                System.out.print(count + " ");
            }
        }
    }
    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productsStr = bufferedReader.readLine();
            String pricesStr = bufferedReader.readLine();
            String countsStr = bufferedReader.readLine();

            basket.products = productsStr.split(" ");
            basket.prices = Arrays.stream(pricesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            basket.counts = Arrays.stream(countsStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
    public void saveBin(File file) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
