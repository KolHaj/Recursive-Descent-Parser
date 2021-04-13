# Recursive Descent Parser - GUI Creation

The application takes input parameters to create a GUI calculator and nest panel from the resources' folder that is created by reading a 
text input file. The process starts with reading of the file to add to the stack, and then interpreting what was in the stack to set 
the parameters of the GUI. The stack will store each line backwards, making it impossible for the program to look for in recursive descending 
order for each particular string associated with the GUI parameters. Using a method to reverse the stack order so that I could 
evaluate it in the intended order. To conform to the recursive descent parsing, it was imperative that the program read each string 
in the stack in chronological order since backtracking is not possible in this format.

## Getting Started

The project is built using Java so using any IDE that supports Java will work and can be used to create a .jar file to run in the desktop. 
For the application to work you will need to use or create your own .txt file that is located in resources folder will provide the input data for GUI creation.

### Prerequisites

Java IDE

### Installing

Clone the project into your folder, build the project and run once you have added the location of the .txt files. 

## Running the tests

No current unit tests exist, will look into adding in the future.

## Built With

* [Java](https://www.java.com/en/) - Main Language
* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

Contact me if you would like to contribute.

## Versioning

Version 1.0

## Authors

* **Kolger Hajati** - *Initial work* - [KolHaj](https://github.com/KolHaj)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
