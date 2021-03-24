package com.gildedrose;

import static java.util.Objects.requireNonNull;

class GildedRose {
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = requireNonNull(items);
    }

    public void updateQuality() {
        for (Item item : items) {
            if (AGED_BRIE.equals(item.name) || BACKSTAGE_PASSES.equals(item.name)) {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;

                    if (BACKSTAGE_PASSES.equals(item.name)) {
                        if (item.sellIn < 11 && item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                        if (item.sellIn < 6 && item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            } else {
                if (item.quality > 0 && !SULFURAS.equals(item.name)) {
                    item.quality = item.quality - 1;
                }
            }

            if (!SULFURAS.equals(item.name)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (AGED_BRIE.equals(item.name)) {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                } else {
                    if (BACKSTAGE_PASSES.equals(item.name)) {
                        item.quality = 0;
                    } else {
                        if (item.quality > 0 && !SULFURAS.equals(item.name)) {
                            item.quality = item.quality - 1;
                        }
                    }
                }
            }
        }
    }
}