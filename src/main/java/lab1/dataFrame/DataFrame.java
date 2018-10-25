package main.java.lab1.dataFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DataFrame {
    protected List<Column> dataF;

    /**
     * Konstruktor tworzący pusty DataFrame
     */
    public DataFrame(){
        dataF=new ArrayList<>();
    }

    /**
     * Konstruktor
     * @param columns - lista kolumn które mają tworzyć nowy DataFrame
     */
    public DataFrame (List<Column> columns){
        dataF=columns;
    }

    /**
     * Konatruktor tworzący DataFrame z pustymi kolumnami
     * @param names - nazwy kolumn
     * @param types - typy kolumn
     */
    public DataFrame(String[] names, Class<? extends Value>[] types) {

        dataF = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {

            if((names.length <= i)) {
                break;
            }

            if(isUnique(names[i])) {
                dataF.add(new Column(names[i], types[i]));
            }
        }
    }

    /**
     Konstruktor tworzący DataFrame czytający dane z pliku csv
     * @param address -adres pliku csv
     * @param types - typy danych w poszczególnych kolumnach
     * @param header - jeśli header==true znaczy to, że w pierwszej linijce pliku
     *      podane są nazwy kolumn
     *      jeśli header==false nazwy kolumn pobierane są na wejściu
     * @throws IOException
     */
    public DataFrame(String address, Class<? extends Value>[] types, boolean header) throws IOException {

        dataF = new ArrayList<>();
        FileInputStream fstream;
        BufferedReader br;

        fstream = new FileInputStream(address);

        if (fstream == null)
            throw new IOException("File not found!");
        else
            br = new BufferedReader(new InputStreamReader(fstream));

        String strLine=br.readLine();
        String[] separated=strLine.split(",");
        String[] names= new String[types.length];
        if (!header){
            Scanner odczyt = new Scanner(System.in);
            for (int l=0;l<types.length;l++){
                System.out.print("Podaj nazwę kolumny: ");
                names[l] = odczyt.nextLine();
            }
        }
        if (header){
            for (int m=0;m<types.length;m++){
                names[m]=separated[m];
            }
        }
        for (int i = 0; i < types.length; i++) {
            if((separated.length <= i)) {
                break;
            }
            dataF.add(new Column(names[i], types[i]));
        }
        Value[] values= new Value[dataF.size()];
        int a=0;
        while ((strLine = br.readLine()) != null){
            if(a>0 || (a==0 && header)){
                strLine=br.readLine();
                if(strLine==null) break;
                separated=strLine.split(",");
            }
            for (int i = 0; i < values.length; i++) {
                    DoubleValue value = new DoubleValue();
                    values[i] = value.create(separated[i]);
            }

            if(dataF.size()!=values.length){
                continue;
            }
            for (int j=0;j<values.length;j++){
                if (!dataF.get(j).checkElement(values[j])) continue;
            }
            for (int i=0;i<values.length;i++){
                dataF.get(i).addElement(values[i]);
            }
        }
        br.close();

    }

    /**
     * Sorawdza czy istnieje kolumna o danej nazwie
     * @param name - nazwa którą sprawdzamy
     * @return false jeśli istnieje
     */
    protected boolean isUnique(String name) {
        for(Column c : dataF) {
            if(c.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Zwraca kolumnę o danej nazwie
     * @param colname
     * @return
     */
    public Column get(String colname){
        for (Column a : dataF){
            if (a.getName().equals(colname)){
                return a;
            }
        }
        return null;
    }

    /**
     * Zwraca kolumnę o danym indeksie
     * @param index
     * @return
     */
    public Column get(int index){
        int i=0;
        for (Column a : dataF){
            if (i==index){
                return a;
            }
            i++;
        }
        return null;
    }

    /**
     * @return aktualny rozmiar DataFrame (długość kolumny)
     */

    public int size(){
        return dataF.get(0).size();
    }

    /**
     * @return aktualną szerokość DataFrame (ilość kolumn)
     */
    public int width(){ return dataF.size();}

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(Column c : dataF) {
            out.append(c.toString()).append("\n");
        }
        return out.toString();
    }
    public Value[] getRowData (int index){
        Value[] result = new Value[width()];
        int a = 0;
        if(index >= 0 && index < size()) {
            for (Column c: dataF) {
                result[a]=dataF.get(index).elementAtIndex(a++);
            }
        }
        return result;
    }

    /**
     * Zwraca kopię DataFrame podanego jako argument
     * @param cols - DataFrmae który kopiujemy
     * @param copy - jeśli true kopia jest głęboka, false - płytka
     * @return
     */
    public DataFrame get(String[] cols, boolean copy) {
        DataFrame result = new DataFrame();

        for (String s: cols) {
            for (Column c: dataF) {
                if(s.equals(c.getName())) {
                    if (copy) result.dataF.add(c.clone());
                    else result.dataF.add(c);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @return nazwa wszystkich kolumn w postaci tablicy Stringów
     */
    public String[] getColumnsNames(){
        String[] result = new String[width()];
        for(int i = 0; i < width(); i++){
            result[i] = dataF.get(i).getName();
        }
        return result;
    }

    /**
     * @return typy wszystkich kolumn
     */
    public Class<? extends Value>[] getColumnsTypes() {
        Class[] classes = new Class[dataF.size()];
        for (int i = 0; i < classes.length ; i++) {
            classes[i] = dataF.get(i).getType();
        }
        return classes;
    }

    /**
     * @param i - indeks wiersza, który nas interesuje
     * @return nowy DataFrame zawierający ten wiersz
     */
    public DataFrame iloc(int i) {
        DataFrame output = new DataFrame(getColumnsNames(), getColumnsTypes());

        int k = 0;
        if(i >= 0 && i < size()) {
            for (Column c: output.dataF) {
                c.addElement(dataF.get(k++).elementAtIndex(i));
            }
        }
        return output;
    }

    /**
     * @param from - indeks wiersza od którego zaczynamy (pierwszy wiersz ma indeks 0)
     * @param to - indeks wiersza na którym kończymy
     * @return nowy DataFrame zawierający wybrane wiersze
     */
    public DataFrame iloc(int from, int to){
        DataFrame result = new DataFrame();
        if (from<0) from=0;
        if (to>=this.size()) to=this.size()-1;
        for (Column a: dataF){
            Column column = new Column(a.getName(),a.getType());
            for (int i=from;i<=to;i++){
                column.addElement(a.elementAtIndex(i));
            }
            result.dataF.add(column);
        }
        return result;
    }

    /**
     * Dodaje wiersz do DataFrame
     * @param values - wartości które chcemy dodać
     * @return zwraca false jeśli nie udało się dodać wiersza
     */

    public boolean addRow(Value...values){
        if(dataF.size()!=values.length){
            System.out.print("Nie podano wszystkich argumentów!");
            return false;
        }
       /* for (int i=0;i<values.length;i++) {
            if (!dataF.get(i)(values[i])) return false;
        }*/
        for (int j=0;j<values.length;j++){
            dataF.get(j).addElement(values[j]);
        }
        return true;
    }

}
