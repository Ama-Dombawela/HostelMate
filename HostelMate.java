//importing required java classes for input handling and date operations
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

class HostelMate {

    // Scanner object for reading user input from the console
    static Scanner input = new Scanner(System.in);

    // Room data
    static final int MaxRows = 100;
    static int NoRooms = 0;
    static String[][] rooms = new String[MaxRows][6];

    // Student data
    static final int MaxStudents = 200;
    static int NoStudents = 0;
    static String[][] students = new String[MaxStudents][5];

    // Allocate bed data
    static final int MaxAllocation = 300;
    static int NoAllocations = 0;
    static String[][] allocations = new String[MaxAllocation][5];

    // Occupancy grid
    static String[][] occupancy = new String[MaxRows][20];

    // Main method
    public static void main(String[] args) {
        // calling the login method
        Login();
        clearConsole();// Calling the clear console method

        int choice;
        do {
            choice = HomePage();// calling the Home page method

            switch (choice) {
                case 1:
                    // if choice is 1 calling manage room method
                    ManageRooms();
                    break;

                case 2:
                    // If choice is 2 calling manage student method
                    ManageStudents();
                    break;
                case 3:
                    // if choice is 3 calling the Allocate bed method
                    AllocateBed();
                    break;
                case 4:
                    // if choice is 4 calling the Vacate Bed method
                    VacateBed();
                    break;
                case 5:
                    // if choice is 5 calling the transfers method
                    Transfers();
                    break;
                case 6:
                    // if choice is 6 calling View report method
                    ViewReports();
                    break;
                case 7:
                    // if choice is 7 exiting the system with the message
                    System.out.println("Exiting System ....");
                    clearConsole();// Calling the clear console method
                    break;

                default:
                    // If non of that displaying a error
                    System.out.println("Invalid choice Please enter a chice between (1-7).");

            }
        } while (choice != 7);

    }

    // Login method
    public static void Login() {
        String Username;
        String Password;

        System.out.println("=== HostelMate Login ===");

        // Looping until correct credentials are entered
        while (true) {
            // -----Validation-----
            // Username validation
            while (true) {
                // Prompting to enter the username
                System.out.print("Username: ");
                Username = input.nextLine();
                boolean UserNameCheck = true;

                // checking the validation of the user input and displaying eror messages if its
                // invalid
                if (Username.isEmpty() || Username.length() < 3) { // check if username is empty or too short
                    UserNameCheck = false;
                    System.out.println("Invalid Username.Please enter the correct Username.");
                    // checking if username contains only letters and spaces
                } else {
                    for (int i = 0; i < Username.length(); i++) {
                        char n = Username.charAt(i);
                        if (!Character.isLetter(n) && n != ' ') {
                            System.out.println("Invalid Username.Numbers cannot be included");
                            UserNameCheck = false;
                            break;
                        }
                    }
                }
                // exiting the loop if username is valid
                if (UserNameCheck)
                    break;
            }

            // Password validation
            while (true) {
                // Prompt user to enter the password
                System.out.print("Password: ");
                Password = input.nextLine();

                boolean passwordCheck = true;
                boolean hasLetter = false;
                boolean hasNumber = false;

                // Check if password is empty or too short
                if (Password.isEmpty() || Password.length() < 4) {
                    passwordCheck = false;
                    System.out.println("Please enter the correct password");
                } else {
                    // Check if password contains at least one letter and one number
                    for (int i = 0; i < Password.length(); i++) {
                        char a = Password.charAt(i);
                        if (Character.isLetter(a))
                            hasLetter = true;
                        if (Character.isDigit(a))
                            hasNumber = true;

                        if (hasLetter && hasNumber)
                            break;
                    }
                    // If validation fails displaying Invalid password and again going to loop
                    if (!hasLetter || !hasNumber) {
                        passwordCheck = false;
                        System.out.println("Invalid password.Please enter the correct password");

                    }
                }
                // Exiting loop if password is valid
                if (passwordCheck)
                    break;
            }
            // -----Authentication-----
            // Checking the hardcoded credentials
            if (Username.equals("warden") && Password.equals("warden123")) {
                System.out.println("Login successful. Welcome," + Username + "!");
                break;
            } else {
                // If the credentials are incorrect displaying Invalid and try again
                System.out.println("Invalid credentials.Try again!\n");
            }
        }

    }

    // Displays the main menu of the system
    // Returns the user's selection option(1-7)
    public static int HomePage() {

        // Displaying main menu options
        System.out.println("""

                \n === HostelMate ===

                 1) Manage Rooms
                 2) Manage Students
                 3) Allocate Bed
                 4) Vacate Bed
                 5) Transfers
                 6) View Reports
                 7) Exit
                          """);

        int choice = -1;

        // Looping until a valid menu option is entered
        while (true) {
            System.out.print("Choose: ");

            try {
                // Read String input and convert to integer
                choice = Integer.parseInt(input.nextLine());
                // Validate menu range
                if (choice >= 1 && choice <= 7)
                    break;
                else
                    System.out.println("Invalid choice.Please enter a choice between (1-7)");

                // Handles non numeric input
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.Enter a number ");
            }
        }
        // Return the valid menu option to the main method
        return choice;
    }

