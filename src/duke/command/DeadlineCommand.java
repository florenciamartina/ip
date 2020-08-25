package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.CalendarException;
import duke.exception.DeadlineException;
import duke.exception.DukeException;
import duke.task.Deadline;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DeadlineCommand extends Command {

    public DeadlineCommand(String task) {
        super(task);
    }

    /**
     * Executes any command corresponding to Deadline keyword.
     * @param taskList List of tasks.
     * @param ui UI of the bot.
     * @param storage Storage managing the file in hard disk.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            processDeadline(this.task, taskList, ui, storage);
        } catch (DeadlineException dead) {
            System.out.println(dead.getMessage());
        }
    }

    /**
     * Processes all the deadline command to determine the correct output.
     * @param theRest Parsed string containing task details.
     * @param taskList List containing all the task(s).
     * @param ui UI of the bot
     * @param storage Storage managing the file in hard disk.
     * @throws DeadlineException If user's input is incomplete or in the wrong format.
     */

    public void processDeadline(
            String theRest, TaskList taskList, Ui ui, Storage storage) throws DeadlineException {
        try {
            String[] taskAndDeadlineAndTime = theRest.split(" /by ", 2);
            Deadline deadline;

            try {
                String task = taskAndDeadlineAndTime[0];
                String dateAndTime = taskAndDeadlineAndTime[1];
                String[] dateTime = dateAndTime.split(" ", 2);

                String date = dateTime[0];

                try {
                    LocalDate localDate = LocalDate.parse(date);

                    if (dateTime.length < 2) {
                        deadline = new Deadline(task, localDate);
                    } else {
                        String time = dateTime[1];
                        LocalTime localTime = LocalTime.parse(time);
                        deadline = new Deadline(task, false, localDate, localTime);
                    }
                    taskList.saveToList(deadline);
                    Storage.updateData(taskList.getTasks());

                } catch (DateTimeParseException e) {
                    throw new CalendarException("Please enter the date in YYYY/MM/DD format and time in HH:MM format.");
                }

            } catch (IndexOutOfBoundsException e) {
                throw new DeadlineException("Please specify the task and deadline.");
            }

        } catch (DukeException d) {
            throw new DeadlineException("Please specify the task and deadline.");
        }
    }

    /**
     * Evaluates whether this and other object if this and
     * other object is the same or of the same type and task details.
     * @param other Other object to compare.
     * @return True if this object
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof DeadlineCommand) {
            DeadlineCommand deadlineCommand = (DeadlineCommand) other;
            return this.task.equals(deadlineCommand.getTask());
        }
        return false;
    }
}