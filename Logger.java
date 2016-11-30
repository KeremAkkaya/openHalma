import java.util.HashSet;

public class Logger {
    private static Logger instanceLogger = null;
    private HashSet<LOGGER_LEVEL> logger_levels;

    private Logger() {
        logger_levels = new HashSet<>();
        for (LOGGER_LEVEL l : LOGGER_LEVEL.values()) {
            logger_levels.add(l);
        }

    } //inaccessible from outside

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
}