    // contains all the logic related to managing room(Add,Update,Delete,search and
    // View)
    public static void ManageRooms() {

        while (true) {
            // Displaying manege room menu
            System.out.println("""

                    === ManageRooms ===

                    1) Add Room
                    2) Update Room
                    3) Delete Room
                    4) Search Room
                    5) View All Rooms
                    6) Back to main menu
                    """);

            // validating the choice in mange rooms
            int manageChoice = -1;
            while (true) {
                System.out.print("Enter your choice(1-6): ");
                try {
                    manageChoice = Integer.parseInt(input.nextLine());
                    if (manageChoice >= 1 && manageChoice <= 6)
                        break;
                    else
                        System.out.println("Invalid choice. Please enter a number between (1-6)");

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between (1-6)");

                }
            }

            // If choice is 1
            // ============ ADD RoOOM ============
            if (manageChoice == 1) {
                System.out.println("\nAdd Room\n");

                // Prompting to to take room ID input
                System.out.print("Room ID : ");
                String RoomId = input.nextLine();

                // Checking wheather the room already exsists
                boolean dup = false;
                for (int i = 0; i < NoRooms; i++) {
                    if (rooms[i][0].equals(RoomId)) {
                        System.out.println("Room Id already exists");
                        dup = true;
                        break;
                    }
                }if (dup)
                    continue;

                // storing the room ID
                rooms[NoRooms][0] = RoomId;

                // Taking input and validating the floor number
                int Floor;
                System.out.print("\nFloor : ");
                try {
                    Floor = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Floor number!");
                    continue;
                }
                // Storing the validated floor
                rooms[NoRooms][1] = Integer.toString(Floor);

                // Taking inputs and validating the room number
                int RoomNo = 0;
                System.out.print("\nRoom No : ");
                try {
                    RoomNo = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Room number!");
                    continue;
                }
                // Storing the validated room no
                rooms[NoRooms][2] = Integer.toString(RoomNo);

                // Taking inputs and validating the room Capacity
                int Capacity;
                System.out.print("\nCapacity : ");
                try {
                    Capacity = Integer.parseInt(input.nextLine());
                    if (Capacity <= 0) {
                        System.out.println("Invalid Capacity ! Must be greater than zero");
                        continue;
                    }
                    if (Capacity > 20) {
                        System.out.println("Maximum room capacity is 20 beds.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Capacity input!");
                    continue;
                }
                // Storing th validated Capacity
                rooms[NoRooms][3] = Integer.toString(Capacity);

                // //Taking inputs and validating the room fee
                double fee;
                System.out.print("\nFee/Day(LKR) : ");
                try {
                    fee = Double.parseDouble(input.nextLine());
                    if (fee < 0) {
                        System.out.println("Invalid fee ! must be greater thatn or equals zero");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Fee input!");
                    continue;
                }
                // Storing the validated room fee
                rooms[NoRooms][4] = String.format("%.2f", fee);
                rooms[NoRooms][5] = Integer.toString(Capacity);

                // Initialize occupancy grid for this room (all the beds empty initialy)
                for (int i = 0; i < Capacity; i++) {
                    occupancy[NoRooms][i] = "EMPTY";
                }

                System.out.println("Room added. Available beds: " + rooms[NoRooms][5]);

                // Increasing the room count
                NoRooms++;

                // If choice is 2
                // =============UPDATE ROOM ===========
            } else if (manageChoice == 2) {

                System.out.println("\nUpdate Room\n");

                // Prompting to get the room ID to update
                System.out.print("Room ID: ");
                String RoomId = input.nextLine();

                int index = -1;
                for (int i = 0; i < NoRooms; i++) {
                    if (rooms[i][0].equals(RoomId)) {
                        index = i;
                        break;
                    }

                }
                // If room not found
                if (index == -1) {
                    System.out.println("Room not Found.Please enter a correct RoomId");
                    continue;
                }

                // Calculating the currently occupied beds
                int CurrentOcup = Integer.parseInt(rooms[index][3]) - Integer.parseInt(rooms[index][5]);

                // Prompting to get the new capacity to update
                System.out.print("New Capacity (or - to skip): ");
                String CapInput = input.nextLine();

                // Reject if new capacity is less than occupied beds or skip
                if (!CapInput.equals("-")) {
                    int NewCap = Integer.parseInt(CapInput);
                    if (NewCap < CurrentOcup) {
                        System.out.println("Rejected");

                    } else {
                        rooms[index][3] = Integer.toString(NewCap);
                        int NewAvailable = NewCap - CurrentOcup;
                        rooms[index][5] = Integer.toString(NewAvailable);
                    }
                }

                // Prompting to get the new fee to update
                System.out.print("New Fee/Day (or - to skip): ");
                String FeeInput = input.nextLine();

                // if not skipping updating the new fee
                if (!FeeInput.equals("-")) {
                    double NewFee = Double.parseDouble(FeeInput);
                    rooms[index][4] = Double.toString(NewFee);
                }

                // Displaying the updated room details
                System.out.println("Updated: " + rooms[index][0] + " | Floor=" + rooms[index][1] + " | RoomNo="
                        + rooms[index][2] + " | Capacity=" + rooms[index][3] + " | Fee/Day=" + rooms[index][4]
                        + " | Avail="
                        + rooms[index][5]);

                // If choice is 3
                // ============DELETE ROOM =============
            } else if (manageChoice == 3) {

                System.out.println("\nDelete Room\n");

                // Taking the user input of Room ID
                System.out.print("Room ID: ");
                String RoomId = input.nextLine();

                // Intitialzing index to -1 (used to check if room is found)
                int index = -1;
                // Searching for the room using room ID
                for (int i = 0; i < NoRooms; i++) {
                    if (rooms[i][0].equals(RoomId)) {
                        index = i;
                        break;
                    }

                }
                // If room is not found displaying a error and exit method
                if (index == -1) {
                    System.out.println("Room not Found.Please enter a correct RoomId");
                    continue;
                }

                // Initialzing variables to store full capacity and availble beds
                int FullCapacity = 0, available = 0;

                // Handles corrupted or inavlid data
                try {
                    FullCapacity = Integer.parseInt(rooms[index][3]);
                    available = Integer.parseInt(rooms[index][5]);
                } catch (NumberFormatException e) {
                    System.out.println("Room Data corrupted");
                    continue;
                }

                // Dleting the room if no beds are occupied
                if (available != FullCapacity) {
                    System.out.println("Cannot Delete! There are ACTIVE ALLOCATIONS for this room");
                    continue;
                } else {
                    // shifting all the rooms after the deleted room one position up
                    for (int i = index; i < NoRooms - 1; i++) {
                        rooms[i] = rooms[i + 1];
                    }
                    // Clearing the last row after shifting
                    rooms[NoRooms - 1] = new String[6];

                }
                // Decresing the room count
                NoRooms--;
                // Displaying successfull delete message
                System.out.println("Deleted successfully.");

                // If choice 3
                // ==========SEARCH ROOM==============
            } else if (manageChoice == 4) {

                System.out.println("\nSearch Room\n");

                // Prompting the user to get the ROOM ID to search the room
                System.out.print("Room ID: ");
                String RoomId = input.nextLine();

                int index = -1;
                // Searching the room in rooms array
                for (int i = 0; i < NoRooms; i++) {
                    if (rooms[i][0].equals(RoomId)) {
                        index = i;
                        // Displaying Found and the room details
                        System.out.println("Found\n");

                        System.out.println("ID\tFloor\tNo\tCap\tAvail\tFee/Day\n");
                        System.out.println("----------------------------------------------\n");

                        System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n",
                                rooms[index][0],
                                rooms[index][1],
                                rooms[index][2],
                                rooms[index][3],
                                rooms[index][5],
                                rooms[index][4]);

                        System.out.println();
                        break;
                    }

                }
                // If room not found
                if (index == -1) {
                    System.out.println("Room not Found.Please enter a correct RoomId");
                    continue;
                }

                // If choice is 5
                // ===========VIEW ALL ROOMS ==============
            } else if (manageChoice == 5) {

                sortRoomsByAvailBed();// callig the sorting method made to sort by the available beds(descending
                                      // order)

                System.out.println("\nAll Rooms\n");
                System.out.println("ID\tFloor\t No\t Cap\t Avail\t Fee/Day\n");
                System.out.println("---------------------------------------------\n");

                for (int i = 0; i < NoRooms; i++) {
                    System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n",
                            rooms[i][0],
                            rooms[i][1],
                            rooms[i][2],
                            rooms[i][3],
                            rooms[i][5],
                            rooms[i][4]);
                }

            } else if (manageChoice == 6) {
                break;

            }

        }

    }

