import mocking.MockScoreDB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tdd.Hangman;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHangman {
    public static Hangman hangman;
    public static Random random;
    int requestedLength;

    @BeforeAll
    static void setupClass() {
        random = new Random();
        //hangman = new Hangman();
        hangman.loadWords();
    }

    @BeforeEach
    public void setupTest() {
        requestedLength = random.nextInt(6) + 5;
    }
    @Test
    void test_AlphabetCountInAWord() {
        String word = "pizza";
        char alphabet = 'a';
        int count = hangman.countAlphabetInAWord(word, alphabet);

        assertEquals(1, count);
    }

    @Test
    void test_CountRandomFetchedWord() throws FileNotFoundException {
        //From 5-10
        Random random = new Random();
        int expectedLength = random.nextInt(6) + 5;
        String randomWord = hangman.randomFetchedWord(expectedLength);

        assertEquals(expectedLength, randomWord.length());
    }

    @Test
    void test_uniquenessOfFetchedWord() throws FileNotFoundException {
        Set<String> usedWordsSet = new HashSet<>();
        int round = 0;
        String word = null;
        while (round < 100) {
            requestedLength = random.nextInt(6) + 5;
            word = hangman.randomFetchedWord(requestedLength);
            round++;
            assertTrue(usedWordsSet.add(word));
        }
    }

    @Test
    void test_saveScoreUsingMockDB() {
        MockScoreDB mockScoreDB = mock(MockScoreDB.class);
        Hangman hangman1 = new Hangman(mockScoreDB);

        when(mockScoreDB.writeScoreDB("apple", 10)).thenReturn(true);

        assertTrue(hangman1.saveWordScore("apple",10));
    }
}


/**
 *
 * Steps of TDD in Agile
 * Write a failing test: In TDD, developers start by writing a test case that fails. This test case should be based on the requirements provided by the customer.
 * Write code: The next step is to write the code to make the test pass. This code should be minimal and focused **only on passing the test**.
 * Refactor: Once the test has passed, developers can refactor the code to improve its design and remove duplication.
 * Repeat: Finally, developers should repeat the process by writing a new failing test and following the same steps until all requirements have been met.
 *
 *
 * */