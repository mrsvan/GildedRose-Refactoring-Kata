package com.gildedrose;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED = "Conjured Mana Cake";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private static final int LEGENDARY_QUALITY = 80;
    private static final int MAX_NON_LEGENDARY_QUALITY = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = requireNonNull(items);
    }

    public void updateQuality() {
        for (Item item : items) {
            int defaultQualityDecrementor = CONJURED.equals(item.name) ? 2 : 1;

            if (AGED_BRIE.equals(item.name) || BACKSTAGE_PASSES.equals(item.name)) {
                if (item.quality < MAX_NON_LEGENDARY_QUALITY) {
                    item.quality = item.quality + 1;

                    if (BACKSTAGE_PASSES.equals(item.name)) {
                        if (item.sellIn < 11 && item.quality < MAX_NON_LEGENDARY_QUALITY) {
                            item.quality = item.quality + 1;
                        }
                        if (item.sellIn < 6 && item.quality < MAX_NON_LEGENDARY_QUALITY) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            } else {
                if (item.quality > 0 && !SULFURAS.equals(item.name)) {
                    item.quality = item.quality - defaultQualityDecrementor;
                }
            }

            if (!SULFURAS.equals(item.name)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (AGED_BRIE.equals(item.name)) {
                    if (item.quality < MAX_NON_LEGENDARY_QUALITY) {
                        item.quality = item.quality + 1;
                    }
                } else {
                    if (BACKSTAGE_PASSES.equals(item.name)) {
                        item.quality = 0;
                    } else {
                        if (item.quality > 0 && !SULFURAS.equals(item.name)) {
                            item.quality = item.quality - defaultQualityDecrementor;
                        }
                    }
                }
            }

            if (SULFURAS.equals(item.name)) {
                item.quality = LEGENDARY_QUALITY;
            } else {
                item.quality = min(max(item.quality, 0), MAX_NON_LEGENDARY_QUALITY);
            }
        }
    }
}