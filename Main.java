//Name: Ethan Parab
//Class Period: 5

//Instructions: Read the README.text file and complete the exercise.

import java.util.Scanner;

class Main {
  //create scanner object
  private static final Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    //ask for the users name
    String username;
    while (true) {
      try {
        username = input("Name: ");
        if (username.equals("")) {
          throw new IllegalArgumentException();
        }
        break;
      } catch (IllegalArgumentException e) {
        System.out.println("You must enter your name. Try again.");
      }
    }

    System.out.println("\nHello, " + username + ". Welcome to the character creator. Here, you can create any character you want! Let's go!");
    //ask which character they would like to create
    System.out.print("\nWhat character would you like to create? Options are:\n1. Elf\n2. Wizard\n3. Warrior\n(Enter 1, 2, or 3): ");

    int characterChoice;

    // a try loop was the simplest way I could think of to deal with bad input here
    // the basic idea here is to get a value in the set {1, 2, 3}
    // if the user does not do this, they will get as many tries as it takes for them to get it right
    while (true) {
      try {
        characterChoice = Integer.parseInt(input.nextLine());

        // Integer.valueOf() does not account for the fact [1,3] is the only interval I can take, so I raise this exception to do that
        if (characterChoice < 1 || characterChoice > 3) {
          throw new IllegalArgumentException();
        }

        break;
      } catch (RuntimeException e) {
        System.out.print("You didn't enter a valid input. It's okay, just try again and make sure you type in 1, 2, or 3: ");
      }
    }

    // the GameCharacter class contains a template for all behaviors and attributes for a character. Elf, Warrior, and Wizard are all subclasses of it
    GameCharacter character;

    //ask user for the name of the character and their attributes
    //make sure to call the correct constructor and apply the variables
    // in the proper order.
    System.out.println("\nWhat are the name and attributes of your character?");

    String name;
    int strength;
    int brain;
    int stealth;

    // name input checking
    while (true) {
      try {
        name = input("Name: ");
        if (name.equals("")) {
          throw new IllegalArgumentException();
        }
        break;
      } catch (IllegalArgumentException e) {
        System.out.println("You must enter a name. Try again.");
      }
    }

    System.out.println("\nFor the following attributes, please input an integer in range [0,100].");

    // strength input checking
    while (true) {
      try {
        strength = checkNum(Integer.parseInt(input("Strength: ")));
        break;
      } catch (IllegalArgumentException e) {
        System.out.print("You didn't enter a valid input. It's okay, just try again and make sure you type in an integer in range [0, 100]. \n");
      }
    }

    // brain input checking
    while (true) {
      try {
        brain = checkNum(Integer.parseInt(input("Brain: ")));
        break;
      } catch (IllegalArgumentException e) {
        System.out.print("You didn't enter a valid input. It's okay, just try again and make sure you type in an integer in range [0, 100]. \n");
      }
    }

    // stealth input checking
    while (true) {
      try {
        stealth = checkNum(Integer.parseInt(input("Stealth: ")));
        break;
      } catch (IllegalArgumentException e) {
        System.out.print("You didn't enter a valid input. It's okay, just try again and make sure you type in an integer in range [0, 100]. \n");
      }
    }

    // initialize the object based on prior input
    if (characterChoice == 1) {
      character = new Elf(name, strength, brain, stealth);
    } else if (characterChoice == 2) {
      character = new Wizard(name, strength, brain, stealth);
    } else if (characterChoice == 3) {
      character = new Warrior(name, strength, brain, stealth);
    } else {
      // theoretically, this is impossible, but it's better to have a failsafe
      character = new GameCharacter("", 0, 0, 0, 0, "");
      System.exit(1);
    }

    // print out the details of the new character
    System.out.println("\n" + character);
    System.out.println(character.howMuchHealth() + "\n");


    // enter quasi-CLI interface
    System.out.println("Now you can control your character. Acceptable commands are as follows:\n" +
            "\"heal {num}\" - increases character's health, {num} must be an integer in range [0, 100]\n" +
            "\"hurt {num}\" - decreases character's health, {num} must be an integer in range [0, 100]\n" +
            "\"output\" - prints the details of the character\n" +
            "\"exit\" - exits the program\n" +
            "Note: Health will stay within range [0, 100]. Attempting to change health beyond this range will result in the health value being overridden to the closest value in range [0, 100].\n");
    while (true) {
      command(character);
    }
  }

  // this makes it easier for me to accept input
  // if I want to dig into the specifics of input (i.e. character choice loop) will just do use the input object instead
  private static String input(String prompt) {
    System.out.print(prompt);
    return input.nextLine();
  }

  private static void command(GameCharacter character) {
    try {
      String command = input("> ");
      int numValue;
      if (command.startsWith("hurt ")) {
        numValue = Integer.parseInt(command.substring(5));
        checkNum(numValue);
        character.changeHealth(numValue * -1);
        System.out.println(character.howMuchHealth());
      } else if (command.startsWith("heal ")) {
        numValue = Integer.parseInt(command.substring(5));
        checkNum(numValue);
        character.changeHealth(numValue);
        System.out.println(character.howMuchHealth());
      } else if (command.startsWith("output")) {
        System.out.println(character);
        System.out.println(character.howMuchHealth());
      } else if (command.startsWith("exit")) {
        System.out.println("Goodbye!");
        System.exit(0);
      } else {
        throw new IllegalArgumentException();
      }
    } catch (NumberFormatException e) {
      System.out.println("Try again, make sure if you use one of the health-changing functions, you type an integer in range [0, 100].");
    } catch (IllegalArgumentException e) {
      System.out.println("Try again, make sure you use \"heal\", \"hurt\", \"output\", or \"exit\" and follow the first two with a space and an integer in range [0, 100].");
    }
  }

  // makes sure the number is in range [0, 100]
  // it returns the int just for convenience
  private static int checkNum(int num) {
    if (num > 100 || num < 0) {
      throw new NumberFormatException();
    }
    return num;
  }
}