    // contains all the logic related to managing Students(Add,Update,Delete,search
    // and View)
    public static void ManageStudents() {

        // Varibales used for MAnage student method
        String studentId;
        int index;
        String contactNo;
        String emailAddress;
        String status = "ACTIVE";

        try {

            while (true) {
                // Displaying the mange student options
                System.out.println("""
                        \n=== ManageStudents ===

                        1) Add Student
                        2) Update Student
                        3) Delete Student
                        4) Search Student
                        5) View All Student
                        6) Back to main menu
                        """);

                // Getting the user choice
                int manageChoice = -1;
                while (true) {
                    System.out.print("Enter your choice(1-6): ");
                    String line = input.nextLine();
                    try {
                        manageChoice = Integer.parseInt(line);
                        if (manageChoice >= 1 && manageChoice <= 6)
                            break;
                        else
                            System.out.println("Invalid choice. Entera number between(1-6)");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Enter a number between (1-6)");
                    }
                }

                // If choice is 1
                // ============ADD STUDENT=========
                switch (manageChoice) {
                    case 1:
                        System.out.println("\nAdd Student\n");

                        System.out.print("Student ID : ");
                        studentId = input.next();
                        input.nextLine();

                        // Validating studentId
                        // Each row represent a student record
                        // Condition checks until the NoStudents rather than the MaxStudents
                        boolean dup = false;
                        for (int i = 0; i < NoStudents; i++) {
                            // In inner arrays, 0th element represents the studentId
                            if (students[i][0].equals(studentId)) {
                                System.out.println("Student Id already exists");
                                dup = true;
                                break;
                            }
                        }
                        if (dup)
                            continue;

                        // Getting user input and validating
                        System.out.print("\nName       : ");
                        String studentName = input.nextLine();

                        System.out.print("\nContact    : ");
                        contactNo = input.next();
                        input.nextLine();
                        // If the number is invalid then redirect to the main menu
                        if (invalidContact(contactNo)) {
                            System.out.println();
                            continue;
                        }

                        System.out.print("\nEmail      : ");
                        emailAddress = input.nextLine();
                        if (invalidEmail(emailAddress)) {
                            continue;

                        }

                        System.out.println("Status     : " + status);

                        // Adding information to the array
                        students[NoStudents][0] = studentId;
                        students[NoStudents][1] = studentName;
                        students[NoStudents][2] = contactNo;
                        students[NoStudents][3] = emailAddress;
                        students[NoStudents][4] = status;

                        System.out.println("Student Added.");
                        NoStudents++;

                        System.out.println();
                        break;

                    case 2:
                        // If choice is 2
                        // ========UPDATE STUDENT ===========
                        System.out.println("\nUpdate Student\n");

                        // Getiing the student ID to be updated
                        System.out.print("Student ID to update      : ");
                        studentId = input.nextLine();

                        index = -1;
                        for (int i = 0; i < NoStudents; i++) {
                            if (students[i][0].equals(studentId)) {
                                index = i;
                                break;
                            }
                        }

                        if (index == -1) {
                            System.out.println("Student not Found.");
                            continue;
                        }

                        System.out.print("\nNew Contact (or - to skip): ");
                        contactNo = input.next();
                        input.nextLine();
                        if (!contactNo.equals("-")) {
                            // If the number is invalid then redirect to the main menu
                            if (invalidContact(contactNo)) {
                                System.out.println();
                                continue;
                            } else {
                                students[index][2] = contactNo;
                            }
                        }

                        System.out.print("\nNew Email (or - to skip)  : ");
                        emailAddress = input.nextLine();
                        if (!emailAddress.equals("-")) {
                            if (invalidEmail(emailAddress)) {
                                continue;
                            }
                            students[index][3] = emailAddress;

                        }
                        // Displaying the updated details
                        System.out.println("Updated: " +
                                students[index][0] + " | " +
                                students[index][1] + " | " +
                                students[index][2] + " | " +
                                students[index][3] + " | " +
                                students[index][4] + " | ");

                        break;

                    case 3:
                        // Id choice is 3
                        // =========DELETE STUDENTS =====
                        System.out.println("\nDelete Student\n");

                        System.out.print("Student ID: ");
                        studentId = input.nextLine();

                        index = -1;
                        for (int i = 0; i < NoStudents; i++) {
                            if (students[i][0].equals(studentId)) {
                                index = i;
                                break;
                            }

                        }

                        if (index == -1) {
                            System.out.println("Student not Found.");
                            continue;
                        }

                        // If the student is active then cannot remove him
                        if (students[index][4].equals("ACTIVE")) {
                            System.out
                                    .println(
                                            "Cannot Delete! Student have ACTIVE ALLOCATIONS.Marking as INACTIVE instead.");
                            students[index][4] = "INACTIVE";// marking student as INACTIVE
                            continue;
                        } else {
                            // Shifting records to the left. Current I is equal to I+1(next student). This
                            // goes until no of NoStudent - 1
                            // length - 1 because there's nothing to replace last one with
                            for (int i = index; i < NoStudents - 1; i++) {
                                students[i] = students[i + 1];
                            }

                            // Last room is now empty and a new array
                            students[NoStudents - 1] = new String[5];

                        }
                        NoStudents--;
                        System.out.println("Deleted successfully.");

                        break;

                    case 4:
                        // If choice is 4
                        // ===========SEARCH STUDENT =========
                        System.out.println("\nSearch Student\n");

                        System.out.print("Student ID: ");
                        studentId = input.nextLine();

                        index = -1;
                        for (int i = 0; i < NoStudents; i++) {
                            if (students[i][0].equals(studentId)) {
                                index = i;
                                System.out.println("Found\n");

                                System.out.printf("%-10s %-10s %-10s %-20s %-10s\n", "ID", "Name", "Contact", "Email",
                                        "Status");
                                System.out
                                        .println("----------------------------------------------------------------\n");

                                // 0 - 5, indexes of the inner array elements
                                System.out.printf(
                                        "%-10s %-10s %-10s %-20s %-10s\n",
                                        students[index][0],
                                        students[index][1],
                                        students[index][2],
                                        students[index][3],
                                        students[index][4]);

                                System.out.println();
                                break;
                            }

                        }

                        if (index == -1) {
                            System.out.println("Student not Found.");
                            continue;
                        }

                        break;

                    case 5:
                        // if choice is 5
                        // =====DISPLAY ALL STUDENTS=======
                        System.out.println("All Students");
                        System.out.printf("%-10s %-10s %-10s %-20s %-10s\n", "ID", "Name", "Contact", "Email",
                                "Status");
                        System.out.println("----------------------------------------------------------------\n");

                        for (int i = 0; i < NoStudents; i++) {
                            System.out.printf(
                                    "%-10s %-10s %-10s %-20s %-10s\n",
                                    students[i][0],
                                    students[i][1],
                                    students[i][2],
                                    students[i][3],
                                    students[i][4]);
                        }

                        System.out.println();
                        break;

                    case 6:
                        return;

                    default:
                        // if the choice id invalid
                        System.out.println("Invalid input. Please enter a choice(1-6)");

                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type.Please enter the correct value.");
            input.nextLine();// clear invalid input
        } catch (Exception e) {
            System.out.println("Error detected : " + e.getMessage());
        }

    }

    // ===========ALLOCATE BED ==========
    public static void AllocateBed() {

        System.out.println("\nAllocate Bed\n");

        // input student id
        System.out.print("\tStudent ID : ");
        String studentId = input.next();
        input.nextLine();

        // checking student exist
        int index = -1;
        for (int i = 0; i < NoStudents; i++) {
            if (students[i][0].equals(studentId)) {
                index = i;
                break;
            }

        }

        if (index == -1) {
            System.out.println("\tStudent not Found.");
            return;
        }

        // checking the student is ACTIVE
        if (!students[index][4].equals("ACTIVE")) {
            System.out.println("\tStudent is not ACTIVE");
            return;
        }

        // input room Id
        System.out.print("\n\tRoom ID : ");
        String roomId = input.next();
        input.nextLine();

        // checking if the room exists
        int roomindex = -1;
        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][0].equals(roomId)) {
                roomindex = i;
                break;
            }
        }

