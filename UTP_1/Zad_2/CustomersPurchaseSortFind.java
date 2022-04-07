/**
 *
 *  @author Gęślicka Anna S21151
 *
 */

package zad2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class CustomersPurchaseSortFind {

    private ArrayList<Purchase> basket = new ArrayList<>();

    public void readFile(String file){
        try {
            Files.readAllLines(Paths.get(file)).forEach(line -> {
                String[] currEl = line.split(";");
                Purchase purchase = new Purchase(line);
                purchase.setId(currEl[0]);
                String[] fullName = currEl[1].split("//s");
                purchase.setName(fullName[0]);
                purchase.setSurname(fullName[0]);
                purchase.setPurchase(currEl[2]);
                purchase.setQuantity(Double.parseDouble(currEl[3]));
                purchase.setPrice(Double.parseDouble(currEl[4]));
                this.basket.add(purchase);
            });
        } catch (IOException ex) {
            System.err.println("ups!");
        }
    }

    public void showSortedBy(String sorter) {
        System.out.println(sorter);

        if (sorter.equals("Nazwiska")) {
            this.basket.stream()
                    .sorted(Comparator.comparing(Purchase::getSurname))
                    .forEach(purchase -> System.out.println(purchase.getCustomerData()));
        } else if (sorter.equals("Koszty")) {
            this. basket.stream()
                    .sorted(Comparator.comparingDouble(pur -> (pur.getQuantity() * pur.getPrice() * -1)))
                    .forEach(purchase -> {
                        double price = purchase.getPrice() * purchase.getQuantity();
                        System.out.println(purchase.getCustomerData() + " (koszt: " + price + ")");
                    });
        } else {
            this.basket
                    .forEach(purchase -> System.out.println(purchase.getCustomerData()));
        }
        System.out.println();
    }

    public void showPurchaseFor(String id) {
        System.out.println("Klient " + id);
        basket.stream()
                .filter(purchase -> purchase.getId().equals(id))
                .forEach(purchase -> System.out.println(purchase.getCustomerData()));
        System.out.println();
    }
}
