import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    List<Item> itemList = new ArrayList<>();

    @BeforeEach
    public void add_Restaurant_for_Testing() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    public List<String> get_item_names_list(List<Item> items){
        List<String> itemNames = new ArrayList<>();
        for(Item item : items){
            itemNames.add(item.getName());
        }
        return itemNames;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant.openingTime = LocalTime.now().minusMinutes(1);
        restaurant.closingTime = LocalTime.now().plusMinutes(1);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant.closingTime = LocalTime.now().minusMinutes(1);
        assertFalse(restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<DISPLAY_ITEMS>>>>>>>>>>>>>>>>>>>

    @Test
    public void should_compare_values_of_items_added_in_menu() {
        itemList = restaurant.getMenu();
        List<String> itemNames = get_item_names_list(itemList);
        int orderValue = restaurant.getOrderValue(itemNames);
        assertEquals(orderValue, 388);
    }

    @Test
    public void order_value_should_reduce_cumulative_total_when_an_item_removed() {
        itemList = restaurant.getMenu();
        List<String> itemNames =  get_item_names_list(itemList);
        int initialOrderValue = restaurant.getOrderValue(itemNames);
        int priceOfItemAtIndex1 = itemList.get(1).getPrice();
        itemList.remove(1);
        List<String> newItemNames = get_item_names_list(itemList);
        assertEquals(initialOrderValue - priceOfItemAtIndex1, restaurant.getOrderValue(newItemNames));
    }

    @Test
    public void should_pass_when_order_value_does_not_match() {
        itemList = restaurant.getMenu();
        List<String> itemNames = get_item_names_list(itemList);
        int orderValue = restaurant.getOrderValue(itemNames);
        assertNotEquals(orderValue, 400);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<DISPLAY_ITEMS>>>>>>>>>>>>>>>>>>>

}