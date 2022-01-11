package com.gildedrose;

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
            if (item.name.equals(Utils.AGED_BRIE) || item.name.equals(Utils.BACKSTAGE_PASSES)) {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;

                    if (item.name.equals(Utils.BACKSTAGE_PASSES)) {
                        if (item.sellIn < 11 && item.quality < 50) {
                            item.quality = item.quality + 1;
                        }

                        if (item.sellIn < 6 && item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            } else {
                if (item.quality > 0 && !item.name.equals(Utils.SULFURAS)) {
                    item.quality = item.quality - 1;
                }
            }

            if (!item.name.equals(Utils.SULFURAS)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (item.name.equals(Utils.AGED_BRIE)) {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                } else {
                    if (item.name.equals(Utils.BACKSTAGE_PASSES)) {
                        item.quality = 0;
                    } else {
                        if (item.quality > 0 && !item.name.equals(Utils.SULFURAS)) {
                            item.quality = item.quality - 1;
                        }
                    }
                }
            }
        }
    }
}
