import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;

class ResizableArray<T>{
    private T[] elements;
    public ResizableArray() {
        this.elements = (T[]) new Object[0];
    }

    public T[] getElements() {
        return elements;
    }

    public void addElement(T element){
        this.elements=Arrays.copyOf(elements,elements.length+1);
        this.elements[elements.length-1]=element;
    }
    public int returnPosition(T element){
        int position=0;
        for (int i=0;i<elements.length;i++){
            if (elements[i].equals(element)){
                position = i+1;
                return position;
            }
        }
        return -1;
        //12345678  position=3 treba da se zeme od 0 position-1 i od position+1 do kraj
    }
    public boolean removeElement(T element){
        int position=returnPosition(element);
        if(position==-1) return false;
        if (this.getElements().length==1){
            elements = (T[]) new Object[0];
            return true;
        }
        T[] first=Arrays.copyOfRange(this.elements,0,position-1); //mozno da e tuka
        T[] second=Arrays.copyOfRange(this.elements,position+1,this.elements.length); //mozno da e tuka
        //how to concat first and second
        T[]result= (T[]) new Object[elements.length-1];
        System.arraycopy(first,0,result,0,first.length);
        System.arraycopy(second,0,result,first.length,second.length);
        this.elements=result;
        return true;
    }
    public boolean contains(T element){
        if (Arrays.asList(this.elements).contains(element)){
            return true;
        }
        return false;
    }
    public Object[] toArray(){
        return elements;
    }
    public boolean isEmpty(){
        if (this.elements.length==0){
            return true;
        }
        return false;
    }
    public int count(){
        return this.elements.length;
    }
    public T elementAt(int idx){
        if (idx<0 || idx>count()) throw new ArrayIndexOutOfBoundsException();
        return this.elements[idx];
    }
    public static <T> void  copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src){
        System.arraycopy(src.elements,0,dest.elements,dest.elements.length+1,src.elements.length); //mozno da e tuka
    }
}
class IntegerArray extends ResizableArray<Integer>{

    ResizableArray<Integer> integerArray;

    public IntegerArray() {
        this.integerArray = new ResizableArray<>();
    }

    public double sum(){
        return Arrays.stream(this.integerArray.getElements()).mapToDouble(i->i).sum();
    }
    public double mean(){
        return Arrays.stream(this.integerArray.getElements()).mapToDouble(i->i).average().orElse(69);
    }
    public int countNonZero(){
        return (int) Arrays.stream(this.integerArray.getElements()).filter(i->!i.equals(0)).count();
    }
    public IntegerArray distinct(){
        return (IntegerArray) Arrays.stream(this.integerArray.getElements()).distinct();
    }
    public IntegerArray increment(int offset){
       return (IntegerArray) Arrays.stream(this.integerArray.getElements()).mapToDouble(i->i+offset);
    }

}
public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if ( test == 0 ) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while ( jin.hasNextInt() ) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if ( test == 1 ) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for ( int i = 0 ; i < 4 ; ++i ) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if ( test == 2 ) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while ( jin.hasNextInt() ) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if ( a.sum() > 100 )
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if ( test == 3 ) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for ( int w = 0 ; w < 500 ; ++w ) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k =  2000;
                int t =  1000;
                for ( int i = 0 ; i < k ; ++i ) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for ( int i = 0 ; i < t ; ++i ) {
                    a.removeElement(k-i-1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
