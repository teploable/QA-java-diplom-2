package site.nomoreparties.stellarburgers.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private ArrayList<String> ingredients;

    public Order(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
