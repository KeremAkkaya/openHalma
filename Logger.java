import java.util.Collections;
import java.util.HashSet;

public class Logger {
    private static Logger instanceLogger = null;
    private HashSet<LOGGER_LEVEL> logger_levels;
    private boolean timestamp = true;

    private Logger() {
        logger_levels = new HashSet<>();
        Collections.addAll(logger_levels, LOGGER_LEVEL.values());
        logger_levels.remove(LOGGER_LEVEL.AI_DEBUG);
        logger_levels.remove(LOGGER_LEVEL.GRAPHICAL_DEBUG);
    }

    private static Logger getInstance() {
        if (instanceLogger == null) {
            instanceLogger = new Logger();
        }
        return instanceLogger;
    }

    public static void log(LOGGER_LEVEL logger_level, String data) {
        getInstance()._log(logger_level, data);
    }

    private void _log(LOGGER_LEVEL logger_level, String data) {
        if (logger_levels.contains(logger_level)) {
            System.out.println("[" + logger_level.toString() + "]: " + data);
        }
    }

    public static void set_loglevel(HashSet<LOGGER_LEVEL> levels) {
        getInstance()._set_loglevel(levels);
    }

    private void _set_loglevel(HashSet<LOGGER_LEVEL> levels) {
        logger_levels = levels;
    }

    public static void set_timestamp(boolean t) {
        getInstance()._set_timestamp(t);
    }

    private void _set_timestamp(boolean t) {
        this.timestamp = t;
    }
}
