package com.example.sqlitepractice;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests d'instrumentation pour tester et valider les opérations SQLite
 * (Conformément au tutoriel Android Developers "Tester et déboguer votre base de données")
 */
@RunWith(AndroidJUnit4.class)
public class FeedReaderDbTest {

    private FeedReaderDbHelper dbHelper;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new FeedReaderDbHelper(context);
    }

    @After
    public void tearDown() {
        dbHelper.close();
    }

    @Test
    public void testInsertAndReadEntry() {
        String testTitle = "TestTitle_" + System.currentTimeMillis();
        String testSubtitle = "TestSubtitle_Description";

        // Insertion
        long rowId = dbHelper.insertEntry(testTitle, testSubtitle);
        assertTrue("L'identifiant retourné doit être supérieur à 0", rowId > 0);

        // Lecture
        List<String> entries = dbHelper.getAllEntries();
        boolean found = false;
        for (String entry : entries) {
            if (entry.contains(testTitle) && entry.contains(testSubtitle)) {
                found = true;
                break;
            }
        }
        assertTrue("L'enregistrement inséré doit être présent dans la base de données", found);
    }

    @Test
    public void testDeleteEntry() {
        String uniqueTitle = "DeleteMe_" + System.currentTimeMillis();
        dbHelper.insertEntry(uniqueTitle, "SubToDelete");

        int deletedCount = dbHelper.deleteEntry(uniqueTitle);
        assertTrue("Au moins un enregistrement doit avoir été supprimé", deletedCount > 0);

        List<String> entries = dbHelper.getAllEntries();
        for (String entry : entries) {
            assertFalse("L'enregistrement supprimé ne doit plus exister", entry.contains(uniqueTitle));
        }
    }

    @Test
    public void testUpdateEntry() {
        String originalTitle = "OldTitle_" + System.currentTimeMillis();
        String updatedTitle = "NewTitle_" + System.currentTimeMillis();
        String updatedSubtitle = "NewSubtitle";

        dbHelper.insertEntry(originalTitle, "OldSubtitle");

        int updatedCount = dbHelper.updateEntry(originalTitle, updatedTitle, updatedSubtitle);
        assertTrue("La mise à jour doit toucher au moins un enregistrement", updatedCount > 0);

        List<String> entries = dbHelper.getAllEntries();
        boolean foundUpdated = false;
        for (String entry : entries) {
            if (entry.contains(updatedTitle) && entry.contains(updatedSubtitle)) {
                foundUpdated = true;
                break;
            }
        }
        assertTrue("Le nouvel enregistrement mis à jour doit être retrouvé", foundUpdated);
    }
}
