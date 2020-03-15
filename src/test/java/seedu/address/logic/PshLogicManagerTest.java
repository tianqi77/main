package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.PshMessages.MESSAGE_INVALID_PET_DISPLAYED_INDEX;
import static seedu.address.commons.core.PshMessages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.PshCommandTestUtil.DOB_DESC_COCO;
import static seedu.address.logic.commands.PshCommandTestUtil.GENDER_DESC_COCO;
import static seedu.address.logic.commands.PshCommandTestUtil.NAME_DESC_COCO;
import static seedu.address.logic.commands.PshCommandTestUtil.SPECIES_DESC_COCO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.COCO;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.pet.AddPetCommand;
import seedu.address.logic.commands.general.CommandResult;
import seedu.address.logic.commands.pet.ListCommand;
import seedu.address.logic.commands.general.exceptions.CommandException;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.PshModel;
import seedu.address.model.PshModelManager;
import seedu.address.model.ReadOnlyPetTracker;
import seedu.address.model.PshUserPrefs;
import seedu.address.model.pet.Pet;
import seedu.address.storage.JsonPetTrackerStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.PshStorageManager;
import seedu.address.testutil.PetBuilder;

public class PshLogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private PshModel model = new PshModelManager();
    private PshLogic logic;

    @BeforeEach
    public void setUp() {
        JsonPetTrackerStorage petTrackerStorage =
                new JsonPetTrackerStorage(temporaryFolder.resolve("petTracker.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        PshStorageManager storage = new PshStorageManager(petTrackerStorage, userPrefsStorage);
        logic = new PshLogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonPetTrackerStorage petTrackerStorage =
                new JsonPetTrackerIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionPetTracker.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        PshStorageManager storage = new PshStorageManager(petTrackerStorage, userPrefsStorage);
        logic = new PshLogicManager(model, storage);

        // Execute add command
        String addCommand = AddPetCommand.COMMAND_WORD + NAME_DESC_COCO + DOB_DESC_COCO + GENDER_DESC_COCO
                + SPECIES_DESC_COCO;
        Pet expectedPet = new PetBuilder(COCO).withTags().build();
        PshModelManager expectedModel = new PshModelManager();
        expectedModel.addPet(expectedPet);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPetList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, PshModel)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      PshModel expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, PshModel)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, PshModel)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, PshModel)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        PshModel expectedModel = new PshModelManager(model.getPetTracker(), new PshUserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, PshModel)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, PshModel expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonPetTrackerIoExceptionThrowingStub extends JsonPetTrackerStorage {
        private JsonPetTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void savePetTracker(ReadOnlyPetTracker petTracker, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}

