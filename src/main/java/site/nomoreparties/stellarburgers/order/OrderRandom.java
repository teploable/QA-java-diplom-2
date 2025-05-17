package site.nomoreparties.stellarburgers.order;

import site.nomoreparties.stellarburgers.service.IngredientsResponse;

import java.util.ArrayList;
import java.util.Random;

public class OrderRandom {
    private final ArrayList<String> order = new ArrayList<>();
    private final Random random = new Random();

    public ArrayList<String> createRandomOrder(IngredientsResponse ingredients) {
        int ingredientsNumber = 10;
        int n = 1 + random.nextInt(ingredientsNumber - 1);
        for (int i = 0; i < n; i++) {
            int ingIndex = random.nextInt(ingredientsNumber);
            order.add(ingredients.getData().get(ingIndex).get_id());
        }
        return order;
    }
}
