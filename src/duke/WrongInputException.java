package duke;

public class WrongInputException extends DukeException {

    public WrongInputException() {
        super("Please enter a valid command.");
    }
}