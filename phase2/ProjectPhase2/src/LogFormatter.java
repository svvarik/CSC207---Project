import java.util.logging.*;

/** The custom Formatter that is used in HistoryManager.java to log the renaming in history.txt */
public class LogFormatter extends Formatter {
    /**
     * Custom format used by HistoryManager.java, only includes the log message
     * @param record The logging record that is to be formatted
     * @return Returns a String with the log message followed by a new line.
     */
    @Override
    public String format(LogRecord record) {
        return record.getMessage() + "\n";
    }
}
