import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class HostelMate {

    static Scanner input = new Scanner(System.in);
    static final int MaxRows = 100;
    static int NoRooms = 0;
    static String[][] rooms = new String[MaxRows][6];

    static final int MaxStudents = 200;
    static int NoStudents = 0;
    static String[][] students = new String[MaxStudents][5];

    // Allocate bed
    static final int MaxAllocation = 300;
    static int NoAllocations = 0;
    static String[][] allocations = new String[MaxAllocation][5];

    static String[][] occupancy = new String[MaxRows][20];

    public static void main(String[] args) {
        Login();

        int choice;
        do {
            choice = HomePage();

            switch (choice) {
                case 1:
                    ManageRooms();
                    break;
                case 2:
                    ManageStudents();
                    break;
                case 3:
                    AllocateBed();
                    break;
                case 4:
                    VacateBed();
                    break;
                case 5:
                    Transfers();
                    break;
                case 6:
                    ViewReports();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid choice Please enter a coice between (1-7).");

            }
        } while (choice != 7);

    }

    public static void Login() {
        String Username;
        String Password;

        System.out.println("=== HostelMate Login ===");

        while (true) {
            while (true) {

                System.out.print("Username: ");
                Username = input.nextLine();
                boolean UserNameCheck = true;

                if (Username.isEmpty() || Username.length() < 3) {
                    UserNameCheck = false;
                    System.out.println("Invalid Username.Please enter the correct Username.");
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
                if (UserNameCheck)
                    break;
            }

            // Password validation
            while (true) {
                System.out.print("Password: ");
                Password = input.nextLine();

                boolean passwordCheck = true;
                boolean hasLetter = false;
                boolean hasNumber = false;

                if (Password.isEmpty() || Password.length() < 4) {
                    passwordCheck = false;
                    System.out.println("Please enter the correct password");
                } else {
                    for (int i = 0; i < Password.length(); i++) {
                        char a = Password.charAt(i);
                        if (Character.isLetter(a))
                            hasLetter = true;
                        if (Character.isDigit(a))
                            hasNumber = true;

                        if (hasLetter && hasNumber)
                            break;
                    }
                    if (!hasLetter || !hasNumber) {
                        passwordCheck = false;
                        System.out.println("Invalid password.Please enter the correct password");

                    }
                }

                if (passwordCheck)
                    break;
            }
            // Authentication
            if (Username.equals("warden") && Password.equals("warden123")) {
                System.out.println("Login successful. Welcome," + Username + "!");
                break;
            } else {
                System.out.println("Invalid credentials.Try again!\n");
            }
        }

    }

    public static int HomePage() {

        clearConsole();

        System.out.println("""

                === HostelMate ===

                1) Manage Rooms
                2) Manage Students
                3) Allocate Bed
                4) Vacate Bed
                5) Transfers
                6) View Reports
                7) Exit
                         """);

        int choice = -1;
        while (true) {
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(input.nextLine());
                if (choice >= 1 && choice <= 7)
                    break;
                else
                    System.out.println("Invalid choice.Please enter a choice between (1-7)");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.Enter a number ");
            }
        }
        return choice;
    }

    public static void ManageRooms() {

        System.out.println("""

                === ManageRooms ===

                1) Add Room
                2) Update Room
                3) Delete Room
                4) Search Room
                5) View All Rooms
                """);

        System.out.print("Enter your choice(1-5): ");
        int manageChoice = input.nextInt();
        input.nextLine();

        if (manageChoice == 1) {
            System.out.println("\nAdd Room\n");

            System.out.print("Room ID : ");
            String RoomId = input.nextLine();

            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    System.out.println("Room Id already exists");
                    return;
                }
            }

            rooms[NoRooms][0] = RoomId;

            int Floor;
            System.out.print("Floor : ");
            try {
                Floor = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Floor number!");
                return;
            }
            rooms[NoRooms][1] = Integer.toString(Floor);

            int RoomNo = 0;
            System.out.print("Room No : ");
            try {
                RoomNo = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Room number!");
                return;
            }
            rooms[NoRooms][2] = Integer.toString(RoomNo);

            int Capacity;
            System.out.print("Capacity : ");

            try {
                Capacity = Integer.parseInt(input.nextLine());
                if (Capacity <= 0) {
                    System.out.println("Invalid Capacity ! Must be greater than zero");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Capacity input!");
                return;
            }
            rooms[NoRooms][3] = Integer.toString(Capacity);

            double fee;
            System.out.print("Fee/Day(LKR) : ");
            try {
                fee = Double.parseDouble(input.nextLine());
                if (fee < 0) {
                    System.out.println("Invalid fee ! must be greater thatn or equals zero");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Fee input!");
                return;
            }

            rooms[NoRooms][4] = String.format("%.2f", fee);
            rooms[NoRooms][5] = Integer.toString(Capacity);

            // Initialize occupancy grid for this room (all the beds empty initialy)
            for (int i = 0; i < Capacity; i++) {
                occupancy[NoRooms][i] = "EMPTY";
            }

            System.out.println("Room added. Available beds: " + rooms[NoRooms][5]);

            NoRooms++;

        } else if (manageChoice == 2) {

            System.out.println("\nUpdate Room\n");

            System.out.print("Room ID: ");
            String RoomId = input.nextLine();

            int index = -1;
            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    index = i;
                    break;
                }

            }

            if (index == -1) {
                System.out.println("Room not Found.Please enter a correct RoomId");
                return;
            }

            int CurrentOcup = Integer.parseInt(rooms[index][3]) - Integer.parseInt(rooms[index][5]);

            System.out.print("New Capacity (or - to skip): ");
            String CapInput = input.nextLine();

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

            System.out.print("New Fee/Day (or - to skip): ");
            String FeeInput = input.nextLine();

            if (!FeeInput.equals("-")) {
                double NewFee = Double.parseDouble(FeeInput);
                rooms[index][4] = Double.toString(NewFee);
            }

            System.out.println("Updated: " + rooms[index][0] + " | Floor=" + rooms[index][1] + " | RoomNo="
                    + rooms[index][2] + " | Capacity=" + rooms[index][3] + " | Fee/Day=" + rooms[index][4] + " | Avail="
                    + rooms[index][5]);

        } else if (manageChoice == 3) {

            System.out.println("\nDelete Room\n");

            System.out.print("Room ID: ");
            String RoomId = input.nextLine();

            int index = -1;
            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    index = i;
                    break;
                }

            }

            if (index == -1) {
                System.out.println("Room not Found.Please enter a correct RoomId");
                return;
            }

            int FullCapacity = 0, available = 0;

            try {
                FullCapacity = Integer.parseInt(rooms[index][3]);
                available = Integer.parseInt(rooms[index][5]);
            } catch (NumberFormatException e) {
                System.out.println("Room Data corrupted");
                return;
            }

            if (available != FullCapacity) {
                System.out.println("Cannot Delete! There are ACTIVE ALLOCATIONS for this room");
                return;
            } else {
                for (int i = index; i < NoRooms - 1; i++) {
                    rooms[i] = rooms[i + 1];
                }
                rooms[NoRooms - 1] = new String[6];

            }
            NoRooms--;
            System.out.println("Deleted successfully.");

        } else if (manageChoice == 4) {

            System.out.println("\nSearch Room\n");

            System.out.print("Room ID: ");
            String RoomId = input.nextLine();

            int index = -1;
            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    index = i;
                    System.out.println("Found\n");

                    System.out.println("ID\tFloor\tNo\tCap\tAvail\tFee/Day\n");
                    System.out.println("--------------------------------------\n");

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

            if (index == -1) {
                System.out.println("Room not Found.Please enter a correct RoomId");
                return;
            }

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

        } else {
            System.out.println("Invalid input.Please enter a choice(1-5)");

        }

    }

    public static void ManageStudents() {

        String studentId;
        int index;
        String contactNo;
        String emailAddress;
        String status = "ACTIVE";

        try {
            System.out.println("""
                    === ManageStudents ===

                    1) Add Student
                    2) Update Student
                    3) Delete Student
                    4) Search Student
                    5) View All Student
                    """);

            System.out.print("Enter your choice(1-5): ");
            int manageChoice = input.nextInt();
            input.nextLine();

            switch (manageChoice) {
                case 1:
                    System.out.println("\nAdd Student\n");

                    System.out.print("Student ID : ");
                    studentId = input.next();
                    input.nextLine();

                    // Validating studentId
                    // Each row represent a student record
                    // Condition checks until the NoStudents rather than the MaxStudents
                    for (int i = 0; i < NoStudents; i++) {
                        // In inner arrays, 0th element represents the studentId
                        if (students[i][0].equals(studentId)) {
                            System.out.println("Student Id already exists");
                            return;
                        }
                    }

                    // Getting user input and validating

                    System.out.print("Name       : ");
                    String studentName = input.nextLine();

                    System.out.print("Contact    : ");
                    contactNo = input.next();
                    input.nextLine();
                    // If the number is invalid then redirect to the main menu
                    if (invalidContact(contactNo)) {
                        System.out.println();
                        return;
                    }

                    System.out.print("Email      : ");
                    emailAddress = input.nextLine();
                    if (invalidEmail(emailAddress)) {
                        return;

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

                    System.out.println("\nUpdate Student\n");

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
                        return;
                    }

                    System.out.print("New Contact (or - to skip): ");
                    contactNo = input.next();
                    input.nextLine();
                    if (!contactNo.equals("-")) {
                        // If the number is invalid then redirect to the main menu
                        if (invalidContact(contactNo)) {
                            System.out.println();
                            return;
                        } else {
                            students[index][2] = contactNo;
                        }
                    }

                    System.out.print("New Email (or - to skip)  : ");
                    emailAddress = input.nextLine();
                    if (!emailAddress.equals("-")) {
                        if (invalidEmail(emailAddress)) {
                            return;
                        }
                        students[index][3] = emailAddress;

                    }

                    System.out.println("Updated: " +
                            students[index][0] + " | " +
                            students[index][1] + " | " +
                            students[index][2] + " | " +
                            students[index][3] + " | " +
                            students[index][4] + " | ");

                    break;

                case 3:

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
                        return;
                    }

                    // If the student is active then cannot remove him
                    if (students[index][4].equals("ACTIVE")) {
                        System.out
                                .println("Cannot Delete! Student have ACTIVE ALLOCATIONS.Marking as INACTIVE instead.");
                        students[index][4] = "INACTIVE";
                        return;
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
                            System.out.println("----------------------------------------------------------------\n");

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
                        return;
                    }

                    break;

                case 5:

                    System.out.println("All Students");
                    System.out.printf("%-10s %-10s %-10s %-20s %-10s\n", "ID", "Name", "Contact", "Email", "Status");
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

                default:
                    System.out.println("Invalid input. Please enter a choice(1-5)");

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type.Please enter the correct value.");
            input.nextLine();// clear invalid input
        } catch (Exception e) {
            System.out.println("Error detected : " + e.getMessage());
        }

    }

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

        LocalDate checkDate = LocalDate.now(); // using current data as check in date
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
        System.out.println("\n\tAllocated: " + studentId + " -> Room " + roomId + "Bed " + bedIndex);
        System.out.println("\nAvailable beds: (" + roomId + "): " + rooms[roomindex][5]);
    }

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

        try {
            bedIndex = Integer.parseInt(allocations[allocateIndex][2]);
        } catch (NumberFormatException e) {
            System.out.println("Data error : invalid numeric value");
            return;
        }

        occupancy[roomindex][bedIndex] = "EMPTY";

        for (int i = allocateIndex; i < NoAllocations - 1; i++) {
            allocations[i] = allocations[i + 1];
        }
        allocations[NoAllocations - 1] = new String[5];
        NoAllocations--;

        int availableBeds = Integer.parseInt(rooms[roomindex][5]) + 1;
        rooms[roomindex][5] = Integer.toString(availableBeds);

        // Printing the result
        System.out.println("\nOverdue days: " + Overduedays + "| Fee/Day: " + OneDayFee + "| Fine: " + fine);
        System.out.println("\nCheckout completed. Bed freed. Available beds (" + roomId + ") : " + availableBeds);

    }

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
        System.out.println("\t Transfer Date: " + transferDate); // optional print statement to display
                                                                 // the transfer date

    }

    public static void ViewReports() {

        System.out.println("\nOccupancy Grid (rooms x beds)");
        System.out.println("\n\tRoomRow Beds ");
        System.out.println("\t--------------------------------------------\n");

        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][3] == null)
                continue;
            int cap = Integer.parseInt(rooms[i][3]);
            System.out.println(i + " [");
            for (int x = 0; x < cap; x++) {
                System.out.print(occupancy[i][x]);
                if (x < cap - 1)
                    System.out.println(", ");
            }
            System.out.println("]");
        }
        System.out.println();

        // Vacant Beds by Floor
        System.out.println("\n\nVacant Beds by Floor");
        System.out.println("\n\tFloor TotalRooms TotalBeds Occupied Vacant");
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
        System.out.println("\n\tRoom Count Students");
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
        System.out.println("\n\tStudent Room DaysOverdue Fee/Day EstimatedFine  ");
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
        System.out.printf("\n\t$ feePerDay for currently occupied beds = " + totalRevenue + "%.2f LKR");

    }

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

    // Supporting methods
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

        String[] parts = StrDueDate.split("-");
        int dueYear = Integer.parseInt(parts[0]);
        int dueMonth = Integer.parseInt(parts[1]);
        int dueDay = Integer.parseInt(parts[2]);

        LocalDate TODAY = LocalDate.now();
        int CURRENT_YEAR = TODAY.getYear();
        int CURRENT_MONTH = TODAY.getMonthValue();
        int CURRENT_DAY = TODAY.getDayOfMonth();

        int dueTotal = (dueYear * 365) + (dueMonth * 30) + dueDay;
        int CurrentTotal = (CURRENT_YEAR * 365) + (CURRENT_MONTH * 30) + CURRENT_DAY;

        // calculating the overdue days by
        int Overduedays = CurrentTotal - dueTotal;
        if (Overduedays < 0)
            Overduedays = 0;
        return Overduedays;

    }

    // Clear the console screen method
    public static void clearConsole() {
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
