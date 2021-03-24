package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED = "Conjured Mana Cake";
    private static final String DEXTERITY_VEST = "+5 Dexterity Vest";
    private static final String ELIXIR_OF_THE_MONGOOSE = "Elixir of the Mongoose";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final int LEGENDARY_VALUE = 80;

    @ParameterizedTest(name = "Item name = {0}")
    @ValueSource(strings = {AGED_BRIE, BACKSTAGE_PASSES, CONJURED, DEXTERITY_VEST, ELIXIR_OF_THE_MONGOOSE})
    void theQualityOfAnItemIsNeverNegative(String item) {
        range(-5, 5).forEach(sellIn -> {
            GildedRose testInstance = new GildedRose(new Item[]{new Item(item, sellIn, -10)});

            testInstance.updateQuality();

            assertThat(testInstance.items[0]).hasFieldOrPropertyWithValue("quality", 0);
        });
    }

    @ParameterizedTest(name = "sellIn = {0}")
    @ValueSource(ints = {-5, -4, -3, -2, -1, 0})
    void updateQuality_givenAgedBrieThatShouldAlreadyBeSold_thenQualityPlus2(int sellIn) {
        GildedRose testInstance = new GildedRose(new Item[]{new Item(AGED_BRIE, sellIn, 1)});

        testInstance.updateQuality();

        assertThat(testInstance.items[0]).usingRecursiveComparison().isEqualTo(new Item(AGED_BRIE, sellIn - 1, 3));
    }

    @Test
    void updateQuality_givenAnItemName_thenSameItemName() {
        String itemName = "foo";
        Item[] items = new Item[]{new Item(itemName, 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items).allSatisfy(item -> assertThat(item).hasFieldOrPropertyWithValue("name", itemName));
    }

    @ParameterizedTest(name = "sellIn = {0}")
    @ValueSource(ints = {-5, -4, -3, -2, -1, 0})
    void updateQuality_givenAnItemThatShouldAlreadyBeSold_thenQualityMinus2(int sellIn) {
        GildedRose testInstance = new GildedRose(new Item[]{new Item(DEXTERITY_VEST, sellIn, 4)});

        testInstance.updateQuality();

        assertThat(testInstance.items[0]).usingRecursiveComparison().isEqualTo(new Item(DEXTERITY_VEST, sellIn - 1, 2));
    }

    @ParameterizedTest(name = "sellIn = {0}")
    @ValueSource(ints = {6, 7, 8, 9, 10})
    void updateQuality_givenBackstagePasses10DaysOrLess_thenQualityPlus3(int sellIn) {
        GildedRose testInstance = new GildedRose(new Item[]{new Item(BACKSTAGE_PASSES, sellIn, 1)});

        testInstance.updateQuality();

        assertThat(testInstance.items[0]).usingRecursiveComparison()
                .isEqualTo(new Item(BACKSTAGE_PASSES, sellIn - 1, 3));
    }

    @ParameterizedTest(name = "sellIn = {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void updateQuality_givenBackstagePasses5DaysOrLess_thenQualityPlus2(int sellIn) {
        GildedRose testInstance = new GildedRose(new Item[]{new Item(BACKSTAGE_PASSES, sellIn, 1)});

        testInstance.updateQuality();

        assertThat(testInstance.items[0]).usingRecursiveComparison()
                .isEqualTo(new Item(BACKSTAGE_PASSES, sellIn - 1, 4));
    }

    @ParameterizedTest(name = "sellIn = {0}")
    @ValueSource(ints = {-1, 0})
    void updateQuality_givenBackstagePassesConcertFinished_thenQuality0(int sellIn) {
        GildedRose testInstance = new GildedRose(new Item[]{new Item(BACKSTAGE_PASSES, sellIn, 1)});

        testInstance.updateQuality();

        assertThat(testInstance.items[0]).usingRecursiveComparison()
                .isEqualTo(new Item(BACKSTAGE_PASSES, sellIn - 1, 0));
    }

    @ParameterizedTest(name = "sellIn = {0}")
    @ValueSource(ints = {-2, -1, 0, 1, 2})
    void updateQuality_givenLegendarySulfuras_thenSellInDoesNotChangeAndQuality80(int sellIn) {
        GildedRose testInstance = new GildedRose(new Item[]{new Item(SULFURAS, sellIn, new Random().nextInt())});

        testInstance.updateQuality();

        assertThat(testInstance.items[0]).hasFieldOrPropertyWithValue("quality", LEGENDARY_VALUE)
                .hasFieldOrPropertyWithValue("sellIn", sellIn);
    }

    @Test
    void updateQuality_givenMultipleCalls_thenChangesSellInAndQuality() {
        GildedRose
                app =
                new GildedRose(new Item[]{new Item(DEXTERITY_VEST, 10, 20),
                        new Item(AGED_BRIE, 2, 0),
                        new Item(ELIXIR_OF_THE_MONGOOSE, 5, 7),
                        new Item(SULFURAS, 0, LEGENDARY_VALUE),
                        new Item(SULFURAS, -1, LEGENDARY_VALUE),
                        new Item(BACKSTAGE_PASSES, 15, 20),
                        new Item(BACKSTAGE_PASSES, 10, 49),
                        new Item(BACKSTAGE_PASSES, 5, 49),
                        // this conjured item does not work properly yet
                        new Item(CONJURED, 3, 6)});

        app.updateQuality();

        assertThat(app.items).usingRecursiveComparison()
                .isEqualTo(new Item[]{new Item(DEXTERITY_VEST, 9, 19),
                        new Item(AGED_BRIE, 1, 1),
                        new Item(ELIXIR_OF_THE_MONGOOSE, 4, 6),
                        new Item(SULFURAS, 0, LEGENDARY_VALUE),
                        new Item(SULFURAS, -1, LEGENDARY_VALUE),
                        new Item(BACKSTAGE_PASSES, 14, 21),
                        new Item(BACKSTAGE_PASSES, 9, 50),
                        new Item(BACKSTAGE_PASSES, 4, 50),
                        // this conjured item does not work properly yet
                        new Item(CONJURED, 2, 5)});

        app.updateQuality();

        assertThat(app.items).usingRecursiveComparison()
                .isEqualTo(new Item[]{new Item(DEXTERITY_VEST, 8, 18),
                        new Item(AGED_BRIE, 0, 2),
                        new Item(ELIXIR_OF_THE_MONGOOSE, 3, 5),
                        new Item(SULFURAS, 0, LEGENDARY_VALUE),
                        new Item(SULFURAS, -1, LEGENDARY_VALUE),
                        new Item(BACKSTAGE_PASSES, 13, 22),
                        new Item(BACKSTAGE_PASSES, 8, 50),
                        new Item(BACKSTAGE_PASSES, 3, 50),
                        // this conjured item does not work properly yet
                        new Item(CONJURED, 1, 4)});
    }

}
