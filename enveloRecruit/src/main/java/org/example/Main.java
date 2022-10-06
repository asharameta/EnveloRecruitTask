package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        //zadanie 1
        //1)a)W jakim celu używa się klas abstrakcyjnych, a w jakim interfejsów?
        //klasa może implementować kilka interfejsów jednocześnie, ale dziedziczyć tylko z jednej klasy
        //Klasy abstrakcyjne są używane przy budowaniu hierarchii podobnych klas.
        //W tym przypadku dziedziczenie z klasy abstrakcyjnej, która implementuje domyślne zachowanie obiektu, może być przydatne, poniewaz pozwala uniknąc pisania powtarzalnego kodu.
        //Interfejsy pozwalają na tworzenie struktur typów bez hierarchii.
        //Implementując interfejs, rozszerza własną funkcjonalność.
        //Interfejsy mogą być implementowane przez klasy, które nie są ze sobą powiązane.

        //1)b)Czym różni się tablica od listy liniowej?
        //Różni się strukturą, tablica przechowuje elementy jeden po jednym(po kolei) i odwolanie odbywa się za pomocą indeksów. W liscie troche inaczej,
        //każdy element ma w sobie link do następnego elementu oraz poprzedniego, żeby znależc potrzebny element, musi przejsc przez całą liste
        //ale w przypadku jezeli element będzie pierwszym lub ostatnim w liscie, to w takim razie od razu można otrzymać szukany element ponieważ on już zna gdzie są.
        //tablicy lepiej użyc kiedy znasz konkretny rozmiar, bo potem będzie sporo roboty aby rozszerzyć tablice, w liscie to łatwiej, wystarczy tylko wskazac linki i tyle.


        //zadanie 2
        int[] numbers = {10,9,8,7,6,5,4,3,2,1};
        System.out.println(search(numbers, 12));

        System.out.println();

        //zadanie 3
        Scanner scan = new Scanner(System.in);
        boolean stop=true;

        while(stop) {
            JSONParser jParser = new JSONParser();
            Response response = RestAssured.get("https://api.kanye.rest/").andReturn();
            JSONObject object = (JSONObject) jParser.parse(response.asString());

            saveQuote(object.get("quote").toString());
            System.out.println("\""+object.get("quote")+"\""+" - Kanye West");
            System.out.println();
            System.out.println("Jezeli chcesz nastepny cytat Kanye, napisz \"next\" lub cokolwiek zeby skonczyc: ");
            if(!scan.next().equals("next")){
                stop=false;
            }
        }
        scan.close();
    }
    private static boolean search(int[] numbers, int x){
        //zlozonosc obliczeniowa O(log n)
        int left = 0;
        int right = numbers.length-1;

        while(left<=right){
            int mid = left + (right - left) / 2;
            int curr = numbers[mid];
            if(curr==x) {System.out.println("if: "+curr); return true;}
            else if(curr<x)right=mid-1;
            else left=mid+1;
        }
        return false;
    }

    private static void saveQuote(String quote){
        try{
            List<String> kanyeQuots = Files.lines(Path.of("KanyeQuots.txt")).toList();
            boolean inFile = kanyeQuots.stream().anyMatch(p->p.equalsIgnoreCase(quote));
            if(!inFile) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("KanyeQuots.txt", true));
                bw.write(quote + "\n");
                bw.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}