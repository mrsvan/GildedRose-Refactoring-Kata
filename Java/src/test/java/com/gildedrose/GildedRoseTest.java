package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    private static final String DEXTERITY_VEST = "+5 Dexterity Vest";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String ELIXIR_OF_THE_MONGOOSE = "Elixir of the Mongoose";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED = "Conjured Mana Cake";

    @Test
    void updateQuality_givenMultipleCalls_thenChangesSellInAndQuality() {
        GildedRose app = new GildedRose(new Item[]{new Item(DEXTERITY_VEST, 10, 20), //
                new Item(AGED_BRIE, 2, 0), //
                new Item(ELIXIR_OF_THE_MONGOOSE, 5, 7), //
                new Item(SULFURAS, 0, 80), //
                new Item(SULFURAS, -1, 80),
                new Item(BACKSTAGE_PASSES, 15, 20),
                new Item(BACKSTAGE_PASSES, 10, 49),
                new Item(BACKSTAGE_PASSES, 5, 49),
                // this conjured item does not work properly yet
                new Item(CONJURED, 3, 6)});

        app.updateQuality();

        assertThat(app.items)
                .usingRecursiveComparison()
                .isEqualTo(new Item[]{new Item(DEXTERITY_VEST, 9, 19), //
                        new Item(AGED_BRIE, 1, 1), //
                        new Item(ELIXIR_OF_THE_MONGOOSE, 4, 6), //
                        new Item(SULFURAS, 0, 80), //
                        new Item(SULFURAS, -1, 80),
                        new Item(BACKSTAGE_PASSES, 14, 21),
                        new Item(BACKSTAGE_PASSES, 9, 50),
                        new Item(BACKSTAGE_PASSES, 4, 50),
                        // this conjured item does not work properly yet
                        new Item(CONJURED, 2, 5)});

        app.updateQuality();

        assertThat(app.items)
                .usingRecursiveComparison()
                .isEqualTo(new Item[]{new Item(DEXTERITY_VEST, 8, 18), //
                        new Item(AGED_BRIE, 0, 2), //
                        new Item(ELIXIR_OF_THE_MONGOOSE, 3, 5), //
                        new Item(SULFURAS, 0, 80), //
                        new Item(SULFURAS, -1, 80),
                        new Item(BACKSTAGE_PASSES, 13, 22),
                        new Item(BACKSTAGE_PASSES, 8, 50),
                        new Item(BACKSTAGE_PASSES, 3, 50),
                        // this conjured item does not work properly yet
                        new Item(CONJURED, 1, 4)});
    }

    @Test
    void updateQuality_givenAnItemName_thenSameItemName() {
        String itemName = "foo";
        Item[] items = new Item[]{new Item(itemName, 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items).allSatisfy(item -> assertThat(item).hasFieldOrPropertyWithValue("name", itemName));
    }

}
