package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.storage.Storage;
import seedu.address.ui.DisplaySystemType;

/**
 * Loads a pet tracker from a file.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the given backup file.\n"
            + "Parameters: FILE_NAME (must be a valid file name)\n"
            + "Example: " + COMMAND_WORD + " 20200402_21_54_52";
    public static final String MESSAGE_SUCCESS = "Pet tracker loaded from %s.";
    public static final String MESSAGE_FILE_NOT_FOUND = "Data file not found";
    public static final String MESSAGE_WRONG_FORMAT = "Data file not in the correct format";
    public static final String MESSAGE_FILE_OPS_ERROR = "Problem while reading from the file";
    private final Path filePath;
    private final Storage storage;

    /**
     * Creates a LoadCommand to load the specified {@code Path}
     */
    public LoadCommand(Storage storage, Path filePath) {
        this.storage = storage;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<ReadOnlyPetTracker> petTrackerOptional;
        try {
            petTrackerOptional = storage.readPetTracker(filePath);
            if (!petTrackerOptional.isPresent()) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
            model.setPetTracker(petTrackerOptional.get());
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_WRONG_FORMAT);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_OPS_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath.getFileName()), false, false, DisplaySystemType.PETS, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoadCommand // instanceof handles nulls
                && filePath.equals(((LoadCommand) other).filePath)); // state check
    }
}
