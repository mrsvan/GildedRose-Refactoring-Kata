package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    @Test
    void updateQuality_givenAnItemName_thenSameItemName() {
        String itemName = "foo";
        Item[] items = new Item[]{new Item(itemName, 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items).allSatisfy(item -> assertThat(item).hasFieldOrPropertyWithValue("name", itemName));
    }

}
