package com.gildedrose;

import java.util.Objects;
import java.util.function.Consumer;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

class GildedRose {

    private static final int LEGENDARY_QUALITY = 80;
    private static final int MAX_NON_LEGENDARY_QUALITY = 50;
    private static final int MIN_QUALITY = 0;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = requireNonNull(items);
    }

    public void updateQuality() {
        stream(items).forEach(item -> typeOf(item).process(item));
    }

    private ItemType typeOf(Item item) {
        return stream(ItemType.values()).filter(type -> Objects.equals(item.name, type.name))
                .findFirst()
                .orElse(ItemType.OTHER);
    }

    private enum ItemType {
        AGED_BRIE("Aged Brie", false, item -> item.quality += qualityIncrementFor(item)),
        BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert", false, item -> {
            if (item.sellIn < 0) {
                item.quality = 0;
            } else if (item.sellIn < 5) {
                item.quality += 3;
            } else if (item.sellIn < 10) {
                item.quality += 2;
            } else {
                item.quality++;
            }
        }),
        CONJURED("Conjured Mana Cake", false, item -> item.quality -= qualityIncrementFor(item) * 2),
        SULFURAS("Sulfuras, Hand of Ragnaros", true, item -> item.quality = LEGENDARY_QUALITY),
        OTHER(null, false, item -> item.quality -= qualityIncrementFor(item));

        private final String name;
        private final Consumer<Item> qualityUpdater;
        private final int sellInIncrement;
        private final int maxQuality;

        ItemType(String name, boolean isLegendary, Consumer<Item> qualityUpdater) {
            this.name = name;
            this.qualityUpdater = qualityUpdater;
            sellInIncrement = isLegendary ? 0 : 1;
            maxQuality = isLegendary ? LEGENDARY_QUALITY : MAX_NON_LEGENDARY_QUALITY;
        }

        private static int qualityIncrementFor(Item item) {
            return item.sellIn < 0 ? 2 : 1;
        }

        void process(Item item) {
            item.sellIn -= sellInIncrement;
            qualityUpdater.accept(item);
            item.quality = min(max(item.quality, MIN_QUALITY), maxQuality);
        }
    }
}