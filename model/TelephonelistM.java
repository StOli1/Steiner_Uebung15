package model;

import java.io.*;
import java.util.ArrayList;

/**
 * @author: Oliver Steiner
 * @version: 1, 24.02.2021
 */

public class TelephonelistM {

    public ArrayList<Person> personList = new ArrayList<>();
    Person person;

    public void removePerson(int index){
            personList.remove(index);
    }

    public void loadFromFile(){
        personList.clear();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("TelephoneList.csv")))
        {
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] rows = line.split(",");
                String rowName = rows[0];
                String rowAddress = rows[1];
                String rowPhoneNr = rows[2];

                Person row123 = new Person(rowName, rowAddress, rowPhoneNr);
                personList.add(row123);
            }
        }
        catch (IOException exception)
        {
            System.out.println("Something went wrong");
        }

    }

    public boolean saveToFile(){
        boolean saved = false;
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("TelephoneList.csv")))
        {
            for (int i = 0; i < personList.size(); i++) {
                bufferedWriter.write(String.valueOf(personList.get(i).getName()));
                bufferedWriter.write(",");
                bufferedWriter.write(String.valueOf(personList.get(i).getAddress()));
                bufferedWriter.write(",");
                bufferedWriter.write(personList.get(i).getPhoneNr());
                bufferedWriter.newLine();

                saved = true;
            }
        }
        catch (IOException ex)
        {
            System.out.println("Something went wrong");
        }
        return saved;
    }
}