        if (roomindex == -1) {
            System.out.println("\tRoom not Found.");
            return;
        }

        // Checking the availablity of beds if its greather than zero
        int availableBeds = Integer.parseInt(rooms[roomindex][5]);
        if (availableBeds <= 0) {
            System.out.println("\tNo available beds in this room");
            return;
        }

        // Checking if studen is already allocated
        for (int i = 0; i < NoAllocations; i++) {
            if (allocations[i][0].equals(studentId)) {
                System.out.println("\tStudent already has an active allocation");
                return;
            }
        }

        // Input due date
        System.out.print("\n\tDue Date(YYYY-MM-DD) : ");
        String DueDate = input.next();
        input.nextLine();

        // using current data as check in date
        LocalDate checkDate = LocalDate.now();
        LocalDate due;

        try {
            due = LocalDate.parse(DueDate);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.Use(YYYY-MM-DD)");
            return;
        }

        // Validating by checking if DueDate >= checkInDate
        if (due.isBefore(checkDate)) {
            System.out.println("\tInvalid due date.Please enter again");
            return;
        }

        // choosing the lowest available bedIndex within the room.
        int bedIndex = -1;
        int roomCapacity = Integer.parseInt(rooms[roomindex][3]);

        // iterating through the rooms and selecting the first emty amoung them
        for (int i = 0; i < roomCapacity; i++) {
            if (occupancy[roomindex][i].equals("EMPTY")) {
                bedIndex = i;
                break;
            }
        }

