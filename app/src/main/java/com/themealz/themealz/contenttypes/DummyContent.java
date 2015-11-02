package com.themealz.themealz.contenttypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }

        addItem(new DummyItem("1", "Moe's Tavern", "קצת מידע נוסף על Moe's Tavern"));
        addItem(new DummyItem("2", "המסעדה הגדולה", "קצת מידע נוסף על המסעדה הגדולה"));
        addItem(new DummyItem("3", "Café 80's", "קצת מידע נוסף על Café 80's"));
        addItem(new DummyItem("4", "Ten Forward", "קצת מידע נוסף על Ten Forward"));
        addItem(new DummyItem("5", "Monk's Café", "קצת מידע נוסף על Monk's Café"));
        addItem(new DummyItem("6", "אימפריית השמש", "קצת מידע נוסף על אימפריית השמש"));
        addItem(new DummyItem("7", "Central Perk", "קצת מידע נוסף על Central Perk"));
        addItem(new DummyItem("8", "Cheers", "קצת מידע נוסף על Cheers"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;
        public String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
