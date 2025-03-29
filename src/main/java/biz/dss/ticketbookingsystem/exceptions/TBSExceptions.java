package biz.dss.ticketbookingsystem.exceptions;

public class TBSExceptions{

    public static class UserNotFoundException extends Exception{
        public UserNotFoundException(){
            super("User not found.");
        }

        public UserNotFoundException(String message){
            super(message);
        }
    }

    public static class UserIsAlreadyBlockedException extends Exception{
        public UserIsAlreadyBlockedException(){
            super("User is already blocked.");
        }
        public UserIsAlreadyBlockedException(String message){
            super(message);
        }
    }

    public static class UserIsAlreadyUnBlockedException extends Exception{
        public UserIsAlreadyUnBlockedException(){
            super("User is already unblocked.");
        }
        public UserIsAlreadyUnBlockedException(String message){
            super(message);
        }
    }

    public static class WrongCredentialExceptions extends Exception{
        public WrongCredentialExceptions(){
            super("Either username is wrong or password is wrong.");
        }

        public WrongCredentialExceptions(String message){
            super(message);
        }
    }

    public static class TransactionNotFoundException extends Exception{
        public TransactionNotFoundException(){
            super("Transaction not found");
        }

        public TransactionNotFoundException(String message){
            super(message);
        }
    }

    public static class TicketAlreadyCancelledException extends Exception{
        public TicketAlreadyCancelledException(String message){
           super(message);
        }
    }

    public static class TrainNotFoundExceptions extends Exception{

        public TrainNotFoundExceptions(){
            super("Train not found.");
        }

        public TrainNotFoundExceptions(String message){
            super(message);
        }
    }

}

