package org.example.Controller;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private Controller controller;

    @BeforeEach
    public void setUp() {
        controller = new Controller();
    }

    @Test
    public void testInitTableRecords() {
        String[] table = controller.getTableScores();
        for (String score : table) {
            assertEquals("0", score);
        }
    }

    @Test
    public void testAddRecordInsertsCorrectly() {
        // Заполним таблицу начальными значениями
        String[] table = controller.getTableScores();
        for (int i = 0; i < 20; i++) {
            table[i] = String.valueOf(i * 10); // 0, 10, 20, ..., 190
        }

        // Добавим новый рекорд 150
        controller.addRecord(150);
        String[] updated = controller.getTableScores();

        // Проверим, что он теперь выше значений < 150
        assertTrue(Integer.parseInt(updated[0]) >= 150);
    }

    @Test
    public void testPauseToggle() {
        boolean before = controller.isPaused();
        controller.pause();
        assertNotEquals(before, controller.isPaused());

        boolean after = controller.isPaused();
        controller.pause();
        assertNotEquals(after, controller.isPaused());
    }

    @Test
    public void testReplayResetsState() throws InterruptedException {
        controller.replay();
        assertEquals(0, controller.getScores());
        assertTrue(controller.isRunning());
    }

    @Test
    public void testStopEndsGame() {
        controller.stop();
        assertFalse(controller.isRunning());
        assertEquals(0, controller.getScores());
    }

    @Test
    public void testResetScoresClearsAndAdds() {
        controller.resetScores();
        assertEquals(0, controller.getScores());

        String[] table = controller.getTableScores();
        boolean foundZero = false;
        for (String s : table) {
            if (s.equals("0")) {
                foundZero = true;
                break;
            }
        }
        assertTrue(foundZero);
    }

    @Test
    public void testPlayRunsAndStops() {
        Thread playThread = new Thread(() -> {
            try {
                controller.play();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        playThread.start();

        try {
            Thread.sleep(1000); // дать немного времени поработать
            controller.stop();  // завершить игру вручную
            Thread.sleep(500);  // дождаться завершения потока
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertFalse(controller.isRunning());
    }
}
