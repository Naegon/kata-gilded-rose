package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.gildedrose.GildedRose.*;
import static com.gildedrose.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    public void simpleItem() {
        Item foo = new Item("foo", 20, 10);
        Item[] items = new Item[] { foo };
        GildedRose gildedRose = new GildedRose(items);

        gildedRose.updateQuality();

        assertEquals(19, foo.sellIn);
        assertEquals(9, foo.quality);
    }

    @Test
    public void decrementQuality_Test() {
        // Given
        Item mongooseElixir = new Item(MONGOOSE_ELIXIR, 5, 25);

        // When
        mongooseElixir.quality = decrementQuality(mongooseElixir);

        // Then
        assertEquals(24, mongooseElixir.quality);
    }

    @Test
    public void decrementQuality_TestWithSulfuras() {
        // Given
        Item sulfuras = new Item(SULFURAS, 5, 25);

        // When
        sulfuras.quality = decrementQuality(sulfuras);

        // Then
        assertEquals(25, sulfuras.quality);
    }

    @Test
    public void decrementQuality_TestWithNegativeQuality() {
        // Given
        Item mongooseElixir = new Item(MONGOOSE_ELIXIR, 5, 0);

        // When
        mongooseElixir.quality = decrementQuality(mongooseElixir);

        // Then
        assertEquals(0, mongooseElixir.quality);
    }

    @Test
    public void incrementQuality_Test() {
        // Given
        Item agedBrie = new Item(AGED_BRIE, 5, 25);

        // When
        agedBrie.quality = incrementQuality(agedBrie);

        // Then
        assertEquals(26, agedBrie.quality);
    }

    @Test
    public void incrementQuality_TestWithMaxedQuality() {
        // Given
        Item agedBrie = new Item(AGED_BRIE, 5, 50);

        // When
        agedBrie.quality = incrementQuality(agedBrie);

        // Then
        assertEquals(50, agedBrie.quality);
    }

    @Test
    public void incrementQuality_TestWithDistantBackstagePasses() {
        // Given
        Item backstagePasses = new Item(BACKSTAGE_PASSES, 12, 25);

        // When
        backstagePasses.quality = incrementQuality(backstagePasses);

        // Then
        assertEquals(26, backstagePasses.quality);
    }

    @Test
    public void incrementQuality_TestWithMediumBackstagePasses() {
        // Given
        Item backstagePasses = new Item(BACKSTAGE_PASSES, 8, 25);

        // When
        backstagePasses.quality = incrementQuality(backstagePasses);

        // Then
        assertEquals(27, backstagePasses.quality);
    }

    @Test
    public void incrementQuality_TestWithImminentBackstagePasses() {
        // Given
        Item backstagePasses = new Item(BACKSTAGE_PASSES, 2, 25);

        // When
        backstagePasses.quality = incrementQuality(backstagePasses);

        // Then
        assertEquals(28, backstagePasses.quality);
    }

    @Test
    public void goldenMaster() throws IOException {
        Item[] items = new Item[] {
            new Item(DEXTERITY_VEST, 10, 20), //
            new Item(AGED_BRIE, 2, 0), //
            new Item(MONGOOSE_ELIXIR, 5, 7), //
            new Item(SULFURAS, 0, 80), //
            new Item(SULFURAS, -1, 80),
            new Item(BACKSTAGE_PASSES, 15, 20),
            new Item(BACKSTAGE_PASSES, 10, 49),
            new Item(BACKSTAGE_PASSES, 5, 49),
            // this conjured item does not work properly yet
            new Item(CONJURED_MANA_CAKE, 3, 6) };

        GildedRose app = new GildedRose(items);

        int days = 30;
        List<String> actualLog = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            actualLog.add("-------- day " + i + " --------");
            actualLog.add("name, sellIn, quality");
            for (Item item : items) {
                actualLog.add(item.toString());
            }
            app.updateQuality();
        }

        List<String> expectedLog = new ArrayList<>();
        String workingDir = System.getProperty("user.dir");
        String expectedPath = workingDir + "/../golden-master/expected-output.txt";
        File expectedFile = new File(expectedPath);
        FileReader reader = new FileReader(expectedFile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if(! line.isEmpty()) {
                expectedLog.add(line);
            }
        }
        assertEquals(expectedLog, actualLog);
    }


}
