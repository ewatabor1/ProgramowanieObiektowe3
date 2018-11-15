package main.java.lab1.dataFrame;

import main.java.lab1.groupBy.GroupBy;
import main.java.lab1.groupBy.Applyable;
import main.java.lab1.groupBy.Operation;
import main.java.lab1.myExceptions.DifferentSizedColumns;
import main.java.lab1.myExceptions.WrongTypeInColumn;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static main.java.lab1.groupBy.Operation.*;

public class DataFrame{
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
    public DataFrame(String address, Class<? extends Value>[] types, boolean header)
            throws IOException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, WrongTypeInColumn {

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
        List<Constructor<? extends Value>> constructors = new ArrayList<>(types.length);
        for (int i=0;i<types.length;i++){
            constructors.add(types[i].getConstructor(String.class));
        }

        //while ((strLine = br.readLine()) != null){
        for (int b=0; b<50;b++) {
            strLine = br.readLine();
            String[] str = strLine.split(",");
            for (int i = 0; i<str.length; i++){
                values[i] = constructors.get(i).newInstance(str[i]);
            }
            addRow(values);

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

    public void switchColumn (int index,Column column){
        dataF.set(index,column);
    }

    /**
     * @return aktualny rozmiar DataFrame (długość kolumny)
     */

    public int size(){
        if (dataF.isEmpty()) return 0;
        else return dataF.get(0).size();
    }

    /**
     * @return aktualną szerokość DataFrame (ilość kolumn)
     */
    public int width(){ return dataF.size();}

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var s: dataF) {
            stringBuilder.append(s.getName()).append("\t\t");
        }
        stringBuilder.append('\n');

        for (int i = 0; i < size(); i++) {
            for (var value: dataF) {
                stringBuilder.append(value.elementAtIndex(i)).append('\t');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
    public Value[] getRowData (int index){
        return dataF.stream().map(column -> column.elementAtIndex(index)).toArray(Value[]::new);
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
    public DataFrame iloc(int i) throws WrongTypeInColumn{
        DataFrame output = new DataFrame(getColumnsNames(), getColumnsTypes());

        int k = 0;
        if(i >= 0 && i < size()) {
            for (Column c: output.dataF) {
                if(!c.checkElement(dataF.get(k).elementAtIndex(i))){
                    throw new WrongTypeInColumn(c.getName(),i);
                }
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
    public DataFrame iloc(int from, int to) throws WrongTypeInColumn{
        DataFrame result = new DataFrame();
        if (from<0) from=0;
        if (to>=this.size()) to=this.size()-1;
        for (Column a: dataF){
            Column column = new Column(a.getName(),a.getType());
            for (int j=from;j<=to;j++){
                if(!column.checkElement(a.elementAtIndex(j))){
                    throw new WrongTypeInColumn(column.getName(),j);
                }
            }
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

    public boolean addRow(Value...values) throws WrongTypeInColumn {
        if(dataF.size()!=values.length){
            System.out.print("Nie podano wszystkich argumentów!");
            return false;
        }
        for (int i=0;i<values.length;i++){
            if(!dataF.get(i).checkElement(values[i])) {
                int a=dataF.get(0).size();
                if(i==0){
                    a=-1;
                    if(a<0) a=0;
                }
                throw new WrongTypeInColumn(get(i).getName(),a);
            }
        }
        for (int j=0;j<values.length;j++){
            dataF.get(j).addElement(values[j]);
        }
        return true;
    }
    private boolean addRow(List<Value> values) throws WrongTypeInColumn{
        for (int j=0;j<dataF.size();j++){
            if(!dataF.get(j).checkElement(values.get(j))){
                throw new WrongTypeInColumn(dataF.get(j).getName(),dataF.size());
            }
        }
        for (int i=0; i<dataF.size(); i++) {
            dataF.get(i).addElement(values.get(i));
        }
        return true;
    }


    public class DataFrameGroupBy implements GroupBy {

        private HashMap<List<Value>, DataFrame> map;
        private List<String> colNames;

        public DataFrameGroupBy(HashMap<List<Value>, DataFrame> map, String[] names) {
            this.map = map;
            this.colNames = Arrays.asList(names);
        }
        private DataFrame operation(Operation operation, boolean flag)throws WrongTypeInColumn{
            DataFrame dataFrame;
            if (flag) {
                List<Class<? extends Value>> classList = new ArrayList<>(List.of(getColumnsTypes()));
                ArrayList<String> nameList = new ArrayList<>(List.of(getColumnsNames()));
                List<Integer> namesToRemove = new ArrayList<>();
                for (int i = 0; i < classList.size(); i++) {
                    if ((classList.get(i).equals(StringValue.class) || classList.get(i).equals(DateTimeValue.class)) && !colNames.contains(nameList.get(i))) {
                        namesToRemove.add(i);
                    }
                }
                for (int i = namesToRemove.size() - 1; i >= 0; i--) {
                    nameList.remove((int)namesToRemove.get(i));
                    classList.remove((int)namesToRemove.get(i));
                }
                String[] names =  nameList.toArray(new String[0]);
                Class[] classes = classList.toArray(new Class [0]);
                dataFrame = new DataFrame(names,classes);
            }
            else dataFrame = new DataFrame(getColumnsNames(),getColumnsTypes());

            for (var values: map.keySet()) {
                List<Value> toAdd = new ArrayList<>(values);
                DataFrame df = map.get(values);

                for (var column: df.dataF) {
                    if(!colNames.contains(column.getName())) {
                        if(flag && !(column.getType().equals(DateTimeValue.class) || column.getType().equals(StringValue.class))) {
                            toAdd.add(column.calculate(operation));
                        } else if(!flag) {
                            toAdd.add(column.calculate(operation));
                        }
                    }
                }
                dataFrame.addRow(toAdd);
            }
            return dataFrame;
        }

        @Override
        public DataFrame max() throws WrongTypeInColumn {
            return operation(Operation.MAX, false);
        }

        @Override
        public DataFrame min() throws WrongTypeInColumn {
            return operation(Operation.MIN,false);
        }

        @Override
        public DataFrame mean() throws WrongTypeInColumn {
            return operation(Operation.MEAN,true);
        }

        @Override
        public DataFrame std()throws WrongTypeInColumn {
            return operation(Operation.STD,true);
        }

        @Override
        public DataFrame sum()throws WrongTypeInColumn {
            return operation(Operation.SUM,true);
        }

        @Override
        public DataFrame var()throws WrongTypeInColumn {
            return operation(Operation.VAR,true);
        }

        @Override
        public DataFrame apply(Applyable applyable) {
            return applyable.apply(DataFrame.this);
        }
    }
    public DataFrameGroupBy groupBy(String... colname) throws WrongTypeInColumn{
        HashMap<List<Value>, DataFrame> map = new HashMap<>(colname.length);
        List<Column> dataF = Arrays.stream(colname).map(this::get).collect(Collectors.toList());

        for (int i = 0; i < size(); i++) {
            List<Value> values = new ArrayList<>(dataF.size());
            for (var column: dataF) {
                values.add(column.elementAtIndex(i));
            }

            if(!map.containsKey(values)) {
                map.put(values, iloc(i));
            } else {
                map.get(values).addRow(getRowData(i));
            }
        }
        return new DataFrameGroupBy(map,colname);
    }

    }

