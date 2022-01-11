package com.gildedrose;

import static com.gildedrose.Utils.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    /*
     /!\ Do not change code above this line /!\
     */

    public void updateQuality() {
        for (Item item : items) {
            switch (item.name) {
                case AGED_BRIE, BACKSTAGE_PASSES -> item.quality = incrementQuality(item);
                default -> item.quality = decrementQuality(item);
            }

            if (!item.name.equals(SULFURAS)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (item.name.equals(AGED_BRIE)) {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                } else {
                    if (item.name.equals(BACKSTAGE_PASSES)) {
                        item.quality = 0;
                    } else {
                        if (item.quality > 0 && !item.name.equals(SULFURAS)) {
                            item.quality = item.quality - 1;
                        }
                    }
                }
            }
        }
    }

    static int decrementQuality(Item item) {
        return max(0, item.name.equals(SULFURAS)? item.quality : item.quality -1);
    }

    static int incrementQuality(Item item) {
        int quality = item.quality;

        quality += 1;

        if (item.name.equals(BACKSTAGE_PASSES)) {
            if (item.sellIn < 11) quality += 1;
            if (item.sellIn < 6) quality += 1;
        }

        return min(quality, 50);
    }
}
