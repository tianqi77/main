package seedu.address.logic.petparser;

import seedu.address.logic.generalparser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DOB = new Prefix("b/");
    public static final Prefix PREFIX_FOODLIST = new Prefix("f/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_SPECIES = new Prefix("s/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

}
