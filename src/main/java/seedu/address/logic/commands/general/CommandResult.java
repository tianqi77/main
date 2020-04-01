package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.ui.DisplaySystemType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Whether or not help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * Whether or not the application should exit.
     */
    private final boolean exit;

    private final boolean showStats;

    /**
     * The system to be displayed.
     */
    private final DisplaySystemType type;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         DisplaySystemType type, boolean showStats) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.type = type;
        this.showStats = showStats;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * while setting {@code changeDisplay} to its default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, DisplaySystemType.NO_CHANGE, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, DisplaySystemType.NO_CHANGE, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public DisplaySystemType getDisplaySystemType() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showStats == otherCommandResult.showStats
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && type == otherCommandResult.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, type, showStats);
    }

}
