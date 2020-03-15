package seedu.address.logic.parser.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.general.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PshTypicalIndexes.INDEX_FIRST_PET;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.pet.DateOfBirth;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.Gender;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DOB = "01-31-2010";
    private static final String INVALID_FOOD_1 = "ABC: -1";
    private static final String INVALID_FOOD_2 = "A*BC: 10";
    private static final String INVALID_FOOD_3 = "ABC 10";
    private static final String INVALID_GENDER = "f";
    private static final String INVALID_SPECIES = "cat^";
    private static final String INVALID_TAG = "#cute";

    private static final String VALID_NAME = "Rachel";
    private static final String VALID_DOB = "31-01-2010";
    private static final String VALID_FOOD_1 = "ABC: 10";
    private static final String VALID_FOOD_2 = "DEF: 5";
    private static final String VALID_GENDER = "female";
    private static final String VALID_SPECIES = "cat";
    private static final String VALID_TAG_1 = "cute";
    private static final String VALID_TAG_2 = "small";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PET, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PET, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseGender_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGender((String) null));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGender(INVALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithoutWhitespace_returnsGender() throws Exception {
        Gender expectedGender = Gender.valueOf(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(VALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithWhitespace_returnsTrimmedGender() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_GENDER + WHITESPACE;
        Gender expectedGender = Gender.valueOf(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(genderWithWhitespace));
    }

    @Test
    public void parseSpecies_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSpecies((String) null));
    }

    @Test
    public void parseSpecies_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSpecies(INVALID_SPECIES));
    }

    @Test
    public void parseSpecies_validValueWithoutWhitespace_returnsSpecies() throws Exception {
        Species expectedSpecies = new Species(VALID_SPECIES);
        assertEquals(expectedSpecies, ParserUtil.parseSpecies(VALID_SPECIES));
    }

    @Test
    public void parseSpecies_validValueWithWhitespace_returnsTrimmedSpecies() throws Exception {
        String speciesWithWhitespace = WHITESPACE + VALID_SPECIES + WHITESPACE;
        Species expectedSpecies = new Species(VALID_SPECIES);
        assertEquals(expectedSpecies, ParserUtil.parseSpecies(speciesWithWhitespace));
    }

    @Test
    public void parseDateOfBirth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateOfBirth((String) null));
    }

    @Test
    public void parseDateOfBirth_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INVALID_DOB));
    }

    @Test
    public void parseDateOfBirth_validValueWithoutWhitespace_returnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DOB);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(VALID_DOB));
    }

    @Test
    public void parseDateOfBirth_validValueWithWhitespace_returnsTrimmedDateOfBirth() throws Exception {
        String dateOfBirthWithWhitespace = WHITESPACE + VALID_DOB + WHITESPACE;
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DOB);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(dateOfBirthWithWhitespace));
    }

    @Test
    public void parseFood_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFood(null));
    }

    @Test
    public void parseFood_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_FOOD_1));

        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_FOOD_2));

        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_FOOD_3));
    }

    @Test
    public void parseFood_validValueWithoutWhitespace_returnsFood() throws Exception {
        Food expectedFood = new Food("ABC", 10);
        assertEquals(expectedFood, ParserUtil.parseFood(VALID_FOOD_1));
    }

    @Test
    public void parseFood_validValueWithWhitespace_returnsTrimmedFood() throws Exception {
        String foodWithWhitespace = WHITESPACE + VALID_FOOD_1 + WHITESPACE;
        Food expectedFood = new Food("ABC", 10);
        assertEquals(expectedFood, ParserUtil.parseFood(foodWithWhitespace));
    }

    @Test
    public void parseFoodList_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFoodList(null));
    }

    @Test
    public void parseFoodList_collectionWithInvalidFoodList_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFoodList(Arrays.asList(VALID_FOOD_1, INVALID_FOOD_2)));
    }

    @Test
    public void parseFoodList_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseFoodList(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseFoodList_collectionWithValidTags_returnsFoodSet() throws Exception {
        Set<Food> actualFoodSet = ParserUtil.parseFoodList(Arrays.asList(VALID_FOOD_1, VALID_FOOD_2));
        Set<Food> expectedFoodSet = new HashSet<Food>(Arrays.asList(new Food("ABC", 10),
                new Food("DEF", 5)));

        assertEquals(expectedFoodSet, actualFoodSet);
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
