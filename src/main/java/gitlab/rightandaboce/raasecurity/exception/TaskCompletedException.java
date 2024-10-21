package gitlab.rightandaboce.raasecurity.exception;

public class TaskCompletedException extends RuntimeException {
    public TaskCompletedException(String message) {
        super(message);
    }
}