        if (bedIndex == -1) {
            System.out.println("\tNo empty bed found");
            return;
        }

        // Recording the allocations
        allocations[NoAllocations][0] = studentId;
        allocations[NoAllocations][1] = roomId;
        allocations[NoAllocations][2] = Integer.toString(bedIndex);
        allocations[NoAllocations][3] = checkDate.toString();
        allocations[NoAllocations][4] = due.toString();
        NoAllocations++;

        // updating Occupancy
        occupancy[roomindex][bedIndex] = studentId;

        // updating available beds
        rooms[roomindex][5] = Integer.toString(Integer.parseInt(rooms[roomindex][5]) - 1);

        // displaying the allocated room and the available beds
        System.out.println("\n\tAllocated: " + studentId + " -> Room " + roomId + " Bed " + bedIndex);
        System.out.println("\nAvailable beds: (" + roomId + "): " + rooms[roomindex][5]);
    }

    // =======VACATE BED ===========
    public static void VacateBed() {

        System.out.println("\nVacate Bed\n");

        // input student id
        System.out.print("\tStudent ID : ");
        String studentId = input.next();
        input.nextLine();

        // input room Id
        System.out.print("\tRoom ID : ");
        String roomId = input.next();
        input.nextLine();

        int roomindex = -1;
        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][0].equals(roomId)) {
                roomindex = i;
                break;
            }
        }

        if (roomindex == -1) {
            System.out.println("\tRoom not Found.Please enter a correct RoomId");
            return;
        }
        // Check if this student is allocated to this room
        int allocateIndex = -1; // will store the row number if found

        for (int x = 0; x < NoAllocations; x++) {
            if (allocations[x][0].equals(studentId) && allocations[x][1].equals(roomId)) {
                allocateIndex = x;
                break;
            }
        }

        // if not found this ,cannot vacate and goes to the main menue
        if (allocateIndex == -1) {
            System.out.println("\tNo ACTIVE allocations found for this student in this room");
            return;
        }

        String StrDueDate = allocations[allocateIndex][4];
        int Overduedays = OverdueDaysCalculate(StrDueDate);// calling the Overduedays calculation method

        // Calculating the fine
        double OneDayFee;
        try {
            OneDayFee = Double.parseDouble(rooms[roomindex][4]);
        } catch (NumberFormatException e) {
            System.out.println("Data error : invalid numeric value");
            return;
        }

        double fine = Overduedays * OneDayFee;

        // Remove allocation row, set occupancy cell to "EMPTY", increase availableBeds
        // by 1.
        int bedIndex;

        // Handles corrupted or invalid numeric data
        try {
            bedIndex = Integer.parseInt(allocations[allocateIndex][2]);
        } catch (NumberFormatException e) {
            System.out.println("Data error : invalid numeric value");
            return;
        }

        // Mark the bed as EMPTY in occupancy grid
        occupancy[roomindex][bedIndex] = "EMPTY";

        // Remove the allocation record by shifting remaining allocations left
        for (int i = allocateIndex; i < NoAllocations - 1; i++) {
            allocations[i] = allocations[i + 1];
        }
        // Clearing the last allocation row after shifting
        allocations[NoAllocations - 1] = new String[5];
        NoAllocations--;// decreasing the allocation count

        int availableBeds = Integer.parseInt(rooms[roomindex][5]) + 1;
        rooms[roomindex][5] = Integer.toString(availableBeds);

        // Printing the result
        System.out.println("\nOverdue days: " + Overduedays + "| Fee/Day: " + OneDayFee + "| Fine: " + fine);
        System.out.println("\nCheckout completed. Bed freed. Available beds (" + roomId + ") : " + availableBeds);

    }

    // =======TRANSFERS===============
    public static void Transfers() {

        System.out.println("\nTransfer\n");

        // input student id
        System.out.print("\tStudent ID : ");
        String studentId = input.next();
        input.nextLine();

        // Check if this student is allocated to this room
        int allocateIndex = -1; // will store the row number if found

        for (int x = 0; x < NoAllocations; x++) {
            if (allocations[x][0].equals(studentId)) {
                allocateIndex = x;
                break;
            }
        }

        // if not found this ,cannot vacate and goes to the main menue
        if (allocateIndex == -1) {
            System.out.println("\tNo ACTIVE allocations found for this student ");
            return;
        }

        String oldRoomID = allocations[allocateIndex][1];
        int oldRommIndex = -1;
        int oldBedIndex;

        try {
            oldBedIndex = Integer.parseInt(allocations[allocateIndex][2]);
        } catch (NumberFormatException e) {
            System.out.println("Data error: invalid numeric value.");
            return;
        }
        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][0].equals(oldRoomID)) {
                oldRommIndex = i;
                break;
            }
        }

        // Prompting from which room to where
        System.out.println("\tFrom Room : " + oldRoomID);

        System.out.print("\tTo Room : ");
        String newRoomId = input.next();
        input.nextLine();

        int newRoomIndex = -1;
        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][0].equals(newRoomId)) {
                newRoomIndex = i;
                break;
            }
        }

        if (newRoomIndex == -1) {
            System.out.println("\tTarget room does not exist.Please enter a valid room");
            return;
        }

        // checking if the target room has available beds
        int newAvaialble;
        int NewCapacity;

        // handling the exceptions
        try {
            newAvaialble = Integer.parseInt(rooms[newRoomIndex][5]);
            NewCapacity = Integer.parseInt(rooms[newRoomIndex][3]);

        } catch (NumberFormatException e) {
            System.out.println("Data error: invalid numeric value.");
            return;
        }
        if (newAvaialble <= 0) {
            System.out.println("\tNo available beds in the target room ");
            return;
        }

        // Finding the lowest available bed in the target room
        int NewbedIndex = -1;

        // iterating through the rooms and selecting the first emty amoung them
        for (int i = 0; i < NewCapacity; i++) {
            if (occupancy[newRoomIndex][i].equals("EMPTY")) {
                NewbedIndex = i;
                break;
            }
        }
        // checking if there are empty beds found
        if (NewbedIndex == -1) {
            System.out.println("\tNo empty bed found in the target room");
            return;
        }

        // Performing the transfer
        occupancy[oldRommIndex][oldBedIndex] = "EMPTY";
        occupancy[newRoomIndex][NewbedIndex] = studentId;

        // Update allocation
        allocations[allocateIndex][1] = newRoomId;
        allocations[allocateIndex][2] = Integer.toString(NewbedIndex);
        // checkIn and due date remain constant
        // log transfer date
        String transferDate = LocalDate.now().toString();

        // Updating the available beds
        rooms[oldRommIndex][5] = Integer.toString(Integer.parseInt(rooms[oldRommIndex][5]) + 1);
        rooms[newRoomIndex][5] = Integer.toString(Integer.parseInt(rooms[newRoomIndex][5]) - 1);

        // Displaying the transfer data
        System.out.println("\n\tTransferred to " + newRoomId + "Bed " + NewbedIndex);
        System.out.println("\n\tAvail (" + oldRoomID + "): " + rooms[oldRommIndex][5] + " | Avail (" + newRoomId + "): "
                + rooms[newRoomIndex][5]);
        System.out.println("\t Transfer Date: " + transferDate); // optional print statement to display the transfer
                                                                 // date

    }

    // =====VIEW REPORTS=========
    public static void ViewReports() {

        //Occupancy Grid
        System.out.println("\nOccupancy Grid (rooms x beds)");
        System.out.println("\n\tRoom\tRow\tBeds ");
        System.out.println("\t--------------------------------------------\n");

        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][3] == null)
                continue;
            int cap = Integer.parseInt(rooms[i][3]);
            System.out.print(i + " [");
            for (int x = 0; x < cap; x++) {
                System.out.print(occupancy[i][x]);
                if (x < cap - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }
        System.out.println();

        // Vacant Beds by Floor
        System.out.println("\n\nVacant Beds by Floor");
        System.out.println("\n\tFloor\tTotalRooms\tTotalBeds\tOccupied\tVacant");
        System.out.println("\t--------------------------------------------");

        // Find max floor number
        int maxiumunFloor = 0;
        for (int i = 0; i < NoRooms; i++) {
            int floor = Integer.parseInt(rooms[i][1]);
            if (floor > maxiumunFloor)
                maxiumunFloor = floor;
        }

        for (int f = 1; f <= maxiumunFloor; f++) {
            int totalRooms = 0, totalBeds = 0, occupied = 0;
            for (int i = 0; i < NoRooms; i++) {
                int floor = Integer.parseInt(rooms[i][1]);
                if (floor == f) {
                    totalRooms++;
                    int Capacity = Integer.parseInt(rooms[i][3]);
                    totalBeds += Capacity;
                    for (int x = 0; x < Capacity; x++) {
                        if (!occupancy[i][x].equals("EMPTY"))
                            occupied++;
                    }
                }
            }
            int Vacant = totalBeds - occupied;

            if (totalRooms > 0) {
                System.out.println(
                        "\n\t" + f + "\t" + totalRooms + "\t\t" + totalBeds + "\t\t" + occupied + "\t\t" + Vacant);
            }

        }

        // Students per Room
        System.out.println("\n\nStudents per Room ");
        System.out.println("\n\tRoom\tCount\tStudents");
        System.out.println("\t--------------------------------");

        for (int i = 0; i < NoRooms; i++) {
            int capacity = Integer.parseInt(rooms[i][3]);
            String ROOMID = rooms[i][0];
            String studentList = "";
            int count = 0;

            for (int x = 0; x < capacity; x++) {
                if (!occupancy[i][x].equals("EMPTY")) {
                    if (count > 0)
                        studentList += ",";
                    studentList += occupancy[i][x];
                    count++;
                }
            }
            System.out.println("\n\t" + ROOMID + "\t" + count + "\t" + studentList);
        }

        // Overdue Dues
        System.out.println("\n\nOverdue Dues  ");
        System.out.println("\n\tStudent\tRoom\tDaysOverdue\tFee/Day\tEstimatedFine  ");
        System.out.println("\t-------------------------------------------------------------------");

        for (int i = 0; i < NoAllocations; i++) {

            String StudentID = allocations[i][0], roomID = allocations[i][1];
            int Overduedays = OverdueDaysCalculate(allocations[i][4]);

            if (Overduedays > 0) {

                int roomindex = -1;
                for (int r = 0; r < NoRooms; r++) {
                    if (rooms[r][0].equals(roomID)) {
                        roomindex = r;
                        break;
                    }
                }

                double OneDayFee = Double.parseDouble(rooms[roomindex][4]);
                double fine = Overduedays * OneDayFee;

                System.out.println(
                        "\n\t" + StudentID + "\t" + roomID + "\t" + Overduedays + "\t" + OneDayFee + "\t" + fine);
            }

        }

        // Revenue Projection (Daily)
        System.out.println("\n\nRevenue Projection (Daily)  ");

        double totalRevenue = 0;

        // Looping through all the rooms
        for (int i = 0; i < NoRooms; i++) {
            int capacity = Integer.parseInt(rooms[i][3]);
            double OneDayFee = Double.parseDouble(rooms[i][4]);

            // iterate through all the beds in the room
            for (int x = 0; x < capacity; x++) {
                if (!occupancy[i][x].equals("EMPTY")) {
                    totalRevenue += OneDayFee;
                }
            }

        }
        System.out.printf("\n\t$ feePerDay for currently occupied beds = %.2f LKR", totalRevenue);

    }

    // ====================Supporting methods=======================
    // Sorting method for sort by availbaleBeds
    public static void sortRoomsByAvailBed() {
        for (int i = 0; i < NoRooms - 1; i++) {
            for (int x = 0; x < NoRooms - i - 1; x++) {
                int bedone = Integer.parseInt(rooms[x][5]);
                int bedtwo = Integer.parseInt(rooms[x + 1][5]);
                if (bedone < bedtwo) {
                    String[] temp = rooms[x];
                    rooms[x] = rooms[x + 1];
                    rooms[x + 1] = temp;
                }
            }
        }
    }

    // supporting method to mange student choice
    public static boolean invalidContact(String contactNo) {
        boolean validNumber = true;

        // Validating contact information
        // Check whether the length is 10
        if (contactNo.length() == 10) {
            // Check whether the number has all digits
            for (int i = 0; i < contactNo.length(); i++) {
                char digit = contactNo.charAt(i);
                if (!Character.isDigit(digit)) {
                    System.out.println("Contact number should in digits!");
                    validNumber = false;
                    break;
                }
            }

        } else {
            System.out.println("Contact number should contain 10 digits" +
                    "(eg: 07xxxxxxxx)");
            validNumber = false;
        }

        // If the number is invalid then return true
        if (!validNumber) {
            System.out.println();
            return true;
        }
        return false;

    }

    public static boolean invalidEmail(String emailAddress) {
        boolean hasAt = false;
        boolean hasDot = false;

        // Validating the email to check whether there is @ and .
        for (int i = 0; i < emailAddress.length(); i++) {
            char a = emailAddress.charAt(i);
            if (a == '@')
                hasAt = true;
            if (a == '.')
                hasDot = true;

            if (hasAt && hasDot)
                break;
        }
        if (!hasAt || !hasDot) {
            System.out.println("Invalid email address. Email must have @ and .\n");
            return true;

        }

        return false;
    }

    // Overduedays calculation method(used in Vacate Bed)
    public static int OverdueDaysCalculate(String StrDueDate) {

        // Split the due date string (YYYY-MM-DD) into year, month, and day
        String[] parts = StrDueDate.split("-");
        int dueYear = Integer.parseInt(parts[0]);
        int dueMonth = Integer.parseInt(parts[1]);
        int dueDay = Integer.parseInt(parts[2]);

        // Getting the current system date
        LocalDate TODAY = LocalDate.now();
        int CURRENT_YEAR = TODAY.getYear();
        int CURRENT_MONTH = TODAY.getMonthValue();
        int CURRENT_DAY = TODAY.getDayOfMonth();

        // Convert both dates into approximate total days
        int dueTotal = (dueYear * 365) + (dueMonth * 30) + dueDay;
        int CurrentTotal = (CURRENT_YEAR * 365) + (CURRENT_MONTH * 30) + CURRENT_DAY;

        // calculating the overdue days by
        int Overduedays = CurrentTotal - dueTotal;
        if (Overduedays < 0)
            Overduedays = 0;
        // returning the number of ovedue days
        return Overduedays;

    }

    // Clear the console screen method
    public static void clearConsole() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        final String os = System.getProperty("os.name");
        try {
            if (os.contains("Linux"))
                System.out.print("\033\143");
            else if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
