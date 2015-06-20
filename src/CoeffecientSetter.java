import java.util.*;

/**
 * Created by goatie on 16/06/15.
 */
public class CoeffecientSetter{

    static int number_of_variables=2;
    static Map<String, List>  map = new HashMap<String, List>();
    static String[] variable_names =    {"a","b","c","d","e","f","g","h","i","j","k", "l","m",
                                         "n","o","p","q","r","s","t","u","v","w","x","y","z"};
    static List<String> rows = new ArrayList<>();
    static int pivotColumn = 0;

    public static void createTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Number of Variables : ");
        number_of_variables = scanner.nextInt();
        int i=0;
        /**the extra one is for the object function,
         * since the object function become the first
         **/
        while(i<number_of_variables+1){
            if(i==0){
                System.out.println("Objective Function");
            }
            else{
                System.out.println("Subject Function "+ i);
            }

            int j = 0;
            List<Double> arrayList = new ArrayList<>();
            arrayList.clear();

            while (j<=number_of_variables*2+1) {//.*2 for the slack variables
                if (j < number_of_variables){//if it is coeffiecent of x and y or z
                    System.out.println("Coefficient of " + variable_names[j] +": ");
                    if(i==0) {
                        arrayList.add(j, scanner.nextDouble() * -1);
                    }
                    else {
                        arrayList.add(j, scanner.nextDouble());
                    }
                }
                else if (j < number_of_variables*2+1){//slack variables
                    if (i !=0) {//if it is not objective function
                        if (number_of_variables == (j - i + 1)) //when its time for the slack variable to be 1
                            arrayList.add(j, 1.0);
                        else //when its time for the slack variable to be 0
                            arrayList.add(j, 0.0);
                    }
                    else{//when it is objective function
                        if(j == number_of_variables*2)
                            arrayList.add(j, 1.0);
                        else
                            arrayList.add(j, 0.0);
                    }
                }
                else {//the value on the other side of the equal sign
                    if(i != 0) {//if it is not objective function
                        System.out.println(" Equals : ");
                        arrayList.add(j, scanner.nextDouble());
                    }
                    else{//objective function is equals 0
                        arrayList.add(j,0.0);
                    }
                }
                j++;
            }
            map.put("row"+i,arrayList);
            rows.add("row"+i);
            i++;
        }
    }


    public static Integer findPivotColumn(){
        int pColumn = 0;
        List list = map.get("row0");
        pColumn = list.indexOf(Collections.min(list));
        System.out.println(pColumn);
        return pColumn;
    }
    public static Integer findPivotRow(){
        pivotColumn = findPivotColumn();
        ArrayList<Double> pivots  = new ArrayList<>();
        pivots.clear();
        for(int i=1;i<map.size();i++){
            List list = map.get("row"+i);
            Double pivot = (Double) list.get(list.size()-1)/ (Double) list.get(pivotColumn);
//            System.out.println(pivot);
            pivots.add(pivot);
        }

        int pivot_row_number = pivots.indexOf(Collections.min(pivots))+1;//index of lowest number
//remember the row numbers start from 1 since objective function has carried the first row

        System.out.println("Pivot" + pivot_row_number);
        return  pivot_row_number;
    }

    public static void usePivot(){
        String rowId = "row"+findPivotRow();
        List<Double> list = map.get(rowId);
        List<Double> temp = new ArrayList<>();

        for (double l : list){
            temp.add(l/list.get(pivotColumn));//divide each value on the row by the pivot.
        }
        map.put(rowId, temp);
        List<Double> new_list = map.get(rowId);//new pivot row after update.

        for(String r : rows){

            if(!r.equals(rowId)){//when it is not on pivot row
                List<Double> list1 = map.get(r);
                List<Double> temp2 = new ArrayList<>();
                int i = 0;
                for (double l : list1){
                    temp2.add(l - list1.get(pivotColumn)*new_list.get(i));//R2 - R1*pivot
                    //System.out.println(l +"-"+ new_list.get(0)+"*"+new_list.get(i));
                    i++;
                }
                map.put(r, temp2);

            }
        }
    }

    public static void showTable(){

        for(int i=0;i<map.size();i++){
            List list = map.get("row"+i);
            for(int j=0;j<list.size();j++){
                System.out.print((Math.round((double) list.get(j) * 100.0 ) / 100.0) + " ");
            }
            System.out.print("\n");
        }
    }

    public static void showAnswer(){
        for(int i=0;i<map.size();i++){
            List list = map.get("row"+i);
            if(i==0)
                System.out.println("Max is " +list.get(list.size()-1));
                else
                System.out.println(variable_names[i-1 ]+" is "+list.get(list.size()-1));
        }
    }

}