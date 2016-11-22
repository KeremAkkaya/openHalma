
public class Logger {
    private static Logger instanceLogger = null;

    private Logger() {
    } //inaccessible

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
        System.out.println("[" + logger_level.toString() + "]: " + data);
    }
}
