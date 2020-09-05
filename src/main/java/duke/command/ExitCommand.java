package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import javafx.application.Platform;

public class ExitCommand extends Command {

    public ExitCommand() {
        super(true);
    }

    /**
     * Processes the exit command to exit the bot.
     *
     * @param taskList List of tasks.
     * @param ui       UI of the bot.
     * @param storage  Storage managing the file in hard disk.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        assert ui != null : "No UI available!";
        return processBye(ui);
    }

    /**
     * Exits the bot.
     *
     * @param ui UI to show the goodbye message.
     */
    public String processBye(Ui ui) {
        return ui.bye();
    }

}
