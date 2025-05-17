package site.nomoreparties.stellarburgers.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.nomoreparties.stellarburgers.order.Ingredient;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsResponse {
    private boolean success;
    private ArrayList<Ingredient> data;
}
